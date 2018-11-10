package cn.exrick.manager.component.impl;

import cn.exrick.common.enums.OrderPayActionEnum;
import cn.exrick.common.enums.PaymentEnum;
import cn.exrick.common.utils.CurrencyStringUtils;
import cn.exrick.common.utils.CurrencyStringUtils.CurrencyFormat;
import cn.exrick.manager.component.PayPalComponent;
import cn.exrick.manager.dto.front.OrderPayAction;
import cn.exrick.manager.mapper.TransactionPaypalItemMapper;
import cn.exrick.manager.mapper.TransactionPaypalMapper;
import cn.exrick.manager.mapper.TransactionPaypalShippingAddressMapper;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.pojo.TbOrderItem;
import cn.exrick.manager.pojo.TbOrderShipping;
import cn.exrick.manager.pojo.TransactionPaypal;
import cn.exrick.manager.pojo.TransactionPaypalItem;
import cn.exrick.manager.pojo.TransactionPaypalShippingAddress;
import com.braintreepayments.http.HttpResponse;
import com.google.common.collect.Lists;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.sdk.v1.payments.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@Component
public class PayPalComponentImpl implements PayPalComponent {

    private static final String USD = "USD";// us dollar

    @Value("${payment.paypal.clientId}")
    private String payPalClientId;

    @Value("${payment.paypal.clientSecret}")
    private String payPalClientSecret;

    @Value("${payment.paypal.sandbox}")
    private boolean sandbox;

    @Value("${payment.paypal.cancelUrl}")
    private String payPalCancelUrl;

    @Value("${payment.paypal.returnUrl}")
    private String payPalReturnUrl;

    @Autowired
    private TransactionPaypalMapper transactionPaypalMapper;

    @Autowired
    private TransactionPaypalItemMapper transactionPaypalItemMapper;

    @Autowired
    private TransactionPaypalShippingAddressMapper transactionPaypalShippingAddressMapper;

    @Override
    public OrderPayAction pay(TbOrder tbOrder,
                              List<TbOrderItem> tbItems,
                              TbOrderShipping tbOrderShipping,
                              cn.exrick.manager.pojo.Transaction xmallTransaction) throws IOException {

        Payment payment = new Payment().intent("sale")
            .payer(new Payer().paymentMethod("paypal"));

        List<Item> payItems = Lists.newArrayList();
        for (TbOrderItem orderItem : tbItems) {
            Item item = new Item().currency(USD)
                .description(orderItem.getSellPoint())
                .name(orderItem.getTitle())
                .price(CurrencyStringUtils.toString(orderItem.getPrice(), CurrencyFormat.Money))
                .sku(orderItem.getSku())
                .tax("0")
                .url("");
            payItems.add(item);
        }
        ShippingAddress shippingAddress = new ShippingAddress()
            .city(tbOrderShipping.getReceiverCity())
            .countryCode(tbOrderShipping.getReceiverState())
            .line1(tbOrderShipping.getReceiverDistrict())
            .line2(tbOrderShipping.getReceiverAddress())
            .postalCode(tbOrderShipping.getReceiverZip())
            .recipientName(tbOrderShipping.getReceiverName())
            .phone(tbOrderShipping.getReceiverPhone());

        ItemList itemList = new ItemList().items(payItems)
            .shippingAddress(shippingAddress);

        Transaction transaction = new Transaction();
        transaction.amount(new Amount().total(CurrencyStringUtils.toString(tbOrder.getPayment(), CurrencyFormat.Money)))
            .itemList(itemList);

        payment.transactions(Lists.newArrayList(transaction))
            .redirectUrls(new RedirectUrls().cancelUrl(payPalCancelUrl).returnUrl(payPalReturnUrl));
        PaymentCreateRequest request = new PaymentCreateRequest()
            .requestBody(payment);

        PayPalEnvironment environment;
        if (sandbox) {
            environment = new PayPalEnvironment.Sandbox(payPalClientId, payPalClientSecret);
        } else {
            environment = new PayPalEnvironment.Live(payPalClientId, payPalClientSecret);
        }
        PayPalHttpClient client = new PayPalHttpClient(environment);
        HttpResponse<Payment> paymentResponse = client.execute(request);

        Payment paymentRes = paymentResponse.result();
        if (Objects.isNull(paymentRes)) {
            return null;
        }
        List<LinkDescriptionObject> links = paymentRes.links();
        String actionUrl = "";
        for (LinkDescriptionObject link : links) {
            if (StringUtils.equalsIgnoreCase(link.href(), "approval_url")) {
                actionUrl = link.href();
            }
        }
        savePayPalDetail(payment, xmallTransaction);
        OrderPayAction orderPayAction = new OrderPayAction();
        orderPayAction.setActionUrl(actionUrl);
        orderPayAction.setAction(OrderPayActionEnum.REDIRECT);
        orderPayAction.setPayId(paymentRes.id());
        orderPayAction.setState(payment.state());
        orderPayAction.setPaymentEnum(PaymentEnum.PAYPAL);
        return orderPayAction;
    }

    private void savePayPalDetail(Payment payment, cn.exrick.manager.pojo.Transaction transaction) {
        TransactionPaypal transactionPaypal = getTransactionPaypal(payment, transaction);
        List<TransactionPaypalItem> items = getTransactionPaypalItems(payment);
        TransactionPaypalShippingAddress address = getTransactionPaypalShippingAddress(payment);
        transactionPaypalMapper.insert(transactionPaypal);
        for (TransactionPaypalItem item : items) {
            transactionPaypalItemMapper.insert(item);
        }
        transactionPaypalShippingAddressMapper.insert(address);
    }

    private TransactionPaypal getTransactionPaypal(Payment payment, cn.exrick.manager.pojo.Transaction xmallTransaction) {
        Transaction transaction = payment.transactions().get(0);
        if (Objects.isNull(transaction)) {
            return null;
        }
        TransactionPaypal transactionPaypal = new TransactionPaypal();
        transactionPaypal.setTid(xmallTransaction.getTid());
        transactionPaypal.setCurrency(xmallTransaction.getCurrency());
        transactionPaypal.setHandlingFee(new BigDecimal(transaction.amount().details().handlingFee()));
        transactionPaypal.setInsurance(new BigDecimal(transaction.amount().details().insurance()));
        transactionPaypal.setSelfUrl(getPayPalLink(payment, SELF));
        transactionPaypal.setApprovalUrl(getPayPalLink(payment, APPROVAL_URL));
        transactionPaypal.setExecuteUrl(getPayPalLink(payment, EXECUTE));
        transactionPaypal.setTotal(new BigDecimal(transaction.amount().total()));
        transactionPaypal.setSubtotal(new BigDecimal(transaction.amount().details().subtotal()));
        transactionPaypal.setTax(new BigDecimal(transaction.amount().details().tax()));
        transactionPaypal.setShipping(new BigDecimal(transaction.amount().details().shipping()));
        return transactionPaypal;
    }

    private String getPayPalLink(Payment payment, String linkName) {
        List<LinkDescriptionObject> links = payment.links();
        for (LinkDescriptionObject link : links) {
            if (StringUtils.equalsIgnoreCase(linkName, link.rel())) {
                return link.href();
            }
        }
        return null;
    }

    private List<TransactionPaypalItem> getTransactionPaypalItems(Payment payment) {
        Transaction transaction = payment.transactions().get(0);
        ItemList itemList = transaction.itemList();
        List<Item> itemListItems = itemList.items();
        List<TransactionPaypalItem> items = Lists.newArrayListWithCapacity(itemListItems.size());
        for (Item item : itemListItems) {
            TransactionPaypalItem transactionPaypalItem = new TransactionPaypalItem();
            transactionPaypalItem.setCurrency(item.currency());
            transactionPaypalItem.setDescription(item.description());
            transactionPaypalItem.setName(item.name());
            transactionPaypalItem.setPrice(new BigDecimal(item.price()));
            transactionPaypalItem.setQuantity(Integer.parseInt(item.quantity()));
            transactionPaypalItem.setSku(item.sku());
            transactionPaypalItem.setTax(new BigDecimal(item.tax()));
            transactionPaypalItem.setTPaypalId(payment.id());
            items.add(transactionPaypalItem);
        }
        return items;
    }

    /**
     * 支付存储
     *
     * @param payment
     *
     * @return
     */
    private TransactionPaypalShippingAddress getTransactionPaypalShippingAddress(Payment payment) {
        Transaction transaction = payment.transactions().get(0);
        ItemList itemList = transaction.itemList();
        ShippingAddress paypalShippingAddress = itemList.shippingAddress();
        TransactionPaypalShippingAddress shippingAddress = new TransactionPaypalShippingAddress();
        shippingAddress.setTPaypayId(payment.id());
        shippingAddress.setRecipientName(paypalShippingAddress.recipientName());
        shippingAddress.setCity(paypalShippingAddress.city());
        shippingAddress.setCountryCode(paypalShippingAddress.countryCode());
        shippingAddress.setLine1(paypalShippingAddress.line1());
        shippingAddress.setLine2(paypalShippingAddress.line2());
        shippingAddress.setPhone(paypalShippingAddress.phone());
        shippingAddress.setPostalCode(paypalShippingAddress.postalCode());
        shippingAddress.setState(paypalShippingAddress.state());
        return shippingAddress;
    }

}

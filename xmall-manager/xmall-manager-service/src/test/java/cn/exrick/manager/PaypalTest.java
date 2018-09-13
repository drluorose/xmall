package cn.exrick.manager;

import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.sdk.v1.payments.Amount;
import com.paypal.sdk.v1.payments.Payer;
import com.paypal.sdk.v1.payments.Payment;
import com.paypal.sdk.v1.payments.PaymentCreateRequest;
import com.paypal.sdk.v1.payments.RedirectUrls;
import com.paypal.sdk.v1.payments.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class PaypalTest {
    Gson gson = new GsonBuilder().create();

    @Test
    public void testPayPay() {
        // Construct an environment with your client id and secret"
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
            "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS",
            "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL");

// Use this environment to construct a PayPalHttpClient
        PayPalHttpClient client = new PayPalHttpClient(environment);

// Construct a request object and set the desired parameters.
// In this case, a PaymentCreateRequest constructs an POST request to /v1/payments
        PaymentCreateRequest request = new PaymentCreateRequest()
            .requestBody(new Payment()
                .intent("sale")
                .payer(new Payer()
                    .paymentMethod("paypal"))
                .transactions(Arrays.asList(
                    new Transaction()
                        .amount(new Amount().total("10").currency("USD"))
                ))
                .redirectUrls(new RedirectUrls()
                    .cancelUrl("http://paypal.com/cancel")
                    .returnUrl("http://paypal.com/return")));

        try {
            // Use your client to execute a request and get a response back
            HttpResponse<Payment> paymentResponse = client.execute(request);

            // If the endpoint returns a body in its response, you can access the deserialized
            // version by calling result() on the response.
            Payment payment = paymentResponse.result();
            log.debug("Payment:{}", payment);
            log.debug("Payment:{}", gson.toJson(payment));
        } catch (IOException ioe) {
            log.error("e", ioe);
            if (ioe instanceof HttpException) {
                // Something went wrong server-side
                HttpException he = (HttpException) ioe;
                int statusCode = he.statusCode();
                String debugId = he.headers().header("PayPal-Debug-Id");
            } else {
                // Something went wrong client-side
                log.error("e", ioe);
            }
        }
    }
}

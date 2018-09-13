package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dongjiejie
 */

@AllArgsConstructor
@Getter
public enum PaymentEnum implements StdEnum {

    PAYPAL(1, "paypal", "paypay支付"),
    ALIPAY(2, "alipay", "支付宝支付"),
    WeChatPay(3, "wechat_pay", "微信支付");

    private final Integer number;

    private final String code;

    private final String name;

    public static PaymentEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static PaymentEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
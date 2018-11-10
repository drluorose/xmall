package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dongjiejie
 */

@AllArgsConstructor
@Getter
public enum OrderStatusEnum implements StdEnum {

    UNPAID(0, "unpaid", "未付款"),
    PAID(1, "paid", "已付款"),
    UNSHIPPED(2, "unshipped", "未发货"),
    SHIPPED(3, "shipped", "已发货"),
    SUCCESS(4, "success", "交易成功"),
    CLOSED(5, "closed", "交易关闭"),
    REFUNDED(6, "refunded", "已经退款");

    private final Integer number;

    private final String code;

    private final String name;

    public static OrderStatusEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static OrderStatusEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
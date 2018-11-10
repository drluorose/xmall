package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum OrderPayActionEnum implements StdEnum {

    REDIRECT(0, "redirect", "重定向"),
    QRCODE(1, "qrcode", "二维码");

    private final Integer number;

    private final String code;

    private final String name;

    public static OrderPayActionEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static OrderPayActionEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
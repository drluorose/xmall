package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dongjiejie
 */

@AllArgsConstructor
@Getter
public enum ValidStatusEnum implements StdEnum {

    INVALID(0, "invalid", "无效"),
    VALID(1, "valid", "有效");

    private final Integer number;

    private final String code;

    private final String name;

    public static ValidStatusEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static ValidStatusEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
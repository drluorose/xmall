package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum EnableStatusEnum implements StdEnum {

    DISABLED(0, "disabled", "已废弃"),
    ENABLED(1, "enable", "已启用");

    private final Integer number;

    private final String code;

    private final String name;

    public static EnableStatusEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static EnableStatusEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dongjiejie
 */

@AllArgsConstructor
@Getter
public enum IndexTypeEnum implements StdEnum {

    ALL(0, "all", "全部"),
    BANNER(1, "bannel", "轮播图"),
    OTHER(2, "bannel", "轮播图");

    private final Integer number;

    private final String code;

    private final String name;

    public static IndexTypeEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static IndexTypeEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
package cn.exrick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dongjiejie
 */

@AllArgsConstructor
@Getter
public enum MemberStateEnum implements StdEnum {

    NEW(0, "新建", "已废弃"),
    VERIFIED(1, "已经验证邮箱", "已启用");

    private final Integer number;

    private final String code;

    private final String name;

    public static MemberStateEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static MemberStateEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

}
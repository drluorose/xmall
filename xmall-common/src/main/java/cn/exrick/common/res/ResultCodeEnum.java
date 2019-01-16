package cn.exrick.common.res;

import cn.exrick.common.enums.StdEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@AllArgsConstructor
@Getter
public enum ResultCodeEnum implements StdEnum {

    CODE_NORMAL_SUCCESS(0, "CODE_NORMAL_SUCCESS", "成功"),

    CODE_REGISTER_USER_EXIST(1001, "CODE_REGISTER_USER_EXIST", "用户已经存在"),

    CODE_LOGIN_FAIL(1101, "CODE_LOGIN_FAIL", "用户登录失败"),

    CODE_ADDRESS_ADD_FAIL(1201, "CODE_ADDRESS_ADD_FAIL", "添加地址达到最大数限制"),

    CODE_NORMAL_FAIL_HUMAN_VERIFY_ERROR(5001, "CODE_NORMAL_HUMAN_VERIFY_ERROR", "人机检查失败"),

    CODE_NORMAL_FAIL_PARAM_ERROR(5002, "CODE_NORMAL_FAIL_PARAM_ERROR", "提交参数错误"),

    CODE_NORMAL_FAIL_SERVER_ERROR(5003, "CODE_NORMAL_FAIL_SERVER_ERROR", "服务器错误");

    private final Integer number;

    private final String code;

    private final String name;

    public static ResultCodeEnum numberOf(int number) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getNumber().equals(number))
            .findFirst()
            .orElse(null);
    }

    public static ResultCodeEnum codeOf(String code) {
        return Arrays.stream(values())
            .filter(e -> e.getNumber() != null && e.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }
}

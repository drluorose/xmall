package cn.exrick.common.utils;

import cn.exrick.common.pojo.Result;
import cn.exrick.common.res.ResultCodeEnum;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class ResultResUtil {

    /**
     * 通过result code enum
     *
     * @param resultCodeEnum
     *
     * @return
     */
    public static final Result<String> withResultCodeEnum(ResultCodeEnum resultCodeEnum) {
        Result<String> result = new Result<>();
        result.setCode(resultCodeEnum.getNumber());
        result.setMessage(resultCodeEnum.getCode());
        return result;
    }

    /**
     * @param data
     * @param <T>
     *
     * @return
     */
    public static final <T> Result<T> successWithData(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.CODE_NORMAL_SUCCESS.getNumber());
        result.setMessage(ResultCodeEnum.CODE_NORMAL_SUCCESS.getCode());
        result.setResult(data);
        return result;
    }

    /**
     * @param resultCodeEnum
     * @param <T>
     *
     * @return
     */
    public static final <T> Result<T> errorWithoutData(ResultCodeEnum resultCodeEnum) {

        Result<T> result = new Result<>();
        result.setCode(resultCodeEnum.getNumber());
        result.setMessage(resultCodeEnum.getCode());
        return result;
    }

}

package cn.exrick.front.exception;

import cn.exrick.common.exception.XmallException;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.BindException;

/**
 * Created by Exrick on 2017/8/20.
 */
@Slf4j
@ControllerAdvice
public class RestCtrlExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Result<Object> bindExceptionHandler(BindException e) {
        log.error("e", e);

        String errorMsg = "请求数据校验不合法: ";
        if (e != null) {
            errorMsg = e.getMessage();
            log.warn(errorMsg);
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(XmallException.class)
    @ResponseBody
    public Result<Object> handleXmallException(XmallException e) {
        log.error("e", e);

        String errorMsg = "Xmall exception: ";
        if (e != null) {
            errorMsg = e.getMsg();
            log.warn(e.getMessage());
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }

    @CrossOrigin
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Result<Object> handleException(Exception e) {
        log.error("e", e);
        String errorMsg = "exception: ";
        if (e != null) {
            log.warn(e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("Maximum upload size")) {
                errorMsg = "上传文件大小超过5MB限制";
            } else if (e.getMessage().contains("XmallException")) {
                errorMsg = e.getMessage();
                errorMsg = StringUtils.substringAfter(errorMsg, "XmallException:");
                errorMsg = StringUtils.substringBefore(errorMsg, "\n");
            } else {
                errorMsg = e.getMessage();
            }
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }
}

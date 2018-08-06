package @packageName@.config;

import com.douyu.wsd.framework.common.collection.CollectionUtils;
import com.douyu.wsd.framework.common.domain.ResultVo;
import com.douyu.wsd.framework.common.excption.DouyuRuntimeException;
import com.douyu.wsd.framework.common.lang.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @ControllerAdvice
    class WebMvcControllerAdvice {

        /**
         * 处理一般异常（兜底）
         *
         * @param e 异常对象
         * @return 200状态码和统一的错误信息
         */
        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.OK)
        @ResponseBody
        public ResultVo processException(Exception e) {
            int code;
            String msg;
            if (e != null) {
                if (e instanceof BindException) {
                    BindException be = (BindException) e;
                    code = 400;
                    msg = "参数校验失败: " + buildErrMsg(be.getBindingResult());
                } else if (e instanceof MultipartException) {
                    code = 400;
                    msg = "上传失败，请确认文件尺寸是否超过上限";
                } else if (e instanceof DouyuRuntimeException) {
                    code = 500;
                    msg = ((DouyuRuntimeException) e).getOriginalMessage();
                } else {
                    code = 999;
                    msg = "服务器开小差了";
                }
            } else {
                code = 999;
                msg = "服务器开小差了";
            }
            log.info("全局异常处理: {}", msg, e);
            return ResultVo.asErrorWithCode(code, msg);
        }
    }


    private String buildErrMsg(BindingResult br) {
        List<ObjectError> errList = br.getAllErrors();
        if (CollectionUtils.isEmpty(errList)) {
            return "";
        }

        return errList.stream()
                .filter(Objects::nonNull)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(", "));
    }

}
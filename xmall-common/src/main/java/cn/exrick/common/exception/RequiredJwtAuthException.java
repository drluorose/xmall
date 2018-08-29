package cn.exrick.common.exception;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class RequiredJwtAuthException extends XmallException {
    public RequiredJwtAuthException(String msg) {
        super(msg);
    }

    public RequiredJwtAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

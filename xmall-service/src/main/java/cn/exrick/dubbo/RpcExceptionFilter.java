package cn.exrick.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongjiejie
 */
@Slf4j
@Activate(group = Constants.PROVIDER)
public class RpcExceptionFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException()) {
                Throwable t = result.getException();
                log.info("dubbo service exception: args={}", invocation.getArguments(), t);
                return result;
            }
            return result;
        } catch (Exception e) {
            log.error("dubbo invoke exception: args={}", invocation.getArguments(), e);
            return null;
        }
    }
}
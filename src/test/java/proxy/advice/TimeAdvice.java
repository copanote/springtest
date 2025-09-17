package proxy.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        log.info("TimeProxy Advice Start");
        Object result = invocation.proceed();
        log.info("TimeProxy Advice End");
        return result;
    }

}

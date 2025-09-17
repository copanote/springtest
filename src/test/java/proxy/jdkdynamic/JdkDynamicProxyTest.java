package proxy.jdkdynamic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class JdkDynamicProxyTest {


    @Test
    public void dynamicA() {

        AInterface target = new AImpl();
        MyInvocationHandler handler = new MyInvocationHandler(target);
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        proxy.call();


    }
}
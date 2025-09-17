package proxy.reflection;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void refliction0() {


    }

    @Test
    void reflection1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<Hello> clazz = Hello.class;
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor<?> c = declaredConstructors[0];
        Method m = clazz.getMethod("callA");


        Object result = m.invoke(c.newInstance());
        Object result2 = dynamicCall(clazz.getMethod("callB"), c.newInstance());
        log.info("result: {}", result);

        Assertions.assertEquals("callA", result);
        Assertions.assertEquals("callB", result2);
    }


    private Object dynamicCall(Method m, Object target, Object... args) throws InvocationTargetException, IllegalAccessException {
        return m.invoke(target, args);
    }

    private Object dynamicCall(Method m, Object target) throws InvocationTargetException, IllegalAccessException {
        return m.invoke(target);
    }


    static class Hello {

        public String callA() {
            log.info("callA");
            return "callA";
        }

        public String callB() {
            log.info("callB");
            return "callB";
        }


    }

}


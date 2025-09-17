package proxy.cglib;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CgblibTest {

    @Test
    void cgblib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();  //CGLIB는 Enhancer를 사용해서 프록시를 생성한다.
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));

        ConcreteService proxy = (ConcreteService) enhancer.create();
        proxy.call();


    }

}

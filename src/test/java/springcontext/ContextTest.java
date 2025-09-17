package springcontext;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;


public class ContextTest {

    @Test
    @DisplayName("Singleton Bean Register Test")
    void beanRegisterTest() {
        //Given
        StaticApplicationContext ac = new StaticApplicationContext();
        //When
        ac.registerSingleton("hello1", Hello.class);  //Hello라는 클래스를 hello1이라는 이름의 싱글톤 빈으로 컨테이너에 등록한다
        Hello hello1 = ac.getBean("hello1", Hello.class);
        //Then
        assertThat(hello1).isNotNull();
    }

    @Test
    @DisplayName("Bean Register By BeanDefinition Test")
    void beanDefinitionTest() {
        //Given
        StaticApplicationContext ac = new StaticApplicationContext();
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        ac.registerBeanDefinition("hello2", helloDef);

        //When
        Hello hello2 = ac.getBean("hello2", Hello.class);

        //Then
        assertThat(ac.getBeanFactory().getBeanDefinitionCount()).isEqualTo(1);
        assertThat(hello2.getName()).isEqualTo("Spring");
    }

    @Test
    @DisplayName("DI Test")
    void diTest() {
        //Given
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerSingleton("printer", ConsolePrinter.class);

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue(
                "printer",
                new RuntimeBeanReference("printer"));
        ac.registerBeanDefinition("hello2", helloDef);

        //When
        Hello hello2 = ac.getBean("hello2", Hello.class);
        hello2.getPrinter().print("Spring");

        //Then
        assertThat(hello2.getName()).isEqualTo("Spring");
    }

}


@Data
class Hello {
    String name;
    Printer printer;


}

interface Printer {
    void print(String message);
}

class StringPrinter implements Printer {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void print(String message) {
        this.sb.append(message);
    }

    @Override
    public String toString() {
        return this.sb.toString();
    }
}

class ConsolePrinter implements Printer {

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}


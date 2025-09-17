package methodref;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class MethdRefStartV1 {

    @Test
    void test() {
        BinaryOperator<Integer> add1 = this::add;
        BinaryOperator<Integer> add2 = MethdRefStartV1::add2;

        log.info("result1={}", add1.apply(1, 2));
    }

    int add(int x, int y) {
        return x + y;
    }

    static int add2(int x, int y) {
        return x + y;
    }


    @Test
    void test2() {
        //정적 메서드 참조
        Supplier<String> staticMethod1 = () -> Person.greeting();
        Supplier<String> staticMethod2 = Person::greeting;

        //특정 객체의 인스턴스 참조
        Person person = new Person("Shin");
        Supplier<String> instanceMethod1 = () -> person.introduce();
        Supplier<String> instanceMethod2 = person::introduce;

        //생성자 참조
        Supplier<Person> newPerson1 = () -> new Person();
        Supplier<Person> newPersion2 = Person::new;


        Function<String, String> staticMethodWithParams = name -> Person.greetingWithName(name);
        Function<String, String> staticMethodWithParams2 = Person::greetingWithName;

        Function<Integer, String> instanceMethodParam1 = i -> person.introduceWIthNumber(i);
        Function<Integer, String> instanceMethodParam2 = person::introduceWIthNumber;

        Function<String, Person> suplier1 = name -> new Person(name);
        Function<String, Person> suplier2 = Person::new;
    }


    @Test
    void test3() {
        Person person1 = new Person("Kim");
        Person person2 = new Person("Park");
        Person person3 = new Person("Shin");

        Function<Person, String> fun1 = person -> person.introduce();
        //메서드 참조, 타입이 첫 번쨰 매개변수가 됨, 그리고 첫 번째 매개변수의 메서드를 호출, 나머지는 순서대로 매개변수에 전달
        Function<Person, String> fun2 = Person::introduce;


    }

}

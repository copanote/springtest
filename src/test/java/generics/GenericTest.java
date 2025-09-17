package generics;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GenericTest {

    @Test
    void test_polymorphism() {
        FruitBox<Fruit> box = new FruitBox<>();

        List<Fruit> fruits = new ArrayList<>();


        // 제네릭 타입은 다형성 원리가 그대로 적용된다.
        box.add(new Fruit());
        box.add(new Apple());
        box.add(new Banana());

        fruits.add(new Fruit());
        fruits.add(new Apple());
        fruits.add(new Banana());

        log.info("box={}", box);
        log.info("fruits={}", fruits);


    }

}

class Fruit {
}

class Apple extends Fruit {
}

class Banana extends Fruit {
}


@ToString
class FruitBox<T> {
    List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }
}



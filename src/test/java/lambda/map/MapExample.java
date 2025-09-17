package lambda.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MapExample {

    public static List<String> map(List<String> list, StringFunction func) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(func.apply(s));
        }

        return result;
    }


    @Test
    void test_uppperMapper() {
        List<String> words = List.of("hello", "java", "lambda");
        List<String> result = map(words, s -> s.toUpperCase());

        Assertions.assertEquals(List.of("HELLO", "JAVA", "LAMBDA"), result);
//        System.out.println(result);


    }


}

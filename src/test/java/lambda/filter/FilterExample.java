package lambda.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterExample {

    public static List<Integer> filter(List<Integer> list, MyPredicate predicate) {
        List<Integer> result = new ArrayList<>();
        for (int v : list) {
            if (predicate.test(v)) {
                result.add(v);
            }
        }
        return result;
    }


    @Test
    void test_negative() {
        List<Integer> numbers = List.of(-3, -2, -1, 1, 2, 3, 5);

        List<Integer> result = filter(numbers, v -> v < 0);
        assertEquals(List.of(-3, -2, -1), result);
    }

    @Test
    void test_even() {
        List<Integer> numbers = List.of(-3, -2, -1, 1, 2, 3, 5);

        List<Integer> result = filter(numbers, v -> v % 2 == 0);
        assertEquals(List.of(-2, 2), result);
    }

}

package lambda.reduce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReduceExample {
    public static int reduce(List<Integer> list, int initial, MyReducer reducer) {

        int init = initial;
        for (int i : list) {
            init = reducer.reduce(init, i);
        }
        return init;
    }


    @Test
    void test_sum_reducer() {
        List<Integer> numbers = List.of(1, 2, 3, 4);

        int sum = reduce(numbers, 0, (a, b) -> a + b);

        Assertions.assertEquals(10, sum);

    }

}

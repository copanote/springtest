package collectors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Slf4j
public class CollectersTest {


    @Test
    void test_list_set() {

        Stream<String> stream = Stream.of("Java", "Spring", "JPA", "AOP");


        List<String> list = stream.toList();
        List<String> list2 = stream.collect(toList());
        List<String> unmodifiableList = stream.collect(toUnmodifiableList());

        Set<String> set = stream.collect(toSet());
        LinkedList<String> linkedList = stream.collect(toCollection(LinkedList::new));
    }

    @Test
    void test_map() {
        Stream<String> stream = Stream.of("Apple", "Banana", "Tomato", "Cherry");

        Map<String, Integer> map1 = stream.collect(toMap(
                name -> name,  //key mapper
                name -> name.length() //value mapper
        ));

        //java.lang.IllegalStateException: Duplicate Key
/*        Stream<String> stream2 = Stream.of("Apple", "Apple", "Tomato", "Cherry");
        Map<String, Integer> map2 = stream2.collect(toMap(
                name -> name,  //key mapper
                name -> name.length() //value mapper
        ));
*/
        LinkedHashMap<String, Integer> map3 = stream.collect(toMap(
                name -> name,  //key mapper
                name -> name.length(), //value mapper
                (oldVal, newVal) -> oldVal + newVal, //merge function. 키 중복 시 조치함수
                LinkedHashMap::new //결과 Map 타입 지정
        ));
    }


    @Test
    void test_group() {
        Stream<String> stream = Stream.of("Apple", "Banana", "Tomato", "Cherry", "Avocado", "Blueberry");

        Map<String, List<String>> grouped = stream.collect(groupingBy(name -> name.substring(0, 1)));
        log.info("groupBy First Letter={}", grouped);


        Stream<Integer> numberStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        //partitioningBy(...)` 는 단순하게 `true` 와 `false` 두 그룹으로 나눈다
        Map<Boolean, List<Integer>> partitioned = numberStream.collect(partitioningBy(n -> n % 2 == 0));
        log.info("partitioned Even or Odd numbers={}", partitioned);
    }


}

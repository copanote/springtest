package parallel.completable.shop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ShopTest {

    @Test
    void test_basic() {
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("Nike"),
                new Shop("Adidas"),
                new Shop("8Seconds")
        );

        long starts = System.nanoTime();
        log.info("{}", Shop.findPrices(shops, "myPhone"));
        long duration = (System.nanoTime() - starts) / 1_000_000;
        log.info("Done in {} msecs", duration);

    }

    @Test
    void test_parallelStream() {
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("Nike"),
                new Shop("Adidas"),
                new Shop("8Seconds")
        );

        long starts = System.nanoTime();
        log.info("{}", Shop.findPricesByParallel(shops, "myPhone"));
        long duration = (System.nanoTime() - starts) / 1_000_000;
        log.info("Done in {} msecs", duration);
    }

    @Test
    void test_completableFuture() {
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("Nike"),
                new Shop("Adidas"),
                new Shop("8Seconds")
        );

        long starts = System.nanoTime();
        log.info("{}", Shop.findPricesByCompletableFuture(shops, "myPhone"));
        long duration = (System.nanoTime() - starts) / 1_000_000;
        log.info("Done in {} msecs", duration);
    }


    @Test
    void test_streamLazy() {
        List<String> words = Arrays.asList("apple", "banana", "orange", "kiwi", "grape", "aaa");

        System.out.println("--- 중간 연산 실행 전 ---");

        words.stream()
                .filter(s -> {
                    System.out.println("Filter 실행: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("Map 실행: " + s.toUpperCase());
                    return s.toUpperCase();
                });
        // 최종 연산이 없으므로, 위의 filter와 map은 실행되지 않습니다.

        System.out.println("--- 최종 연산 호출 ---");

        // 최종 연산을 호출하면, 모든 연산이 실행됩니다.
        words.stream()
                .filter(s -> {
                    System.out.println("Filter 실행: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("Map 실행: " + s.toUpperCase());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("최종 출력: " + s));
    }


}
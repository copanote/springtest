package parallel.completable;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureTest {

    /*
    비동기 작업 실행

    runAsync
    반환값이 없는 경우
    비동기로 작업 실행 콜

    supplyAsync
    반환값이 있는 경우
    비동기로 작업 실행 콜

     runAsync와 supplyAsync는 기본적으로 자바7에 추가된 ForkJoinPool의 commonPool()을 사용해
     작업을 실행할 쓰레드를 쓰레드 풀로부터 얻어 실행시킨다.
     만약 원하는 쓰레드 풀을 사용하려면, ExecutorService를 파라미터로 넘겨주면 된다
     */

    @Test
    void test_runAsync() throws ExecutionException, InterruptedException {

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info("Thread: {}", Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
        });

        future.get();
        log.info("Thread: {}", Thread.currentThread().getName()); //main
    }

    @Test
    void test_supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();  //ForkJoinPool.commonPool-worker-1
        });

        String r = future.join();
        log.info(r);
        log.info("Thread: {}", Thread.currentThread().getName()); //main
    }

    /*
     작업 콜백

     thenApply
     반환 값을 받아서 다른 값을 반환함
     함수형 인터페이스 Function을 파라미터로 받음

     thenAccpet
     반환 값을 받아 처리하고 값을 반환하지 않음
     함수형 인터페이스 Consumer를 파라미터로 받음

     thenRun
     반환 값을 받지 않고 다른 작업을 실행함
     함수형 인터페이스 Runnable을 파라미터로 받음
     */

    @Test
    void test_thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("inThenApply");
            return "Thread: " + Thread.currentThread().getName();  //ForkJoinPool.commonPool-worker-1
        }).thenApply(String::toUpperCase);

        log.info(future.get());
    }

    @Test
    void test_thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();  //ForkJoinPool.commonPool-worker-1
        }).thenAccept(log::info);

    }

    @Test
    void test_thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("Thread in supplyAsync: {}", Thread.currentThread().getName()); // //ForkJoinPool.commonPool-worker-1
            return "Thread: " + Thread.currentThread().getName();
        }).thenRun(() -> {
            log.info("Thread in thenRun: {}", Thread.currentThread().getName()); // //ForkJoinPool.commonPool-worker-1
        });
        future.get();
    }

    /*
    작업 조합

    thenCompose
    두 작업이 이어서 실행하도록 조합하며, 앞선 작업의 결과를 받아서 사용할 수 있음
    함수형 인터페이스 Function을 파라미터로 받음

    thenCombine
    두 작업을 독립적으로 실행하고, 둘 다 완료되었을 때 콜백을 실행함
    함수형 인터페이스 Function을 파라미터로 받음

    allOf
    여러 작업들을 동시에 실행하고, 모든 작업 결과에 콜백을 실행함

    anyOf
    여러 작업들 중에서 가장 빨리 끝난 하나의 결과에 콜백을 실행함
     */


    @Test
    void test_thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future_hello = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future_helloworld = future_hello
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world"));


        log.info(future_helloworld.get());
    }


    @Test
    void test_thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future_hello = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });

        CompletableFuture<String> future_world = CompletableFuture.supplyAsync(() -> {
            return "World";
        });


        CompletableFuture<String> stringCompletableFuture = future_hello.thenCombine(future_world, (h, w) -> {
            log.info("{}{}", h, w);
            return h + w;
        });

        log.info(stringCompletableFuture.get());
    }

    @Test
    void test_allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            return "World";
        });

        CompletableFuture<String> huhu = CompletableFuture.supplyAsync(() -> {
            return "huhu";
        });
        List<CompletableFuture<String>> futures = List.of(hello, world, huhu);

        CompletableFuture<List<String>> result =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                        .thenApply(v -> futures.stream().
                                map(CompletableFuture::join).
                                collect(Collectors.toList()));

        result.join().forEach(System.out::println);
    }


    @Test
    void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            return "World";
        });

        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, world).thenAccept(System.out::println);
        future.get();
    }


    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void exceptionally(boolean doThrow) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (doThrow) {
                throw new IllegalArgumentException("Invalid Argument");
            }

            return "Thread: " + Thread.currentThread().getName();
        }).exceptionally(Throwable::getMessage);

        System.out.println(future.get());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void handle(boolean doThrow) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (doThrow) {
                throw new IllegalArgumentException("Invalid Argument");
            }

            return "Thread: " + Thread.currentThread().getName();
        }).handle((result, e) -> e == null ? result : e.getMessage());

        System.out.println(future.join());
    }

}

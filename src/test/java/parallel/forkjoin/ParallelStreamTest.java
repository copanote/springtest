package parallel.forkjoin;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static parallel.forkjoin.MyLogger.log;

public class ParallelStreamTest {

    @Test
    void test_sequentialStream() {
        long startTIme = System.currentTimeMillis();


        int sum = IntStream.rangeClosed(1, 8)
                .map(HeavyJob::heavyTask)
                .reduce(0, Integer::sum);

        long endTime = System.currentTimeMillis();

        log("time: " + (endTime - startTIme) + "ms, sum: " + sum);
    }

    @Test
    void test_usingThreadDirectly() throws InterruptedException {
        long startTIme = System.currentTimeMillis();


        //1. Fork 작업을 분할한다.
        SumTask task1 = new SumTask(1, 4);
        SumTask task2 = new SumTask(5, 8);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task1, "thread-2");

        //2. 분할한 작업을 처리한다.
        thread1.start();
        thread2.start();

        //3. join  처리한 결과를 합친다.
        thread1.join();
        thread2.join();
        log("main 스레드 대기 완료");

        int sum = task1.result + task2.result;

        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTIme) + "ms, sum: " + sum);
    }


    static class SumTask implements Runnable, Callable<Integer> {

        int startValue;
        int endValue;
        int result = 0;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            int sum = 0;

            for (int i = startValue; i <= endValue; i++) {
                int calculated = HeavyJob.heavyTask(i);
                sum += calculated;
            }

            result = sum;

            log("작업 완료 result=" + result);

        }

        @Override
        public Integer call() throws Exception {
            log("작업 시작");
            int sum = 0;

            for (int i = startValue; i <= endValue; i++) {
                int calculated = HeavyJob.heavyTask(i);
                sum += calculated;
            }

            result = sum;

            log("작업 완료 result=" + result);
            return result;
        }
    }


    @Test
    void test_threadpool() throws ExecutionException, InterruptedException {
        long startTIme = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(2);

        //1. Fork 작업을 분할한다.
        SumTask task1 = new SumTask(1, 4);
        SumTask task2 = new SumTask(5, 8);

        Future<Integer> f1 = es.submit((Callable<Integer>) task1);
        Future<Integer> f2 = es.submit((Callable<Integer>) task2);

        Integer i1 = f1.get();
        Integer i2 = f2.get();

        int sum = i1 + i2;
        long endTime = System.currentTimeMillis();

        log("time: " + (endTime - startTIme) + "ms, sum: " + sum);

    }


    @Test
    void test_forkJoin() {
        long startTIme = System.currentTimeMillis();

        List<Integer> data = IntStream.rangeClosed(0, 8).boxed().toList();


        ForkJoinPool pool = new ForkJoinPool(10);
        SumRecursiveTask task = new SumRecursiveTask(data);
        int sum = pool.invoke(task);

        pool.close();
        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTIme) + "ms, sum: " + sum);
    }


    @Test
    void test_commonpool() {
        long startTIme = System.currentTimeMillis();

        int processorCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        log("processorCount = " + processorCount + ", commonPool = " + commonPool.getParallelism());

        List<Integer> data = IntStream.rangeClosed(0, 8).boxed().toList();


        ForkJoinPool pool = new ForkJoinPool(10);
        SumRecursiveTask task = new SumRecursiveTask(data);
        int sum = task.invoke();


        commonPool.close();
        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTIme) + "ms, sum: " + sum);
    }


    @Test
    void test_parallel() {
        long startTIme = System.currentTimeMillis();

        int processorCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        log("processorCount = " + processorCount + ", commonPool = " + commonPool.getParallelism());

        int sum = IntStream.rangeClosed(0, 8).parallel().map(HeavyJob::heavyTask).sum();

        commonPool.close();
        long endTime = System.currentTimeMillis();
        log("time: " + (endTime - startTIme) + "ms, sum: " + sum);
    }

}

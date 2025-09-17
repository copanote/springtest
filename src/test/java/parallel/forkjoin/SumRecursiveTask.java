package parallel.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import static parallel.forkjoin.MyLogger.log;

public class SumRecursiveTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private final List<Integer> list;

    public SumRecursiveTask(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Integer compute() {

        if (list.size() <= THRESHOLD) {
            log("[처리 시작]" + list);
            int sum = list.stream().mapToInt(HeavyJob::heavyTask).sum();
            log("[처리 완료]" + list + " -> sum: " + sum);
            return sum;
        } else {
            int mid = list.size() / 2;
            List<Integer> left = list.subList(0, mid);
            List<Integer> right = list.subList(mid, list.size());
            log("[분할]" + list + " -> LEFT" + left + ", RIGHT" + right);
            SumRecursiveTask leftTask = new SumRecursiveTask(left);
            SumRecursiveTask rightTask = new SumRecursiveTask(right);

            leftTask.fork();
            int rightResult = rightTask.compute();

            int leftResult = leftTask.join();

            int sum = leftResult + rightResult;

            log("LEFT[" + leftResult + "] + RIGHT[" + rightResult + "] -> sum: " + sum);

            return sum;

        }
    }
}

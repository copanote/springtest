package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MyStream<T> {
    private List<T> internalList;

    private MyStream(List<T> internalList) {
        this.internalList = internalList;
    }

    public static <T> MyStream<T> of(List<T> inernalList) {
        return new MyStream<>(inernalList);
    }


    public MyStream<T> filter(Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        for (T t : internalList) {
            if (predicate.test(t)) {
                filtered.add(t);
            }
        }
        return of(filtered);
    }

    public <R> MyStream<R> map(Function<T, R> mapper) {
        List<R> mapped = new ArrayList<>();
        for (T t : internalList) {
            mapped.add(mapper.apply(t));
        }
        return of(mapped);
    }

    public void forEach(Consumer<T> consumer) {
        for (T t : internalList) {
            consumer.accept(t);
        }
    }


    public List<T> toList() {
        return internalList;
    }


}

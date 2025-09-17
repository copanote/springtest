package lambda.filter;


@FunctionalInterface
public interface MyPredicate {
    boolean test(int v);
}

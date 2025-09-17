package proxy.jdkdynamic;


public class AImpl implements AInterface {
    @Override
    public String call() {
        System.out.println("A call");
        return "a";
    }
}

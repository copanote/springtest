package proxy.jdkdynamic;

public class BImpl implements BInterface {
    @Override
    public String call() {
        System.out.println("B Call");
        return "b";
    }
}

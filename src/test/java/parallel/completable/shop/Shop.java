package parallel.completable.shop;

import lombok.Getter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Getter
public class Shop {

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }

        }).start();
        return futurePrice;
    }


    public Future<Double> getPriceAsync2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));

    }


    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }


    public static List<String> findPrices(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .toList();
    }

    public static List<String> findPricesByParallel(List<Shop> shops, String product) {
        return shops.stream()
                .parallel()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .toList();
    }

    public static List<String> findPricesByCompletableFuture(List<Shop> shops, String product) {

        /*
            두 map 연산을 하나의 스트림 처리 파이프라인으로 처리하지 않고 두 개의 스트림 파이프라인으로 처리했다는 사실에 주목하자
            스트림 연산은 게으른 특성이 있으므로 하나의 파이프라인으로 연산을 처리했다면 모든 가격 정보 요청 동작이 동기적, 순차적으로 이루어지는 결과가 된다
         */

        List<CompletableFuture<String>> priceFuture =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " + shop.getPrice(product)))
                        .toList();


        return priceFuture.stream()
                .map(CompletableFuture::join)
                .toList();
    }


    private static final Executor executor = Executors.newFixedThreadPool(
            100, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
            }
    );

    public static List<String> findPricesByCompletableFutureAndCustomExcutors(List<Shop> shops, String product) {


        List<CompletableFuture<String>> priceFuture =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " + shop.getPrice(product), executor))
                        .toList();


        return priceFuture.stream()
                .map(CompletableFuture::join)
                .toList();
    }


    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.example.demo.features;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FuturesAndSemaphores {
    public void future1() throws InterruptedException {
        var thread = new Thread(() -> {
            IntStream.range(0, 1_000_000).forEach(System.out::println);
        });
        thread.start();

        thread.join();
    }

    public void future2() throws InterruptedException {
        var thread = new Thread(() -> {
            IntStream.range(0, 2_000_000).forEach(System.out::println);
        });

        var thread2 = new Thread(() -> {
            IntStream.range(0, 1_000_000).forEach(System.out::println);
        });

        thread.start();
        thread2.start();

        thread2.join();
        thread.join();
    }

    public void futureCompletable() throws InterruptedException {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        var thread = new Thread(() -> {
            var result = IntStream.range(0, 1_000_000).sum();
            completableFuture.complete(result);
            completableFuture.completeExceptionally(new Exception(""));
        });

        thread.start();

        CompletableFuture<Integer> result = completableFuture
                .thenApply((v) -> v * 2)
                .exceptionally(this::func)
                        .completeOnTimeout(42, 400, TimeUnit.MILLISECONDS);
//                .join();
        System.out.println(result);
    }

    Integer func(Throwable t) {
        System.out.println(t);
        return 1;
    }

    public void futureCompletablePool() {
        CompletableFuture<Integer> completableFuture = CompletableFuture
                .supplyAsync(() -> IntStream.range(0, 1_000_000).sum());

        var result = completableFuture.join();
        System.out.println(result);
    }

    public void mutex() throws InterruptedException {
        Semaphore mutex = new Semaphore(1);
        List<Integer> values = IntStream.range(0, 1_000_000).boxed().toList();
        var thread = new Thread(() -> {
            try {
                mutex.acquire();
                values.forEach(System.out::println);
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        var thread2 = new Thread(() -> {
            try {
                mutex.acquire();
                values.forEach(System.out::println);
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread2.start();

        thread2.join();
        thread.join();
    }

    public void semaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        List<Integer> values = IntStream.range(0, 1_000_000).boxed().toList();
        AtomicInteger item = new AtomicInteger(3);
        var thread = new Thread(() -> {
            try {
                semaphore.acquire();
                values.forEach(System.out::println);
                semaphore.release();
                item.compareAndSet(3, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        var thread2 = new Thread(() -> {
            try {
                semaphore.acquire();
                values.forEach(System.out::println);
                semaphore.release();
                item.set(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread2.start();

        thread2.join();
        thread.join();
    }

    public static void main(String... args) throws InterruptedException {
        var run = new FuturesAndSemaphores();

//        run.future1();
//        run.mutex();
        run.semaphore();
    }
}

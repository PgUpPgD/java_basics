package com.example.excel.java8;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * 参照：<a href="https://blog.csdn.net/sermonlizhi/article/details/123356877">...</a>
 * <p>
 * 1.1 概述
 * CompletableFuture实现了对任务编排的能力
 * CompletableFuture 继承 Future<T>, CompletionStage<T>
 * 为了业务之间互不影响，且便于定位问题，强烈推荐使用自定义线程池
 * <p>
 * 1.2 功能
 * 1.2.1 常用方法
 * 依赖关系
 * thenApply()：把前面任务的执行结果，交给后面的Function
 * thenCompose()：用来连接两个有依赖关系的任务，结果由第二个任务返回
 * and集合关系
 * thenCombine()：合并任务，有返回值
 * thenAccepetBoth()：两个任务执行完成后，将结果交给thenAccepetBoth处理，无返回值
 * runAfterBoth()：两个任务都执行完成后，执行下一步操作(Runnable类型任务)
 * or聚合关系
 * applyToEither()：两个任务哪个执行的快，就使用哪一个结果，有返回值
 * acceptEither()：两个任务哪个执行的快，就消费哪一个结果，无返回值
 * runAfterEither()：任意一个任务执行完成，进行下一步操作(Runnable类型任务)
 * 并行执行
 * allOf()：当所有给定的 CompletableFuture 完成时，返回一个新的 CompletableFuture
 * anyOf()：当任何一个给定的CompletablFuture完成时，返回一个新的CompletableFuture
 * 结果处理
 * whenComplete：当任务完成时，将使用结果(或 null)和此阶段的异常(或 null如果没有)执行给定操作
 * exceptionally：返回一个新的CompletableFuture，当前面的CompletableFuture完成时，它也完成，当它异常完成时，给定函数的异常触发这个CompletableFuture的完成
 */
public class CompletableFutureApi {

    /**
     * 1.2.2 异步操作
     * CompletableFuture提供了四个静态方法来创建一个异步操作：
     * <p>
     * public static CompletableFuture<Void> runAsync(Runnable runnable)
     * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
     * <p>
     * 这四个方法的区别：
     * <p>
     * runAsync() 以Runnable函数式接口类型为参数，没有返回结果，supplyAsync() 以Supplier函数式接口类型为参数，返回结果类型为U；Supplier接口的 get()是有返回值的(会阻塞)
     * 使用没有指定Executor的方法时，内部使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池，则使用指定的线程池运行。
     * 默认情况下CompletableFuture会使用公共的ForkJoinPool线程池，这个线程池默认创建的线程数是 CPU 的核数（也可以通过 JVM option:-Djava.util.concurrent.ForkJoinPool.common.parallelism 来设置ForkJoinPool线程池的线程数）。如果所有CompletableFuture共享一个线程池，那么一旦有任务执行一些很慢的 I/O 操作，就会导致线程池中所有线程都阻塞在 I/O 操作上，从而造成线程饥饿，进而影响整个系统的性能。所以，强烈建议你要根据不同的业务类型创建不同的线程池，以避免互相干扰
     */
    @Test
    public void future01() {
        Runnable runnable = () -> System.out.println("无返回异步任务");
        CompletableFuture.runAsync(runnable);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("有返回值的异步任务");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello World";
        });
        try {
            //get 方法会阻塞，直到拿到结果
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取结果(join&get)
     * join()和get()方法都是用来获取CompletableFuture异步之后的返回值。join()方法抛出的是uncheck异常（即未经检查的异常),不会强制开发者抛出。get()方法抛出的是经过检查的异常，ExecutionException, InterruptedException 需要用户手动处理（抛出或者 try catch）
     * <p>
     * 结果处理
     * 当CompletableFuture的计算结果完成，或者抛出异常的时候，我们可以执行特定的 Action。主要是下面的方法：
     * <p>
     * public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
     * <p>
     * Action的类型是BiConsumer<? super T,? super Throwable>，它可以处理正常的计算结果，或者异常情况。
     * 方法不以Async结尾，意味着Action使用相同的线程执行，而Async可能会使用其它的线程去执行(如果使用相同的线程池，也可能会被同一个线程选中执行)。
     * 这几个方法都会返回CompletableFuture，当Action执行完毕后它的结果返回原始的CompletableFuture的计算结果或者返回异常
     */
    @Test
    public void future02() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (new Random().nextInt(10) % 2 == 0) {
                int i = 12 / 0;
            }
            System.out.println("执行结束！");
            return "test";
        });

        /**
         * 任务完成或异常方法完成时执行该方法
         * 如果出现了异常,任务结果为 null
         */
        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String t, Throwable throwable) {
                System.out.println(t + " 执行完成！");
            }
        });

        /**
         * 出现异常时先执行该方法
         */
        future.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable t) {
                System.out.println("执行失败：" + t.getMessage());
                return "异常xxxx";
            }
        });

        try {
            String s = future.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 二、应用场景
     * 2.1 结果转换
     * 将上一段任务的执行结果作为下一阶段任务的入参参与重新计算，产生新的结果。
     * <p>
     * thenApply
     * thenApply接收一个函数作为参数，使用该函数处理上一个CompletableFuture调用的结果，并返回一个具有处理结果的Future对象。
     * <p>
     * 常用使用：
     * public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
     * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
     */
    @Test
    public void future03() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            System.out.println("第一次运算：" + result);
            return result;
        }).thenApply(number -> {
            int result = number * 3;
            System.out.println("第二次运算：" + result);
            return result;
        });
    }

    /**
     * thenCompose
     * thenCompose的参数为一个返回CompletableFuture实例的函数，该函数的参数是先前计算步骤的结果。
     * <p>
     * 常用方法：
     * public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);
     * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) ;
     */
    @Test
    public void future04() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(30);
            System.out.println("第一次运算：" + number);
            return number;
        }).thenCompose(new Function<Integer, CompletionStage<Integer>>() {
            @Override
            public CompletionStage<Integer> apply(Integer param) {
                return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = param * 2;
                        System.out.println("第二次运算：" + number);
                        return number;
                    }
                });
            }
        });
    }
    /**
     * thenApply 和 thenCompose的区别：
     *
     * thenApply转换的是泛型中的类型，返回的是同一个CompletableFuture；
     * thenCompose将内部的CompletableFuture调用展开来并使用上一个CompletableFutre调用的结果在下一步的CompletableFuture调用中进行运算，是生成一个新的CompletableFuture。
     */

    /**
     * 2.2 结果消费
     * 与结果处理和结果转换系列函数返回一个新的CompletableFuture不同，结果消费系列函数只对结果执行Action，而不返回新的计算值。
     * <p>
     * 根据对结果的处理方式，结果消费函数又可以分为下面三大类：
     * <p>
     * thenAccept()：对单个结果进行消费
     * thenAcceptBoth()：对两个结果进行消费
     * thenRun()：不关心结果，只对结果执行Action
     * thenAccept
     * 观察该系列函数的参数类型可知，它们是函数式接口Consumer，这个接口只有输入，没有返回值。
     * <p>
     * 常用方法：
     * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
     * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
     */
    @Test
    public void future05() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(10);
            System.out.println("第一次运算：" + number);
            return number;
        }).thenAccept(number -> System.out.println("第一次运算：" + number * 2));
    }

    /**
     * thenAcceptBoth
     * thenAcceptBoth函数的作用是，当两个CompletionStage都正常完成计算的时候，就会执行提供的action消费两个异步的结果。
     * <p>
     * 常用方法：
     * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     */
    @Test
    public void future06() throws InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(3) + 1;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1结果：" + number);
            return number;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int number = new Random().nextInt(3) + 1;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务2结果：" + number);
                return number;
            }
        });

        future1.thenAcceptBoth(future2, new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer x, Integer y) {
                System.out.println("最终结果：" + (x + y));
            }
        });

        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * thenRun
     * thenRun也是对线程任务结果的一种消费函数，与thenAccept不同的是，thenRun会在上一阶段 CompletableFuture计算完成的时候执行一个Runnable，而Runnable并不使用该CompletableFuture计算的结果。
     * <p>
     * 常用方法：
     * public CompletionStage<Void> thenRun(Runnable action);
     * public CompletionStage<Void> thenRunAsync(Runnable action);
     */
    @Test
    public void future07() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int number = new Random().nextInt(10);
            System.out.println("第一阶段：" + number);
            return number;
        }).thenRun(() -> System.out.println("thenRun 执行"));
    }

    /**
     * 2.3 结果组合
     * thenCombine
     * 合并两个线程任务的结果，并进一步处理。
     * <p>
     * 常用方法：
     * public <U,V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * public <U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * public <U,V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn, Executor executor);
     */
    @Test
    public void future08() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        System.out.println("任务1结果：" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        System.out.println("任务2结果：" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> result = future1.thenCombine(future2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x + y;
            }
        });
        System.out.println("组合后结果：" + result.get());
    }

    /**
     * 2.4 任务交互
     * 线程交互指将两个线程任务获取结果的速度相比较，按一定的规则进行下一步处理。
     *
     * applyToEither
     * 两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的转化操作。
     *
     * 常用方法：
     * public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
     * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
     */
    @Test
    public void future09() throws InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        try {
                            TimeUnit.SECONDS.sleep(number);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("任务1结果:" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int number = new Random().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务2结果:" + number);
                return number;
            }
        });

        future1.applyToEither(future2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer number) {
                System.out.println("最快结果：" + number);
                return number * 2;
            }
        });
        TimeUnit.SECONDS.sleep(11);
    }

    /**
     * acceptEither
     * 两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的消费操作。
     *
     * 常用方法：
     * public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
     * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
     */
    @Test
    public void future10() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第一阶段：" + 1);
                return 1;
            }
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第二阶段：" + 2);
                return 2;
            }
        });

        CompletableFuture<Void> future = future1.acceptEither(future2, new Consumer<Integer>() {
            @Override
            public void accept(Integer number) {
                System.out.println("最快结果：" + number);
            }
        });
        future.get();
    }

    /**
     * runAfterEither
     * 两个线程任务相比较，有任何一个执行完成，就进行下一步操作，不关心运行结果。
     *
     * 常用方法：
     * public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
     * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
     */
    @Test
    public void future11() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int number = new Random().nextInt(5);
                try {
                    TimeUnit.SECONDS.sleep(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务1结果：" + number);
                return number;
            }
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int number = new Random().nextInt(5);
                try {
                    TimeUnit.SECONDS.sleep(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务2结果:" + number);
                return number;
            }
        });

        future1.runAfterEither(future2, new Runnable() {
            @Override
            public void run() {
                System.out.println("已经有一个任务完成了");
            }
        }).join();
    }

    /**
     * anyOf
     * anyOf()的参数是多个给定的 CompletableFuture，当其中的任何一个完成时，方法返回这个 CompletableFuture。
     *
     * 常用方法：
     * public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
     */
    @Test
    public void future12() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        });
        CompletableFuture<Object> result = CompletableFuture.anyOf(future1, future2);
        System.out.println(result.get());
    }

    /**
     * allOf
     * allOf方法用来实现多 CompletableFuture 的同时返回。
     *
     * 常用方法：
     * public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
     */
    @Test
    public void future13(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1完成！");
            return "future1完成！";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2完成！");
            return "future2完成！";
        });

        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        try {
            combindFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}

package reactive.analysis;

import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author zy
 * @date 2023/3/7 11:46
 * 阻塞调用，页面加载信息:获取用户信息200ms，获取用户的待办列表50ms，获取公告信息100ms
 */

public class Caller {
    public static void main(String[] args) {
        System.out.println("block call...");
        blockingCall();
        System.out.println("async call with callback...");
        asynCallWithBack();
        System.out.println("async call with completablefuture...");
        asyncCallWithFuture();
        System.out.println("async call with Publisher...");
        callWithReactor();
        System.exit(0);
    }

    // 阻塞调用
    private static void blockingCall() {
        HomePageService homePageService = new HomePageService();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String userInfo = homePageService.getUserInfo();
        System.out.println(userInfo);
        String noticeInfo = homePageService.getNoticeInfo();
        System.out.println(noticeInfo);
        String todoInfo = homePageService.getTodoInfo(userInfo);
        System.out.println(todoInfo);
        stopWatch.stop();
        System.out.println("call methods costs :" + stopWatch.getTotalTimeMillis() + "ms");

    }

    // 非阻塞回调调用
    private static void asynCallWithBack() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        HomePageService homePageService = new HomePageService();
        HomePageServiceWrapper homePageServiceWrapper = new HomePageServiceWrapper(homePageService);

        Runnable finallCallback = () -> {
            countDownLatch.countDown();
        };

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        homePageServiceWrapper.getUserInfoAsync(
                (userInfo) -> {
                    System.out.println(userInfo);
                    homePageServiceWrapper.getTodos(userInfo,
                            System.out::println,
                            System.err::println,
                            finallCallback);
                },
                System.err::println,
                finallCallback
        );
        homePageServiceWrapper.getNoticeAsyc(
                System.out::println,
                System.err::println,
                finallCallback
        );
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println("call method costs :" + stopWatch.getTotalTimeMillis() + "ms");
        //System.exit(0);
    }


    private static void  asyncCallWithFuture() {
        HomePageService homePageService = new HomePageService();
        HomePageServiceCompletableFutureWrapper homePageServiceCompletableFutureWrapper = new HomePageServiceCompletableFutureWrapper(homePageService);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        homePageServiceCompletableFutureWrapper.getUserInfoAsync()
                .thenCompose(
                        userInfo -> {
                            System.out.println(userInfo);
                            return homePageServiceCompletableFutureWrapper.getTodoInfoAsync(userInfo);})
                .thenAcceptAsync(System.out::println)
                .thenRun(() -> countDownLatch.countDown());

        homePageServiceCompletableFutureWrapper.getNoticInfoAsync()
                .thenAcceptAsync(System.out::println)
                .thenRun(() -> countDownLatch.countDown());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println("call method cost: " + stopWatch.getTotalTimeMillis() + "ms");
    }

    private static void callWithReactor() {
        HomePageService homePageService = new HomePageService();
        HomePageServicePublisherWrapper homePageServicePublisherWrapper = new HomePageServicePublisherWrapper(homePageService);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable finallyCallback = () -> countDownLatch.countDown();
        StopWatch stopWatch = new StopWatch();
        homePageServicePublisherWrapper
                .getUserInfoAsync()
                .doOnSubscribe(subscription -> stopWatch.start())
                .doOnNext(System.out::println)
                .flatMap(userInfo -> homePageServicePublisherWrapper.getTodoInfoAsync(userInfo))
                .doOnNext(System.out::println)
                .doFinally(s -> finallyCallback.run())
                .subscribe();

        homePageServicePublisherWrapper
                .getNoticeInfoAsync()
                .doOnNext(System.out::println)
                .doFinally(s -> finallyCallback.run())
                .subscribe();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println("Call method costs:" + stopWatch.getTotalTimeMillis() + "ms");
    }

}

package reactive.analysis;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author zy
 * @date 2023/3/7 15:55
 */
public class HomePageServicePublisherWrapper {
    private final HomePageService homePageService;

    private Scheduler executor = Schedulers.elastic();

    public HomePageServicePublisherWrapper(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    public Mono<String> getUserInfoAsync() {
         return Mono.fromCallable(this.homePageService::getUserInfo).subscribeOn(executor);
    }

    public Mono<String> getNoticeInfoAsync() {
        return Mono.fromCallable(this.homePageService::getNoticeInfo).subscribeOn(executor);
    }

    public Mono<String> getTodoInfoAsync(String userInfo) {
        return Mono.fromCallable(() -> this.homePageService.getTodoInfo(userInfo)).subscribeOn(executor);
    }
}

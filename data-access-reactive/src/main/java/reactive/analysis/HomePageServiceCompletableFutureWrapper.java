package reactive.analysis;

import java.util.concurrent.CompletableFuture;

/**
 * @author zy
 * @date 2023/3/7 14:23
 */
public class HomePageServiceCompletableFutureWrapper {

    private HomePageService homePageService;


    public HomePageServiceCompletableFutureWrapper(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    CompletableFuture<String> getUserInfoAsync() {
        return CompletableFuture.supplyAsync(this.homePageService::getUserInfo);
    }

    CompletableFuture<String> getNoticInfoAsync() {
        return CompletableFuture.supplyAsync(this.homePageService::getNoticeInfo);
    }

    CompletableFuture<String> getTodoInfoAsync(String userInfo) {
        return CompletableFuture.supplyAsync(() -> this.homePageService.getTodoInfo(userInfo));
    }
}

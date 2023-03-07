package reactive.analysis;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author zy
 * @date 2023/3/7 12:33
 */
public class HomePageServiceWrapper {
    private final HomePageService homePageService;

    private final ExecutorService threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());


    public HomePageServiceWrapper(HomePageService homePageService) {
        this.homePageService = homePageService;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> threadPool.shutdownNow()));
    }

    public void getUserInfoAsync(Consumer<String> successCallback, Consumer<Throwable> errorCallback, Runnable finallyCallback) {
        threadPool.submit(() -> {
           try {
               String userInfo = this.homePageService.getUserInfo();
               successCallback.accept(userInfo);
           }catch (Throwable ex) {
               errorCallback.accept(ex);
           }finally {
               finallyCallback.run();
           }
        });
    }

    public void getNoticeAsyc(Consumer<String> successCallback, Consumer<Throwable> errorCallback, Runnable finallyCallback) {
        threadPool.submit(() -> {
            try {
                String noticeInfo = this.homePageService.getNoticeInfo();
                successCallback.accept(noticeInfo);
            }catch (Throwable ex) {
                errorCallback.accept(ex);
            }finally {
                finallyCallback.run();
            }
        });
    }

    public void getTodos(String userInfo, Consumer<String> successCallback, Consumer<Throwable> errorCallback, Runnable finallyCallback) {
        threadPool.submit(() -> {
            try {
                String todoInfo = this.homePageService.getTodoInfo(userInfo);
                successCallback.accept(todoInfo);
            }catch (Throwable ex) {
                errorCallback.accept(ex);
            }finally {
                finallyCallback.run();
            }
        });
    }


}

package reactive.analysis;

import java.util.concurrent.TimeUnit;

/**
 * @author zy
 * @date 2023/3/7 11:49
 */
public class HomePageService {

    public String getUserInfo() {
        // 处理耗时
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread() + "user info";
    }

    public String getNoticeInfo() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  Thread.currentThread() + "notic info";
    }

    public String getTodoInfo(String userInfo) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  Thread.currentThread() + userInfo + " has todo list";
    }
}

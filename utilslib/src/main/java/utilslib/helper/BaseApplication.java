package utilslib.helper;


import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;

public abstract class BaseApplication extends Application{
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: 2017/12/18 解决安卓7.0拍照崩溃问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        context = getApplicationContext();
        handler = new Handler();
        //当前线程的id这里是主线程的id
        mainThreadId = Process.myTid();
        init();
    }
    public abstract void init();
    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }




}

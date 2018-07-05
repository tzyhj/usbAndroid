package utilslib.global;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.zch.utilslib.BuildConfig;
import com.zch.utilslib.R;

import java.io.File;
import de.greenrobot.event.EventBus;
import utilslib.crash.CrashHandler;
import utilslib.dialog.CommonData;
import utilslib.dialog.CommonDialogService;
import utilslib.helper.BaseApplication;
import utilslib.log.LogUtils;
import utilslib.sp.SharedInfo;


public class MyApplication extends BaseApplication implements Application.ActivityLifecycleCallbacks{
    //自定义application，进行全局初始化
    private static final String TAG = MyApplication.class.getSimpleName();

    private static Handler handler;
    private static int mainThreadId;
    public static final String SP_NAME = "basic_params";  //shareInfo name
    private Context mContext;
    @Override
    public void onCreate() {

        super.onCreate();
        mContext = this;

        //EventBus
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();

        //对话框服务开启
        this.registerActivityLifecycleCallbacks(this);//注册
        CommonData.applicationContext = this;
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        CommonData.ScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
        CommonData.ScreenHeight = metric.heightPixels; // 屏幕高度（像素）
        Intent dialogservice = new Intent(this, CommonDialogService.class);
        startService(dialogservice);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext


        handler = new Handler();
        //当前线程的id这里是主线程的id
        mainThreadId = Process.myTid();
        SharedInfo.init(SP_NAME);

//        initLocation();      //百度定位服务
        //应用发生crash时自动保存到本地
        CrashHandler.getInstance().init(this,"usbAndroid/error");
        //log 工具初始化
        LogUtils.init(true,"logcat",this,"usbAndroid/logs");
       /*
       * 在sd卡创建一个文件夹，存放图片和word
       * */
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory(); }
    }

    @Override
    public void init() {

    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}

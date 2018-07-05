package utilslib.log;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utilslib.helper.DateUtil;
import utilslib.helper.FileUtil;
import utilslib.log.baselog.MLog;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class LogUtils {
    private static String TAG_ = "";
    private static Context appContext;
    private static String logPath="";
    private static String logPathRoot= Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator;


    public static void init(@NonNull boolean isDebug, @NonNull String TAG,
                            @NonNull Application context,String path) {
        TAG_=TAG;
        logPath=path;
        if (TAG == null || context == null) throw new RuntimeException("LogUtils 初始化参数均不能为空");
        appContext = context.getApplicationContext();
        if (isDebug) init(TAG, true);//开启日志打印
        deleteLog();
//        Log.d(TAG_,"----*>"+logPathRoot);
    }

    private static boolean isInit = false;//是否初始化
    private static boolean isDebug = false;//是否输出日志
    private static String fileName=new SimpleDateFormat("yyyy年MM月dd日").
            format(new Date(System.currentTimeMillis()))+".txt";

    /**
     * 私有化构造参数
     */
    private LogUtils() {
        throw new RuntimeException("当前实例不需要创建");
    }

    /**
     * 初始化日志打印
     *
     * @param TAG     日志输出标签
     * @param debug 是否输出日志
     */
    public static void init(@NonNull String TAG, @NonNull boolean debug) {
        isDebug=debug;
        new Thread(new Runnable() {
            @Override
            public void run() {
                MLog.init(isDebug, TAG);
                isInit = true;
            }
        }).start();

    }

    public static void d(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) MLog.d(objectMsg);
        if (logPath!=null&&!logPath.equals("")) {
            File file=new File(logPathRoot+logPath);
            if (!file.exists()) {
                file.mkdir();
                file=new File(logPathRoot+logPath);
            }
            MLog.file(TAG_,file,fileName,objectMsg);
        }
    }

    public static void e(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) MLog.e(objectMsg);
        if (logPath!=null&&!logPath.equals("")) {
            File file=new File(logPathRoot+logPath);
            if (!file.exists()) {
                file.mkdir();
                file=new File(logPathRoot+logPath);
            }
            MLog.file(TAG_,file,fileName,objectMsg);
        }
    }

    public static void w(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) MLog.w(objectMsg);
        if (logPath!=null&&!logPath.equals("")) {
            File file=new File(logPathRoot+logPath);
            if (!file.exists()) {
                file.mkdir();
                file=new File(logPathRoot+logPath);
            }
            MLog.file(TAG_,file,fileName,objectMsg);
        }
    }

    public static void i(Object objectMsg) {
        if (!isInit) throwException();
        if (isDebug) MLog.i(objectMsg);
        if (logPath!=null&&!logPath.equals("")) {
            File file=new File(logPathRoot+logPath);
            if (!file.exists()) {
                file.mkdir();
                file=new File(logPathRoot+logPath);
            }
            MLog.file(TAG_,file,fileName,objectMsg);
        }
    }

    private static void throwException() {
        throw new NullPointerException("日志未初始化," +
                "请调用init()方法初始化后再试!");
    }

    // TODO: 2018/1/25 0025 自动删除前7天日志文件
    private static void deleteLog(){
        long currentTime=System.currentTimeMillis();
        //遍历日志文件夹
        List<String> fileNameList = FileUtil.getFileNameList(Environment.
                getExternalStorageDirectory().getPath() + "/roms3000/query/logs/"); //返回的只是文件名，不包含全路径
        if(fileNameList.size()>0){
            for (String s:fileNameList
                    ) {
                String name = FileUtil.getFileNameWithoutExtension(s);
                long timeCurrent=System.currentTimeMillis();
                long oldTime= DateUtil.getLongTime("yyyy年MM月dd日",name);
                if(timeCurrent>=(oldTime+7*24*60*60*1000)){
                    File file=new File(Environment.
                            getExternalStorageDirectory().getPath() + "/roms3000/query/logs/"+s);
                    if(file.exists()){
                        file.delete();
                    }
                }


            }
        }
    }
}

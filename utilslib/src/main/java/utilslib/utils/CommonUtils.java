package utilslib.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import utilslib.dialog.DialogUtils;

public class CommonUtils {


    private static final String TAG = CommonUtils.class.getSimpleName();
    private static MediaPlayer mMediaPlayer;
    private static Vibrator vibrator;


    /**
     * String[] 转 List<String>
     */
    public static List<String> getArrList(String[] arr) {
        List<String> list = new ArrayList<>();
        for(String col : arr) {
            list.add(col);
        }
        return list;
    }


    /**
     * String 转 List<String>
     */
    public static List<String> getArrList(String s,String split) {
        String[] Str = s.split(split);
        List<String> list = new ArrayList<>();
        for(String col : Str) {
            list.add(col);
        }
        return list;
    }


    /**
     * 根据给定的类型名和字段名，返回R文件中的字段的值
     * @param typeName 属于哪个类别的属性 （id,layout,drawable,string,color,attr......）
     * @param fieldName 字段名
     * @return 字段的值
     * @throws Exception
     */
    public static int getFieldValue(String typeName,String fieldName,Context context){
        int i = -1;
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".R$"+typeName);
            i = clazz.getField(fieldName).getInt(null);
        } catch (Exception e) {
            Log.d(""+context.getClass(),"没有找到"+  context.getPackageName() +".R$"+typeName+"类型资源 "+fieldName+"请copy相应文件到对应的目录.");
            return -1;
        }
        return i;
    }

    /**
     *  博客地址： http://blog.csdn.net/ouyang_peng/
     * 获取MediaPlayer  修复bug ( MediaPlayer: Should have subtitle controller already set )
     * </br><a href = "http://stackoverflow.com/questions/20087804/should-have-subtitle-controller-already-set-mediaplayer-error-android/20149754#20149754">
     *     参考链接</a>
     *
     *  </br> This code is trying to do the following from the hidden API
     *   <p>
     * </br> SubtitleController sc = new SubtitleController(context, null, null);
     * </br> sc.mHandler = new Handler();
     * </br> mediaplayer.setSubtitleAnchor(sc, null)</p>
     */
    private static MediaPlayer getMediaPlayer(Context context) {
        MediaPlayer mediaplayer = new MediaPlayer();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }
        try {
            Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
            Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
            Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
            Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");
            Constructor constructor = cSubtitleController.getConstructor(
                    new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});
            Object subtitleInstance = constructor.newInstance(context, null, null);
            Field f = cSubtitleController.getDeclaredField("mHandler");
            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler());
            } catch (IllegalAccessException e) {
                return mediaplayer;
            } finally {
                f.setAccessible(false);
            }
            Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor",
                    cSubtitleController, iSubtitleControllerAnchor);
            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
        } catch (Exception e) {
//            LogUtils.d("getMediaPlayer crash ,exception = "+e);
        }
        return mediaplayer;
    }
    public interface mediaPlayCallback{
        void completion();
    }
    public static void speak(Context context, String fileName, final mediaPlayCallback callback){

        String file = fileName+".mp3";
//        MediaPlayer mediaPlayer = new MediaPlayer();
        MediaPlayer mediaPlayer = getMediaPlayer(context);
        AssetManager assetManager = context.getAssets();
        try {
                AssetFileDescriptor fileDescriptor = assetManager.openFd(file);
                mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
//                        assetManager.close();
                        if (callback != null){
                            callback.completion();
                        }
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 开启扫描
//     */
//    public static void startEpc(Context mContext){
//
//        Intent intent_epc = new Intent(mContext, EpcService.class);
//        mContext.startService(intent_epc);
//        CommonUtils.speak(mContext, "scan_ing",null);
//        Log.d(TAG,"---->startEpc");
//    }
//    /**
//     * 开启扫描并设置功率
//     */
//    public static void startEpcWithPower(Context mContext,int power){
//
//        Intent intent_epc = new Intent(mContext, EpcService.class);
//        intent_epc.putExtra("power",power);
//        mContext.startService(intent_epc);
//    }
//    /**
//     * 停止扫描RFID
//     */
//    public static void stopEpcWith(Context mContext) {
//        Intent intent_epc = new Intent(mContext, EpcService.class);
//        mContext.stopService(intent_epc);
//    }
//
//    /**
//     * 停止扫描RFID
//     */
//    public static void stopEpc(Context mContext) {
//        Intent intent_epc = new Intent(mContext, EpcService.class);
//        mContext.stopService(intent_epc);
//        DialogUtils.getInstances().cancel();
//        Log.d(TAG,"---->stopEpc");
//    }
//
//
//    public static void clearFile(String filePaht){
//        File file = new File(filePaht);
//        if (file.exists()){
//            file.delete();
//        }
//    }
//
//    public static void startJLV(Context mContext){
//
//        Intent jlv_intent = new Intent(mContext, JlvService.class);
//        mContext.startService(jlv_intent);
//        Log.d(TAG,"---->startjlv");
//    }
//
//    /**
//     *开启GPS服务
//     */
//    public static void startGPS(Context mContext){
//        Intent gps_intent = new Intent(mContext, GpsLocationService.class);
//        mContext.startService(gps_intent);
//        Log.d(TAG,"---->startGPS");
//    }
//
//    /**
//     * 关闭GPS服务
//     */
//    public static void stopGPS(Context mContext) {
//        Intent gps_intent = new Intent(mContext, GpsLocationService.class);
//        mContext.stopService(gps_intent);
//        Log.d(TAG,"---->stopGPS");
//    }
//
//
//    public static void stopJLV(Context mContext) {
//        Intent jlv_intent = new Intent(mContext, JlvService.class);
//        mContext.stopService(jlv_intent);
//        Log.d(TAG,"---->stopJLV");
//    }



    //震动milliseconds毫秒
    public static void vibrate(Context mContext, long milliseconds) {
        Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    //以pattern[]方式震动
//以pattern[]方式震动
    public static void vibrate(Context mContext, long[] pattern,int repeat){
        if (vibrator == null){
            vibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern,repeat);
        }

    }
    //取消震动
    public static void virateCancle(Context mContext){
        if (vibrator != null){
            vibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.cancel();
            vibrator = null;
        }

    }



    //开始播放
    public static void playRing( Context mContext){
        try {
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);//用于获取手机默认铃声的Uri
            if (mMediaPlayer == null){
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(mContext, alert);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);//告诉mediaPlayer播放的是铃声流
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //停止播放
    public static void stopRing(Context context){
//        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        MediaPlayer  mMediaPlayer = MediaPlayer.create(context,alert);
        if (mMediaPlayer!=null){
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }


}

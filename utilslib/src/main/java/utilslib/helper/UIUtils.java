package utilslib.helper;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static android.content.Context.TELEPHONY_SERVICE;


public class UIUtils {   //工具类

    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    //获取字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //获取字符串数组
    public static String[] getStringArry(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //获取图片
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    //获取颜色
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }
    //根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }
    //返回尺寸dimens
    public static int getDimens(int id) {
        return getContext().getResources().getDimensionPixelSize(id);  //返回像素值px
    }

    //    ///////////////dip和px的转换////////////////
    //dip转px
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density; //设备密度
        return (int) (dip * density + 0.5f);
    }

    //px转dip
    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density; //设备密度
        return (px / density);
    }

    // ////////加载布局文件//////////
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }


    //   ///////判断是否运行在主线程///////
    public static boolean isRunOnUiThread() {

        //获取当前线程id，如果当前线程id与主线程id相同，那么当前线程就是主线程
        int myTid = android.os.Process.myTid();
        if (getMainThreadId() == myTid) {
            return true;
        }
        return false;
    }

    //运行在主线程的方法
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUiThread()) {   //已经是主线程，直接运行
            r.run();
        }else{  //如果当前是子线程，借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

    //对图片进行压缩处理
    /*
       * 图片质量压缩
       * */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    /*
    * 按比例大小压缩
    * */

    /*
    * 图片按比例大小压缩
    *
    * 根据路径获取图片并压缩
    *
    * */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        /*
        * // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        * */
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /*
    * 图片按比例大小压缩
    *
    * 根据Bitmap图片压缩
    * */
    public static Bitmap compressScale(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;


        /*
        * // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        * */
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        float hh = 800f;
        float ww = 480f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
//         newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩

        //return bitmap;
    }
    /*
          * 控制按钮的连续点击
          * */
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //生成4位数的随机验证码
    public static String verificationCode(){
        Random random = new Random();
        String s1="",s2="",s3="",s4="";
        do {
            s1=random.nextInt(10)+"";
            s2=random.nextInt(10)+"";
            s3=random.nextInt(10)+"";
            s4=random.nextInt(10)+"";
        }while (s1.equals(s2)&&s2.equals(s3)&&s3.equals(s4));


        return s1+s2+s3+s4;
    }

    //获取制定文件夹下的文件，返回文件名
    public static List<String> GetPhotoFileName(String fileAbsolutePath,String like) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为jpg结尾
                if (filename.trim().toLowerCase().endsWith(".jpg")&&filename.contains(like)) {
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }
    public static List<String> GetPhotoFileName(String fileAbsolutePath) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        if(file.exists()){
            File[] subFile = file.listFiles();

            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    // 判断是否为MP4结尾
                    if (filename.trim().toLowerCase().endsWith(".jpg")) {
                        vecFile.add(filename);
                    }
                }
            }
        }

        return vecFile;
    }
    //获取制定文件夹下的文件，返回文件名
    public static List<String> GetPhotoFileName(String fileAbsolutePath,String like,String type) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith(".jpg")&&filename.contains(like)&&filename.contains(type)) {
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }
    // TODO: 2017/11/28 0028 获取手机的IMEI号
    public static String getIMEI(){
        TelephonyManager mTm = (TelephonyManager)getContext().
                getSystemService(TELEPHONY_SERVICE);
        try {
            return mTm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    // TODO: 2017/12/15 0015 移除list集合里面的重复元素
    public static List removeRepeatElement(List l) throws Exception {
        Set set = new HashSet();
        List list = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            set.add(l.get(i));
        }
        for (Iterator it = set.iterator(); it.hasNext();) {
            list.add(it.next());
        }
        return list;
    }

}

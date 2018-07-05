package utilslib.dialog;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.zch.utilslib.R;

import utilslib.log.LogUtils;


public class CommonDialogService extends Service implements CommonDialogListener {

    private static Dialog dialog;
    private static TextView tv_str;
    private static ImageView img_loading;
    private static View view;
    private static AnimationDrawable animationDrawable;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DialogUtils.getInstances().setListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dialog!=null&&dialog.isShowing()){
            dialog.cancel();
            dialog=null;
        }
    }

    private void showDialog(){
        if(dialog==null&&CommonData.mNowContext!=null){
            dialog = new Dialog(CommonData.mNowContext, R.style.MyDialogStyle);
            dialog.setCancelable(true);
            view = LayoutInflater.from(this).inflate(R.layout.dialog,null,false);
            img_loading = (ImageView) view.findViewById(R.id.myloading_image_id);
            tv_str = (TextView) view.findViewById(R.id.mylaoding_text_id);
            DialogUtils.getInstances().setView(tv_str);
            animationDrawable = (AnimationDrawable) img_loading.getDrawable();
            animationDrawable.start();
            dialog.setContentView(view);
            dialog.show();

            WindowManager.LayoutParams lp = dialog.getWindow()
                    .getAttributes();
            if(CommonData.ScreenWidth!=0)
            lp.width =  CommonData.ScreenWidth/ 3;
            lp.height = CommonData.ScreenHeight/4;
            dialog.getWindow().setAttributes(lp);
        }else {
//            Toast.makeText(CommonData.applicationContext,"showDialog有误",Toast.LENGTH_SHORT).show();
            LogUtils.e("showDialog有误");
        }
    }

    @Override
    public void show(String s) {
       showDialog();
    }

    @Override
    public void cancel() {
         if(dialog!=null){
                dialog.dismiss();
                dialog=null;
             animationDrawable.stop();
         }
    }
}

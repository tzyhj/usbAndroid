package utilslib.dialog;


import android.widget.TextView;

public class DialogUtils {

    private static DialogUtils instances;
    private static CommonDialogListener mListener;
    private TextView tv;

    private DialogUtils(){

    }

    public void setListener(CommonDialogListener listener){
        this.mListener = listener;
    }

    public void setView(TextView tv){
       this.tv=tv;
   }

    public static  DialogUtils getInstances(){
        if (instances == null)
        {
            synchronized (DialogUtils.class)
            {
                if (instances == null)
                {
                    instances = new DialogUtils();
                }
            }
        }
        return instances;
    }


    public void showDialog(String s){
        if(mListener!=null){
            mListener.show(s);
            tv.setText(s);
        }
    }

    public void cancel(){
        if(mListener!=null){
            mListener.cancel();
        }
    }
}

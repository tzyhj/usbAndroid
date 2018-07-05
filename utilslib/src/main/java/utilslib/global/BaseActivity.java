package utilslib.global;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import pub.devrel.easypermissions.EasyPermissions;
import utilslib.log.LogUtils;


/*
* Activity的基类，便于统一管理activity
* */
public class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    private final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=0;
    private final static int ACCESS_FINE_LOCATION_REQUEST_CODE=1;
    private final static int ACCESS_COARSE_LOCATION_REQUEST_CODE=2;
    private final static int READ_PHONE_STATE_REQUEST_CODE=3;
    private final static int BLUETOOTH_REQUEST_CODE=4;
    private final static int BLUETOOTH_ADMIN_REQUEST_CODE=5;
    private final static int WRITE_SETTINGS_REQUEST_CODE=6;
    private final static int MOUNT_UNMOUNT_FILESYSTEMS_REQUEST_CODE=7;
    private final static int RESTART_PACKAGES_REQUEST_CODE=8;
    //所要申请的权限,拍照和写入sd卡
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.
            ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(getClass().getSimpleName());
        ActivityCollector.addActivity(this);
        if (EasyPermissions.hasPermissions(BaseActivity.this, perms)){//检查是否获取该权限
            LogUtils.e( "已获取权限");
            /////////将日志写成文件保存在本地
//            LogHelper.initLog();
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(BaseActivity.this, "请打开应用程序必要的权限", 0, perms);

        }

        //动态获取权限
//        getPersimmions();
    }
    /*
           * 动态获取权限的方法
           * */
    //动态获取权限的方法
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ArrayList<String> permissions = new ArrayList<String>();
//            /***
//             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
//             */
//            // 定位精确位置
//            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            }
//            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            }
//			/*
//			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
//			 */
//            // 读写权限
//            //这个读写权限只要用户拒绝，就会每次询问读写权限
//            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }
//
//            if (!Settings.System.canWrite(this)) {
//                Uri selfPackageUri = Uri.parse("package:"
//                        + this.getPackageName());
//                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
//                        selfPackageUri);
//                startActivity(intent);
//            }
//            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            }
//            //下面这个是只会弹出申请一次
////            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
////                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
////            }
//            // 读取电话状态权限
//            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
//                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
//            }
//            //蓝牙权限，如果用户拒绝，每次询问
//            if(checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED){
//                permissions.add(Manifest.permission.BLUETOOTH);
//            }
//            if(checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED){
//                permissions.add(Manifest.permission.BLUETOOTH_ADMIN);
//            }
//            if (permissions.size() > 0) {
//                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
//            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS},
                        WRITE_SETTINGS_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH},
                        BLUETOOTH_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                        BLUETOOTH_ADMIN_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        ACCESS_COARSE_LOCATION_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                        MOUNT_UNMOUNT_FILESYSTEMS_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RESTART_PACKAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RESTART_PACKAGES},
                        RESTART_PACKAGES_REQUEST_CODE);
            }
        }
    }

    //用户选择允许或需要后，会回调onRequestPermissionsResult方法,
    //  该方法类似于onActivityResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }
    //我们接着需要根据requestCode和grantResults(授权结果)做相应的后续处理
    private void doNext(int requestCode, int[] grantResults) {
//        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted
//            } else {
//                // Permission Denied
//            }
//        }
    }
    //    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
//            if (shouldShowRequestPermissionRationale(permission)){
//                return true;
//            }else{
//                permissionsList.add(permission);
//                return false;
//            }
//
//        }else{
//            return true;
//        }
//    }
     /*
    *  //监听相机拍完照片后的操作
    * */

    private static final int RESULT_CAPTURE_IMAGE = 1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        Log.e(TAG,"---->ActivityCollector remove"+ActivityCollector.activities.size());
        LogUtils.e("---->ActivityCollector remove  测试 "+ActivityCollector.activities.size());
        if (ActivityCollector.activities.size() == 0){
            //关闭可能运行的服务
        }
    }
}

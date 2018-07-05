package utilslib.sp;

import android.content.SharedPreferences;

import utilslib.toast.ContextHolder;
import utilslib.tools.utils.SPUtil;
import utilslib.tools.utils.TextUtil;


/**
 * Created by Administrator on 2017/11/13.
 */

public class SharedInfo {
    private SoftHashMap<String, Object> moMap;
    private SharedPreferences sp;
    private static String fileName = "filename";

    private SharedInfo() {
        sp = SPUtil.getSp(ContextHolder.getContext(), fileName);
    }

    public static SharedInfo getInstance() {
        return SharedInfoInstance.instance;
    }

    private static class SharedInfoInstance {
        static SharedInfo instance = new SharedInfo();
    }

    public static void init(String fileName) {
        SharedInfo.fileName = fileName;
    }

    //获取数据
    public <T> T getEntity(Class<T> clazz) {
        if (null == clazz) {
            return null;
        }
        String key = getKey(clazz);
        if (getMoMap().containsKey(key)) {
            return (T) moMap.get(key);
        }
        T mo = SPUtil.getEntity(sp, clazz, null);
        if (null != mo) {
            moMap.put(key, mo);
        }
        return mo;
    }

    public Object getValue(String key, Object defaultValue) {
        if (getMoMap().containsKey(key)) {
            return moMap.get(key);
        }
        Object mo = SPUtil.getValue(sp, key, defaultValue);
        if (null != mo) {
            moMap.put(key, mo);
        }
        return mo;
    }


    //保存数据
    public void saveEntity(Object obj) {
        if (null == obj) {
            return;
        }
        getMoMap().put(getKey(obj.getClass()), obj);
        SPUtil.saveEntity(sp, obj);

    }

    public void saveValue(String key, Object value) {
        getMoMap().put(key, value);
        SPUtil.saveValue(sp, key, value);
    }

    //删除数据
    public void remove(Class<?> clazz) {
        if (null == clazz) {
            return;
        }
        String key = getKey(clazz);
        getMoMap().remove(key);
        SPUtil.remove(sp, key);
    }

    public void remove(String key) {
        if (TextUtil.isEmpty(key)) {
            return;
        }
        getMoMap().remove(key);
        SPUtil.remove(sp, key);
    }

    private String getKey(Class<?> clazz) {
        return clazz.getName();
    }

    private SoftHashMap<String, Object> getMoMap() {
        if (moMap == null)
            moMap = new SoftHashMap<>();
        return moMap;
    }


}

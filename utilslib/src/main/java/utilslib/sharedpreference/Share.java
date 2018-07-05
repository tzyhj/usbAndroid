package utilslib.sharedpreference;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2017/9/26.
 */
/*
* 主体类
* */
public final class Share {
    //名字
    private static String shareName;
    //缓存大小
    private static int cacheSize;
    private static DiskCache diskCache = null;
    private static MemoryCache memoryCache = null;
    private static boolean saveDisk=false;//是否保存在sd卡



    /**
     * 初始化
     */
    public static void init(String shareName, int cacheSize, String path,boolean sd) {
        Share.shareName = shareName;
        Share.cacheSize = cacheSize;
        Share.memoryCache = new MemoryCache();
        Share.diskCache = new DiskCache(cacheSize, path, 1);
        saveDisk=sd;

    }

    /**
     * 设置int类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        Share.memoryCache.put(key, new Integer(value));
        if (saveDisk) {
            Share.diskCache.put(key, new Integer(value));
        }

    }

    /**
     * 设置long类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putLong(String key, long value) {
        Share.memoryCache.put(key, new Long(value));
        if (saveDisk) {
            Share.diskCache.put(key, new Long(value));
        }
    }

    /**
     * 设置boolean类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        Share.memoryCache.put(key, new Boolean(value));
        if (saveDisk) {
            Share.diskCache.put(key, new Boolean(value));
        }
    }


    /**
     * 设置String类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putString(String key, String value) {
        Share.memoryCache.put(key, value);
        if (saveDisk) {
            Share.diskCache.put(key, value);
        }
    }

    /**
     * 设置float类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putFloat(String key, float value) {
        Share.memoryCache.put(key, new Float(value));
        if (saveDisk) {
            Share.diskCache.put(key, new Float(value));
        }
    }

    /**
     * 设置double类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putDouble(String key, double value) {
        Share.memoryCache.put(key, new Double(value));
        if (saveDisk) {
            Share.diskCache.put(key, new Double(value));
        }
    }

    /**
     * 设置Object类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putObject(String key, Object value) {
        Share.memoryCache.put(key, value);
        if (saveDisk) {
            Share.diskCache.put(key, value);
        }

    }

    /**
     * 设置bytes类型的值
     *
     * @param key   键
     * @param bytes 值
     */
    public static void putBytes(String key, byte[] bytes) {
        Share.memoryCache.put(key, bytes);
        if (saveDisk) {
            Share.diskCache.put(key, bytes);
        }

    }

    /**
     * 设置Bitmap类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void putBitmap(String key, Bitmap value) {
        Share.memoryCache.put(key, value);
        if (saveDisk) {
            Share.diskCache.put(key, value);
        }

    }


    /**
     * 得到int类型的值
     *
     * @param key     键
     * @param defalut 默认值
     * @return int值
     */
    public static int getInt(String key, int defalut) {
        Integer value = memoryCache.getInt(key);
        if (value == null) {
            value = diskCache.getInt(key);

            if (value == null) {
                return defalut;
            } else {
                memoryCache.put(key,value);
                return value.intValue();
            }
        } else {
            return value.intValue();
        }

    }

    /**
     * 得到long类型的值
     *
     * @param key     键
     * @param defalut 默认值
     * @return long值
     */
    public static long getLong(String key, long defalut) {
        Long value = memoryCache.getLong(key);
        if (value == null) {
            value = diskCache.getLong(key);

            if (value != null) {
                memoryCache.put(key,value);
                return value.longValue();
            }
            else
                return defalut;
        } else {
            return value.longValue();
        }
    }

    /**
     * 得到String类型的值
     *
     * @param key 键
     * @return String值
     */
    public static String getString(String key) {
        String value = memoryCache.getString(key);
        if (value == null) {
            value = diskCache.getString(key);
            if (value!=null)
                memoryCache.put(key,value);
            return value;
        } else {
            return value;
        }
    }

    /**
     * 得到boolean类型的值
     *
     * @param key     键
     * @param defalut 默认值
     * @return boolean值
     */
    public static boolean getBoolean(String key, boolean defalut) {
        Boolean value = memoryCache.getBoolean(key);
        if (value == null) {
            value = diskCache.getBoolean(key);
            if (value == null)
                return defalut;
            memoryCache.put(key,value);
            return value.booleanValue();
        } else {
            return value.booleanValue();
        }
    }

    /**
     * 得到double类型的值
     *
     * @param key     键
     * @param defalut 默认值
     * @return double值
     */
    public static double getDouble(String key, double defalut) {
        Double value = memoryCache.getDouble(key);
        if (value == null) {
            value = diskCache.getDouble(key);

            if (value == null)
                return defalut;
            memoryCache.put(key,value);
            return value.doubleValue();
        } else {
            return value.doubleValue();
        }
    }

    /**
     * 得到float类型的值
     *
     * @param key     键
     * @param defalut 默认值
     * @return float值
     */
    public static float getFloat(String key, float defalut) {
        Float value = memoryCache.getFloat(key);
        if (value == null) {
            value = diskCache.getFloat(key);
            if (value == null)
                return defalut;
            memoryCache.put(key,value);
            return value.floatValue();
        } else {
            return value.floatValue();
        }
    }

    /**
     * 得到object类型的值
     *
     * @param key 键
     * @return object值
     */
    public static Object getObject(String key) {
        Object object = memoryCache.getObject(key);
        if (object == null) {
            object = diskCache.getObject(key);
            if (object!=null)
                memoryCache.put(key,object);
            return object;
        } else {
            return object;
        }
    }
    public static Object getObject(String key,boolean readSdCard) { //是否从磁盘读
        Object object = null;
        if(!readSdCard){
          object=  memoryCache.getObject(key);//从内存读
        }

        if (object == null) {
            object = diskCache.getObject(key);
            if (object!=null)
                memoryCache.put(key,object);
            return object;
        } else {
            return object;
        }
    }
    /**
     * 得到bytes类型的值
     *
     * @param key 键
     * @return bytes值
     */

    public static byte[] getBytes(String key) {
        byte[] bytes = memoryCache.getBytes(key);
        if (bytes == null) {
            bytes = diskCache.getBytes(key);
        }
        if (bytes!=null)
            memoryCache.put(key,bytes);
        return bytes;
    }

    /**
     * 得到Bitmap类型的值
     *
     * @param key 键
     * @return bitmap值
     */
    public static Bitmap getBitmap(String key) {
        Bitmap bitmap = memoryCache.getBitmap(key);
        if (bitmap == null) {
            bitmap = diskCache.getBitmap(key);

        }
        if (bitmap!=null)
            memoryCache.put(key,bitmap);

        return bitmap;
    }


    /**
     * 清楚数据
     */
    public static void clearData() {
        Share.memoryCache.clear();
        Share.diskCache.clear();

    }

    /**
     * 清除指定的数据
     *
     * @param key 键
     */
    public static void remove(String key) {
        Share.diskCache.remove(key);
        Share.memoryCache.remove(key);
    }


    //异步方法

    /**
     * 异步设置Bitmap类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putBitmapAsync(final String key, final Bitmap value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putBitmap(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 异步设置String类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putStringAsync(final String key, final String value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putString(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    /**
     * 异步设置Object类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putObjectAsync(final String key, final Object value,  final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putObject(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    /**
     * 异步设置long类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putLongAsync(final String key, final long value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putLong(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 异步设置int类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putIntAsync(final String key, final int value, final Callback callback) {

        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putInt(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    /**
     * 异步设置double类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putDoubleAsync(final String key, final double value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putDouble(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 异步设置float类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putFloatAsync(final String key, final float value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putFloat(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 异步设置byte[]类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putBytesAsync(final String key, final byte[] value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putBytes(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 异步设置boolean类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putBooleanAsync(final String key, final boolean value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putBoolean(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {

                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 回调接口
     */
    public interface Callback {
        void apply();
    }
    //在activity中的用法
//    People people = new People(20, "yjx");
//    //设置字符串
//        Share.putString("str", "你好啊");
//    //设置int
//        Share.putInt("int", 1);
//    //设置boolean
//        Share.putBoolean("boolean", true);
//    //设置double
//        Share.putDouble("double", 2.1d);
//    //设置long
//        Share.putLong("long", 20000);
//    //设置flot
//        Share.putFloat("float", 2.2f);
//    //设置类
//        Share.putObject("obj", people);
//    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dd);
//    //设置bitmap
//        Share.putBitmap("bitmap", bitmap);
//    //设置集合
//        Share.putObject("list", items);
//
//    //得到字符串
//    String str=Share.getString("str");
//    //得到double
//    double dd=Share.getDouble("double", 0.0d);
//    //得到int
//    int value=Share.getInt("int", 0);
//    //得到float
//    float ff=Share.getFloat("float", 0.0f);
//    //得到bitmap
//    Bitmap map=Share.getBitmap("bitmap");
//    //得到集合
//    List<String> copy= (List<String>) Share.getObject("list");

}

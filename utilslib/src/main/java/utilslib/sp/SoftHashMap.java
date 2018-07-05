package utilslib.sp;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/13.
 */

public class SoftHashMap<K, V> {
    private SoftReference<HashMap<K, V>> innerMap = new SoftReference<HashMap<K, V>>(null);

    //向map中存入key - value
    public void put(K k, V v) {
        HashMap<K, V> map = innerMap.get();
        if (map == null) {
            map = new HashMap<>();
            map.put(k, v);
            innerMap = new SoftReference<>(map);
        } else {
            map.put(k, v);
        }
    }


    //从map中取出对应key的value
    public V get(K k) {
        final HashMap<K, V> map = innerMap.get();
        if (map != null) {
            return map.get(k);
        }
        return null;
    }

    //从map中删除key对应的value
    public void remove(K k) {
        final HashMap<K, V> map = innerMap.get();
        if (map != null && map.containsKey(k)) {
            map.remove(k);
        }
    }

    //判断map中是否有此key
    public boolean containsKey(K k) {
        final HashMap<K, V> map = innerMap.get();
        return map != null && map.containsKey(k);
    }


}

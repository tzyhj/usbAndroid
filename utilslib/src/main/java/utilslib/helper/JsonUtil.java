package utilslib.helper;


/*
* 解析多层次Json字符串,封装多层Json，避免字符串中有特殊字符而出现的错误
* */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {
    private final static String regex = "\"([^\\\" ]+?)\":";


    /*
    * 解析多层json数据  json + 正则 + 递归
    * */
    public static Object jsonParse(final String jsonStr) {
        if (jsonStr == null) throw new NullPointerException("JsonString shouldn't be null");
        try {
            if (isJsonObject(jsonStr)) {
                final Pattern pattern = Pattern.compile(regex);
                final Matcher matcher = pattern.matcher(jsonStr);
                final Map<String, Object> map = new HashMap<String, Object>();
                final JSONObject jsonObject = new JSONObject(jsonStr);
                try {
                    for (; matcher.find(); ) {
                        String groupName = matcher.group(1);
                        Object obj = jsonObject.opt(groupName);
                        //Log.e(groupName, obj+"");
                        if (isJsonObject(obj + "") || isJsonArray(obj + "")) {
                            matcher.region(matcher.end() + (obj + "").replace("\\", "").length(), matcher.regionEnd());
                            map.put(groupName, jsonParse(obj + ""));
                        } else {
                            map.put(groupName, obj + "");
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("object---error", e.getMessage() + "--" + e.getLocalizedMessage());
                }

                return map;
            } else if (isJsonArray(jsonStr)) {
                List<Object> list = new ArrayList<Object>();
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object object = jsonArray.opt(i);
                        list.add(jsonParse(object + ""));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("array---error", e.getMessage() + "--" + e.getLocalizedMessage());
                }
                return list;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("RegexUtil--regexJson", e.getMessage() + "");
        }
        return jsonStr;
    }
    /*
    * 是否是json对象
    * */
    private static boolean isJsonObject(final String jsonStr) {
        if (jsonStr == null) return false;
        return Pattern.matches("^\\{.*\\}$", jsonStr.trim());
    }
    /*
    * 是否是jsonarray对象
    * */
    private static boolean isJsonArray(final String jsonStr) {
        if (jsonStr == null) return false;
        return Pattern.matches("^\\[.*\\]$", jsonStr.trim());
    }

    /*
    *将对象分装为json字符串 (json + 递归)
    * */
    public static Object jsonEnclose(Object obj) {
        try {
            if (obj instanceof Map) {   //如果是Map则转换为JsonObject
                Map<String, Object> map = (Map<String, Object>)obj;
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                JSONStringer jsonStringer = new JSONStringer().object();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));
                }
                JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));
                return jsonObject;
            } else if (obj instanceof List) {  //如果是List则转换为JsonArray
                List<Object> list = (List<Object>)obj;
                JSONStringer jsonStringer = new JSONStringer().array();
                for (int i = 0; i < list.size(); i++) {
                    jsonStringer.value(jsonEnclose(list.get(i)));
                }
                JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));
                return jsonArray;
            } else {
                return obj;
            }
        } catch (Exception e) {
            // TODO: handle exception
           Log.e("jsonUtil--Enclose", e.getMessage());
            return e.getMessage();
        }
    }


}

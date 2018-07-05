package utilslib.log.baselog;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilslib.log.LogUtils;

import static utilslib.log.baselog.MLog.D;
import static utilslib.log.baselog.MLog.E;
import static utilslib.log.baselog.MLog.I;
import static utilslib.log.baselog.MLog.W;


/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class JsonLog {

    public static void printJson(int type, String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(MLog.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(MLog.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        MLogUtil.printLine(type, tag, true);
        message = headString + MLog.LINE_SEPARATOR + message;
        String[] lines = message.split(MLog.LINE_SEPARATOR);
        for (String line : lines) {
            switch (type) {
                case D:
//                    Log.d(tag, "║ " + line);
                    BaseLog.printDefault(D, tag, line);
                    break;
                case E:
//                    Log.e(tag, "║ " + line);
                    BaseLog.printDefault(E, tag, line);
                    break;
                case W:
//                    Log.w(tag, "║ " + line);
                    BaseLog.printDefault(W, tag, line);
                    break;
                case I:
//                    Log.i(tag, "║ " + line);
                    BaseLog.printDefault(I, tag, line);
                    break;
            }
        }
        MLogUtil.printLine(type, tag, false);
    }

    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(MLog.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(MLog.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        MLogUtil.printLine(tag, true);
        message = headString + MLog.LINE_SEPARATOR + message;
        String[] lines = message.split(MLog.LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        MLogUtil.printLine(tag, false);
    }
}

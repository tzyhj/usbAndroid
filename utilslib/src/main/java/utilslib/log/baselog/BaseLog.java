package utilslib.log.baselog;

import android.util.Log;

import utilslib.log.LogUtils;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class BaseLog {

    private static final int MAX_LENGTH = 3000;

    public static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;
        if (countOfSub > 0) {
            for (int i = 0; i <= countOfSub; i++) {
                int max = MAX_LENGTH * (i + 1);
                if (max >= msg.length()) {
                    printSub(type, tag, msg.substring(MAX_LENGTH*i));
                   } else {
                    printSub(type, tag, msg.substring(MAX_LENGTH*i,max));
                   }
                     }
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case MLog.V:
                Log.v(tag, sub);
                break;
            case MLog.D:
                Log.d(tag, sub);
                break;
            case MLog.I:
                Log.i(tag, sub);
                break;
            case MLog.W:
                Log.w(tag, sub);
                break;
            case MLog.E:
                Log.e(tag, sub);
                break;
            case MLog.A:
                Log.wtf(tag, sub);
                break;
        }
    }

}

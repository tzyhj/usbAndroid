package utilslib.log.baselog;

import android.text.TextUtils;
import android.util.Log;

import static utilslib.log.baselog.MLog.D;
import static utilslib.log.baselog.MLog.E;
import static utilslib.log.baselog.MLog.I;
import static utilslib.log.baselog.MLog.W;


/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class MLogUtil {

    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    public static void printLine(int type,String tag, boolean isTop) {
        if (isTop) {

            switch (type) {
                case D:
                    Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
                case E:
                    Log.e(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
                case W:
                    Log.w(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
                case I:
                    Log.i(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
            }
        } else {
            switch (type) {
                case D:
                    Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
                case E:
                    Log.e(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
                case W:
                    Log.w(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
                case I:
                    Log.i(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
                    break;
            }
        }
    }
}

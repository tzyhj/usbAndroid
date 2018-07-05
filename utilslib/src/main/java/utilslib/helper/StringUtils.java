package utilslib.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 去掉字符串的换行空格
* */
public class StringUtils {
    public static String formatString(String s){
        String s1=null;
        if (s!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(s);
            s1 = m.replaceAll("");
        }
        return s1;
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}

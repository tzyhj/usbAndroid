package utilslib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 去掉字符串中的空格、回车、换行符、制表符
* */
public class StringUtils {
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

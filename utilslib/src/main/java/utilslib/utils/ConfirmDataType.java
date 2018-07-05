package utilslib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
* 判断是数字，字母或者中文的工具类
* */
public class ConfirmDataType {

    //判断是否是数字
    //-?[0-9]+.*[0-9]*可以判断是正整数，小数，负数等
    //[0-9]*只能判断正整数
    static Pattern p = Pattern.compile("-?[0-9]+.*[0-9]*");
    public static boolean isNumber(String s){
        Matcher m = p.matcher(s);
        if(m.matches() ){
           return true;//为数字
        }
        return false;
    }

    //判断是否为字母
    static Pattern p1=Pattern.compile("[a-zA-Z]");
    public static boolean isAZ(String s1){
        Matcher m1 = p1.matcher(s1);
        if(m1.matches() ){
            return true;//为字母
        }
        return false;
    }

    //判断是否为汉字
    static Pattern  p2=Pattern.compile("[\u4e00-\u9fa5]");
    public static boolean isChinese(String s3){
        Matcher m2 = p2.matcher(s3);
        if(m2.matches() ){
            return true;//为中文
        }
        return false;
    }

    // 判断是否为手机号
//    static Pattern p3 = Pattern
//            .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//    public static boolean isPhone(String inputText) {
//
//        Matcher m3 = p3.matcher(inputText);
//        return m3.matches();
//    }
    static Pattern  p3 = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
    public static boolean isPhone(String inputText) {

        Matcher m3 = p3.matcher(inputText);
        return m3.matches();
    }
}

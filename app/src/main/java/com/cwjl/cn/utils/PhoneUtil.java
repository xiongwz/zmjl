package com.cwjl.cn.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by xiyuan on 2018/8/22.
 * 手机相关utils
 */

public class PhoneUtil {

    /**
     *  验证手机格式是否正确
     * @param text 待验证的字符串
     * @return flag 如果符合手机格式就返回true,否则返回false
     */
    public static boolean isPhoneNo(String text) {
        boolean flag = false;
        if(text != null){
            //String regex = "^1(3\\d{1}|5[0-9]|(45)|(47)|8[0-9]|7[6-8])\\d{8}";
            //String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9])|(17[0-9]))\\d{8}$";
            if(text.startsWith("1")&&text.length()==11) {
                flag=true;
            }
        }
        return flag;
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String hidePhoneNo(String phone) {
        if (phone.length() == 11)
            phone = phone.substring(0, 3) + "******" + phone.substring(9, phone.length());
        else if(phone.length() > 5)
            phone = phone.substring(0, 3) + "******" + phone.substring(4, phone.length());
        return phone;
    }
}

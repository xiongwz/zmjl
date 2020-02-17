package com.cwjl.cn.utils;

import android.text.TextUtils;

import com.cwjl.cn.pub.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiyuan on 2018/9/3.
 */

public class StringUtil {

    /**
     * 去掉所有html标签返回文字
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }


    /**
     * 验证用户名只包含字母，数字，中文和-
     * @param name
     * @return
     */
    public static boolean isName(String name) {
        String all = "^[a-zA-Z0-9\\u4e00-\\u9fa5-]+$";
        Pattern pattern = Pattern.compile(all);
        return pattern.matches(all, name);
    }

    /**
     * 拼接图片路径
     * @param headerPic
     */
    public static String montagePicPath(String headerPic) {
        if (!TextUtils.isEmpty(headerPic) && !headerPic.contains("http"))
            return Constants.PIC_HOST + headerPic;
        else
            return headerPic;
    }

    /**
     * 拼接IM图片路径
     */
    public static String montageIMPicPath(String pic) {
        if (!TextUtils.isEmpty(pic) && pic.endsWith("_thum"))
            pic = pic.substring(0, pic.length() - 5);
        if (!TextUtils.isEmpty(pic) && !pic.contains("http"))
            return Constants.PIC_HOST_IM + pic;
        else
            return pic;
    }

    /**
     * 去掉money后面的 .0或 .00结尾
     * 如果有小数，去掉小鼠末尾的0
     */
    public static String updateMoney(String str) {
        if (!TextUtils.isEmpty(str) && str.endsWith(".00"))
            return str.replace(".00", "");
        else if (!TextUtils.isEmpty(str) && str.endsWith(".0"))
            return str.replace(".0", "");
        else if (!TextUtils.isEmpty(str) && str.contains(".") && str.endsWith("0"))
            return str.substring(0,str.length() - 1);
        else
            return str;
    }

    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     * @param value
     * @return Sting
     */
    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            String str = df.format(value);
            if (!TextUtils.isEmpty(str) && str.contains(".") && str.startsWith("."))
                str = "0" + str;
            return str;
        }else{
            return "0.00";
        }

    }

}

package com.cwjl.cn.utils;

import java.util.Random;

/**
 * Created by xiyuan on 2018/9/7.
 * 发起项目时根据填入数据自动生成相应内容
 */

public class AutoCreateStringUtil {

    private static String[] self_titleTpl = new String[]{
            "身患{sick}急需救助，大家帮帮我！",
            "助人为善，感恩有你，恳请大家帮帮我！",
            "还想要再努力一下，还想要活下去！",
            "{patient}，你要坚强，一切都会好起来的！",
            "{sick}巨额药费无力负担，大家帮帮我！",
            "年轻的生命不想就此说再见，请大家救救我！"
    };

    private static String[] her_titleTpl = new String[]{
            "恳求好心人救救我的{who}，谢谢你们了！",
            "救救我患有{sick}的{who}！",
            "为{sick}治疗已负债累累，求好心人帮渡过难关！",
            "这个世界那么美好，{who}你一定要撑住！",
            "给{who}一个活下去的希望！",
            "{who}！你要坚强，一切都会好的！"
    };

    private static String[] self_infoTpl = new String[]{
            "大家好，我叫{patient}，家住{country}，天有不测风云，原本幸福美满的家庭却被一场突如其来的大病拖垮。 在{year}年{mounth}月{day}日，突感不适，被送往{hospital}紧急救治，最后确诊为{sick}，如今已经花去{money}元，目前病情虽然已经控制住了，但后期治疗费用还需要很多资金，朋友亲戚该借的都借了，实在是无力承担这笔天大的费用。请大家帮帮我，多多转发，大家的恩情我一定会铭记在心，感恩，好人一生平安！",
            "好心人，您好！我叫{patient}，家住{country}，于{year}年{mounth}月{day}日，在{hospital}被查出患有{sick}，疾病的噩耗让我的精神几乎崩溃，高额的费用让原本不太富裕的家庭陷入了绝境，现在已经花去{money}元，后面还需要一系列的康复治疗，家庭的经济实力已难以支撑下去，现发起筹款希望好心人能多帮帮我，今后我有了能力，一定将这份无私的爱回馈社会。感谢大家！",
            "我叫{patient}，家住{country}。世事无常，{mounth}月{day}日突感不适，在{hospital}经过一系列的检查，最终被确诊为{sick}；那一刻对我而言就像天塌了一样，幸好家人一直在身边鼓励我，朋友也在安慰我，才让我有了治疗下去的勇气；经过一系列的治疗，病情基本控制住，但是巨额的医疗费用已经让我们家已经负债累累，真的撑不下去了......病魔无情，人间有爱，希望大家帮帮我，哪怕是仅仅转发，谢谢大家！好人一生平安！",
    };

    private static String[] her_infoTpl = new String[]{
            "大家好，我叫{patient}，家住{country}，天有不测风云，原本幸福美满的家庭却被一场突如其来的大病拖垮。我的{who}{name}， 在{year}年{mounth}月{day}日，突感不适，被送往{hospital}紧急救治，最后确诊为{sick}，如今已经花去{money}元，目前病情虽然已经控制住了，但后期治疗费用还需要很多资金，朋友亲戚该借的都借了，实在是无力承担这笔天大的费用。请大家救救我可怜的{who}，我还没来得及好好陪{who}，大家的恩情我一定会铭记在心，感恩，好人一生平安！",
            "好心人，您好！我叫{patient}，家住{country}，我的{who}{name}于{year}年{mounth}月{day}日，在{hospital}被查出患有{sick}，疾病的噩耗让我的精神几乎崩溃，高额的费用让原本不太富裕的家庭陷入了绝境，现在已经花去{money}元，后面还需要一系列的康复治疗，家庭的经济实力已难以支撑下去，希望好心人帮我的{who}度过这个难关，感激不尽！",
            "我叫{patient}，家住{country}。世事无常，{mounth}月{day}日我的{who}{name}突感不适，在{hospital}经过一系列的检查，最终被确诊为{sick}；那一刻感觉就像天塌了一样，一边要鼓励我{who}积极配合治疗，一边还要安慰家人，同时还要为巨额的医疗费用担忧着。虽然经过一系列的治疗，{who}的病情基本得控制住，但是巨额的医疗费用已经让我们家已经负债累累，真的撑不下去了......病魔无情，人间有爱，希望大家帮帮我的{who}，哪怕是仅仅转发，谢谢大家！好人一生平安！",
    };

    /**
     * 获取随机标题
     */
    public static String autoCreateTitleString(String name, String sickName, String who, String country, String year, String month,
                                               String day, String hospital, String sick, String money, int number) {
        String title;
        if ("本人".equals(who) || "其他" .equals(who)) {
            title = self_titleTpl[number];
        } else {
            title = her_titleTpl[number];
        }
        title = replaceString(title, name, sickName, who, country, year, month, day, hospital, sick, money);
        return title;
    }

    /**
     * 获取随机内容
     */
    public static String autoCreateContentString(String name, String sickName, String who, String country, String year, String month,
                                                 String day, String hospital, String sick, String money, int number) {
        String title;
        if ("本人".equals(who) || "其他" .equals(who)) {
            title = self_infoTpl[number];
        } else {
            title = her_infoTpl[number];
        }
        title = replaceString(title, name, sickName, who, country, year, month, day, hospital, sick, money);
        return title;
    }

    /**
     * 【患者姓名】替换{name}，【为谁筹款】替换{who}，【患者家乡】替换{country}，
     * 【确证日期年】替换{year}，【确证日期月】替换{mounth}，【确证日期日】替换{day}，【所在医院】替换{hospital}，
     * 【疾病名称】替换{sick}，【已花费金额】替换{money}
     */
    private static String replaceString(String title, String name, String sickName, String who, String country, String year, String month,
                                        String day, String hospital, String sick, String money) {
        if ("本人".equals(who) || "其他" .equals(who)) {
            title = title.replace("{patient}", sickName);
        } else {
            title = title.replace("{patient}", name);
        }
        title = title.replace("{name}", sickName);
        title = title.replace("{who}", who);
        title = title.replace("{country}", country);
        title = title.replace("{year}", year);
        title = title.replace("{mounth}", month);
        title = title.replace("{day}", day);
        title = title.replace("{hospital}", hospital);
        title = title.replace("{sick}", sick);
        title = title.replace("{money}", money);

        return title;
    }

    /**
     * 获取title随机数
     */
    public static int getTitleRandomNumber(String who) {
        int number;
        if ("本人".equals(who) || "其他" .equals(who)) {
            number = new Random().nextInt(self_titleTpl.length);
        } else {
            number = new Random().nextInt(her_infoTpl.length);
        }
        return number;
    }

    /**
     * 获取content随机数
     */
    public static int getContentRandomNumber(String who) {
        int number;
        if ("本人".equals(who) || "其他" .equals(who)) {
            number = new Random().nextInt(self_infoTpl.length);
        } else {
            number = new Random().nextInt(her_infoTpl.length);
        }
        return number;
    }
}

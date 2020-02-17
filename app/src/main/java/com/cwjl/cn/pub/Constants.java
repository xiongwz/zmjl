package com.cwjl.cn.pub;

/**
 * Created by xiyuan on 2018/7/17.
 */

public class Constants {

    // QQ APP_ID
    public static final String APP_ID_QQ = "101488183";
    // 微信APP_ID 通过登录时接口动态获取
    public static String APP_ID_WX = "wx2f29da1e71380ddc";
    // 微信支付APP_ID 通过登录时接口动态获取
    public static String APP_PAY_ID_WX = "wx2f29da1e71380ddc";
    // 拼单微信APP_ID
    public static final String APP_ID_WX_SPELL = "wx5ddb244945103bdf";
    // 微信登录成功后返回的code
    public static String WECHAT_CODE = "";
    // 微信APP_ID
    public static final String APP_ID_WB = "3944664736";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://www.zhentianxia.net";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     *
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     *
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     *
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    // 应用登录后返回的token
    public static String TOKEN = "";

    // 服务器地址
    public static String HOST ;
    // 服务器图片地址
    public static String PIC_HOST ;
    // socket地址
    public static String SOCKET_CHAT;
    // IM图片地址
    public static String PIC_HOST_IM = "http://dcsvip1imapp.cloopen.net:8888" ;
    // 是否显示引导页
    public static String IS_SHOW_GUIDE ="is_show_guide";
    // 是否显示首页欢迎弹出框
    public static String IS_SHOW_WELCOME_WINDOW ="is_show_welcome_window";
    // 是否显示发起拼单弹出框
    public static String IS_SHOW_SEND_SPELL_WINDOW ="is_show_send_spell_window";
    // 是否自动登录
    public static String AUTO_LOGIN="auto_login";
    // 是否第一次申请志愿者
    public static String FIRST_APPLY_VOLUNTEER="first_apply_volunteer";
    // 用户信息
    public static String USER_INFO_MODEL="userInfoModel";
    // 普通用户信息
    public static String ORDINARY_USER_INFO_MODEL="ordinaryUserInfoModel";
    // 用户登录token
    public static String LOGIN_TOKEN="login_token";
    // 用户名
    public static String LOGIN_ID="id";
    // 用户密码
    public static String LOGIN_PASSWORD="login_password";
    // 用户关联志愿者
    public static String RELEVANCE_VOLUNTEER="relevance_password";
    // 用户类型
    public static String USER_TYPE="user_type";
    // 经销商用户
    public static String AGENT_USER="agent_user";
    // 普通商用户
    public static String ORDINARY_USER="ordinary_user";
    // 设备号
    public static String DEVICES_NUMBER = "device_number";
    // bug检测id
    public static String BUG_ID = "f288289b919b48e605cbc5f1b48bcf90";

    //云通讯
    public static final String YUNTONGXUN_APPKER_CODE = "8aaf0708697b6beb0169b8ddd78125ca";
    public static final String YUNTONGXUN_TOKEN = "08a96c911caa1401a7304cc1eaf2c917";
}

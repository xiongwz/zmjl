package com.cwjl.cn.pub;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseActivity;
import com.cwjl.cn.base.BasePresenter;
import com.cwjl.cn.utils.AvToast;
import com.cwjl.cn.utils.PLog;
import com.cwjl.cn.utils.Util;
import com.cwjl.cn.view.TopViewLayout;

import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    public final static String WEB_TITLE = "title";
    public final static String WEB_URL = "url";
    public final static String HIDE_FOOT = "hide_foot";
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.topview)
    TopViewLayout mTopview;
    @BindView(R.id.ll_layout)
    LinearLayout mLyoutLl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_layout;
    }

    @Override
    public void initData() {
        mTopview.setFinishActivity(this);
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mSettings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        mSettings.setSupportZoom(true);//是否可以缩放，默认true
        mSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式setBlockNetworkImage
        mSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mSettings.setAppCacheEnabled(true);//是否使用缓存
        mSettings.setDomStorageEnabled(true);//DOM Storage
        mSettings.setBlockNetworkImage(false);
        // 允许从任何来源加载内容，即使起源是不安全的；
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(WEB_TITLE)) {
            String title = intent.getStringExtra(WEB_TITLE);
            mTopview.setTitle(title);
        }
        if (intent.hasExtra(HIDE_FOOT)) {
            boolean hideFoot = intent.getBooleanExtra(HIDE_FOOT, false);
            if (hideFoot) {
                int bottom = Util.dp2px(this.getResources(), -55);
                mLyoutLl.setPadding(0, 0, 0, bottom);
            }
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                PLog.e("------------------webview progress----"+newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        if (intent.hasExtra(WEB_URL)) {
            String url = intent.getStringExtra(WEB_URL);
            mWebView.loadUrl(url);
        } else {
            mWebView.loadUrl("https://www.dishuihuzhu.cn/Plan/seriousillnesslist"); // 正式
//            mWebView.loadUrl("http://192.168.3.17:8081/Plan/seriousillnesslist");   // 测试
//            finish();
        }

        mWebView.addJavascriptInterface(new JsShare(), "androidJsShare");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.tv_share})
    void click(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
                mWebView.loadUrl("javascript:shareInfoAction()");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if (mWebView.canGoBack()) {
                mWebView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }

    public class JsShare{
        @JavascriptInterface
        public void share(String url, String title, final String desc, String picPath) {
            if (TextUtils.isEmpty(url) && TextUtils.isEmpty(title) && TextUtils.isEmpty(picPath)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AvToast.makeText(WebViewActivity.this, desc);
                        return;
                    }
                });
            } else {
//                ShareUtil.showShare2((BaseActivity) mContext, url, title, desc, picPath, false);
            }
        }
    }
}

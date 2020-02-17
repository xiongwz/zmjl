package com.cwjl.cn.utils;


import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.cwjl.cn.R;

/**
 * Created by xiyuan on 2018/8/22.
 */
public class CountDownTimerUtils extends CountDownTimer {

    private Context mContext;
    private TextView mTextView;

    /**
     * @param textView          The TextView
     *
     *
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receiver
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(Context context, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mContext = context;
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("重新获取" + millisUntilFinished / 1000 + "S");  //设置倒计时时间
        mTextView.setTextColor(mContext.getResources().getColor(R.color.text_color_bc));
        mTextView.setBackgroundResource(R.drawable.gray_background); //设置按钮为灰色，这时是不能点击的
        mTextView.setPadding(Util.dp2px(mContext.getResources(), 20), Util.dp2px(mContext.getResources(), 8), Util.dp2px(mContext.getResources(), 20), Util.dp2px(mContext.getResources(), 8));
    }

    @Override
    public void onFinish() {
        mTextView.setText("获取验证码");
        mTextView.setTextColor(mContext.getResources().getColor(R.color.text_color_28));
        mTextView.setClickable(true);//重新获得点击
        mTextView.setBackgroundResource(R.drawable.black_background);  //还原背景色
        mTextView.setPadding(Util.dp2px(mContext.getResources(), 20), Util.dp2px(mContext.getResources(), 8), Util.dp2px(mContext.getResources(), 20), Util.dp2px(mContext.getResources(), 8));
    }
}


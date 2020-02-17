package com.cwjl.cn.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cwjl.cn.R;

/**
 * Created by user on 2017/1/2.
 */

public class LoadDialog extends Dialog {

    private int buju;
    private ImageView linearLayout;
    private Animation animation;

    public LoadDialog(Context context, int theme, int buju) {
        super(context, theme);
        this.buju = buju;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(buju);
        linearLayout = (ImageView) findViewById(R.id.main);
            animation= AnimationUtils.loadAnimation(getContext(),R.anim.load_anim);
    }

    @Override
    public void show() {
        super.show();
//        animation(1000);
        linearLayout.startAnimation(animation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animation.cancel();
    }
//    public void animation(int mDuration) {
//        AnimatorSet animatorSet = new_message AnimatorSet();
////        linearLayout.startAnimation(animation);
//        animatorSet.playTogether(
////                //设置view按照Y周旋转的过程，从-90°到0°
////                ObjectAnimator.ofFloat(this, "rotationY", -90, 0).setDuration(mDuration),
////                //设置view在X轴的位移过程
////                ObjectAnimator.ofFloat(this, "translationX", -300, 0).setDuration(mDuration),
////
////                //定义view的旋转过程，从1080度转到0度，旋转两圈
//                ObjectAnimator.ofFloat(linearLayout, "rotation", 0,360).setDuration(mDuration)
//
////                //定义view的透明度从全隐，到全显的过程，setDuration(mDuration)是设置动画时间
////                ObjectAnimator.ofFloat(this, "alpha", 0, 1).setDuration(mDuration * 3 / 2),
//                //定义view基于scaleX的缩放过程
////                ObjectAnimator.ofFloat(linearLayout, "scaleX", 0.1f, 0.5f, 1).setDuration(mDuration),
////                ObjectAnimator.ofFloat(linearLayout, "scaleY", 0.1f, 0.5f, 1).setDuration(mDuration)
//        );
//
//        animatorSet.start();
//    }
}

package com.cwjl.cn.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cwjl.cn.R;
import com.cwjl.cn.utils.Util;

import androidx.core.content.ContextCompat;

/**
 * 顶部view
 * Created by wangjinxue on 16/11/11.
 */

public class TopViewLayout extends RelativeLayout {

    private Context mContext;
    public ImageView mBackImage;
    private TextView mTitleView;
    private ImageView mRightMenu;

    LayoutParams mBackParams;
    LayoutParams mTitleParams;
    LayoutParams mMenuParams;
    public final static int WARP_CONTENT = LayoutParams.WRAP_CONTENT;
    public final static int MATCH_PARENT = LayoutParams.MATCH_PARENT;

    public TopViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopViewLayout(Context context) {
        this(context, null);
    }

    String mTitle;
    int mLeftImage;
    int rightImage;
    int background;
    int dp5;
    int dp15;
    int dp20;
    int dp30;

    public TopViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (isInEditMode())
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopViewLayout, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.TopViewLayout_topview_left:
                    mLeftImage = array.getResourceId(attr, -1);
                    break;
                case R.styleable.TopViewLayout_topview_title:
                    mTitle = array.getString(attr);
                    break;
                case R.styleable.TopViewLayout_topview_right:
                    rightImage = array.getResourceId(attr, -1);
                    break;
                case R.styleable.TopViewLayout_topview_background:
                    background = array.getResourceId(attr, -1);
                    break;
                default:
                    break;
            }
        }
        dp5 = Util.dp2px(getResources(), 15);
        dp15 = Util.dp2px(getResources(), 15);
        dp20 = Util.dp2px(getResources(), 20);
        dp30 = Util.dp2px(getResources(), 30);
        mBackImage = new ImageView(mContext);
        mTitleView = new TextView(mContext);
        mRightMenu = new ImageView(mContext);

        mBackParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mTitleParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mMenuParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);

        mTitleParams.addRule(CENTER_IN_PARENT);

        mMenuParams.addRule(ALIGN_PARENT_RIGHT);
        mMenuParams.addRule(CENTER_VERTICAL);
        mRightMenu.setPadding(dp30, Util.dp2px(getResources(), 20), dp15,Util.dp2px(getResources(), 5));
        mBackParams.addRule(CENTER_VERTICAL);
        mBackImage.setPadding(dp15, Util.dp2px(getResources(), 20), dp30,Util.dp2px(getResources(), 5));

        addView(mBackImage, mBackParams);
        addView(mTitleView, mTitleParams);
        addView(mRightMenu, mMenuParams);
        mTitleView.setText(mTitle);
        mTitleView.setMaxLines(1);
        mTitleView.setPadding(5 * 20, Util.dp2px(getResources(), 20), 5 * 20, Util.dp2px(getResources(), 5));

        if (rightImage > 0) {
            mRightMenu.setImageResource(rightImage);
        }
        if (mLeftImage > 0) {
            mBackImage.setImageResource(mLeftImage);
        }
        if (background > 0) {
            setBackgroundResource(background);
        } else {
            setBackgroundResource(R.drawable.shape_gradient_horizontal_red2);
        }
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        mTitleView.setTextColor(ContextCompat.getColor(context, R.color.white));
        array.recycle();
    }


    public void setTitle(String text) {
        mTitleView.setText(text);
    }

    public void setTitleColor(int color) {
        mTitleView.setTextColor(color);
    }

    public void setRightMenuRes(int resId) {
        this.mRightMenu.setImageResource(resId);
    }

    public void setBackImageRes(int resid) {
        this.mBackImage.setImageResource(resid);
    }

    public void setRightMenuListener(OnClickListener mClick) {
        if (mClick != null) {
            mRightMenu.setOnClickListener(mClick);
        }
    }

    public void setBackOnClickListener(OnClickListener mL) {
        if (mL != null) {
            mBackImage.setOnClickListener(mL);
        }
    }

    /**
     * 设置返回按钮的便利方法
     *
     * @param mActivity
     */
    public void setFinishActivity(final Activity mActivity) {
//       if(mBackImage.)
        mBackImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }
}

package com.cwjl.cn.view.loadmore;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.cwjl.cn.base.BaseLoadAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xiyuan on 2018/8/20.
 * 加载更多
 */

public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView recyclerView;
    private boolean mLogin;
    private Context mContext;
    private int mCurrentY;
    private PopupWindow window;
    private int totalY;
    private OnBackTopListener mOnBackTopListener;

    public LoadMoreScrollListener(Context context, RecyclerView recyclerView, boolean login) {
        this.mContext = context;
        this.recyclerView = recyclerView;
        mLogin = login;
    }

    public void setBackTopListener(OnBackTopListener listener) {
        this.mOnBackTopListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        BaseLoadAdapter adapter = (BaseLoadAdapter) recyclerView.getAdapter();

        if (null == manager) {
            throw new RuntimeException("you should call setLayoutManager() first!!");
        }

        if (manager instanceof LinearLayoutManager) {
            int lastVisibleItemPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();

            Log.v("dyp", "lastVisibleItemPosition:" + lastVisibleItemPosition + ",adapter.getItemCount()-1 :" + (adapter.getItemCount() - 1) + ",hasMore:" + adapter.mHasMore);

            if (adapter.mHasMore && adapter.getItemCount() > adapter.getPageCount() && adapter.getItemCount() - 1 == lastVisibleItemPosition) {
                if (!adapter.isLoading()) {
//                        if (mLogin && (!PreferenceUtil.getInstance(mContext).getBoolean(Constants.AUTO_LOGIN, false) || "".equals(Constants.TOKEN))) {
//                            if (dy >= mCurrentY) {
//                                mCurrentY = dy;
//                                showLoginWindow();
//                            }
//                        } else {
                            adapter.LoadingMore();
//                        }
                }
            }
        }
        totalY += dy;
        if (null != mOnBackTopListener) {
            mOnBackTopListener.onBackTop(recyclerView, totalY);
        }
    }

//    // 弹出登录提示窗口
//    private void showLoginWindow() {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_login, null);
//        if (null == window) {
//            window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
//            window.setOutsideTouchable(true);
//            window.setBackgroundDrawable(new BitmapDrawable());
//            window.setTouchable(true);
//            window.setTouchInterceptor(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
//        }
//        if (!window.isShowing()) {
//            window.showAtLocation(view, Gravity.CENTER, 0, 0);
//        }
//        LoadingEvent event = new LoadingEvent();
//        event.isLoading = true;
//        EventBus.getDefault().post(event);
//
//        final TextView sureTv = view.findViewById(R.id.tv_sure);
//        sureTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                window.dismiss();
//                mContext.startActivity(new Intent(mContext, LoginActivity.class));
//            }
//        });
//        final TextView cancelTv = view.findViewById(R.id.tv_cancel);
//        cancelTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                window.dismiss();
//            }
//        });
//    }

    public interface OnBackTopListener{

        void onBackTop(View view, int dy);
    };
}

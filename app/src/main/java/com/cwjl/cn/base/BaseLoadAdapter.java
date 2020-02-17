package com.cwjl.cn.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cwjl.cn.ContextApplication;
import com.cwjl.cn.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xiyuan on 2018/8/20.
 * baseadapter
 */

public abstract class BaseLoadAdapter<T> extends RecyclerView.Adapter {


    public static final int TYPE_ITEM = 1;
    public static final int TYPE_BOTTOM = 2;

    public int mLoadState;
    public static final int STATE_LOADING = 1;
    public static final int STATE_LASTED = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_NO_LOADING = 4;

    public boolean mHasMore = true;
    public boolean mIsLoading = false;
    public boolean mHideFoot = false;
    public int mBottomHeight;
    public int mBottomColor;

    private int mPageCount = 5;//每一页和后台说定的条数
    public List<T> mList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOTTOM) {
            if (!mHideFoot)
                return new BottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_layout, parent, false));
            else
                return new BottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_layout_nomal, parent, false));
        } else {
            return setItemViewHolder(parent, viewType);
        }
    }

    protected abstract RecyclerView.ViewHolder setItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (TYPE_BOTTOM == getItemViewType(position)) {

            if (!mHideFoot) {
                final ProgressBar progressBar = ((BottomViewHolder) holder).progressBar;
                final TextView bottomTextView = ((BottomViewHolder) holder).bottomTextView;
                final ImageView bottomIcon = ((BottomViewHolder) holder).bottomIcon;
                final LinearLayout bottomLayout = ((BottomViewHolder) holder).bottomLayout;

                if (0 != mBottomHeight) {
                    ViewGroup.LayoutParams params = bottomLayout.getLayoutParams();
                    params.height = mBottomHeight;
                    bottomLayout.setLayoutParams(params);
                }
                if (0 != mBottomColor) {
                    bottomLayout.setBackgroundColor(ContextApplication.mContext.getResources().getColor(mBottomColor));
                }
                switch (mLoadState) {
                    case STATE_LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        bottomIcon.setVisibility(View.GONE);
                        bottomTextView.setText("加载中");
                        holder.itemView.setOnClickListener(null);
                        mHasMore = true;
                        break;
                    case STATE_LASTED:
                        progressBar.setVisibility(View.GONE);
                        bottomIcon.setVisibility(View.VISIBLE);
                        bottomIcon.setImageResource(R.mipmap.info_icon);
                        bottomTextView.setText("没有更多了");
                        holder.itemView.setOnClickListener(null);
                        mHasMore = false;
                        break;
                    case STATE_ERROR:
                        progressBar.setVisibility(View.GONE);
                        bottomIcon.setVisibility(View.VISIBLE);
                        bottomIcon.setImageResource(R.mipmap.error_icon);
                        bottomTextView.setText("加载失败请点击重试");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressBar.setVisibility(View.VISIBLE);
                                bottomIcon.setVisibility(View.GONE);
                                bottomTextView.setText("加载中");
                                LoadingMore();
                            }
                        });
                        mHasMore = true;
                        break;
                    case STATE_NO_LOADING:
                        progressBar.setVisibility(View.GONE);
                        bottomIcon.setVisibility(View.GONE);
                        bottomTextView.setText("");
                        holder.itemView.setOnClickListener(null);
                        mHasMore = true;
                        break;
                }
            }
        } else {
            onBindItemViewHolder(holder, position);
        }
    }

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (mList.size() < mPageCount) {
            return TYPE_ITEM;
        } else {
            if (position == mList.size()) {
                return TYPE_BOTTOM;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() < mPageCount) {
//            mHasMore = false;
            return mList.size();
        } else {
            return mList.size() + 1;
        }
    }


    public abstract void LoadingMore();

    public void setErrorStatus() {
        mLoadState = STATE_ERROR;
        notifyItemChanged(mList.size());
        setLoading(false);
    }

    public void setLastedStatus() {
        mLoadState = STATE_LASTED;
        notifyItemChanged(mList.size());
    }

    public void hideBottomViewHolder(boolean flag) {
        mHideFoot = flag;
    }

    public void setBottomHeight(int height) {
        mBottomHeight = height;
    }

    public void setBottomBackColor(int color) {
        mBottomColor = color;
    }

    public void addList(List addList) {
        int count = this.mList.size();
        this.mList.addAll(addList);
        notifyItemRangeChanged(count, addList.size());
        setLoading(false);
    }

    public void refreshList(List newList) {
        this.mList.clear();
        this.mList.addAll(newList);
        notifyDataSetChanged();
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {

        TextView bottomTextView;
        ImageView bottomIcon;
        ProgressBar progressBar;
        LinearLayout bottomLayout;

        public BottomViewHolder(View itemView) {
            super(itemView);
            if (!mHideFoot) {
                bottomTextView = (TextView) itemView.findViewById(R.id.bottom_title);
                bottomIcon = (ImageView) itemView.findViewById(R.id.bottom_icon);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
                bottomLayout = (LinearLayout) itemView.findViewById(R.id.ll_bottom);
            }
        }
    }

    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public boolean isHasMore() {
        return mHasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.mHasMore = hasMore;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        mLoadState = STATE_NO_LOADING;
        notifyItemChanged(mList.size());
    }

}

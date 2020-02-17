package com.cwjl.cn.mvp.home;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cwjl.cn.ContextApplication;
import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseLoadAdapter;
import com.cwjl.cn.utils.StringUtil;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

class GoodsListAdapter extends BaseLoadAdapter<GoodsModel> {

    private Context mContext;
    private LoadMoreListener mListener;
    private OnItemSelectedListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemSelectedListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public GoodsListAdapter(Context context, List<GoodsModel> list, LoadMoreListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, parent, false));
        return holder;
    }

    @Override
    protected RecyclerView.ViewHolder setItemViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final GoodsModel model = mList.get(position);
        if (!TextUtils.isEmpty(model.image)) {
            Glide.with(mContext).load(model.image).error(R.mipmap.default_square).placeholder(R.mipmap.default_square).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    Drawable current = resource.getCurrent();
                    ((MyViewHolder) holder).mImageIv.setImageDrawable(current);
                }
            });
        }

        if (!TextUtils.isDigitsOnly(model.name))
            ((MyViewHolder) holder).mNameTv.setText(model.name);

        ((MyViewHolder) holder).mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null && position < mList.size()) {
                    mOnItemClickListener.onItemSelected(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void LoadingMore() {
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageIv;
        TextView mNameTv;
        LinearLayout mLayout;

        public MyViewHolder(View view) {
            super(view);
            mImageIv = (ImageView) view.findViewById(R.id.iv_image);
            mNameTv = (TextView) view.findViewById(R.id.tv_name);
            mLayout = (LinearLayout) view.findViewById(R.id.item_layout);
        }
    }

    /**
     * item点击接口
     */
    public interface OnItemSelectedListener {
        /**
         * item点击事件
         *
         * @param view
         * @param position
         */
        void onItemSelected(View view, int position);
    }

    /**
     * 加载更多接口
     */
    public interface LoadMoreListener{
        void loadMoreData();
    }
}

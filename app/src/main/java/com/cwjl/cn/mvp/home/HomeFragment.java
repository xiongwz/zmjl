package com.cwjl.cn.mvp.home;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwjl.cn.R;
import com.cwjl.cn.base.BaseFragment;
import com.cwjl.cn.base.BasePresenter;
import com.cwjl.cn.mvp.home.details.GoodsDetailsActivity;
import com.cwjl.cn.utils.Util;
import com.cwjl.cn.view.RecycleViewDivider;
import com.cwjl.cn.view.headerrecycleview.HeaderRecyclerView;
import com.cwjl.cn.view.loadmore.LoadMoreScrollListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView, GoodsListAdapter.LoadMoreListener {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mReFreshLayout;
    @BindView(R.id.recycleview)
    HeaderRecyclerView mRecyclerView;
    @BindView(R.id.ll_empty)
    LinearLayout mEmptyLl;
    @BindView(R.id.tv_empty)
    TextView mEmptyTv;

    private GoodsListAdapter mAdapter;
    private List<GoodsModel> mDataList = new ArrayList<>();
    private int mPageSize = 10; // 每页显示的记录数量
    private int mPageIndex = 1; // 获取的页数
    private int mPageCount; // 总页数


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        mReFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, Util.dp2px(getResources(), 2), getResources().getColor(R.color.white)));
        mRecyclerView.setOnScrollListener(new LoadMoreScrollListener(mContext, mRecyclerView, false));
        mAdapter = new GoodsListAdapter(mContext, mDataList, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(mContext, GoodsDetailsActivity.class);
                intent.putExtra("id", mDataList.get(position).id);
                startActivity(intent);
            }
        });

        showLoading();
        loadData();
    }

    private void loadData() {
        mDataList.clear();
        mAdapter.notifyDataSetChanged();
        mPageIndex = 1;
        mPresenter.getGoodsList();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void getGoodsList(GoodsDataModel model) {
        mReFreshLayout.setRefreshing(false);//取消刷新
        hideLoading();
        mAdapter.setLoading(false);
        if (null != model && null != model.data && model.data.size() > 0) {
            if (0 == mPageCount) {
//                mPageCount = model.data.size();
                LinearLayout footer = new LinearLayout(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = Util.dp2px(getResources(), 80);
                footer.setLayoutParams(params);
                mRecyclerView.addFooterView(footer);
            }
            mDataList.addAll(model.data);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.setLastedStatus();
            mAdapter.notifyDataSetChanged();
        }
        if (mDataList.size() > 0)
            mEmptyLl.setVisibility(View.GONE);
        else {
            mEmptyTv.setText("暂无数据");
            mEmptyLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadMoreData() {
        if(mAdapter.isLoading()){
            return;
        }
        mAdapter.setLoading(true);
        if (mPageIndex < mPageCount) {
            mPageIndex ++;
            mPresenter.getGoodsList();
        } else {
            mAdapter.setLastedStatus();
        }
    }
}

package com.cwjl.cn.mvp.home.details;

import com.cwjl.cn.base.BasePresenter;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class GoodsDetailsPresenter extends BasePresenter<GoodsDetailsView> {

    public GoodsDetailsPresenter(GoodsDetailsView baseView) {
        super(baseView);
    }

    /**
     * 获取宠物详情
     */
    public void getGoodsDetails(String id) {
        //获取Call对象
        Call<ResponseBody> call = apiServer.getGoodsDetails(id);
        //开始异步操作
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String json = null;
                try {
                    json = response.body().string();
                    Gson gson = new Gson();
                    GoodsDetailsModel model = gson.fromJson(json,GoodsDetailsModel.class);//jsonTest是json字符串
                    baseView.getGoodsDetails(model);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseView.hideLoading();
            }
        });
    }
}

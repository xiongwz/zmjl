package com.cwjl.cn.mvp.home;

import com.cwjl.cn.base.BaseObserver;
import com.cwjl.cn.base.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter extends BasePresenter<HomeView> {

    public HomePresenter(HomeView baseView) {
        super(baseView);
    }

    /**
     * 获取宠物列表
     */
    public void getGoodsList() {
        //获取Call对象
        Call<ResponseBody> call = apiServer.getGoodsList();
        //开始异步操作
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String json = null;
                try {
                    List<GoodsModel> list = new ArrayList<GoodsModel>();
                    json = response.body().string();
                    Gson gson = new Gson();
                    JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
                    for (JsonElement jsonElement : arry) {
                        list.add(gson.fromJson(jsonElement, GoodsModel.class));
                    }
                    GoodsDataModel model = new GoodsDataModel();
                    model.data = list;
                    baseView.getGoodsList(model);
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

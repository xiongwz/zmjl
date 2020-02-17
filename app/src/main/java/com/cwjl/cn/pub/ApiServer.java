package com.cwjl.cn.pub;

import com.cwjl.cn.mvp.home.GoodsDataModel;
import com.google.gson.JsonArray;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求接口封装
 */

public interface ApiServer {

    /**
     * 获取宠物列表
     */
    @GET("/pets")
    Call<ResponseBody> getGoodsList();

    /**
     * 获取宠物详情
     */
    @GET("/pet/{id}")
    Call<ResponseBody> getGoodsDetails(@Path("id")String id);
}

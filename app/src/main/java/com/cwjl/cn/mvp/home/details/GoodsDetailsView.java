package com.cwjl.cn.mvp.home.details;

import com.cwjl.cn.base.BaseView;

interface GoodsDetailsView extends BaseView {

    /**
     * 获取宠物详情
     */
    void getGoodsDetails(GoodsDetailsModel model);
}

package com.cwjl.cn.mvp.home;

import com.cwjl.cn.base.BaseView;

interface HomeView extends BaseView {

    /**
     * 获取宠物列表
     */
    void getGoodsList(GoodsDataModel model);
}

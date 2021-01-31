package com.ming.sjll.base.event;

import com.ming.sjll.Bean.GoodsItem;

/**
 * 添加编辑商品
 */
public class GoodsEditEvent {
    public GoodsItem.DataBean param;
    public int oldPoint = 0 ;//id ，大于0编辑

    public GoodsEditEvent(GoodsItem.DataBean param , int oldPoint) {
        this.param = param;
        this.oldPoint = oldPoint;
    }
//    public void update(HttpParamsObject param)
//    {
//        ;
//    }
}

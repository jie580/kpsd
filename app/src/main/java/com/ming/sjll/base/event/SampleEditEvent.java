package com.ming.sjll.base.event;

import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.base.tool.http.HttpParamsObject;

import java.util.Observer;
/**
 * 添加编辑作品
 */
public class SampleEditEvent {
    public WorkItem.DataBean param;
    public int oldPoint = 0 ;//作品id ，大于0编辑

    public SampleEditEvent(WorkItem.DataBean param , int oldPoint) {
        this.param = param;
        this.oldPoint = oldPoint;
    }
//    public void update(HttpParamsObject param)
//    {
//        ;
//    }
}

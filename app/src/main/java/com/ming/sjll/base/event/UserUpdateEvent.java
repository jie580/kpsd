package com.ming.sjll.base.event;

import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.WorkItem;

import java.util.List;

/**
 * 用户信息更新
 */
public class UserUpdateEvent {
    public String value;
    public Object objectValue;
//    public List<Object> listObjectValue;
    public String updateKey = "" ;//作品id ，大于0编辑

    public UserUpdateEvent( String key, String value) {
        this.value = value;
        this.updateKey = key;
    }

    public UserUpdateEvent( String key, Object value) {
        this.objectValue = value;
        this.updateKey = key;
    }

//    public UserUpdateEvent( String key, List<Object> value) {
//        this.listObjectValue = value;
//        this.updateKey = key;
//    }
//    public void update(HttpParamsObject param)
//    {
//        ;
//    }
}

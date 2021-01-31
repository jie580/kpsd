package com.ming.sjll.base.tool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.ming.sjll.ui.PagedListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MyUtil {

    public static boolean ListStringHasItem(List<String> list, String str)
    {
        for (String temp : list) {
            if (temp.contains(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制对象
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T cloneObject(T obj) {
        T result = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            //对象写到内存中
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(obj);

            //从内存中再读出来
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            inputStream = new ObjectInputStream(byteArrayInputStream);
            result = (T) inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
                if (byteArrayOutputStream != null)
                    byteArrayOutputStream.close();
                if (byteArrayInputStream != null)
                    byteArrayInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     *  重置listView高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(PagedListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //获得Adapter
        if (listAdapter == null) {  //判断是否为空
            return;
        }
        int totalHeight = 0;  //定义总高度
        //根据listAdapter.getCount()获取当前拥有多少个item项，然后进行遍历对每一个item获取高度再相加最终获得总的高度。
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //获取到list的布局属性
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //listview最终高度为item的高度+分隔线的高度，这是重新设置listview的属性
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//将重新设置的params再应用到listview中
        listView.setLayoutParams(params);
    }

}

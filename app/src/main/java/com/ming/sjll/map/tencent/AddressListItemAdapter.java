package com.ming.sjll.map.tencent;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.ui.AntoLineUtil;
import com.tencent.lbssearch.httpresponse.Poi;
import com.tencent.map.geolocation.TencentLocationUtils;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

import java.math.BigDecimal;
import java.util.List;

public class AddressListItemAdapter extends BaseAdapter {

    setOnClickListener listener;
    List<Poi> mData;
    LatLng selectLatLng;
    LatLng myLatLng;
    private Context mContext = null; // 上下文环境

    public AddressListItemAdapter(Context context, List<Poi> arr, LatLng selectLatLng,LatLng myLatLng) {
        mData = arr;
        mContext = context;
        this.selectLatLng = selectLatLng;
        this.myLatLng = myLatLng;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private String distanceBetween(LatLng latLng)
    {
        double distance = TencentLocationUtils.distanceBetween(latLng.latitude,latLng.longitude,myLatLng.getLatitude(),myLatLng.getLongitude());
        if(distance <= 100)
        {
            return "100m内";
        }
        if(distance <= 1000)
        {
            return ( new BigDecimal(distance).setScale(0,BigDecimal.ROUND_HALF_UP))+"m";
//            return Integer.parseInt(distance+"")+"m";
        }
        else {
           ;
            return ( new BigDecimal(distance /1000).setScale(1,BigDecimal.ROUND_HALF_UP))+"Km";
        }
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {


        Poi data = mData.get(pos);

        convertView = LayoutInflater.from(mContext).inflate(R.layout.map_tencent_list_item, parent, false);


//        if(data.getIsChange() == false)
//        {
//            return convertView;
//        }
        //            项目

        RelativeLayout wrap = (RelativeLayout) convertView.findViewById(R.id.wrap);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        ImageView isSelect = (ImageView) convertView.findViewById(R.id.isSelect);
        title.setText(data.title);
        address.setText(distanceBetween(data.latLng)+" | "+data.address);

        isSelect.setVisibility(View.GONE);
        MyTencentMap m = (MyTencentMap)mContext;
        if(m.selectLocationLatLng.equals(data.latLng))
        {
            isSelect.setVisibility(View.VISIBLE);
        }

        wrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.lookTimes, v);
                }
            }
        });


        return convertView;
    }

    /***
     * 事件列表
     */
    public interface setOnClickListener {
        /**
         * 点击
         *
         * @param pos
         */
        void onClick(int pos, @IdRes int id, View parent);

    }

    public void setOnClickListenerInterface(setOnClickListener listener) {
        this.listener = listener;

    }


}


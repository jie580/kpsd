package com.ming.sjll.Message.item;

import android.content.Intent;
import android.view.View;

import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.map.tencent.ShowTencentMap;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.plugin.location.AMapPreviewActivity2D;
import io.rong.imkit.widget.provider.LocationMessageItemProvider;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.LocationMessage;
import io.rong.message.TextMessage;

@ProviderTag( messageContent = LocationMessage.class )
public class CustomLocationMessageItemProvider extends LocationMessageItemProvider{
    @Override
    public void onItemClick(View view, int position, LocationMessage content, UIMessage message) {
//        super.onItemClick(view, position, content, message);

        Intent intent = new Intent(view.getContext(), ShowTencentMap.class);
        intent.putExtra("location", message.getContent());
        view.getContext().startActivity(intent);

    }
    //    @Override
//    public void bindView(View v, int position, TextMessage content, UIMessage data) {
//        super.bindView(v, position, content, data);
//        TextView textView = (TextView) v;
//        if (data.getMessageDirection() == Message.MessageDirection.SEND) {
//            textView.setTextColor(Color.WHITE);
//        } else {
//            textView.setTextColor(Color.BLACK);
//        }
//    }

//    @Override
//    public void bindView(View v, int position, TextMessage content, UIMessage data) {
//        super.bindView(v, position, content, data);
//        v.findViewById(16908308);
//        textView.setBackgroundResource(R.drawable.rc_ic_bubble_right_transparency);
//
//
//    }

}

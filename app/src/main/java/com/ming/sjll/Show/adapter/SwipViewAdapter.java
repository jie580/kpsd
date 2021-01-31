package com.ming.sjll.Show.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.Image.ImagePagerActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;


import org.raphets.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class SwipViewAdapter extends BaseAdapter {

    public static List<WorkItem.DataBean> list;

    Context context;
    private LayoutInflater l_Inflater;
    public static ViewHolder holder;

    ListenerClick listener;

    public SwipViewAdapter(Context mContext, List<WorkItem.DataBean> al) {
        this.context = mContext;
        list = al;
        l_Inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
//            holder = new ViewHolder();
//            l_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = l_Inflater.inflate(R.layout.show_item, viewGroup, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);


            ImageView showImage = (ImageView) convertView.findViewById(R.id.showImage);
            new ImageHelper().displayBackgroundLoading(  showImage,
                    Constant.BASE_API + list.get(i).getCoverImg());


            RoundImageView headImage = (RoundImageView) convertView.findViewById(R.id.headImage);
            new ImageHelper().displayBackgroundLoading(  headImage,
                    Constant.BASE_API + list.get(i).getDefault_avatar());

            //            作品列表
            LinearLayout showWorks = (LinearLayout) convertView.findViewById(R.id.showWorks);
//            其他
            LinearLayout linearClose = (LinearLayout) convertView.findViewById(R.id.linearClose);
            TextView nameClose = (TextView) convertView.findViewById(R.id.nameClose);
            TextView titleClose = (TextView) convertView.findViewById(R.id.titleClose);
            TextView describeClose = (TextView) convertView.findViewById(R.id.describeClose);

            LinearLayout linearOpen = (LinearLayout) convertView.findViewById(R.id.linearOpen);
            TextView nameOpen = (TextView) convertView.findViewById(R.id.nameOpen);
            TextView titleOpen = (TextView) convertView.findViewById(R.id.titleOpen);
            TextView describeOpen = (TextView) convertView.findViewById(R.id.describeOpen);

            nameClose.setText(list.get(i).getName());
            titleClose.setText(list.get(i).getTitle());
            describeClose.setText(list.get(i).getDescribe());

            nameOpen.setText(list.get(i).getName());
            titleOpen.setText(list.get(i).getTitle());
            describeOpen.setText(list.get(i).getDescribe());

            if(list.get(i).getIs_show_work())
            {
                linearClose.setVisibility(View.GONE);
                linearOpen.setVisibility(View.VISIBLE);
                LinearLayout  linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
                if(linearLayout.getChildCount() <= 0 && list.get(i).getWorkImg().size() > 0) {
                    Log.e("TAG", "顯示1");

                    List<LocalMedia> selectList = new ArrayList<>();
                    for (String img : list.get(i).getWorkImg()) {
                        LocalMedia localMedia = new LocalMedia(Constant.BASE_API + img, 0, 1, "image/jpeg");
                        selectList.add(localMedia);
                    }

                    for (int j = 0; j < list.get(i).getWorkImg().size(); j++) {
                        View worksView = l_Inflater.inflate(R.layout.show_item_works, null);
                        ImageView iv = (ImageView) worksView.findViewById(R.id.worksItem);
                        iv.setTag(j);
                        new ImageHelper().displayBackgroundLoading(iv,
                                Constant.BASE_API + list.get(i).getWorkImg().get(j));
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ArrayList<String> arrayList = new ArrayList<String>();
//                                arrayList =  list.get(i).getWorkImg();
//                                Intent intent = new Intent(context, ImagePagerActivity.class);
//                                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
//                                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, list.get(i).getWorkImg());
//                                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
//                                context.startActivity(intent);

                                 int indexOf = (int)v.getTag();
                                PictureSelector.create((Activity) context).themeStyle(R.style.picture_default_style).
                                        openExternalPreview(indexOf, selectList);
                            };
                        });


                        linearLayout.addView(worksView);
                    }
                }
            }
            else
            {
                linearClose.setVisibility(View.VISIBLE);
                linearOpen.setVisibility(View.GONE);
            }

            showWorks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(i, R.id.showWorks, v);
                    }
                }
            });
//            headImage.setBorderWidth(3)
//                    .setBorderColor(Color.RED)
//                    .setType(RoundImageView.TYPE_ROUND)
//                    .setLeftTopCornerRadius(0)
//                    .setRightTopCornerRadius(10)
//                    .setRightBottomCornerRadius(30)
//                    .setLeftBottomCornerRadius(50);
            headImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(i, R.id.headImage, v);
                    }
                }
            });

//            showImage.callOnClick();
//            Log.e("onItemClicked",itemPosition+"onItemClicked");
//            holder.ivZoomable = (TouchImageView) convertView.findViewById(R.id.ivZoomable);
//+"?"+ System.currentTimeMillis()
//+"?"+ System.currentTimeMillis()
//            convertView.setTag(holder);
        }



//        holder.ivZoomable.setImageURI(list.get(i).getCardImageDrawable());


        return convertView;
    }

    public void setListener(ListenerClick listener)
    {
        this.listener = listener;
    }

    public interface ListenerClick
    {
        void onClick(int pos, @IdRes int id, View parent);
    }

    public static class ViewHolder {
//        public static TouchImageView ivZoomable;
    }
}
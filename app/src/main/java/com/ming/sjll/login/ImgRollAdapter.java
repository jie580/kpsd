package com.ming.sjll.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.MvpFragment;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.login.bean.DefaultAvatar;
import com.ming.sjll.ui.HeadImgRollEvent;
import com.ming.sjll.ui.LocateCenterHorizontalView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.annotation.Nullable;

/**
 * 选择默认头像
 */
public class ImgRollAdapter extends RecyclerView.Adapter<ImgRollAdapter.AgeViewHolder>
        implements LocateCenterHorizontalView.IAutoLocateHorizontalView {
    private Context mContext;
    private View mView;
    private List<DefaultAvatar.DataBean> mDatas;
    private int circle;
    private LocateCenterHorizontalView recyclerview;

    public ImgRollAdapter(Context context, List<DefaultAvatar.DataBean> datas, int circle, LocateCenterHorizontalView recyclerview) {
        this.mContext = context;
        this.mDatas = datas;
        this.circle = circle;
        this.recyclerview = recyclerview;
    }

    @Override
    public AgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.login_img_roll_adapter, parent, false);
        return new AgeViewHolder(mView);
    }

    /**
     * 初始化
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(AgeViewHolder holder, final int position) {
        int size = mDatas.size();
        Log.e("背景图", Constant.BASE_API + mDatas.get(position % size).gethoto() + "<<<<<ID:" + mDatas.get(position % size).getId() + "");
        holder.name.setText(mDatas.get(position % size).getId() + "");
        ImageHelper.displayDef(((AgeViewHolder) holder).headimg, Constant.BASE_API + mDatas.get(position % size).gethoto(), R.mipmap.ic_launcher);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("产生点击", "产生点击");
                recyclerview.moveToPosition(position);
//                EventBus.getDefault().post(new HeadImgRollEvent(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size() * circle;
    }

    @Override
    public View getItemView() {
        return mView;
    }

    public List<DefaultAvatar.DataBean> getData() {
        return mDatas;
    }

    /**
     * 点击时
     *
     * @param isSelected 是否被选中
     * @param pos        当前view的位置
     * @param holder
     * @param itemWidth  当前整个item的宽度
     */
    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        int size = mDatas.size();

        Log.e("onViewSelected", "点击后");
        ImageHelper.displayDef(((AgeViewHolder) holder).headimg, Constant.BASE_API + mDatas.get(pos % size).gethoto(), R.mipmap.ic_launcher);
        if (isSelected) {
            ((AgeViewHolder) holder).name.setTextSize(20);
            ((AgeViewHolder) holder).name.setTextColor(Color.parseColor("#FD7422"));
        } else {
            ((AgeViewHolder) holder).name.setTextSize(14);
            ((AgeViewHolder) holder).name.setTextColor(Color.parseColor("#999999"));
        }
    }

    static class AgeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView headimg;

        AgeViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            headimg = itemView.findViewById(R.id.tv_select_image);
        }
    }


//
////    public static ImgRollAdapter newInstance(String  pic) {
////        ImgRollAdapter situationFragemt = new ImgRollAdapter();
////        Bundle bundle = new Bundle();
////        bundle.putParcelable("pic", pic);
////        situationFragemt.setArguments(bundle);
////        return situationFragemt;
////    }
//    public String cursorPic = "";
//    public String lastPic = "";
//
//    public ImgRollAdapter(@Nullable List<DefaultAvatar.DataBean> data) {
//        super(R.layout.login_img_roll_adapter, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder baseViewHolder, DefaultAvatar.DataBean dataBean) {
//    Log.e("convert","绘制中");
//
//            if(cursorPic.equals(""))
//            {
//                cursorPic = dataBean.toString();
//            }
//            ImageView cuItme = (ImageView) baseViewHolder.getView(R.id.tv_select_image);
//            ImageHelper.displayDef(cuItme, Constant.BASE_API + dataBean.gethoto(), R.mipmap.ic_launcher);
//            TextView txt  = (TextView)baseViewHolder.getView(R.id.textView);
//            txt.setText(dataBean.getId()+"");
//             baseViewHolder.addOnClickListener(R.id.tv_select_image);
//            int oh = dataBean.getOldHeight();
//             if( oh > 0)
//             cuItme.getLayoutParams().height =  dataBean.getOldHeight() * 2;
////             int cuHeight = cuItme.getLayoutParams().height;
////             dataBean.setOldHeight(0);
////
//////                             放大当前选择的图片
////            ImageView itme = (ImageView) baseViewHolder.getView(R.id.tv_select_image_consult);
////            int h = itme.getLayoutParams().height;
////             if(dataBean.toString().equals(cursorPic))
////             {
////
////
////                 int nh = (int)(h * 0.8);
////                 cuItme.getLayoutParams().height = nh;
////                 Log.e( "选择了图片：",cursorPic);
////             }
////             else
////             {
////                 cuItme.getLayoutParams().height = h;
////             }
//    }


}

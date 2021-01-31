package com.ming.sjll.login;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.login.bean.DefaultAvatar;

import java.util.List;

import javax.annotation.Nullable;

/**
 * 选择默认头像
 */
public class ImgRollAdapterTest extends BaseQuickAdapter<DefaultAvatar.DataBean, BaseViewHolder> {

    //
//    public static ImgRollAdapter newInstance(String  pic) {
//        ImgRollAdapter situationFragemt = new ImgRollAdapter();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("pic", pic);
//        situationFragemt.setArguments(bundle);
//        return situationFragemt;
//    }
    public String cursorPic = "";
    public String lastPic = "";

    public ImgRollAdapterTest(@Nullable List<DefaultAvatar.DataBean> data) {
        super(R.layout.login_img_roll_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DefaultAvatar.DataBean dataBean) {
        Log.e("convert", "绘制中");

        if (cursorPic.equals("")) {
            cursorPic = dataBean.toString();
        }
        ImageView cuItme = (ImageView) baseViewHolder.getView(R.id.tv_select_image);
        ImageHelper.displayBackground(cuItme, Constant.BASE_API + dataBean.gethoto(), R.mipmap.ic_launcher);
        TextView txt = (TextView) baseViewHolder.getView(R.id.textView);
        txt.setText(dataBean.getId() + "");
        baseViewHolder.addOnClickListener(R.id.tv_select_image);
        int oh = dataBean.getOldHeight();
        if (oh > 0)
            cuItme.getLayoutParams().height = dataBean.getOldHeight() * 2;
//             int cuHeight = cuItme.getLayoutParams().height;
//             dataBean.setOldHeight(0);
//
////                             放大当前选择的图片
//            ImageView itme = (ImageView) baseViewHolder.getView(R.id.tv_select_image_consult);
//            int h = itme.getLayoutParams().height;
//             if(dataBean.toString().equals(cursorPic))
//             {
//
//
//                 int nh = (int)(h * 0.8);
//                 cuItme.getLayoutParams().height = nh;
//                 Log.e( "选择了图片：",cursorPic);
//             }
//             else
//             {
//                 cuItme.getLayoutParams().height = h;
//             }
    }

}

package com.ming.sjll.My.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.rey.material.app.BottomSheetDialog;

import java.util.List;

/**
 * 个人中心-个人-职业-列表
 */
public class PersonageOccupationListItemAdapter extends BaseQuickAdapter<GetInfoBean.DataBean.occupationInfoBean.paymentBean, BaseViewHolder> {


    public boolean canEdit = true;

    public PersonageOccupationListItemAdapter(@Nullable List<GetInfoBean.DataBean.occupationInfoBean.paymentBean> data) {
        super(R.layout.my_personage_occupation_list_item_adapter, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean.occupationInfoBean.paymentBean dataBean) {
//         final  GetInfoBean.DataBean.occupationInfoBean.paymentBean dataBean = tempDataBean;

            if(mData.get(0).equals(dataBean))
            {
                baseViewHolder.getView(R.id.delete).setVisibility(View.GONE);
            }
            else
            {
                baseViewHolder.getView(R.id.delete).setVisibility(View.VISIBLE);
            }


        EditText price = (EditText)baseViewHolder.getView(R.id.price);
        if(!canEdit)
        {
            baseViewHolder.getView(R.id.delete).setVisibility(View.GONE);
            price.setFocusable(false);
            price.setFocusableInTouchMode(false);
        }



        //        通过tag判断当前editText是否已经设置监听，有监听的话，移除监听再给editText赋值
        if (price.getTag() instanceof TextWatcher){
            price.removeTextChangedListener((TextWatcher) price.getTag());
        }
        //        必须在判断tag后给editText赋值，否则会数据错乱
        price.setText(dataBean.getPrice());

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.e("Tag", "FirstAdapter afterTextChanged name---" + item.getName() + ",---" + editable);
//                if (!TextUtils.isEmpty(editable)) {
                    dataBean.setPrice(editable.toString());
//                }
            }
        };
        //        给item中的editText设置监听
        price.addTextChangedListener(watcher);
//        给editText设置tag，以便于判断当前editText是否已经设置监听
        price.setTag(watcher);




//        price.addTextChangedListener(new MyTextWatcher(baseViewHolder));
//        price.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                for (int i = 0; i < mData.size(); i++) {
//                    if(mData.get(i).equals(dataBean))
//                    {
//                        mData.get(i).setPrice(price.getText().toString());
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        TextView units = (TextView)baseViewHolder.getView(R.id.units);
        switch (dataBean.getUnits())
        {
            case "1":
                units.setText("时");
                break;
            case "2":
                units.setText("天");
                break;
            case "3":
                units.setText("件");
                break;
            case "4":
                units.setText("张");
                break;
            case "5":
                units.setText("套");
                break;
            case "6":
                units.setText("报价");
                break;
            default:
                units.setText("请选择");
                break;
        }

//
//        if(mData.size() > 1 && mData.get(mData.size()-1 ) == dataBean)
//        {
//            price.requestFocus();
//        }

        baseViewHolder.addOnClickListener(R.id.units);
        baseViewHolder.addOnClickListener(R.id.delete);

    }



//    class MyTextWatcher implements TextWatcher {
//        public MyTextWatcher(BaseViewHolder holder) {
//            mHolder = holder;
//        }
//
//        private BaseViewHolder mHolder;
//
//        @Override
//        public void onTextChanged(CharSequence s, int start,
//                                  int before, int count) {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start,
//                                      int count, int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            if (s != null && !"".equals(s.toString())) {
//                int position = mHolder.getAdapterPosition();
////                int position = (Integer) mHolder.getView(R.id.price).getTag();
//                mData.get(position).setPrice(
//                        s.toString());// 当EditText数据发生改变的时候存到data变量中
//            }
//        }
//    }


}

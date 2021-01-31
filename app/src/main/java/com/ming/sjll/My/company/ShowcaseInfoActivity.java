package com.ming.sjll.My.company;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyShowCaseInfoBinding;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心-橱窗-商品详情
 */
public class ShowcaseInfoActivity extends BaseActivity {


    CustomRoundAngleImageView backdrop;
    TextView deposit, payment, title, describe;
    LinearLayout imgList;
    ViewGroup btns;
//    DragFloatActionButton addSample;

    GoodsItem DataBean;
    //    总数据
    HttpParamsObject commParam;

    //    别人查看详情
    String goodsId,projectId;
    MyShowCaseInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_show_case_info);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_show_case_info, null, false);
        setContentView(binding.getRoot());

        commParam = (HttpParamsObject) getIntent().getSerializableExtra("commParam");
        goodsId = getIntent().getStringExtra("goodsId");
        projectId = getIntent().getStringExtra("projectId");

        initView();
        event();

    }

    private void event()
    {
        binding.openSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataBean == null)
                {
                    ToastShow.s("数据正在加载中，请稍后");
                    return;
                }
                if(DataBean.getData().getType().equals("1"))
                {
                    BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseInfoActivity.this);
                    bottomInterPasswordDialog.setContentView(R.layout.dialog_show_case_service_type);
                    bottomInterPasswordDialog.inDuration(300);
                    bottomInterPasswordDialog.outDuration(200);

                    LinearLayout guide1menu = bottomInterPasswordDialog.findViewById(R.id.guide1menu);
                    LinearLayout guide2menu = bottomInterPasswordDialog.findViewById(R.id.guide2menu);
                    LinearLayout guide3menu = bottomInterPasswordDialog.findViewById(R.id.guide3menu);

                    if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"1"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"2"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"3"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    guide1menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"1"))
                            {
                              return;
                            }
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked);
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_un);
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_un);
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"1"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"2"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"3"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                        }
                    });
                    guide2menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"2"))
                            {
                                return;
                            }
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_un);
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked);
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_un);
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"1"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"2"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"3"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                        }
                    });
                    guide3menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"3"))
                            {
                                return;
                            }
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_un);
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_un);
                            ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked);
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"1"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"2"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                            if(!MyUtil.ListStringHasItem(DataBean.getData().getServiceType(),"3"))
                            {
                                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                            }
                        }
                    });

                    bottomInterPasswordDialog.show();
                    return;
                }
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseInfoActivity.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_release_project_date_frame);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                bottomInterPasswordDialog.findViewById(R.id.btns).setVisibility(View.GONE);
                DateFrame ateFrame = bottomInterPasswordDialog.findViewById(R.id.dateFrame);

                ateFrame.setPointList(DataBean.getData().getDate_time());
//                bottomInterPasswordDialog.setOnShowListener(new DialogInterface.OnShowListener(){
//                    @Override
//                    public void onShow(DialogInterface dialog)
//                    {
//                        //日期跳转
////                        Log.e(TAG,"11111111");
//                        if(!commParam.getStringMapParam("closingTime").equals(""))
//                        {
//                            ateFrame.jumpDate(Tools.getDateformat(Long.parseLong(commParam.getStringMapParam("closingTime"))));
//                        }
//
//                    }
//                });

                bottomInterPasswordDialog.show();
            }
        });

        binding.openChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataBean == null)
                {
                    ToastShow.s("数据正在加载中，请稍后");
                    return;
                }
                RongImUtils.privateChat(ShowcaseInfoActivity.this,DataBean.getData().getIm_uid(),DataBean.getData().getIm_name());
            }
        });
//
//        binding.submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(DataBean == null)
//                {
//                    ToastShow.s("数据正在加载中，请稍后");
//                    return;
//                }
//                //                提示
//                DialogTextTip dialog = new DialogTextTip(getContext(), "直接租赁将会确保您的优先使用权，后续您可在订单中将其添加进项目",DialogTextTip.Prompt.GOODS_BUY);
//                dialog.show(new CommonCallback() {
//                    @Override
//                    public void onNext() {
//                        super.onNext();
//                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseInfoActivity.this);
//                        bottomInterPasswordDialog.setContentView(R.layout.dialog_show_case_buy_goods);
//                        bottomInterPasswordDialog.inDuration(300);
//                        bottomInterPasswordDialog.outDuration(200);
//                        TextView addressTip = bottomInterPasswordDialog.findViewById(R.id.addressTip);
//                        String ad  = DataBean.getData().getProvinces() + "-" +  DataBean.getData().getCity() + "-" +  DataBean.getData().getCounty();
//                        addressTip.setText(ad);
//                        TextView addressTip2 = bottomInterPasswordDialog.findViewById(R.id.addressTip2);
//                        addressTip2.setText(DataBean.getData().getAddress_title());
//
//                        LinearLayout guide1menu = bottomInterPasswordDialog.findViewById(R.id.guide1menu);
//                        LinearLayout guide2menu = bottomInterPasswordDialog.findViewById(R.id.guide2menu);
//
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(true);
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(false);
//                        bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.VISIBLE);
//                        bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.GONE);
//
//                        guide1menu.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(true);
//                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(false);
//                                bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.VISIBLE);
//                                bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.GONE);
//
//                            }
//                        });
//                        guide2menu.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(false);
//                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(true);
//                                bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.GONE);
//                                bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.VISIBLE);
//                            }
//                        });
//
//                        bottomInterPasswordDialog.show();
//                    }
//                });
//
//
//            }
//        });

//        租赁、购买商品
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("DataBean", DataBean);
                bundle.putSerializable("goodsId", goodsId);
//                        bundle.putString("icon", showCaseItemBean.getData().get(position).icon);
                Tools.jump(ShowcaseInfoActivity.this, ShowcaseGoodsSelectTimeActivity.class, bundle, false);
            }
        });
//        购买服务
        binding.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("DataBean", DataBean);
                bundle.putSerializable("goodsId", goodsId);
//                        bundle.putString("icon", showCaseItemBean.getData().get(position).icon);
                Tools.jump(ShowcaseInfoActivity.this, ShowcaseServiceSelectTimeActivity.class, bundle, false);
            }
        });
//        binding.preview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(DataBean == null)
//                {
//                    ToastShow.s("数据正在加载中，请稍后");
//                    return;
//                }
//                //                提示
//                DialogTextTip dialog = new DialogTextTip(getContext(), "添加进项目不能确保您在支付时仍有库存，如确定使用，建议选择直接租赁此商品并添加进项目",DialogTextTip.Prompt.GOODS_JOIN_PROJECT);
//                dialog.show(new CommonCallback() {
//                    @Override
//                    public void onNext() {
//                        super.onNext();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("DataBean", DataBean);
//                        bundle.putSerializable("projectId", projectId);
////                        bundle.putString("icon", showCaseItemBean.getData().get(position).icon);
//                        Tools.jump(ShowcaseInfoActivity.this, ShowcaseJoinProjectActivity.class, bundle, false);
//                        dialog.hide();
//                    }
//                });
//
//
//            }
//        });
    }
    private void initView() {
        backdrop = (CustomRoundAngleImageView) findViewById(R.id.backdrop);
        deposit = (TextView) findViewById(R.id.deposit);
        payment = (TextView) findViewById(R.id.payment);
        title = (TextView) findViewById(R.id.title);
        describe = (TextView) findViewById(R.id.describe);
        imgList = (LinearLayout) findViewById(R.id.imgList);
        btns = findViewById(R.id.btns);
//        addSample = findViewById(R.id.addSample);
//        addSample.btn_add_left = R.mipmap.btn_add_left;
        btns.setVisibility(View.GONE);



        if(getIsShowDelete())
        {
            initData();
        }
        else
        {
            btns.setVisibility(View.VISIBLE);
            HttpParamsObject param = new HttpParamsObject();
            param.setParam("goods_id", goodsId);
            new HttpUtil().sendRequest(Constant.GOODS_GOODS_INFO, param, new CommonCallback() {
                @Override
                public void onSuccessJson(String data, int code) {
                    DataBean = new Gson().fromJson(data, GoodsItem.class);
                    updataUI();
                }
            });
        }

    }



    private boolean getIsShowDelete()
    {
        if(goodsId == null || goodsId.equals(""))
        {
            return true;
        }
        return  false;
    }

    private void initData() {
        DataBean = new GoodsItem();
        DataBean.setData(new GoodsItem.DataBean());
        DataBean.getData().setCover_img(commParam.getStringMapParam("cover_img"));
        DataBean.getData().setDeposit(commParam.getStringMapParam("deposit"));
        DataBean.getData().setTitle(commParam.getStringMapParam("title"));
        DataBean.getData().setDescribe(commParam.getStringMapParam("describe"));
        DataBean.getData().setImgList(commParam.getStringListParam("imgList"));
        DataBean.getData().setType(commParam.getStringMapParam("type"));
        List<GoodsItem.DataBean.DataBeanX> paymentList = (List<GoodsItem.DataBean.DataBeanX>) getIntent().getSerializableExtra("paymentList");
        DataBean.getData().setPaymentList(paymentList);
        DataBean.getData().setServiceType(commParam.getStringListParam("serviceType"));


//        String units = "";
//        switch (commParam.getStringMapParam("units")) {
//            case "1":
//                units = "时";
//                break;
//            case "2":
//                units = "天";
//                break;
//            case "3":
//                units = "件";
//                break;
//            case "4":
//                units = "张";
//                break;
//            case "5":
//                units = "套";
//                break;
//            case "6":
//                units = "报价";
//                break;
//        }
//
//        DataBean.getData().setPayment(commParam.getStringMapParam("price") + "/" + units);
        updataUI();
    }

    private void updataUI() {
        new ImageHelper().displayBackgroundLoading(backdrop,
                Constant.BASE_IMAGE + DataBean.getData().getCover_img());
        deposit.setText(DataBean.getData().getDeposit());
        if(DataBean.getData().getPayment_str() != null && DataBean.getData().getPayment_str().size() > 0 )
        {
            payment.setText(DataBean.getData().getPayment_str().get(0));
        }
//        payment.setText(DataBean.getData().getPayment());
        title.setText(DataBean.getData().getTitle());

        describe.setText(DataBean.getData().getDescribe());
        binding.describe2.setText(DataBean.getData().getDescribe());
        if(DataBean.getData().getType().equals("1"))
        {
//            服务
            binding.describe.setVisibility(View.GONE);
            binding.openSchedule.setBackgroundResource( R.mipmap.btn_open_service);
            binding.submit.setVisibility(View.GONE);
//            binding.preview.setVisibility(View.GONE);
            binding.unitGoods.setVisibility(View.GONE);
            binding.goodsStatus.setVisibility(View.GONE);
            for (int i = 0; i < DataBean.getData().getPaymentList().size(); i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.my_show_case_info_unit_item, null);
                view.setTag(i);
                ((TextView)view.findViewById(R.id.payment)).setText(DataBean.getData().getPaymentList().get(i).getPrice_str());
                binding.unitService.addView(view);
            }

        }
        else
        {
//            商品
            binding.describe2.setVisibility(View.GONE);
            binding.subscribe.setVisibility(View.GONE);
            binding.unitService.setVisibility(View.GONE);
            if(!DataBean.getData().getNum().equals("0"))
            {
                binding.goodsStatus.setVisibility(View.GONE);
            }

        }

        new ImageHelper().displayBackgroundLoading(binding.headImage,
                Constant.BASE_IMAGE + DataBean.getData().getDefault_avatar());
        binding.name.setText(DataBean.getData().getIm_name());
        binding.city.setText(DataBean.getData().getIm_city());


        List<LocalMedia> selectList = new ArrayList<>();
        for (String img : DataBean.getData().getImgList()) {
            LocalMedia localMedia = new LocalMedia(Constant.BASE_API + img, 0, 1, "image/jpeg");
            selectList.add(localMedia);
        }
        for (int i = 0; i < DataBean.getData().getImgList().size(); i++) {
            View view = LayoutInflater.from(ShowcaseInfoActivity.this).inflate(R.layout.item_goods_image, null);
            view.setTag(i);
            CustomRoundAngleImageView imageItem = (CustomRoundAngleImageView) view.findViewById(R.id.imageItem);
            new ImageHelper().displayBackgroundLoading(imageItem,
                    Constant.BASE_IMAGE + DataBean.getData().getImgList().get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexOf = (int) v.getTag();
                    PictureSelector.create(ShowcaseInfoActivity.this).themeStyle(R.style.picture_default_style).
                            openExternalPreview(indexOf, selectList);
                }
            });
            imgList.addView(view);
        }

//        模特身材
        binding.figureWrap.setVisibility(View.GONE);
        if(DataBean.getData().getModelInfo() != null)
        {
            binding.figureWrap.setVisibility(View.VISIBLE);
            binding.height.setText(DataBean.getData().getModelInfo().getHeight());
            binding.chest.setText(DataBean.getData().getModelInfo().getChest());
            binding.waistline.setText(DataBean.getData().getModelInfo().getWaistline());
            binding.hipline.setText(DataBean.getData().getModelInfo().getHipline());
            binding.shoeSize.setText(DataBean.getData().getModelInfo().getShoe_size());
            binding.braSize.setText(DataBean.getData().getModelInfo().getBra_size());
            skin_color = DataBean.getData().getModelInfo().getSkin_color();
            initFigure();
        }


    }

    String skin_color;
    /**
     * 肤色初始化
     */
    private void initFigure()
    {
        switch (skin_color)
        {
            case "1":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1_select);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4);
                break;
            case "2":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2_select);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4);
                break;
            case "3":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3_select);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4);
                break;
            case "4":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4_select);
                break;
        }
    }



}

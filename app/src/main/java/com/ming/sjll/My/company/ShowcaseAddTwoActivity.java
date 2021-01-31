package com.ming.sjll.My.company;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.adapter.PersonageOccupationListAdapter;
import com.ming.sjll.My.adapter.SampleItemEditImageAdapter;
import com.ming.sjll.My.adapter.ShowcasePriceLineAdapter;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.event.GoodsEditEvent;
import com.ming.sjll.base.event.SampleEditEvent;
import com.ming.sjll.base.manager.AppManager;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyShowCaseAddTwoBinding;
import com.ming.sjll.map.MapMainActivity;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.project.ReleaseProject;
import com.ming.sjll.project.ReleaseProjectCover;
import com.ming.sjll.ui.CircleImageView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心-橱窗-商品列表
 */
public class ShowcaseAddTwoActivity extends BaseActivity {

    TextView units,address,sendTypeTip;
    EditText  price, deposit;
    //    ImageView is_inventory, is_mail;
    ImageView submit, preview;
    boolean is_relevancy_flag = false;
    //    boolean is_mail_flag = true;
    //    总数据
    HttpParamsObject commParam;

    private RecyclerView listView;

    MyShowCaseAddTwoBinding binding;


    private String type;//类型 1-服务 2-商品

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_show_case_add_two);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_show_case_add_two, null, false);
        setContentView(binding.getRoot());

        commParam = (HttpParamsObject) getIntent().getSerializableExtra("commParam");
        GoodsItem MyData = (GoodsItem) getIntent().getSerializableExtra("MyData");
        if (MyData != null) {
            paymentList = MyData.getData().getPaymentList();
        }


//        AppManager.getAppManager().finishActivity(ShowcaseAddActivity.class);
        Log.e("传送获取到param:", commParam.getUrlParam());
        initView();
        event();
    }


    private void initView() {
        units = findViewById(R.id.units);
        address = findViewById(R.id.address);
//        is_inventory = findViewById(R.id.is_inventory);
//        is_mail = findViewById(R.id.is_mail);
        submit = findViewById(R.id.submit);
        preview = findViewById(R.id.preview);
        price = findViewById(R.id.price);
        deposit = findViewById(R.id.deposit);
        sendTypeTip = findViewById(R.id.sendTypeTip);
        listView = (RecyclerView) findViewById(R.id.listView);

//
//        sendTypeWrap = (RecyclerView) findViewById(R.id.sendTypeWrap);
//        selectCountWrap = (RecyclerView) findViewById(R.id.selectCountWrap);
//        depositWrap = (RecyclerView) findViewById(R.id.depositWrap);


//        address.setText(commParam.getStringMapParam("address"));
//        price.setText(commParam.getStringMapParam("price"));
        deposit.setText(commParam.getStringMapParam("deposit"));
        address.setText(commParam.getStringMapParam("address_title"));
        ((TextView) findViewById(R.id.countInput)).setText(commParam.getStringMapParam("num"));
        ((TextView) findViewById(R.id.street)).setText(commParam.getStringMapParam("street"));


        type = commParam.getStringMapParam("type");
        binding.depositWrap.setVisibility(View.GONE);
        binding.selectCountWrap.setVisibility(View.GONE);
        binding.sendTypeWrap.setVisibility(View.GONE);
        binding.streetWrap.setVisibility(View.GONE);
        binding.serviceTypeWrap.setVisibility(View.GONE);
        binding.emailWrap.setVisibility(View.GONE);
        binding.addressWrap.setVisibility(View.GONE);
        binding.relevancyWrap.setVisibility(View.GONE);
        binding.userListWrap.setVisibility(View.GONE);

        if (type.equals("1")) {
            binding.serviceTypeWrap.setVisibility(View.VISIBLE);
            binding.relevancyWrap.setVisibility(View.VISIBLE);
            binding.userListWrap.setVisibility(View.VISIBLE);

            binding.addressTitle.setText("到场/寄送地址");
            setIsRelevancy();


            if (commParam.getStringMapParam("is_relevancy").equals("1")) {
                is_relevancy_flag = true;
            }

            changeServiceType();
            initUserList();

        } else {
            binding.depositWrap.setVisibility(View.VISIBLE);
            binding.selectCountWrap.setVisibility(View.VISIBLE);
            binding.sendTypeWrap.setVisibility(View.VISIBLE);
            binding.addressWrap.setVisibility(View.VISIBLE);
            binding.streetWrap.setVisibility(View.VISIBLE);

            String resultString = "";
            for (int j = 0; j < commParam.getStringListParam("sendType").size(); j++) {
                if (j < commParam.getStringListParam("sendType").size() - 1) {
                    resultString += ((commParam.getStringListParam("sendType").get(j)).equals("1") ? "买家自取" : "商家邮寄") + "  ";
                } else {
                    resultString += ((commParam.getStringListParam("sendType").get(j)).equals("1") ? "买家自取" : "商家邮寄");
                }
            }
            sendTypeTip.setText(resultString);

        }

//        if (commParam.getStringMapParam("is_inventory").equals("0")) {
//            is_inventory_flag = false;
//        }
//        if (commParam.getStringMapParam("is_inventory").equals("0")) {
//            is_mail_flag = false;
//        }
//        setIs_inventory();
//        setIs_mail();
//        setUnits();
        initPriceLineList();
    }

    private void changeServiceType() {
        List<String> list = commParam.getStringListParam("serviceType");
        String resultString = "";
        binding.addressWrap.setVisibility(View.GONE);
        binding.streetWrap.setVisibility(View.GONE);
        binding.emailWrap.setVisibility(View.GONE);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("1")) {
                binding.addressWrap.setVisibility(View.VISIBLE);
                binding.streetWrap.setVisibility(View.VISIBLE);
            } else if (list.get(i).equals("3")) {
                binding.emailWrap.setVisibility(View.VISIBLE);
            }


            if (i < list.size() - 1) {
                resultString += ((list.get(i)).equals("1") ? "买家到场/寄送" : list.get(i).equals("2") ? "到场服务" : "线上传输") + "  ";
            } else {
                resultString += ((list.get(i)).equals("1") ? "买家到场/寄送" : list.get(i).equals("2") ? "到场服务" : "线上传输");
            }
        }
        binding.serviceTypeTip.setText(resultString);
    }

    GetInfoBeans userInfoList;

    private void initUserList() {
        //        获取用戶列表
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_COMPANY_MEMBER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfoList = new Gson().fromJson(data, GetInfoBeans.class);

                for (int j = 0; j < userInfoList.getData().size(); j++) {
                    View userListView = LayoutInflater.from(ShowcaseAddTwoActivity.this).inflate(R.layout.my_schedule_user_occupation_item, null);
                    userListView.setTag(j);
                    CircleImageView imageItem = (CircleImageView) userListView.findViewById(R.id.imageItem);

                    new ImageHelper().displayBackgroundLoading(imageItem,
                            Constant.BASE_API + userInfoList.getData().get(j).getDefault_avatar());

                    ((TextView) userListView.findViewById(R.id.name)).setText(userInfoList.getData().get(j).getName());
                    ((TextView) userListView.findViewById(R.id.occupation)).setText(userInfoList.getData().get(j).getOccupation());

                    ImageView imageItemWrap = (ImageView) userListView.findViewById(R.id.imageItemWrap);

                    List<String> list = commParam.getStringListParam("userIds");
                    for (int i = 0; i < list.size(); i++) {

                        if (userInfoList.getData().get(j).getUser_id().equals(list.get(i))) {
                            imageItemWrap.setBackgroundResource(R.mipmap.bg_user2);
                        } else {
                            imageItemWrap.setBackgroundResource(R.mipmap.bg_user_blue2);
                        }

                    }

                    userListView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int indexOf = (int) v.getTag();
                            clickUser(indexOf, v);
                        }
                    });
                    binding.userList.addView(userListView);

                }

            }
        });

    }

    private void clickUser(int indexOf, View v) {
        String uid = userInfoList.getData().get(indexOf).getUser_id();

        List<String> list = commParam.getStringListParam("userIds");
//        list.add(indexOf+"");
        boolean hasUser = false;
        for (int i = 0; i < list.size(); i++) {
            if (uid.equals(list.get(i))) {
                hasUser = true;
            }
        }
        if (hasUser) {
            list.remove(uid);
            binding.userList.getChildAt(indexOf).findViewById(R.id.imageItemWrap).setBackgroundResource(R.mipmap.bg_user);
        } else {
            list.add(uid);
            binding.userList.getChildAt(indexOf).findViewById(R.id.imageItemWrap).setBackgroundResource(R.mipmap.bg_user_blue2);
        }
        commParam.setParam("userIds", list);
    }

    ShowcasePriceLineAdapter personageOccupationListAdapter;
    private List<GoodsItem.DataBean.DataBeanX> paymentList;

    /**
     * 初始化价格
     */
    private void initPriceLineList() {
        if (paymentList == null || paymentList.size() == 0) {
            paymentList = new LinkedList<>();
            paymentList.add(new GoodsItem.DataBean.DataBeanX());
        }
        listView.setLayoutManager(new LinearLayoutManager(ShowcaseAddTwoActivity.this));
        personageOccupationListAdapter = new ShowcasePriceLineAdapter(paymentList);
        listView.setAdapter(personageOccupationListAdapter);

        personageOccupationListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.delete) {
                    paymentList.remove(position);
                    personageOccupationListAdapter.notifyDataSetChanged();
                } else if (view.getId() == R.id.units) {

                    BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseAddTwoActivity.this);
                    bottomInterPasswordDialog.setContentView(R.layout.dialog_units_type);
                    bottomInterPasswordDialog.inDuration(300);
                    bottomInterPasswordDialog.outDuration(200);

                    if (!type.equals("1")) {
                        bottomInterPasswordDialog.findViewById(R.id.line2).setVisibility(View.GONE);
                        bottomInterPasswordDialog.findViewById(R.id.units3).setVisibility(View.GONE);
                        bottomInterPasswordDialog.findViewById(R.id.units7).setVisibility(View.VISIBLE);
                    }


                    bottomInterPasswordDialog.show();
                    bottomInterPasswordDialog.findViewById(R.id.units1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "1")) {
                                paymentList.get(position).setType("1");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }

                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "2")) {
                                paymentList.get(position).setType("2");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "4")) {
                                paymentList.get(position).setType("4");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "5")) {
                                paymentList.get(position).setType("5");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "6")) {
                                paymentList.get(position).setType("6");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units6).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "7")) {
                                paymentList.get(position).setType("7");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units7).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(paymentList, "3")) {
                                paymentList.get(position).setType("3");
                                bottomInterPasswordDialog.hide();
                                personageOccupationListAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("1")) {
                    if (paymentList.size() < 6) {
                        paymentList.add(new GoodsItem.DataBean.DataBeanX());
                        personageOccupationListAdapter.notifyDataSetChanged();
                    } else {
                        ToastShow.s("最多只能添加6个");
                    }

                }
                else {
                    if (paymentList.size() < 3) {
                        paymentList.add(new GoodsItem.DataBean.DataBeanX());
                        personageOccupationListAdapter.notifyDataSetChanged();
                    } else {
                        ToastShow.s("最多只能添加3个");
                    }

                }


            }
        });
    }

    private boolean hasSameUnit(List<GoodsItem.DataBean.DataBeanX> paymentBean, String value) {
        for (GoodsItem.DataBean.DataBeanX p :
                paymentBean) {
            if (p.getType().equals(value)) {
                ToastShow.s("已存在相同单位，请重新选择");
                return true;
            }
        }

        return false;
    }

    private void setIsRelevancy() {
        if (is_relevancy_flag) {
            binding.isRelevancy.setBackgroundResource(R.mipmap.btn_switch_y);
        } else {
            binding.isRelevancy.setBackgroundResource(R.mipmap.btn_switch_n);
        }
    }

//    private void setIs_mail() {
//        if (is_mail_flag) {
//            is_mail.setBackgroundResource(R.mipmap.btn_switch_y);
//        } else {
//            is_mail.setBackgroundResource(R.mipmap.btn_switch_n);
//        }
//    }

    List<String> TeamList;
    private void event() {

        findViewById(R.id.sendTypeWrap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseAddTwoActivity.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_goods_send_type);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                for (int i = 0; i < commParam.getStringListParam("sendType").size(); i++) {
                    if (commParam.getStringListParam("sendType").get(i).equals("1")) {
//                        bottomInterPasswordDialog.findViewById(R.id.guide1Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                        bottomInterPasswordDialog.findViewById(R.id.guide1).setBackgroundResource(R.mipmap.btn_checked);
                    } else if (commParam.getStringListParam("sendType").get(i).equals("2")) {
//                        bottomInterPasswordDialog.findViewById(R.id.guide2Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                        bottomInterPasswordDialog.findViewById(R.id.guide2).setBackgroundResource(R.mipmap.btn_checked);
                    }
                }

                TeamList = MyUtil.cloneObject(commParam.getStringListParam("sendType"));

                bottomInterPasswordDialog.findViewById(R.id.guide1menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyUtil.ListStringHasItem(TeamList,"1"))
                        {
                            for (int i = 0; i < TeamList.size(); i++) {
                                if (TeamList.get(i).equals("1")) {
//                                    bottomInterPasswordDialog.findViewById(R.id.guide1Wrap).setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    bottomInterPasswordDialog.findViewById(R.id.guide1).setBackgroundResource(R.mipmap.btn_checked_un);
                                    TeamList.remove(i);
                                }
                            }
                        }
                        else
                        {
                            TeamList.add("1");
//                            bottomInterPasswordDialog.findViewById(R.id.guide1Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                            bottomInterPasswordDialog.findViewById(R.id.guide1).setBackgroundResource(R.mipmap.btn_checked);
                        }

//                        commParam.setParam("sendType", sendTypeList);
//
//
//                        String resultString = "";
//                        for (int j = 0; j < TeamList.size(); j++) {
//                            if (j < TeamList.size() - 1) {
//                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄") + "  ";
//
//                            } else {
//                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄");
//                            }
//                        }
//                        TeamList =  sendTypeList;



                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.guide2menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyUtil.ListStringHasItem(TeamList,"2"))
                        {
                            for (int i = 0; i < TeamList.size(); i++) {
                                if (TeamList.get(i).equals("2")) {
//                                    bottomInterPasswordDialog.findViewById(R.id.guide2Wrap).setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    bottomInterPasswordDialog.findViewById(R.id.guide2).setBackgroundResource(R.mipmap.btn_checked_un);
                                    TeamList.remove(i);
                                }
                            }
                        }
                        else
                        {
                            TeamList.add("2");
//                            bottomInterPasswordDialog.findViewById(R.id.guide2Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                            bottomInterPasswordDialog.findViewById(R.id.guide2).setBackgroundResource(R.mipmap.btn_checked);
                        }

                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String resultString = "";
                        for (int j = 0; j < TeamList.size(); j++) {
                            if (j < TeamList.size() - 1) {
                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄") + "  ";

                            } else {
                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄");
                            }
                        }

                        commParam.setParam("sendType", TeamList);
                        sendTypeTip.setText(resultString);
                        bottomInterPasswordDialog.hide();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });


//
//
//                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseAddTwoActivity.this);
//                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_goods_send_type);
//                bottomInterPasswordDialog.inDuration(300);
//                bottomInterPasswordDialog.outDuration(200);
//                for (int i = 0; i < TeamList; i++) {
//                    if (commParam.getStringListParam("sendType").get(i).equals("1")) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(true);
//                    } else if (commParam.getStringListParam("sendType").get(i).equals("2")) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(true);
//                    }
//
//                }
//                bottomInterPasswordDialog.findViewById(R.id.guide1menu).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(!((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).isChecked());
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.guide2menu).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(!((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).isChecked());
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        List<String> sendTypeList = new LinkedList<>();
//                        if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).isChecked()) {
//                            sendTypeList.add("1");
//                        }
//                        if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).isChecked()) {
//                            sendTypeList.add("2");
//                        }
//                        if (sendTypeList.size() == 0) {
//                            ToastShow.s("至少选择一个");
//                            return;
//                        }
//                        commParam.setParam("sendType", sendTypeList);
//
//                        String resultString = "";
//                        for (int j = 0; j < commParam.getStringListParam("sendType").size(); j++) {
//                            if (j < commParam.getStringListParam("sendType").size() - 1) {
//                                resultString += ((commParam.getStringListParam("sendType").get(j)).equals("1") ? "买家自取" : "商家邮寄") + "  ";
//
//                            } else {
//                                resultString += ((commParam.getStringListParam("sendType").get(j)).equals("1") ? "买家自取" : "商家邮寄");
//                            }
//                        }
//                        sendTypeTip.setText(resultString);
//
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//
//                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//                bottomInterPasswordDialog.show();


            }
        });

        binding.serviceTypeWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseAddTwoActivity.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_service_send_type);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                for (int i = 0; i < commParam.getStringListParam("serviceType").size(); i++) {
                    if (commParam.getStringListParam("serviceType").get(i).equals("1")) {
//                        bottomInterPasswordDialog.findViewById(R.id.guide1Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                        bottomInterPasswordDialog.findViewById(R.id.guide1).setBackgroundResource(R.mipmap.btn_checked);
                    } else if (commParam.getStringListParam("serviceType").get(i).equals("2")) {
//                        bottomInterPasswordDialog.findViewById(R.id.guide2Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                        bottomInterPasswordDialog.findViewById(R.id.guide2).setBackgroundResource(R.mipmap.btn_checked);
                    } else if (commParam.getStringListParam("serviceType").get(i).equals("3")) {
//                        bottomInterPasswordDialog.findViewById(R.id.guide3Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                        bottomInterPasswordDialog.findViewById(R.id.guide3).setBackgroundResource(R.mipmap.btn_checked);
                    }

                }

                TeamList = MyUtil.cloneObject(commParam.getStringListParam("serviceType"));

                bottomInterPasswordDialog.findViewById(R.id.guide1menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyUtil.ListStringHasItem(TeamList,"1"))
                        {
                            for (int i = 0; i < TeamList.size(); i++) {
                                if (TeamList.get(i).equals("1")) {
//                                    bottomInterPasswordDialog.findViewById(R.id.guide1Wrap).setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    bottomInterPasswordDialog.findViewById(R.id.guide1).setBackgroundResource(R.mipmap.btn_checked_un);
                                    TeamList.remove(i);
                                }
                            }
                        }
                        else
                        {
                            TeamList.add("1");
//                            bottomInterPasswordDialog.findViewById(R.id.guide1Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                            bottomInterPasswordDialog.findViewById(R.id.guide1).setBackgroundResource(R.mipmap.btn_checked);
                        }

//                        commParam.setParam("serviceType", serviceTypeList);
//
//
//                        String resultString = "";
//                        for (int j = 0; j < TeamList.size(); j++) {
//                            if (j < TeamList.size() - 1) {
//                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄") + "  ";
//
//                            } else {
//                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄");
//                            }
//                        }
//                        TeamList =  serviceTypeList;



                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.guide2menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyUtil.ListStringHasItem(TeamList,"2"))
                        {
                            for (int i = 0; i < TeamList.size(); i++) {
                                if (TeamList.get(i).equals("2")) {
//                                    bottomInterPasswordDialog.findViewById(R.id.guide2Wrap).setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    bottomInterPasswordDialog.findViewById(R.id.guide2).setBackgroundResource(R.mipmap.btn_checked_un);
                                    TeamList.remove(i);
                                }
                            }
                        }
                        else
                        {
                            TeamList.add("2");
//                            bottomInterPasswordDialog.findViewById(R.id.guide2Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                            bottomInterPasswordDialog.findViewById(R.id.guide2).setBackgroundResource(R.mipmap.btn_checked);
                        }

                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.guide3menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyUtil.ListStringHasItem(TeamList,"3"))
                        {
                            for (int i = 0; i < TeamList.size(); i++) {
                                if (TeamList.get(i).equals("3")) {
//                                    bottomInterPasswordDialog.findViewById(R.id.guide3Wrap).setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    bottomInterPasswordDialog.findViewById(R.id.guide3).setBackgroundResource(R.mipmap.btn_checked_un);
                                    TeamList.remove(i);
                                }
                            }
                        }
                        else
                        {
                            TeamList.add("3");
//                            bottomInterPasswordDialog.findViewById(R.id.guide3Wrap).setBackgroundColor(Color.parseColor("#F2F3F7"));
                            bottomInterPasswordDialog.findViewById(R.id.guide3).setBackgroundResource(R.mipmap.btn_checked);
                        }

                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String resultString = "";
//                        for (int j = 0; j < TeamList.size(); j++) {
//                            if (j < TeamList.size() - 1) {
//                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄") + "  ";
//
//                            } else {
//                                resultString += ((TeamList.get(j)).equals("1") ? "买家自取" : "商家邮寄");
//                            }
//                        }

                        commParam.setParam("serviceType", TeamList);
                        changeServiceType();
//                        sendTypeTip.setText(resultString);
                        bottomInterPasswordDialog.hide();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });


//
//                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseAddTwoActivity.this);
//                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_add_member_type);
//                bottomInterPasswordDialog.inDuration(300);
//                bottomInterPasswordDialog.outDuration(200);
//                for (int i = 0; i < commParam.getStringListParam("serviceType").size(); i++) {
//                    if (commParam.getStringListParam("serviceType").get(i).equals("1")) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(true);
//                    } else if (commParam.getStringListParam("serviceType").get(i).equals("2")) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(true);
//                    } else if (commParam.getStringListParam("serviceType").get(i).equals("3")) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(true);
//                    }
//
//                }
//                bottomInterPasswordDialog.findViewById(R.id.guide1menu).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(!((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).isChecked());
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.guide2menu).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(!((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).isChecked());
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.guide3menu).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(!((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).isChecked());
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        List<String> sendTypeList = new LinkedList<>();
//                        if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).isChecked()) {
//                            sendTypeList.add("1");
//                        }
//                        if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).isChecked()) {
//                            sendTypeList.add("2");
//                        }
//                        if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).isChecked()) {
//                            sendTypeList.add("3");
//                        }
//                        if (sendTypeList.size() == 0) {
//                            ToastShow.s("至少选择一个");
//                            return;
//                        }
//                        commParam.setParam("serviceType", sendTypeList);
//
//                        changeServiceType();
//
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//
//                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//                bottomInterPasswordDialog.show();
//

            }
        });
//        保存
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                commParam.setParam("price", price.getText().toString());
                commParam.setParam("deposit", deposit.getText().toString());
                commParam.setParam("num", ((TextView) findViewById(R.id.countInput)).getText().toString());
                commParam.setParam("street", ((TextView) findViewById(R.id.street)).getText().toString());

                commParam.setParam("email", binding.email.getText().toString());
                commParam.setParam("is_relevancy", is_relevancy_flag? 1 : 0);

                HttpParamsObject param = MyUtil.cloneObject(commParam);
                for (int i = 0; i < paymentList.size(); i++) {
                    param.setParam("paymentList[" + i + "][price]", paymentList.get(i).getPrice());
                    param.setParam("paymentList[" + i + "][type]", paymentList.get(i).getType());
                }


//                commParam.setParam("address", address.getText().toString());
//                commParam.setParam("is_inventory", is_inventory_flag ? 1 : 0);
//                commParam.setParam("is_mail", is_mail_flag ? 1 : 0);
                String url = type.equals("1") ? Constant.GOODS_ADD_SERVICE : Constant.GOODS_ADD_GOODS;
                new HttpUtil().sendRequest(url, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        GoodsItem DataBean = new Gson().fromJson(data, GoodsItem.class);
                        EventBus.getDefault().post(new GoodsEditEvent(DataBean.getData(), Integer.parseInt(commParam.getStringMapParam("oldPoint"))));
                        AppManager.getAppManager().finishActivity(ShowcaseAddActivity.class);
                        ToastShow.s("保存成功");
                        finish();
                    }
                });
            }
        });
//       预览
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                commParam.setParam("price", price.getText().toString());
                commParam.setParam("deposit", deposit.getText().toString());
//                commParam.setParam("address", address.getText().toString());
//                commParam.setParam("is_inventory", is_inventory_flag ? 1 : 0);
//                commParam.setParam("is_mail", is_mail_flag ? 1 : 0);

                Intent intent = new Intent(ShowcaseAddTwoActivity.this, ShowcaseInfoActivity.class);
//                intent.putExtra("commParam", commParam);
//                intent.putExtra("paymentList", paymentList);

                Bundle bundle = new Bundle();
                bundle.putSerializable("commParam", commParam);
                bundle.putSerializable("paymentList", (Serializable) paymentList);
                intent.putExtras(bundle);
                Log.e("提交", "推送" + commParam.getUrlParam());
                startActivity(intent);

            }
        });

//        是否关联员工
        binding.isRelevancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_relevancy_flag = !is_relevancy_flag;
                setIsRelevancy();
            }
        });
//
////        是否支持邮寄
//        is_mail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                is_mail_flag = !is_mail_flag;
//                setIs_mail();
//
//            }
//        });

        //        地图选择
        binding.addressWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.init(ShowcaseAddTwoActivity.this);
                Intent intent = new Intent(ShowcaseAddTwoActivity.this, MyTencentMap.class);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
            }
        });
//        单位
//        units.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseAddTwoActivity.this);
//                bottomInterPasswordDialog.setContentView(R.layout.dialog_units_type);
//                bottomInterPasswordDialog.inDuration(300);
//                bottomInterPasswordDialog.outDuration(200);
////                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
////                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
//
//                bottomInterPasswordDialog.findViewById(R.id.line2).setVisibility(View.GONE);
//                bottomInterPasswordDialog.findViewById(R.id.units3).setVisibility(View.GONE);
//                bottomInterPasswordDialog.findViewById(R.id.units7).setVisibility(View.VISIBLE);
//                bottomInterPasswordDialog.show();
//                bottomInterPasswordDialog.findViewById(R.id.units1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        commParam.setParam("units", 1);
//                        setUnits();
//                        bottomInterPasswordDialog.hide();
//
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.units2).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        commParam.setParam("units", 2);
//                        setUnits();
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.units7).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        commParam.setParam("units", 3);
//                        setUnits();
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//
//
//            }
//        });
    }

//    private void setUnits() {
//
//        switch (commParam.getStringMapParam("units")) {
//            case "1":
//                units.setText("时");
//                break;
//            case "2":
//                units.setText("天");
//                break;
//            case "3":
//                units.setText("出售");
//                break;
//            default:
//                units.setText("请选择");
//                break;
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.MAP_HEAD_REQUEST) {
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                String area_code = data.getStringExtra("area_code");
                String title = data.getStringExtra("title");
                String adname = data.getStringExtra("address");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");

                commParam.setParam("area_code", area_code);
                commParam.setParam("provinces", province);
                commParam.setParam("city", city);
                commParam.setParam("county", district);
                commParam.setParam("address_title", title);
                commParam.setParam("address", adname);
                commParam.setParam("meridian", longitude + "");
                commParam.setParam("weft", latitude + "");

                address.setText(title);
            }

        }
    }


}

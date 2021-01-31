package com.ming.sjll.My.personage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.PersonageOccupationListAdapter;
import com.ming.sjll.My.adapter.SampleListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.Config;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.dialog.EditTextDialog;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.ReleaseProjectCover;
import com.ming.sjll.search.CompanyFragment;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import io.rong.imkit.utilities.RongUtils;

/**
 * 个人中心-个人-职业
 */
public class PersonageOccupation extends BaseActivity {


    LinearLayout linearLayout;
    OccupationList occList;


    List<GetInfoBean.DataBean.occupationInfoBean> userOccupationList;

    private PersonageOccupationListAdapter personageOccupationListAdapter;
    private RecyclerView listView;

    //    用户信息
//    GetInfoBean userInfo;
//    公司状态
    private String companyStatus;


    RelativeLayout myCompany;
    TextView myCompanyTip,myCompanyTitle;

    RelativeLayout tag,face,figure;
    TextView tagTip,faceTip,figureTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.my_personage_occupation);
        initView();
    }
    private void initView()
    {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        listView = (RecyclerView) findViewById(R.id.listView);
        myCompany = (RelativeLayout) findViewById(R.id.myCompany);
        myCompanyTip = (TextView) findViewById(R.id.myCompanyTip);
        myCompanyTitle = (TextView) findViewById(R.id.myCompanyTitle);
        tag = (RelativeLayout) findViewById(R.id.tag);
        tagTip = (TextView) findViewById(R.id.tagTip);
        face = (RelativeLayout) findViewById(R.id.face);
        faceTip = (TextView) findViewById(R.id.faceTip);
        figure = (RelativeLayout) findViewById(R.id.figure);
        figureTip = (TextView) findViewById(R.id.figureTip);

//        userInfo = (GetInfoBean) getIntent().getSerializableExtra("userInfo");
        userOccupationList = MyUtil.cloneObject(MainActivity.userInfo.getData().getOccupationInfo());
        initUserOccupationList();
        getOccupationList();
        myCompany();
        orther();
        event();
    }


//    是否有模特职业
    private void initModel()
    {
        for (int i = 0; i < userOccupationList.size(); i++) {
            if(userOccupationList.get(i).getOccupation_id().equals("5"))
            {
                face.setVisibility(View.VISIBLE);
                figure.setVisibility(View.VISIBLE);
                return;
            }
        }
        face.setVisibility(View.GONE);
        figure.setVisibility(View.GONE);
    }

    private void orther()
    {
        updateUserUi("tags",MainActivity.userInfo.getData().getTags());
        updateUserUi("face",MainActivity.userInfo.getData().getFace()+"");
        initModel();
    }
    private void myCompany()
    {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_CHECK_COMPANY, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {
                //0-未关联公司 1-待公司审核 2-已加入公司
                companyStatus = ObjeGetValue.GetValu("status", data).toString();
                if(companyStatus.equals("0"))
                {
                    myCompanyTitle.setText("关联在职公司");
                }
                else if(companyStatus.equals("1"))
                {
                    myCompanyTitle.setText("关联在职公司(待审核)");
                }
                else if(companyStatus.equals("2"))
                {
                    myCompanyTitle.setText("我的公司");
                }
                myCompanyTip.setText(MainActivity.userInfo.getData().getCompany_name());
            }
        });


    }

    private void event() {

        ImageView title_bar_IvRight = findViewById(R.id.title_bar_IvRight);
        title_bar_IvRight.setVisibility(View.VISIBLE);
        title_bar_IvRight.setBackground(getResources().getDrawable(R.drawable.btn_save_top2));
        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<GetInfoBean.DataBean.occupationInfoBean>  哈哈 = userOccupationList;

                MainActivity.saveInfo("payment", userOccupationList, new CommonCallback(){
                    @Override
                    public void onSuccessJson(String data, int code) {
                        super.onSuccess(data, code);
                        ToastShow.s("保存成功");
                        finish();
                    }
                });

            }
        });

        /**
         * 点击我的公司
         */
        myCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(PersonageOccupation.this, CompanyFragment.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("comeType",2);
//                //1 其实就是 requestCode 请求码 传入了唯一值 1
//                intent.putExtras(bundle);
//                startActivityForResult(intent,44);

                if(companyStatus.equals("0"))
                {
//                    跳搜索列表
                    Bundle bundle = new Bundle();
                    bundle.putInt("comeType",2);
                    Tools.jump(PersonageOccupation.this, CompanyFragment.class, bundle,false);
                }
                else if(companyStatus.equals("1"))
                {
//                   打开重新申请弹窗
                    BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(PersonageOccupation.this);
                    bottomInterPasswordDialog.setContentView(R.layout.dialog_company_apply);
                    bottomInterPasswordDialog.inDuration(300);
                    bottomInterPasswordDialog.outDuration(200);
//                    bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                    bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                    bottomInterPasswordDialog.show();
                    View confirm = bottomInterPasswordDialog.findViewById(R.id.confirm);
                    View apply = bottomInterPasswordDialog.findViewById(R.id.apply);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomInterPasswordDialog.hide();
                        }
                    });
                    apply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("comeType",2);
                            Tools.jump(PersonageOccupation.this, CompanyFragment.class,bundle, false);
                        }
                    });
                }
                else if(companyStatus.equals("2"))
                {
//                    跳到我的公司页面
                    Tools.jump(PersonageOccupation.this, MyCompany.class, false);
                }
            }
        });


        /**
         * 标签
         */
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userInfo",userInfo);
                Tools.jump(PersonageOccupation.this, PersonageTag.class, false);
            }
        });

        /**
         * 人脸识别
         */
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(PersonageOccupation.this, PersonageFacial.class, false);
            }
        });
        /**
         * 身材信息
         */
        figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(PersonageOccupation.this, PersonageFigure.class, false);
            }
        });
    }

    /**
     * 初始化用户职业
     */
    private void initUserOccupationList()
    {
        listView.setLayoutManager(new LinearLayoutManager(PersonageOccupation.this));
        personageOccupationListAdapter = new PersonageOccupationListAdapter(userOccupationList);
        listView.setAdapter(personageOccupationListAdapter);

        personageOccupationListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.add) {
                    if(userOccupationList.get(position).getPayment().size() < 6)
                    {
                        userOccupationList.get(position).getPayment().add(new GetInfoBean.DataBean.occupationInfoBean.paymentBean());
                        personageOccupationListAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        ToastShow.s("最多只能添加6个");
                    }

                }

            }
        });
    }




    /**
     * 获取职业列表
     */
    private void getOccupationList() {

        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.PROJECT_GETOCCUPATION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                occList = new Gson().fromJson(data, OccupationList.class);

                for (int j = 0; j < occList.getData().size(); j++) {
                    View occupationView = LayoutInflater.from(PersonageOccupation.this).inflate(R.layout.show_home_ineer_occupation, null);
                    ImageView imageItem = (ImageView) occupationView.findViewById(R.id.imageItem);
                    imageItem.setTag(j);
                    new ImageHelper().displayBackgroundLoading(imageItem,
                            Constant.BASE_API + occList.getData().get(j).getIcon());
                    //选中职业
                    for (int i = 0; i < userOccupationList.size(); i++) {
                        int tempId1 = Integer.parseInt(userOccupationList.get(i).getOccupation_id());
                        int tempId2 = occList.getData().get(j).getId();
                        if(tempId1 == tempId2)
                        {
                            animationChange(j,imageItem,true);
                            break;
                        }
                    }

                    imageItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int indexOf = (int) v.getTag();
                            animationChange(indexOf,v,false);

//                            dataList = null;

                        }
                    });
                    linearLayout.addView(occupationView);
                }
            }
        });

    }


    /**
     * 选中或取消选中
     * @param indexOf
     * @param v
     */
    private void animationChange(int indexOf,View v,boolean isInit)
    {
        if(occList.getData().get(indexOf).getAnimation() == null)
        {
            Animation animation = AnimationUtils.loadAnimation(PersonageOccupation.this, R.anim.normal_to_large);
            animation.setFillAfter(true);
            v.startAnimation(animation);
            occList.getData().get(indexOf).setAnimation(animation);

            /**
             * 添加相关的职业
             */
            if(!isInit)
            {
                /**
                 * 添加空职业
                 */
                GetInfoBean.DataBean.occupationInfoBean bean = new GetInfoBean.DataBean.occupationInfoBean();
                bean.setOccupation_id(occList.getData().get(indexOf).getId()+"");
                bean.setOccupation(occList.getData().get(indexOf).getTitle());
                bean.setIcon(occList.getData().get(indexOf).getSmall_icon());
                List<GetInfoBean.DataBean.occupationInfoBean.paymentBean> listTemp = new LinkedList<>();
                listTemp.add(new GetInfoBean.DataBean.occupationInfoBean.paymentBean());
                bean.setPayment( listTemp );
                userOccupationList.add(bean);
                personageOccupationListAdapter.notifyDataSetChanged();
//                for (int i = 0; i < userOccupationList.size(); i++) {
//                   if(userOccupationList.get(i).getOccupation_id().equals(indexOf)){
////                       判断是否已存在相关的职业
//                       userOccupationList.get(i) = new
//                   }
//                }
            }

        }
        else
        {
            occList.getData().get(indexOf).getAnimation().cancel();
            occList.getData().get(indexOf).setAnimation(null);
            v.clearAnimation();
             for (int i = 0; i < userOccupationList.size(); i++) {
                 int tempId1 = Integer.parseInt(userOccupationList.get(i).getOccupation_id());
                 int tempId2 = occList.getData().get(indexOf).getId();
                   if(tempId1 == tempId2){
//                       判断是否已存在相关的职业
                       userOccupationList.remove(i);
                       personageOccupationListAdapter.notifyDataSetChanged();
                       break;
                   }
             }

        }

        initModel();

    }




    /**
     * 用户信息更新
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserUpdateEvent e) {
        Object objectValue = e.objectValue;
        String value = e.value;
        String key = e.updateKey;
        updateUserUi(key,value);
        updateUserUi(key,objectValue);
    }

    /**
     * 更新UI
     * @param key
     * @param value
     */
    private void updateUserUi(String key ,Object value )
    {
        if(key.equals("tags"))
        {
            String str = "";
            List<String> dataTemp = (List<String>)value;
            for (int i = 0; i < dataTemp.size(); i++) {
                str += dataTemp.get(i)+",";
            }
            if(!str.equals(""))
            {
                str = str.substring(0, str.length()-1);
            }
            tagTip.setText(str);
        }

    }
    private void updateUserUi(String key ,String value )
    {
        if(key.equals("company_name"))
        {
            myCompanyTip.setText(value);
//            myCompanyTitle.setText("关联在职公司(待审核)");
            myCompany();
        }
        if(key.equals("face") && value.equals("true"))
        {
            faceTip.setText("已识别");
        }
    }


}

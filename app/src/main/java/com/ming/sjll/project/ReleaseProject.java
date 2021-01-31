package com.ming.sjll.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.WrapContentHeightViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 发布项目
 */
public class ReleaseProject extends BaseActivity {

    //    总数据
    HttpParamsObject commParam;
    Button submit;
    String projectId;
    boolean isLook = false;
    ProjecItem dataProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        showTypePos = getIntent().getStringExtra("showType").equals("project") ? 0 : 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_release_project);

        projectId = getIntent().getStringExtra("id");
        commParam = new HttpParamsObject();
//        param.setParam("test1","111111");
        initView();


//
//        StickyScrollView scrollViewWarp = (StickyScrollView)findViewById(R.id.scrollViewWarp);
////         scrollViewWarp.setOnTouchListener(new View.OnTouchListener() {
////                @Override
////                public boolean onTouch(View v, MotionEvent event) {
////                return true;
////                }
////            });
//        scrollViewWarp.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                Log.e("滚动","scrollX:"+scrollX+",,,scrollY："+scrollY+",,,,,oldScrollX"+oldScrollX+",,,,oldScrollY"+oldScrollY);
////                if(supplier != null)
////                Log.e("高度：",supplier.findViewById(R.id.tabMode).getTop()+"");
//
//
//            }
//
//        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);

        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {//背景图片
//                String backgroundImage=data.getStringExtra("backgroundImage");
//                Log.e("backgroundImage",backgroundImage+"111"+data.getStringExtra("backgroundImage"));
//                param.setParam("backgroundImage",backgroundImage);
//            }
//            else
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }


    List<GetInfoBean.DataBean> userList = new LinkedList<>();
    private void initView() {

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLook)
                {
                    RongImUtils.privateChat(getActivity(),dataProject.getData().getIm_uid(),dataProject.getData().getIm_name());
                    return;
                }
                if (!tab1.getCommParam() || !tab2.getCommParam()) {
                    return;
                }
//                commParam.setParam("test3", (i++).toString());
                Intent intent = new Intent(ReleaseProject.this, ReleaseProjectNext.class);
                intent.putExtra("param", commParam);
                intent.putExtra("userList", (Serializable) userList);
//                Log.e("提交",tab1.getCommParam().getUrlParam()+"推送"+param.getUrlParam());
                startActivity(intent);

            }
        });

        if(projectId == null || projectId.equals(""))
        {
            showView();
        }
        else
        {
            isLook = getIntent().getBooleanExtra("isLook",false);
            HttpParamsObject param = new HttpParamsObject();
            param.setParam("project_id",projectId);
            new HttpUtil().sendRequest(Constant.PROJECT_PROJECT_INFO, param, new CommonCallback() {
                @Override
                public void onSuccessJson(String data, int code) {
                    dataProject = new Gson().fromJson(data, ProjecItem.class);
                    commParam.setParam("projectId",projectId);
                    commParam.setParam("backgroundImage",dataProject.getData().getBackground_image());
                    commParam.setParam("demand",dataProject.getData().getDemand());
                    commParam.setParam("brand",dataProject.getData().getBrand());
                    commParam.setParam("budget",dataProject.getData().getBudget());
                    commParam.setParam("isScheme",dataProject.getData().getIs_scheme());
                    commParam.setParam("isLocal",dataProject.getData().getIs_local());
                    commParam.setParam("closingTime",dataProject.getData().getClosingTime());
                    commParam.setParam("brief",dataProject.getData().getBrief());
                    commParam.setParam("area_code",dataProject.getData().getArea_code());
                    commParam.setParam("provinces",dataProject.getData().getProvinces());
                    commParam.setParam("city",dataProject.getData().getCity());
                    commParam.setParam("county",dataProject.getData().getCounty());
                    commParam.setParam("address_title",dataProject.getData().getAddress_title());
                    commParam.setParam("address",dataProject.getData().getAddress());
                    commParam.setParam("meridian",dataProject.getData().getMeridian());
                    commParam.setParam("weft",dataProject.getData().getWeft());
                    List<String> pushList = new LinkedList<>();
                    for (int j = 0; j < dataProject.getData().getPush().size(); j++) {
                        pushList.add(dataProject.getData().getPush().get(j).getOccupation_id()+"");
                    }
                    commParam.setParam("push",pushList);
                    userList = dataProject.getData().getPushUser();
                    if(isLook)
                    {
                        if(dataProject.getData().getIm_uid().equals(Cache.getUserId()))
                        {
                            submit.setVisibility(View.GONE);
                        }
                        submit.setText("立即沟通");
                    }
                    showView();
                }
            });

        }



    }


//
//    private PagedListView listView;
//    private ArrayAdapter<String> listAdapter;
//    private List<String> listData;

    public WrapContentHeightViewPager viewpager;
    //    private ArrayList<View> aList;
    private List<Fragment> fragmentList;
    private ReleaseProjectTabGenral tab1;
    private ReleaseProjectTabRequire tab2;
    private ImageView project_list_type1;
    private ImageView project_list_type2;
//    private SearchNoticeFragemt searchNoticeFragemt;

    /**
     * 项目列表
     */
    public void showView() {
        project_list_type1 = (ImageView) findViewById(R.id.project_list_type1);
        project_list_type2 = (ImageView) findViewById(R.id.project_list_type2);
        project_list_type1.setActivated(true);
//        aList = new ArrayList<View>();
        tab1 = ReleaseProjectTabGenral.newInstance(isLook);
        tab2 = ReleaseProjectTabRequire.newInstance(isLook);

        tab1.setCommParam(commParam);
        tab2.setCommParam(commParam);

        fragmentList = new ArrayList<>();
        fragmentList.add(tab1);
        fragmentList.add(tab2);
        viewpager = (WrapContentHeightViewPager) findViewById(R.id.viewpager);

        viewpager.setAdapter(new ReleaseProject.Adaper(getSupportFragmentManager()));
//        viewpager.requestLayoutByPosition("projectProject");


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    viewpager.requestLayoutByPosition("projectProject");
//                    viewpager.setCurrentItem(0);
                    viewpager.resetHeight(0);
                    project_list_type1.setActivated(true);
                    project_list_type2.setActivated(false);

//                project_list_type1.setActivated(true);
//                project_list_type2.setActivated(false);

                } else {
//                    viewpager.requestLayoutByPosition("projectNotice");
//                    viewpager.setCurrentItem(1);
                    viewpager.resetHeight(1);
                    project_list_type2.setActivated(true);
                    project_list_type1.setActivated(false);

//                project_list_type2.setActivated(true);
//                project_list_type1.setActivated(false);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        project_list_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewpager.requestLayoutByPosition("projectProject");

//                if(!project_list_type1.isChecked())
//                {

//                }
                viewpager.setCurrentItem(0);
            }
        });

        project_list_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewpager.requestLayoutByPosition("projectNotice");
                viewpager.setCurrentItem(1);
//                if(!project_list_type2.isChecked())
//                {

//                }
//                ToastShow.s("1");
            }
        });

        viewpager.setCurrentItem(0);
//        tab1.setCommParam(param);

    }


    class Adaper extends FragmentStatePagerAdapter {
        public Adaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList != null ? fragmentList.get(position) : null;
        }

        @Override
        public int getCount() {
            return fragmentList != null ? fragmentList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }

    }


}

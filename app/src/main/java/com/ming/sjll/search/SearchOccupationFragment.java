package com.ming.sjll.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.databinding.SearchOccupationFragmentBinding;
import com.ming.sjll.map.selectcity.SelectCityActivity;
import com.ming.sjll.search.adapter.GoodsBrandItemAdapter;
import com.ming.sjll.search.adapter.GoodsCitytemAdapter;
import com.ming.sjll.ui.AboutHeightViewpager;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.tools.net.http.HttpResponseListener;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 搜索职业
 */
public class SearchOccupationFragment extends BaseActivity {

    SearchOccupationFragmentBinding binding;

    String occupation_id;
    String projectId;
    String search="";
    String fromType="";

    HttpParamsObject paramPersonnel = new HttpParamsObject();
    HttpParamsObject paramCompany = new HttpParamsObject();
    HttpParamsObject paramPersonnelCompany = new HttpParamsObject();

    List<GoodsCitytemAdapter.City> cityListPersonnel = new LinkedList<>();
    List<GoodsCitytemAdapter.City> cityListCompany = new LinkedList<>();
    List<GoodsCitytemAdapter.City> cityListPersonnelCompany = new LinkedList<>();

    StringDataListBean.DataBean brandDataListPersonnel;
    StringDataListBean.DataBean brandDataListCompany;
    StringDataListBean.DataBean brandDataListPersonnelCompany;


    StringDataListBean.DataBean tagDataListPersonnel;
    StringDataListBean.DataBean tagDataListCompany;
    StringDataListBean.DataBean tagDataListPersonnelCompany;


    StringDataListBean.DataBean skinDataListPersonnel;
    StringDataListBean.DataBean skinDataListCompany;
    StringDataListBean.DataBean skinDataListPersonnelCompany;

    //
//    private PagedListView listView;
//    private ArrayAdapter<String> listAdapter;
//    private List<String> listData;

    public AboutHeightViewpager viewpager;
    //    private ArrayList<View> aList;
    private List<Fragment> fragmentList;
    private SearchOccupationItem searchPersonnel;
    private SearchOccupationItem searchCompany;
    private SearchOccupationGoodsItem searchPersonnelCompany;
    private TextView project_list_type1;
    private TextView project_list_type2;
    private TextView project_list_type3;
//    private RelativeLayout searchWrap;
//    private SearchCompany searchCompany;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.search_occupation_fragment);
        occupation_id =  getIntent().getStringExtra("occupationId");

        projectId =  getIntent().getStringExtra("projectId");
        fromType =  getIntent().getStringExtra("fromType");

        paramPersonnel.setParam("type","user");
        paramPersonnel.setParam("occupation_id",occupation_id);

        paramCompany.setParam("type","company ");
        paramCompany.setParam("occupation_id",occupation_id);

        paramPersonnelCompany.setParam("class_id",occupation_id);



        initMYLocation();
        initView();

        event();
    }


    private boolean isProjectJump()
    {
        if(projectId != null && !projectId.equals(""))
        {
            return true;
        }
        return false;
    }


    private void initView() {

        initViewList();
        getBrandList();
        getTagList();
        getSkinList();

        EditText searchEdit = binding.searchEdit;
        searchEdit.clearFocus();

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                search = searchEdit.getText().toString();
//                commParam.setParam("search",search);
                getDataList(true);

            }
        });

    }




    /**
     * 装载抽屉
     */
    private void initDrawerLayout()
    {
        HttpParamsObject param;

        binding.priceWrap.setVisibility(View.GONE);
        binding.cityWrap.setVisibility(View.GONE);
        binding.skinWrap.setVisibility(View.GONE);
        binding.heightWrap.setVisibility(View.GONE);
        binding.chestWrap.setVisibility(View.GONE);
        binding.waistlineWrap.setVisibility(View.GONE);
        binding.hiplineWrap.setVisibility(View.GONE);
        binding.braSizeWrap.setVisibility(View.GONE);
        binding.shoeSizeWrap.setVisibility(View.GONE);
        binding.brandWrap.setVisibility(View.GONE);
        binding.tagWrap.setVisibility(View.GONE);

        if(viewpager.getCurrentItem() == 0)
        {
//            个人
            initCityList(cityListPersonnel);
            initBrandList(brandDataListPersonnel);
            initTagList(tagDataListPersonnel);
            initSkinList(skinDataListPersonnel);

            param = paramPersonnel;
            binding.priceWrap.setVisibility(View.VISIBLE);
            binding.cityWrap.setVisibility(View.VISIBLE);
            binding.tagWrap.setVisibility(View.VISIBLE);
            if(occupation_id.equals("5"))
            {
                binding.priceWrap.setVisibility(View.VISIBLE);
                binding.cityWrap.setVisibility(View.VISIBLE);
                binding.skinWrap.setVisibility(View.VISIBLE);
                binding.heightWrap.setVisibility(View.VISIBLE);
                binding.chestWrap.setVisibility(View.VISIBLE);
                binding.waistlineWrap.setVisibility(View.VISIBLE);
                binding.hiplineWrap.setVisibility(View.VISIBLE);
                binding.braSizeWrap.setVisibility(View.VISIBLE);
                binding.shoeSizeWrap.setVisibility(View.VISIBLE);
                binding.brandWrap.setVisibility(View.VISIBLE);
                binding.tagWrap.setVisibility(View.VISIBLE);
            }

        }
        else if(viewpager.getCurrentItem() == 1)
        {
//            公司
            initCityList(cityListCompany);
            initBrandList(brandDataListCompany);
            initTagList(tagDataListCompany);
            initSkinList(skinDataListCompany);

            param = paramCompany;
            binding.cityWrap.setVisibility(View.VISIBLE);
            binding.brandWrap.setVisibility(View.VISIBLE);

        }
        else
        {
//            服務
            initCityList(cityListPersonnelCompany);
            initBrandList(brandDataListPersonnelCompany);
            initTagList(tagDataListPersonnelCompany);
            initSkinList(skinDataListPersonnelCompany);

            param = paramPersonnelCompany;
            binding.priceWrap.setVisibility(View.VISIBLE);
            binding.cityWrap.setVisibility(View.VISIBLE);
            binding.brandWrap.setVisibility(View.VISIBLE);
        }

        if(!binding.searchEdit.getText().toString().equals(param.getStringMapParam("search")))
        {
            binding.searchEdit.setText(param.getStringMapParam("search"));
        }

        binding.priceMin.setText(param.getStringMapParam("priceMin"));
        binding.priceMax.setText(param.getStringMapParam("priceMax"));
        binding.heightMin.setText(param.getStringMapParam("heightMin"));
        binding.heightMax.setText(param.getStringMapParam("heightMax"));
        binding.chestMin.setText(param.getStringMapParam("chestMin"));
        binding.chestMax.setText(param.getStringMapParam("chestMax"));
        binding.waistlineMin.setText(param.getStringMapParam("waistlineMin"));
        binding.waistlineMax.setText(param.getStringMapParam("waistlineMax"));
        binding.hiplineMin.setText(param.getStringMapParam("hiplineMin"));
        binding.hiplineMax.setText(param.getStringMapParam("hiplineMax"));
        binding.shoeSizeMin.setText(param.getStringMapParam("shoeSizeMin"));
        binding.shoeSizeMax.setText(param.getStringMapParam("shoeSizeMax"));
        binding.braSizeMin.setText(param.getStringMapParam("braSizeMin"));
        binding.braSizeMax.setText(param.getStringMapParam("braSizeMax"));

    }

//    private void initList()
//    {
//        searchPersonnel.searchKey(search,occupation_id,"user");
//        searchCompany.searchKey(search,occupation_id,"company");
//        searchPersonnelCompany.searchKey(search,occupation_id,"all");
//    }



//    @BindView(R.id.submitBtn)
//    Button submitBtn;
//    @BindView(R.id.cancelBtn)
//    Button cancelBtn;
//    @BindView(R.id.filtrateBtn)
//    ImageView filtrateBtn;
//
//    @BindView(R.id.priceMin)
//    EditText priceMin;
//
//    @BindView(R.id.priceMax)
//    EditText priceMax;
//    @BindView(R.id.recyclerCityList)
//    RecyclerView recyclerCityList;
//    @BindView(R.id.recyclerBrandList)
//    RecyclerView recyclerBrandList;
//
//    @BindView(R.id.drawerLayout)
//    DrawerLayout drawerLayout;



    private void getDataList()
    {
        getDataList(false);
    }
    private void getDataList(boolean isSearch)
    {

        if(!isSearch)
        {
//            loadingDailog.show();
        }
        List<GoodsCitytemAdapter.City> cityList;
        HttpParamsObject tempParam;
        HttpParamsObject returnParam;
        StringDataListBean.DataBean brandDataList;
        StringDataListBean.DataBean tagDataList;
        StringDataListBean.DataBean skinDataList;
        if(viewpager.getCurrentItem() == 0)
        {
//            个人
            cityList = cityListPersonnel;
            brandDataList = brandDataListPersonnel;
            tagDataList = tagDataListPersonnel;
            skinDataList = skinDataListPersonnel;
            returnParam = paramPersonnel;
            tempParam = MyUtil.cloneObject(paramPersonnel);

        }
        else if(viewpager.getCurrentItem() == 1)
        {
            cityList = cityListCompany;
            brandDataList = brandDataListCompany;
            tagDataList = tagDataListCompany;
            skinDataList = skinDataListCompany;
            returnParam = paramCompany;
            tempParam = MyUtil.cloneObject(paramCompany);
        }
        else
        {
            cityList = cityListPersonnelCompany;
            brandDataList = brandDataListPersonnelCompany;
            tagDataList = tagDataListPersonnelCompany;
            skinDataList = skinDataListPersonnelCompany;
            returnParam = paramPersonnelCompany;
            tempParam = MyUtil.cloneObject(paramPersonnelCompany);
        }

        tempParam.setParam("search",binding.searchEdit.getText().toString());
        returnParam.setParam("search",binding.searchEdit.getText().toString());


        List<String> tempCity = new LinkedList<>();
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).isSelect) {
                tempCity.add(cityList.get(i).code);
            }
        }
        tempParam.setParam("areaCode", tempCity);


        List<String> brandDataListTemp = new LinkedList<>();
        if (brandDataList != null) {
            for (int i = 0; i < brandDataList.getData().size(); i++) {
                if (brandDataList.getData().get(i).isSelect()) {
                    brandDataListTemp.add(brandDataList.getData().get(i).getBrand_id());
                }
            }
        }
        tempParam.setParam("brandId", brandDataListTemp);

        List<String> tagDataListTemp = new LinkedList<>();
        if (tagDataList != null) {
            for (int i = 0; i < tagDataList.getData().size(); i++) {
                if (tagDataList.getData().get(i).isSelect()) {
                    tagDataListTemp.add(tagDataList.getData().get(i).getTitle());
                }
            }
        }
        tempParam.setParam("tagsTitle", tagDataListTemp);

        List<String> skinDataListTemp = new LinkedList<>();
        if (skinDataList != null) {
            for (int i = 0; i < skinDataList.getData().size(); i++) {
                if (skinDataList.getData().get(i).isSelect()) {
                    skinDataListTemp.add(skinDataList.getData().get(i).getColor_id());
                }
            }
        }
        tempParam.setParam("colorId", skinDataListTemp);


        tempParam.setParam("priceMin", binding.priceMin.getText().toString());
        tempParam.setParam("priceMax", binding.priceMax.getText().toString());
        tempParam.setParam("heightMin", binding.heightMin.getText().toString());
        tempParam.setParam("heightMax", binding.heightMax.getText().toString());
        tempParam.setParam("chestMin", binding.chestMin.getText().toString());
        tempParam.setParam("chestMax", binding.chestMax.getText().toString());
        tempParam.setParam("waistlineMin", binding.waistlineMin.getText().toString());
        tempParam.setParam("waistlineMax", binding.waistlineMax.getText().toString());
        tempParam.setParam("hiplineMin", binding.hiplineMin.getText().toString());
        tempParam.setParam("hiplineMax", binding.hiplineMax.getText().toString());
        tempParam.setParam("shoeSizeMin", binding.shoeSizeMin.getText().toString());
        tempParam.setParam("shoeSizeMax", binding.shoeSizeMax.getText().toString());
        tempParam.setParam("braSizeMin", binding.braSizeMin.getText().toString());
        tempParam.setParam("braSizeMax", binding.braSizeMax.getText().toString());


        HttpParamsObject itemClickParam = new HttpParamsObject();
        itemClickParam.setParam("fromType",fromType);
        itemClickParam.setParam("projectId",projectId);
        itemClickParam.setParam("occupation_id",occupation_id);

        ((OccupationImplements)fragmentList.get(viewpager.getCurrentItem())).searchKey(tempParam, isSearch, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GoodsListItem dataListTemp = new Gson().fromJson(data, GoodsListItem.class);
                if (dataListTemp.getData().getData().size() <= 0) {
                    showDialogTip();
                    return;
                }
                for (int i = 0; i < cityList.size(); i++) {
                    cityList.get(i).realIsSelect = cityList.get(i).isSelect;
                }
                if (brandDataList != null) {
                    for (int i = 0; i < brandDataList.getData().size(); i++) {
                        brandDataList.getData().get(i).setRealIsSelect(brandDataList.getData().get(i).isSelect());
                    }
                }
                if (tagDataList != null) {
                    for (int i = 0; i < tagDataList.getData().size(); i++) {
                        tagDataList.getData().get(i).setRealIsSelect(tagDataList.getData().get(i).isSelect());
                    }
                }

                if (skinDataList != null) {
                    for (int i = 0; i < skinDataList.getData().size(); i++) {
                        skinDataList.getData().get(i).setRealIsSelect(skinDataList.getData().get(i).isSelect());
                    }
                }
                if(viewpager.getCurrentItem() == 0)
                {
                    paramPersonnel = tempParam;
                }
                else if(viewpager.getCurrentItem() == 1)
                {
                    paramCompany = tempParam;
                }
                else
                {
                    paramPersonnelCompany = tempParam;
                }


                binding.drawerLayout.closeDrawer(Gravity.END);
            }

            @Override
            public void onFinal() {
                super.onFinal();
                if(!isSearch) {
//                    loadingDailog.hide();
                }
            }
        },itemClickParam);

    }

    private void event() {

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.closeDrawer(Gravity.END);
            }
        });

        /**
         * 提交筛选
         */
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                List<GoodsCitytemAdapter.City> cityList;
//                if(viewpager.getCurrentItem() == 0)
//                {
////            个人
//                    cityList = cityListPersonnel;
//
//                }
//                else if(viewpager.getCurrentItem() == 1)
//                {
//                    cityList = cityListCompany;
//                }
//                else
//                {
//                    cityList = cityListPersonnelCompany;
//                }
//
//                for (int i = 0; i < cityList.size(); i++) {
//                    cityList.get(i).realIsSelect = cityList.get(i).isSelect;
//                }

                getDataList();


//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        });




        /**
         * 打开抽屉
         */
        binding.filtrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.drawerLayout.openDrawer(Gravity.END);
            }
        });

        /**
         * 抽屉打开时执行
         */
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
//                Log.e("onDrawerSlide",v+"");

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                initDrawerLayout();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                List<GoodsCitytemAdapter.City> cityList;
                StringDataListBean.DataBean brandDataList;
                if(viewpager.getCurrentItem() == 0)
                {
//            个人
                    cityList = cityListPersonnel;
                    brandDataList = brandDataListPersonnel;

                }
                else if(viewpager.getCurrentItem() == 1)
                {
                    cityList = cityListCompany;
                    brandDataList = brandDataListCompany;
                }
                else
                {
                    cityList = cityListPersonnelCompany;
                    brandDataList = brandDataListPersonnelCompany;
                }


                for (int i = 0; i < cityList.size(); i++) {
                    cityList.get(i).isSelect = cityList.get(i).realIsSelect;
                }
                if (adapterCity != null) {
                    adapterCity.notifyDataSetChanged();
                }


                if (brandDataList != null) {
                    for (int i = 0; i < brandDataList.getData().size(); i++) {
                        brandDataList.getData().get(i).setSelect(brandDataList.getData().get(i).isRealIsSelect());
                    }
                    if (adapterBrand != null) {
                        adapterBrand.notifyDataSetChanged();
                    }
                }


//                if (brandDataList != null) {
//                    for (int i = 0; i < brandDataList.getData().size(); i++) {
//                        brandDataList.getData().get(i).setSelect(brandDataList.getData().get(i).isRealIsSelect());
//                    }
//                    if (adapterBrand != null) {
//                        adapterBrand.notifyDataSetChanged();
//                    }
//                }
//
//                priceMin.setText(priceMinStr);
//                priceMax.setText(priceMaxStr);
//                Log.e("onDrawerClosed","333333333333333333333");
            }

            @Override
            public void onDrawerStateChanged(int i) {
//                Log.e("onDrawerStateChanged",i+"");
            }
        });

    }


    /**
     * 项目列表
     */
    public void initViewList() {
        project_list_type1 = binding.projectListType1;
        project_list_type2 = binding.projectListType2;
        project_list_type3 = binding.projectListType3;
//        searchWrap = (RelativeLayout) findViewById(R.id.searchWrap);

        project_list_type1.setActivated(true);
//        aList = new ArrayList<View>();
        searchPersonnel = SearchOccupationItem.newInstance();
        searchCompany = SearchOccupationItem.newInstance();
        searchPersonnelCompany = SearchOccupationGoodsItem.newInstance();


        fragmentList = new ArrayList<>();
        fragmentList.add(searchPersonnel);
        fragmentList.add(searchCompany);
        if(isProjectJump())
        {
            project_list_type3.setVisibility(View.GONE);
        }
        else
        {
            fragmentList.add(searchPersonnelCompany);
        }


        viewpager = binding.searchProjectList;

        viewpager.setAdapter(new SearchOccupationFragment.Adaper(getSupportFragmentManager()));
//        viewpager.requestLayoutByPosition("projectProject");
//        initList();

        viewpager.setOffscreenPageLimit(3);
//        searchPersonnel.searchKey(search,occupation_id,"user");
        getDataList();
        initDrawerLayout();
//        searchWrap.setVisibility(View.GONE);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                searchWrap.setVisibility(View.GONE);
                if (position == 0) {
//                    viewpager.requestLayoutByPosition("projectProject");
//                    viewpager.setCurrentItem(0);
                    project_list_type1.setTextColor(Color.parseColor("#80B5FF"));
                    project_list_type2.setTextColor(Color.parseColor("#000000"));
                    project_list_type3.setTextColor(Color.parseColor("#000000"));
//                    searchPersonnel.searchKey(search,occupation_id,"user");
//                    searchCompany.searchKey(search,occupation_id,"company");
//                    searchPersonnelCompany.searchKey(search,occupation_id,"all");
//                project_list_type1.setActivated(true);
//                project_list_type2.setActivated(false);

                } else if (position == 1){
//                    viewpager.requestLayoutByPosition("projectNotice");
//                    viewpager.setCurrentItem(1);
                    project_list_type1.setTextColor(Color.parseColor("#000000"));
                    project_list_type2.setTextColor(Color.parseColor("#80B5FF"));
                    project_list_type3.setTextColor(Color.parseColor("#000000"));
//                    searchPersonnel.searchKey(search,occupation_id,"user");
//                    searchCompany.searchKey(search,occupation_id,"company");
//                    searchPersonnelCompany.searchKey(search,occupation_id,"all");
//                project_list_type2.setTextColor(Color.parseColor("#80B5FF"));
//                project_list_type1.setTextColor(Color.parseColor("#000000"));

                }
                else
                {
                    project_list_type1.setTextColor(Color.parseColor("#000000"));
                    project_list_type2.setTextColor(Color.parseColor("#000000"));
                    project_list_type3.setTextColor(Color.parseColor("#80B5FF"));
//                    searchWrap.setVisibility(View.VISIBLE);
//                    searchPersonnel.searchKey(search,occupation_id,"user");
//                    searchCompany.searchKey(search,occupation_id,"company");
//                    searchPersonnelCompany.searchKey(search,occupation_id,"all");
                }
                initDrawerLayout();
                getDataList();

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
//                ToastShow.s("0");
            }
        });

        project_list_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
            }
        });

        project_list_type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(2);
            }
        });

//        viewpager.setCurrentItem(showTypePos);

    }


    /**
     * 我当前位置的标记
     */
    private void initMYLocation() {

        GoodsCitytemAdapter.City tempCity = new GoodsCitytemAdapter.City();
        tempCity.name = "正在定位...";
        tempCity.code = "0";
        tempCity.isSelect = false;
        tempCity.realIsSelect = false;
        cityListCompany.add(tempCity);
        cityListPersonnel.add(tempCity);
        cityListPersonnelCompany.add(tempCity);

        GoodsCitytemAdapter.City tempCity2 = new GoodsCitytemAdapter.City();
        tempCity2.name = "其他地区";
        tempCity2.code = "0";
        tempCity2.isSelect = false;
        cityListCompany.add(tempCity2);
        cityListPersonnel.add(tempCity2);
        cityListPersonnelCompany.add(tempCity2);

        initCityList(cityListPersonnel);

        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(this);
        mLocationManager.requestSingleFreshLocation(null, new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

                reGeocoder(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()));

            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        }, Looper.getMainLooper());

    }

    /**
     * 获取经纬的当前地理位置和周边地理位置
     */
    protected void reGeocoder(LatLng latLng) {
//        String str = etRegeocoder.getText().toString().trim();
//        LatLng latLng = str2Coordinate(this, str);
        if (latLng == null) {
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(this);
        //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
        //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
        Geo2AddressParam geo2AddressParam = new Geo2AddressParam(latLng)
                .getPoi(false);
        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {
            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Geo2AddressResultObject obj = (Geo2AddressResultObject) arg1;
                StringBuilder sb = new StringBuilder();
                String temCode = obj.result.ad_info.adcode;




                GoodsCitytemAdapter.City tempCity = new GoodsCitytemAdapter.City();
                if (obj.result.ad_info.city == null || obj.result.ad_info.city.equals("") || temCode == null || temCode.equals("")) {
                    tempCity.name = "重新定位";
                    tempCity.code = "0";
                    tempCity.isSelect = false;
                    tempCity.realIsSelect = false;


                } else {
                    temCode = temCode.substring(0, 4);
                    tempCity.name = obj.result.ad_info.city;
                    tempCity.code = temCode + "00";
                    tempCity.isSelect = false;
                    tempCity.realIsSelect = false;

                }

                cityListPersonnel.remove(0);
                cityListPersonnel.add(0, MyUtil.cloneObject(tempCity));

                cityListCompany.remove(0);
                cityListCompany.add(0, MyUtil.cloneObject(tempCity));

                cityListPersonnelCompany.remove(0);
                cityListPersonnelCompany.add(0, MyUtil.cloneObject(tempCity));

                adapterCity.notifyDataSetChanged();

//                City tempCity2 = new City();
//                tempCity2.name = "其他地区";
//                tempCity2.code = "0";
//                tempCity2.isSelect = false;
//                cityList.add(tempCity2);


//
//                City tempCity3 = new City();
//                tempCity3.name = "安康市";
//                tempCity3.code = "610900";
//                tempCity3.isSelect = true;
//                cityList.add(tempCity3);
//                initList();
//
//
//                City tempCity4 = new City();
//                tempCity4.name = "百色市";
//                tempCity4.code = "451000";
//                tempCity4.isSelect = true;
//                cityList.add(tempCity4);
//
//
//                City tempCity5 = new City();
//                tempCity5.name = "宝鸡市";
//                tempCity5.code = "610300";
//                tempCity5.isSelect = false;
//                cityList.add(tempCity5);

//                for (int i = 0; i < 30; i++) {
//                    cityList.add(cityList.get(0));
//                }
//                initList();
                Log.e("test", sb.toString());
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                GoodsCitytemAdapter.City tempCity = new GoodsCitytemAdapter.City();
                tempCity.name = "重新定位";
                tempCity.code = "0";
                tempCity.isSelect = false;
                tempCity.realIsSelect = false;

                cityListPersonnel.remove(0);
                cityListPersonnel.add(0, MyUtil.cloneObject(tempCity));

                cityListCompany.remove(0);
                cityListCompany.add(0, MyUtil.cloneObject(tempCity));

                cityListPersonnelCompany.remove(0);
                cityListPersonnelCompany.add(0, MyUtil.cloneObject(tempCity));

                adapterCity.notifyDataSetChanged();

                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });
    }


    GoodsCitytemAdapter adapterCity;
    /**
     * 地区初始化
     */
    private void initCityList(List<GoodsCitytemAdapter.City> cityList) {

        adapterCity = new GoodsCitytemAdapter(cityList);
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(SearchOccupationFragment.this, true);
        binding.recyclerCityList.setNestedScrollingEnabled(false);
        binding.recyclerCityList.setAdapter(adapterCity);
        binding.recyclerCityList.setLayoutManager(layout);

        adapterCity.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (cityList.get(position).name.equals("其他地区")) {
                    Intent intent = new Intent(SearchOccupationFragment.this, SelectCityActivity.class);
                    intent.putExtra("cityList", (Serializable) cityList);
                    SearchOccupationFragment.this.startActivityForResult(intent, 5);

//                    Bundle bundle = new Bundle();
//                    Tools.jump(GoodsFragment.this, SelectCityActivity.class, bundle, false);
                } else if (cityList.get(position).name.equals("重新定位")) {
                    initMYLocation();
                } else if (cityList.get(position).code.equals("0")) {
                    return;
                } else {
                    cityList.get(position).isSelect = !cityList.get(position).isSelect;
                    adapterCity.notifyDataSetChanged();
                }

            }
        });

    }

    GoodsBrandItemAdapter adapterBrand;
    /**
     * 品牌初始化
     */
    private void initBrandList(StringDataListBean.DataBean  brandDataList) {
        if(brandDataList == null)
        {
            return;
        }
        adapterBrand = new GoodsBrandItemAdapter(brandDataList.getData());
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(SearchOccupationFragment.this, true);
        binding.recyclerBrandList.setNestedScrollingEnabled(false);
        binding.recyclerBrandList.setAdapter(adapterBrand);
        binding.recyclerBrandList.setLayoutManager(layout);

        adapterBrand.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                brandDataList.getData().get(position).setSelect(!brandDataList.getData().get(position).isSelect());
                adapterBrand.notifyDataSetChanged();
            }
        });

    }

    private void getBrandList() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.HOME_GET_BRAND, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                StringDataListBean.DataBean  brandDataList = new Gson().fromJson(data, StringDataListBean.DataBean.class);

                brandDataListPersonnel = MyUtil.cloneObject(brandDataList);
                brandDataListCompany = MyUtil.cloneObject(brandDataList);
                brandDataListPersonnelCompany = MyUtil.cloneObject(brandDataList);

                initBrandList(brandDataListPersonnel);
            }
        });
    }



    GoodsBrandItemAdapter adapterTag;
    /**
     * 品牌初始化
     */
    private void initTagList(StringDataListBean.DataBean  tagDataList) {
        if(tagDataList == null)
        {
            return;
        }
        adapterTag = new GoodsBrandItemAdapter(tagDataList.getData());
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(SearchOccupationFragment.this, true);
        binding.recyclerTagList.setNestedScrollingEnabled(false);
        binding.recyclerTagList.setAdapter(adapterTag);
        binding.recyclerTagList.setLayoutManager(layout);

        adapterTag.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                tagDataList.getData().get(position).setSelect(!tagDataList.getData().get(position).isSelect());
                adapterTag.notifyDataSetChanged();
            }
        });
    }

    private void getTagList() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.SEARCH_GET_TAGS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                StringDataListBean.DataBean  tagDataList = new Gson().fromJson(data, StringDataListBean.DataBean.class);

                tagDataListPersonnel = MyUtil.cloneObject(tagDataList);
                tagDataListCompany = MyUtil.cloneObject(tagDataList);
                tagDataListPersonnelCompany = MyUtil.cloneObject(tagDataList);

                initTagList(tagDataListPersonnel);
            }
        });
    }



    GoodsBrandItemAdapter adapterSkin;
    /**
     * 皮肤初始化
     */
    private void initSkinList(StringDataListBean.DataBean  skinDataList) {
        if(skinDataList == null)
        {
            return;
        }
        adapterSkin = new GoodsBrandItemAdapter(skinDataList.getData());
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(SearchOccupationFragment.this, true);
        binding.recyclerSkinList.setNestedScrollingEnabled(false);
        binding.recyclerSkinList.setAdapter(adapterSkin);
        binding.recyclerSkinList.setLayoutManager(layout);

        adapterSkin.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                skinDataList.getData().get(position).setSelect(!skinDataList.getData().get(position).isSelect());
                adapterSkin.notifyDataSetChanged();
            }
        });
    }

    private void getSkinList() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.USER_APPROVE_GET_SKIN_COLOR, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                StringDataListBean.DataBean  skinDataList = new Gson().fromJson(data, StringDataListBean.DataBean.class);

                skinDataListPersonnel = MyUtil.cloneObject(skinDataList);
                skinDataListCompany = MyUtil.cloneObject(skinDataList);
                skinDataListPersonnelCompany = MyUtil.cloneObject(skinDataList);

                initSkinList(skinDataListPersonnel);
            }
        });
    }



    /**
     * 城市设置选中，并返回下标
     * @param city
     * @param cityList
     * @return
     */
    private int isInCityList(GoodsCitytemAdapter.City city, List<GoodsCitytemAdapter.City> cityList) {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).code.equals(city.code)) {
                cityList.get(i).isSelect = true;
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 5) {
//            double longitude = data.getDoubleExtra("longitude", 0);
//            double latitude = data.getDoubleExtra("latitude", 0);
//            String adname = data.getStringExtra("adname");
//            ((TextView)findViewById(R.id.address)).setText(adname);
                List<GoodsCitytemAdapter.City> tempCityList = (List<GoodsCitytemAdapter.City>) data.getSerializableExtra("cityList");
                List<GoodsCitytemAdapter.City> cityList;
                if(viewpager.getCurrentItem() == 0)
                {
//            个人
                    cityList = cityListPersonnel;

                }
                else if(viewpager.getCurrentItem() == 1)
                {
                    cityList = cityListCompany;
                }
                else
                {
                    cityList = cityListPersonnelCompany;
                }

                for (int i = 0; i < cityList.size(); i++) {
                    cityList.get(i).isSelect = false;
                }
                for (int i = 0; i < tempCityList.size(); i++) {
                    if (isInCityList(tempCityList.get(i), cityList) == -1) {
                        cityList.add(tempCityList.get(i));
                    }

                }
                adapterCity.notifyDataSetChanged();

            }
        }
    }




    private void showDialogTip() {
        showDialogTip("暂无匹配的，请更换其他选项");
    }

    private void showDialogTip(String text) {

        binding.dialogTipText.setText(text);
        binding.dialogTip.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //要执行的任务
                binding.dialogTip.setVisibility(View.GONE);
            }
        }, 1500);

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
//            return view == object;
            return super.isViewFromObject(view, object);
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
////            String[] title = {"第一", "第二", "第三"};
////            return title[position];
//        }
    }


}

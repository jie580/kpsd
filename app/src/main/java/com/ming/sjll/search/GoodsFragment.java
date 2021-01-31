package com.ming.sjll.search;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.company.ShowcaseInfoActivity;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;

import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.map.selectcity.SelectCityActivity;
import com.ming.sjll.search.adapter.GoodsBrandItemAdapter;
import com.ming.sjll.search.adapter.GoodsCitytemAdapter;
import com.ming.sjll.search.adapter.GoodsCitytemAdapter.City;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.ming.sjll.ui.PaginationScrollListener;
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
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 服务，借物
 */
public class GoodsFragment extends BaseActivity {


    @BindView(R.id.dialogTip)
    LinearLayout dialogTip;

    @BindView(R.id.dialogTipText)
    TextView dialogTipText;

    @BindView(R.id.submitBtn)
    Button submitBtn;

    @BindView(R.id.cancelBtn)
    Button cancelBtn;

    @BindView(R.id.recyclerCityList)
    RecyclerView recyclerCityList;

    @BindView(R.id.recyclerBrandList)
    RecyclerView recyclerBrandList;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.filtrateBtn)
    ImageView filtrateBtn;

    @BindView(R.id.priceMin)
    EditText priceMin;

    @BindView(R.id.priceMax)
    EditText priceMax;

    @BindView(R.id.recyclerList)
    RecyclerView recyclerList;

//    @BindView(R.id.backdrop)
//    ImageView backdrop;

    String classId;

    String projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_goods_fragment);

        classId = getIntent().getStringExtra("classId");
        projectId = getIntent().getStringExtra("projectId");
//        getBackdrop();
        getList();
        requirePermission();
        initBrandList();
        event();

    }
//    private void getBackdrop()
//    {
//        HttpParamsObject param = new HttpParamsObject();
//        param.setParam("class_id",classId);
//        new HttpUtil().sendRequest(Constant.SHOW_CASE_GET_CLASS_IMG, param, new CommonCallback() {
//
//            @Override
//            public void onSuccess(Object data, int code) {
//                super.onSuccess(data, code);
//                new ImageHelper().displayBackgroundLoading(backdrop,
//                        Constant.BASE_IMAGE + ObjeGetValue.GetValu("icon", data).toString());
//
//            }
//        });
//
//    }


    private void event() {

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.END);
            }
        });
        /**
         * 提交筛选
         */
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getList();

//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        });


        /**
         * 打开抽屉
         */
        filtrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.END);
            }
        });

        /**
         * 抽屉打开时执行
         */
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
//                Log.e("onDrawerSlide",v+"");

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {


            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
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

                priceMin.setText(priceMinStr);
                priceMax.setText(priceMaxStr);
//                Log.e("onDrawerClosed","333333333333333333333");
            }

            @Override
            public void onDrawerStateChanged(int i) {
//                Log.e("onDrawerStateChanged",i+"");
            }
        });

        EditText searchEdit = findViewById(R.id.searchEdit);
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

                search = searchEdit.getText().toString();
//                commParam.setParam("search",search);
                getList(true);

            }
        });
    }


    private void showDialogTip() {
        showDialogTip("暂无匹配的，请更换其他选项");
    }

    private void showDialogTip(String text) {

        dialogTipText.setText(text);
        dialogTip.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //要执行的任务
                dialogTip.setVisibility(View.GONE);
            }
        }, 1500);

    }



    private boolean isLoad = false;
    private boolean hasData = true;
    private int page = 1;
    private int pageSize = 5;
    String search;
    String priceMinStr;
    String priceMaxStr;

    GoodsListItem dataList;
    GoodsListAdapter listAdapter;
    LinearLayoutManager linearLayoutManager;

    HttpParamsObject param;



    public abstract class GridDecoration extends RecyclerView.ItemDecoration {

        private Paint mPaint;
        private int   lineWidth;//px 分割线宽

        public GridDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB) {
            this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, context.getResources().getDisplayMetrics());
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(colorRGB);
            mPaint.setStyle(Paint.Style.FILL);
        }

        public GridDecoration(Context context, int lineWidthDp, @ColorInt int colorRGB) {
            this(context, (float) lineWidthDp, colorRGB);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            //left, top, right, bottom
            int childCount1 = parent.getChildCount();
            //        int childCount2 = parent.getLayoutManager().getChildCount();
            //        int childCount3 = parent.getAdapter().getItemCount();
            //        Log.e("count", "getChildCount()=" + childCount1 + "-----getLayoutManager().getChildCount()=" + childCount2 + "----getAdapter().getItemCount()=" + childCount3);
            for (int i = 0; i < childCount1; i++) {
                View child = parent.getChildAt(i);

                int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();

                boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);
                if (sideOffsetBooleans[0]) {
                    drawChildLeftVertical(child, c, parent);
                }
                if (sideOffsetBooleans[1]) {
                    drawChildTopHorizontal(child, c, parent);
                }
                if (sideOffsetBooleans[2]) {
                    drawChildRightVertical(child, c, parent);
                }
                if (sideOffsetBooleans[3]) {
                    drawChildBottomHorizontal(child, c, parent);
                }
            }
        }

        private void drawChildBottomHorizontal(View child, Canvas c, RecyclerView parent) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getLeft() - params.leftMargin - lineWidth;
            int right = child.getRight() + params.rightMargin + lineWidth;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + lineWidth;

            c.drawRect(left, top, right, bottom, mPaint);
        }

        private void drawChildTopHorizontal(View child, Canvas c, RecyclerView parent) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getLeft() - params.leftMargin - lineWidth;
            int right = child.getRight() + params.rightMargin + lineWidth;
            int bottom = child.getTop() - params.topMargin;
            int top = bottom - lineWidth;

            c.drawRect(left, top, right, bottom, mPaint);
        }

        private void drawChildLeftVertical(View child, Canvas c, RecyclerView parent) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin - lineWidth;
            int bottom = child.getBottom() + params.bottomMargin + lineWidth;
            int right = child.getLeft() - params.leftMargin;
            int left = right - lineWidth;

            c.drawRect(left, top, right, bottom, mPaint);
        }

        private void drawChildRightVertical(View child, Canvas c, RecyclerView parent) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin - lineWidth;
            int bottom = child.getBottom() + params.bottomMargin + lineWidth;
            int left = child.getRight() + params.rightMargin;
            int right = left + lineWidth;

            c.drawRect(left, top, right, bottom, mPaint);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
            //作为传递left,right,top,bottom的偏移值来用的
            int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);

            //如果是设置左边或者右边的边距，就只设置成指定宽度的一半，
            // 因为这个项目中的 Grid 是一行二列，如果不除以二的话，那么中间的间距就会很宽，
            //可根据实际项目需要修改成合适的值
            int left = sideOffsetBooleans[0] ? lineWidth/2 : 0;
            int top = sideOffsetBooleans[1] ? lineWidth : 0;
            int right = sideOffsetBooleans[2] ? lineWidth/2 : 0;
            int bottom = sideOffsetBooleans[3] ? lineWidth : 0;

            outRect.set(left, top, right, bottom);
        }

        /**
         * 顺序:left, top, right, bottom
         *
         * @return boolean[4]
         */
        public abstract boolean[] getItemSidesIsHaveOffsets(int itemPosition);


    }


    private void getList()
    {
        getList(false);
    }
    private void getList(boolean isSearch) {
        if (isLoad) {
            return;
        }
        if(!isSearch)
        {
            loadingDailog.show();
        }
        isLoad = true;
         param = new HttpParamsObject();
        String priceMinTemp = priceMin.getText().toString();
        String priceMaxTemp = priceMax.getText().toString();
        page = 1;
        param.setParam("page", page);
        param.setParam("class_id", classId);
        param.setParam("pageSize", pageSize);
        param.setParam("priceMin", priceMinTemp);
        param.setParam("priceMax", priceMaxTemp);
        param.setParam("search", search);
        List<String> tempCity = new LinkedList<>();
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).isSelect) {
                tempCity.add(cityList.get(i).code);
            }
        }
        param.setParam("areaCode", tempCity);


        List<String> brandDataListTemp = new LinkedList<>();
        if (brandDataList != null) {
            for (int i = 0; i < brandDataList.getData().size(); i++) {
                if (brandDataList.getData().get(i).isSelect()) {
                    brandDataListTemp.add(brandDataList.getData().get(i).getBrand_id());
                }
            }
        }
        param.setParam("brandId", brandDataListTemp);

//        param.setParam("priceMax",priceMax);

        new HttpUtil().sendRequest(Constant.SEARCH_GOODS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GoodsListItem dataListTemp = new Gson().fromJson(data, GoodsListItem.class);
                if (dataListTemp.getData().getData().size() <= 0 && !isSearch) {
//                    hasData = false;
                    showDialogTip();
                    return;
                }

                dataList = dataListTemp;
                for (int i = 0; i < cityList.size(); i++) {
                    cityList.get(i).realIsSelect = cityList.get(i).isSelect;
                }
                if (brandDataList != null) {
                    for (int i = 0; i < brandDataList.getData().size(); i++) {
                        brandDataList.getData().get(i).setRealIsSelect(brandDataList.getData().get(i).isSelect());
                    }
                }

                priceMinStr = priceMinTemp;
                priceMaxStr = priceMaxTemp;

                linearLayoutManager = new GridLayoutManager( GoodsFragment.this, 2);
                recyclerList.setLayoutManager(linearLayoutManager);
                int color = getResources().getColor(R.color.transparency);
//                recyclerList.addItemDecoration(new GridDecoration(getActivity(), 8, color){
//                    @Override
//                    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
//                        //顺序:left, top, right, bottom
//                        boolean[] booleans = {false, false, false, false};
//                        if (itemPosition == 0) {
//                            //因为给 RecyclerView 添加了 header，所以原本的 position 发生了变化
//                            //position 为 0 的地方实际上是 header，真正的列表 position 从 1 开始
//                        } else {
//                            switch (itemPosition % 2) {
//                                case 0:
//                                    //每一行第二个只显示左边距和下边距
//                                    booleans[0] = true;
//                                    booleans[3] = true;
//                                    break;
//                                case 1:
//                                    //每一行第一个显示右边距和下边距
//                                    booleans[2] = true;
//                                    booleans[3] = true;
//                                    break;
//                            }
//                        }
//                        return booleans;
//                    }
//                });

                listAdapter = new GoodsListAdapter(dataList.getData().getData(), false, true);

                recyclerList.setAdapter(listAdapter);


                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.wrap:
//                               查看详情
                                Intent intent = new Intent(GoodsFragment.this, ShowcaseInfoActivity.class);
                                intent.putExtra("goodsId", dataList.getData().getData().get(position).getGoods_id());
                                intent.putExtra("projectId", projectId);
                                startActivity(intent);
                                break;
                            case R.id.userWrap:
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", dataList.getData().getData().get(position).getUser_id());
                                Tools.jump(GoodsFragment.this, HomepageActivity.class, bundle,false);
                                break;
                            case R.id.collect:
                                loadingDailog.show();
                                HttpParamsObject param = new HttpParamsObject();
                                param.setParam("targetId",dataList.getData().getData().get(position).getGoods_id());
                                param.setParam("type","goods");
                                new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT, param, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        GetInfoBean temp = new Gson().fromJson(data, GetInfoBean.class);
                                        dataList.getData().getData().get(position).setIs_collect(temp.getData().getIs_collect());
                                        listAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFinal() {
                                        super.onFinal();
                                        loadingDailog.hide();
                                    }
                                });
                                break;
                                }
                        }
                    });


                recyclerList.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                    @Override
                    protected void loadMoreItems() {
                        nextPage();
                    }

                    @Override
                    public int getTotalPageCount() {
                        return dataList.getData().getTotal();
                    }

                    @Override
                    public boolean isLastPage() {
                        return page == dataList.getData().getLastPage();
                    }

                    @Override
                    public boolean isLoading() {
                        return isLoad;
                    }
                });

                drawerLayout.closeDrawer(Gravity.END);
            }

            @Override
            public void onFinal() {
                super.onFinal();
                if(!isSearch && loadingDailog!=null)
                {
                    loadingDailog.hide();
                }
                isLoad = false;

            }

        });
    }


    @AfterPermissionGranted(1)
    private void requirePermission() {


        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        String[] permissionsForQ = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                "android.permission.ACCESS_BACKGROUND_LOCATION", //target为Q时，动态请求后台定位权限
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (Build.VERSION.SDK_INT >= 29 ? EasyPermissions.hasPermissions(this, permissionsForQ) :
                EasyPermissions.hasPermissions(this, permissions)) {
//            Toast.makeText(this, "权限OK", Toast.LENGTH_LONG).show();
            initMYLocation();
        } else {
            EasyPermissions.requestPermissions(this, "需要权限",
                    1, Build.VERSION.SDK_INT >= 29 ? permissionsForQ : permissions);
        }
    }

    private void nextPage() {
        if (loadingDailog != null) {
            loadingDailog.show();
        }
        HttpParamsObject param = new HttpParamsObject();
        page++;
//        if (type.equals("all")) {
//            param.setParam("search", search);
//        }
//        param.setParam("type", type);
//        param.setParam("occupation_id", occupation_id);
        param.setParam("page", page);
//        searchParam.setParam("pageSize", pageSize);

        new HttpUtil().sendRequest(Constant.SEARCH_GOODS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GoodsListItem tempDataList = new Gson().fromJson(data, GoodsListItem.class);
                for (int i = 0; i < tempDataList.getData().getData().size(); i++) {
                    dataList.getData().getData().add(tempDataList.getData().getData().get(i));
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinal() {
                super.onFinal();

                if (loadingDailog != null) {
                    loadingDailog.hide();
                }
                isLoad = false;

            }
        });

    }

    List<City> cityList = new LinkedList<>();

    /**
     * 我当前位置的标记
     */
    private void initMYLocation() {

        City tempCity = new City();
        tempCity.name = "正在定位...";
        tempCity.code = "0";
        tempCity.isSelect = false;
        tempCity.realIsSelect = false;
        cityList.add(tempCity);

        City tempCity2 = new City();
        tempCity2.name = "其他地区";
        tempCity2.code = "0";
        tempCity2.isSelect = false;
        cityList.add(tempCity2);
        initCityList();

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


                if (obj.result.ad_info.city == null || obj.result.ad_info.city.equals("") || temCode == null || temCode.equals("")) {

                    City tempCity = new City();
                    tempCity.name = "重新定位";
                    tempCity.code = "0";
                    tempCity.isSelect = false;
                    tempCity.realIsSelect = false;
                    cityList.remove(0);
                    cityList.add(0, tempCity);
                    adapterCity.notifyDataSetChanged();
                } else {
                    temCode = temCode.substring(0, 4);
                    City tempCity = new City();
                    tempCity.name = obj.result.ad_info.city;
                    tempCity.code = temCode + "00";
                    tempCity.isSelect = false;
                    tempCity.realIsSelect = false;
                    cityList.remove(0);
                    cityList.add(0, tempCity);
                }


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
                City tempCity = new City();
                tempCity.name = "重新定位";
                tempCity.code = "0";
                tempCity.isSelect = false;
                tempCity.realIsSelect = false;
                cityList.remove(0);
                cityList.add(0, tempCity);
                adapterCity.notifyDataSetChanged();

                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });
    }

    GoodsCitytemAdapter adapterCity;

    private void initCityList() {
        adapterCity = new GoodsCitytemAdapter(cityList);
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(GoodsFragment.this, true);
        recyclerCityList.setNestedScrollingEnabled(false);
        recyclerCityList.setAdapter(adapterCity);
        recyclerCityList.setLayoutManager(layout);

        adapterCity.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (cityList.get(position).name.equals("其他地区")) {
                    Intent intent = new Intent(GoodsFragment.this, SelectCityActivity.class);
                    intent.putExtra("cityList", (Serializable) cityList);
                    GoodsFragment.this.startActivityForResult(intent, 5);

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

    StringDataListBean.DataBean brandDataList;
    GoodsBrandItemAdapter adapterBrand;

    private void initBrandList() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.HOME_GET_BRAND, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                brandDataList = new Gson().fromJson(data, StringDataListBean.DataBean.class);

                adapterBrand = new GoodsBrandItemAdapter(brandDataList.getData());
                AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(GoodsFragment.this, true);
                recyclerBrandList.setNestedScrollingEnabled(false);
                recyclerBrandList.setAdapter(adapterBrand);
                recyclerBrandList.setLayoutManager(layout);

                adapterBrand.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        brandDataList.getData().get(position).setSelect(!brandDataList.getData().get(position).isSelect());
                        adapterBrand.notifyDataSetChanged();
                    }
                });

            }
        });


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
                List<City> tempCityList = (List<GoodsCitytemAdapter.City>) data.getSerializableExtra("cityList");
                for (int i = 0; i < cityList.size(); i++) {
                    cityList.get(i).isSelect = false;
                }
                for (int i = 0; i < tempCityList.size(); i++) {
                    if (isInCityList(tempCityList.get(i)) == -1) {
                        cityList.add(tempCityList.get(i));
                    }

                }
                adapterCity.notifyDataSetChanged();
            }
        }
    }

    private int isInCityList(City city) {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).code.equals(city.code)) {
                cityList.get(i).isSelect = true;
                return i;
            }
        }
        return -1;
    }

}

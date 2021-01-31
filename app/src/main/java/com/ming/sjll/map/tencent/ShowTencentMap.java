package com.ming.sjll.map.tencent;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.ui.PagedListView;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.Poi;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.tools.net.http.HttpResponseListener;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.AlphaAnimation;
import com.tencent.tencentmap.mapsdk.maps.model.Animation;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.rong.message.LocationMessage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ShowTencentMap extends BaseActivity {

    @BindView(R.id.title_bar_IvRight)
    ImageView title_bar_IvRight;


    MapView mMapView_info;
    Marker marker;
    TencentMap tencentMap;
    EditText searchAddress;
    PagedListView listView ;
    List<Poi> locationList;
    String nowCity;
    LatLng selectLocationLatLng;
    Poi selectPoi;

    AddressListItemAdapter addressListItemAdapter;

    private final Location myLocation = new Location("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_tencent);

//        title_bar_IvRight.setVisibility(View.VISIBLE);
//        title_bar_IvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top6));

        searchAddress = findViewById(R.id.searchAddress);
        mMapView_info = findViewById(R.id.map);
        listView = findViewById(R.id.listView);
        findViewById(R.id.wrap).setVisibility(View.GONE);
//        listView.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色
//        listView.showFooter(false);

        initMap();
//        requirePermission();
    }

    private void initMap() {

        tencentMap = mMapView_info.getMap();
        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        initMYLocation();
//        event();
    }


    private void submit()
    {
        TencentSearch tencentSearch = new TencentSearch(this);
        //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
        //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
        Geo2AddressParam geo2AddressParam = new Geo2AddressParam(selectLocationLatLng)
                .getPoi(false);
//                .setPoiOptions(new Geo2AddressParam.PoiOptions()
//                        .setRadius(1000)
//                        .setAddressFormat(Geo2AddressParam.PoiOptions.ADDRESS_FORMAT_SHORT))
//                        .setCategorys("面包")
//                        .setPolicy(Geo2AddressParam.PoiOptions.POLICY_DEFAULT));
        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {
            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Geo2AddressResultObject obj = (Geo2AddressResultObject)arg1;
                StringBuilder sb = new StringBuilder();
//                Log.e("定位",obj.result.ad_info.adcode+",,"+obj.result.address);

//                 String 区域底阿妈  = obj.result.ad_info.adcode;
//                 String 地址标题  = selectPoi.title;
//                 String 带省市区的详细地址 = obj.result.address;
//                 String 省 = obj.result.ad_info.province;
//                 String 市 = obj.result.ad_info.city;
//                 String 区 = obj.result.ad_info.district;
//                 double 经度 = selectPoi.latLng.latitude;
//                 double 纬度 = selectPoi.latLng.longitude;

                Intent intent = new Intent();
                intent.putExtra("area_code", obj.result.ad_info.adcode);
                intent.putExtra("title", selectPoi.title);
                intent.putExtra("address", obj.result.address);
                intent.putExtra("province", obj.result.ad_info.province);
                intent.putExtra("city", obj.result.ad_info.city);
                intent.putExtra("district", obj.result.ad_info.district);
                intent.putExtra("latitude", selectPoi.latLng.latitude);
                intent.putExtra("longitude", selectPoi.latLng.longitude);


                intent.putExtra("thumb", getMapUrl(selectPoi.latLng.latitude, selectPoi.latLng.longitude));
                intent.putExtra("lat", selectPoi.latLng.latitude);
                intent.putExtra("lng", selectPoi.latLng.longitude);
                intent.putExtra("poi", selectPoi.title);

                setResult(RESULT_OK, intent);
                finish();


            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });
    }

    private String getMapUrl(double latitude, double longitude) {
        return "https://apis.map.qq.com/ws/staticmap/v2/?center=39.8802147,116.415794&zoom=10&size=600*300&maptype=roadmap&markers=size:large|color:0xFFCCFF|label:k|39.8802147,116.415794&key=QLYBZ-AQRW2-LZ2UN-C5EPO-2CLUO-5AF4Z";
//        return "https://apis.map.qq.com/ws/staticmap/v2/?center=" + longitude + "," + latitude + "&zoom=10&size=600*300&maptype=roadmap&markers=size:large|color:0xFFCCFF|label:k|" + longitude + "," + latitude + "&key=QLYBZ-AQRW2-LZ2UN-C5EPO-2CLUO-5AF4Z";
//        return "http://restapi.amap.com/v3/staticmap?location=" + longitude + "," + latitude + "&zoom=16&scale=2&size=408*240&markers=mid,,A:" + longitude + "," + latitude + "&key=e09af6a2b26c02086e9216bd07c960ae";
    }

    private void event()
    {

        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                submit();


            }
        });


        searchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
//                searchBtn.setLayoutParams(lp);
//                searchComprehensiveFragemt.searchKey(searchEdit.getText().toString());
//                searchNoticeFragemt.searchKey(searchEdit.getText().toString());

                searchKey(searchAddress.getText().toString());
            }
        });


    }


    private void searchKey(String key)
    {
        suggestion(key);
    }




    /**
     * 关键字提示
     * @param keyword
     */
    protected void suggestion(String keyword) {
        if (keyword.trim().length() == 0) {
//            lvSuggesion.setVisibility(View.GONE);
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(this);
        SuggestionParam suggestionParam = new SuggestionParam(keyword, nowCity);

        //suggestion也提供了filter()方法和region方法
        //具体说明见文档，或者官网的webservice对应接口
        tencentSearch.suggestion(suggestionParam, new HttpResponseListener<BaseObject>() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null ||
                        nowCity.trim().length() == 0) {
//                    lvSuggesion.setVisibility(View.GONE);
                    return;
                }
                SuggestionResultObject obj = (SuggestionResultObject)arg1;
                locationList = new LinkedList<>();

                for (SuggestionResultObject.SuggestionData pos : obj.data) {

                    Poi poi = new Poi();
                    poi.address = pos.address;
                    poi.title = pos.title;
                    poi.id = pos.id;
                    poi.latLng = pos.latLng;
                    poi._distance = pos._distance;
                    locationList.add(poi);
//
//                    locationList.add();
                }
//                locationList = obj.sub_pois;
                selectLocationLatLng = locationList.get(0).latLng;
                initList();

                Log.e("test", "suggestion:" + arg0);
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });
    }

    /**
     * 我当前位置的标记
     */
    private void initMYLocation() {



//        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(this);
//        mLocationManager.requestSingleFreshLocation(null, new TencentLocationListener() {
//            @Override
//            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
//                lat;
        Intent intent = this.getIntent();
        LocationMessage locationMessage = (LocationMessage)intent.getParcelableExtra("location");
                double lat = locationMessage.getLat();
                double lng = locationMessage.getLng();
                nowCity = tencentMap.getCityName(new LatLng(lat, lng));
                myLocation.setLatitude(lat);
                myLocation.setLongitude(lng);
                LatLng latLng = new LatLng(lat, lng);
                makeMark(latLng);
                reGeocoder(latLng);
//            }
//
//            @Override
//            public void onStatusUpdate(String s, int i, String s1) {
//
//            }
//        }, Looper.getMainLooper());

    }





    /**
     * 绘制标记
     * @param latLng
     */
    private void makeMark(LatLng latLng) {

        if (marker != null) {
            marker.remove();
        }

        View userView2 = LayoutInflater.from(ShowTencentMap.this).inflate(R.layout.map_tencent_mylocation, null);
        BitmapDescriptor custom = BitmapDescriptorFactory.fromView(userView2);
        marker = tencentMap.addMarker(new MarkerOptions(latLng)
                .icon(custom)
                .alpha(0.7f)
                .anchor(0.5f, 0.5f)
                .flat(true)
                .clockwise(false)
        );

        //显示信息窗
        marker.showInfoWindow();
        //创建Marker对象之后，修改属性
        Animation animation = new AlphaAnimation(0.7f, 1f);
        animation.setDuration(2000);
        marker.setAnimation(animation);
        marker.startAnimation();
        marker.setInfoWindowEnable(true);


        moveCamera();
    }

    private void moveCamera()
    {
        /**
         * 縮放
         */
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(marker.getPosition().getLatitude(), marker.getPosition().getLongitude()), //中心点坐标，地图目标经纬度
                        17,  //目标缩放级别
                        0f, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                        0f)); //目标旋转角 0~360° (正北方为0)

        tencentMap.animateCamera(cameraSigma); //移动地图
//        tencentMap.moveCamera(cameraSigma); //移动地图
    }


    List<String> data;
    List<Poi>  pp;
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
                .getPoi(true)
                .setPoiOptions(new Geo2AddressParam.PoiOptions()
                        .setRadius(1000)
                        .setAddressFormat(Geo2AddressParam.PoiOptions.ADDRESS_FORMAT_SHORT)
//                        .setCategorys("面包")
                        .setPolicy(Geo2AddressParam.PoiOptions.POLICY_DEFAULT));
        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {
            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Geo2AddressResultObject obj = (Geo2AddressResultObject)arg1;
                StringBuilder sb = new StringBuilder();
                sb.append("逆地址解析");

                sb.append("\n地址：" + obj.result.address);
                sb.append("\npois:");
                data = new ArrayList<>();
//                obj.result.formatted_addresses.id
                locationList = obj.result.pois;
                initList();
//                for (Poi poi : obj.result.pois) {
//                    data.add(TencentLocationUtils.distanceBetween(poi.latLng.latitude,poi.latLng.longitude,myLocation.getLatitude(),myLocation.getLongitude())+",,"+poi.title+",,"+poi.address);
//
////                    sb.append("\n\t" + poi.title);
////                    tencentMap.addMarker(new MarkerOptions()
////                            .position(poi.latLng)  //标注的位置
////                            .title(poi.title)     //标注的InfoWindow的标题
////                            .snippet(poi.address) //标注的InfoWindow的内容
////                    );
//                }
//                selectLocationLatLng = obj.result.ad_info.latLng;

                Log.e("test", sb.toString());
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("test", "error code:" + arg0 + ", msg:" + arg1);
            }
        });
    }

    private void initList()
    {
        selectLocationLatLng = locationList.get(0).latLng;
        selectPoi = locationList.get(0);
        makeMark(selectLocationLatLng);
//                Poi poi = new Poi();
//                poi.address = obj.result.address;
//                locationList.add(poi);
        addressListItemAdapter = new AddressListItemAdapter(ShowTencentMap.this,locationList,selectLocationLatLng,new LatLng(myLocation.getLatitude(),myLocation.getLongitude()));
        listView.setAdapter(addressListItemAdapter);
        addressListItemAdapter.setOnClickListenerInterface(new AddressListItemAdapter.setOnClickListener() {
            @Override
            public void onClick(int pos, @IdRes int id, View v) {
                selectLocationLatLng = locationList.get(pos).latLng;
                selectPoi = locationList.get(pos);
                addressListItemAdapter.notifyDataSetChanged();
                makeMark(locationList.get(pos).latLng);
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
            initMap();
        } else {
            EasyPermissions.requestPermissions(this, "需要权限",
                    1, Build.VERSION.SDK_INT >= 29 ? permissionsForQ : permissions);
        }
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mMapView_info.onStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView_info.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView_info.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mMapView_info.onStop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        mMapView_info.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView_info.onDestroy();
    }


}

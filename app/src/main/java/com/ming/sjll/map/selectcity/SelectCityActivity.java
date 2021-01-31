package com.ming.sjll.map.selectcity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.map.selectcity.binding.Bind;
import com.ming.sjll.map.selectcity.model.CityEntity;
import com.ming.sjll.map.selectcity.view.LetterListView;
import com.ming.sjll.map.selectcity.binding.ViewBinder;

import com.ming.sjll.map.selectcity.utils.JsonReadUtil;
import com.ming.sjll.search.adapter.GoodsCitytemAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class SelectCityActivity extends BaseActivity implements AbsListView.OnScrollListener {

    //文件名称
    private final  String CityFileName = "allcity.json";

    @Bind(R.id.tool_bar_fl)
    private FrameLayout mToolbar;
    @Bind(R.id.search_locate_content_et)
    private EditText searchContentEt;
    @Bind(R.id.total_city_lv)
    private ListView totalCityLv;
    @Bind(R.id.total_city_letters_lv)
    private LetterListView lettersLv;
    @Bind(R.id.search_city_lv)
    private ListView searchCityLv;
    @Bind(R.id.no_search_result_tv)
    private TextView noSearchDataTv;
    @BindView(R.id.title_bar_IvRight)
    ImageView title_bar_IvRight;

    private Handler handler;
    private TextView overlay; // 对话框首字母TextView
    private OverlayThread overlayThread; // 显示首字母对话框
    private boolean mReady = false;
    private boolean isScroll = false;

    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    protected List<CityEntity> hotCityList = new ArrayList<>();
    protected List<CityEntity> totalCityList = new ArrayList<>();
    protected List<CityEntity> curCityList = new ArrayList<>();
    protected List<CityEntity> searchCityList = new ArrayList<>();
    protected CityListAdapter cityListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;
    protected HotCityListAdapter hotCityListAdapter;

    private String locationCity, curSelCity;

    List<GoodsCitytemAdapter.City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        setSystemBarTransparent();

        setContentView(R.layout.map_select_city_activity_main);
        cityList = (List<GoodsCitytemAdapter.City>)getIntent().getSerializableExtra("cityList");

        title_bar_IvRight.setVisibility(View.VISIBLE);
        title_bar_IvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top6));

        initView();
        initData();
        initListener();
        event();
    }

    private void event()
    {
        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GoodsCitytemAdapter.City> tempCityList = new LinkedList<>();
                for (int i = 0; i < totalCityList.size(); i++) {
                    if(totalCityList.get(i).isSelect())
                    {
                        GoodsCitytemAdapter.City tempCity = new GoodsCitytemAdapter.City();
                        tempCity.name = totalCityList.get(i).getName();
                        tempCity.code = totalCityList.get(i).getCityCode()+"00";
                        tempCity.isSelect = true;
                        cityList.add(tempCity);
                        tempCityList.add(tempCity);
                    }

                }
                Intent intent = new Intent();
                intent.putExtra("cityList", (Serializable) tempCityList);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
    private void initView() {
        ViewBinder.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int top = AppUtils.getSystemBarHeight();
            mToolbar.setPadding(0, top, 0, 0);
        }

        handler = new Handler();
        overlayThread = new OverlayThread();
        searchCityListAdapter = new SearchCityListAdapter(this, searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);
        locationCity = "杭州";
        curSelCity = locationCity;
    }

    private void initData() {
        initTotalCityList();
        cityListAdapter = new CityListAdapter(this, totalCityList, hotCityList);
        totalCityLv.setAdapter(cityListAdapter);
        totalCityLv.setOnScrollListener(this);
        totalCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 1) {
                    CityEntity cityEntity = totalCityList.get(position);
                    showSetCityDialog(cityEntity);
//                }

            }
        });
        lettersLv.setOnTouchingLetterChangedListener(new LetterListViewListener());
        initOverlay();
    }

    private void initListener() {

        searchCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = searchCityList.get(position);
                showSetCityDialog(cityEntity);
            }
        });
        searchContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = searchContentEt.getText().toString().trim().toLowerCase();
                setSearchCityList(content);
            }
        });

        searchContentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput(searchContentEt.getWindowToken());
                    String content = searchContentEt.getText().toString().trim().toLowerCase();
                    setSearchCityList(content);
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 设置搜索数据展示
     */
    private void setSearchCityList(String content) {
        searchCityList.clear();
        if (TextUtils.isEmpty(content)) {
            totalCityLv.setVisibility(View.VISIBLE);
            lettersLv.setVisibility(View.VISIBLE);
            searchCityLv.setVisibility(View.GONE);
            noSearchDataTv.setVisibility(View.GONE);
        } else {
            totalCityLv.setVisibility(View.GONE);
            lettersLv.setVisibility(View.GONE);
            for (int i = 0; i < curCityList.size(); i++) {
                CityEntity cityEntity = curCityList.get(i);
                if (cityEntity.getName().contains(content) || cityEntity.getPinyin().contains(content)
                        || cityEntity.getFirst().contains(content)) {
                    searchCityList.add(cityEntity);
                }
            }

            if (searchCityList.size() != 0) {
                noSearchDataTv.setVisibility(View.GONE);
                searchCityLv.setVisibility(View.VISIBLE);
            } else {
                noSearchDataTv.setVisibility(View.VISIBLE);
                searchCityLv.setVisibility(View.GONE);
            }

            searchCityListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化全部城市列表
     */
    public void initTotalCityList() {
        hotCityList.clear();
        totalCityList.clear();
        curCityList.clear();

        String cityListJson = JsonReadUtil.getJsonStr(this, CityFileName);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(cityListJson);
            JSONArray array = jsonObject.getJSONArray("City");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String key = object.getString("key");
                String pinyin = object.getString("full");
                String first = object.getString("first");
                String cityCode = object.getString("code");

                CityEntity cityEntity = new CityEntity();
                cityEntity.setName(name);
                cityEntity.setKey(key);
                cityEntity.setPinyin(pinyin);
                cityEntity.setFirst(first);
                cityEntity.setCityCode(cityCode);
                cityEntity.setSelect(isSelect(cityCode));

                if (key.equals("热门")) {
                    hotCityList.add(cityEntity);
                } else {
                    if (!cityEntity.getKey().equals("0") && !cityEntity.getKey().equals("1")) {
                        curCityList.add(cityEntity);
                    }
                    totalCityList.add(cityEntity);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isSelect(String cityCode)
    {
        for (int i = 0; i < cityList.size(); i++) {
            if(cityList.get(i).code.equals(cityCode+"00") && cityList.get(i).isSelect)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        } else {
            isScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String key = getAlpha(totalCityList.get(firstVisibleItem).getKey());
            overlay.setText(key);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟让overlay为不可见
            handler.postDelayed(overlayThread, 700);
        }
    }

    /**
     * 总城市适配器
     */
    private class CityListAdapter extends BaseAdapter {
        private Context context;

        private List<CityEntity> totalCityList;
        private List<CityEntity> hotCityList;
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        CityListAdapter(Context context,
                        List<CityEntity> totalCityList,
                        List<CityEntity> hotCityList) {
            this.context = context;
            this.totalCityList = totalCityList;
            this.hotCityList = hotCityList;
            inflater = LayoutInflater.from(context);

            alphaIndexer = new HashMap<>();

            for (int i = 0; i < totalCityList.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = totalCityList.get(i).getKey();

                String previewStr = (i - 1) >= 0 ? totalCityList.get(i - 1).getKey() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(currentStr);
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
//            return position;
            return position < 2 ? position : 2;
        }

        @Override
        public int getCount() {
            return totalCityList == null ? 0 : totalCityList.size();
        }

        @Override
        public Object getItem(int position) {
            return totalCityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView curCityNameTv;
            ViewHolder holder;
            int viewType = getItemViewType(position);
//            if(position == 0)
//            {
//                viewType = 1;
//            }
            if (false) { // 定位

                convertView = inflater.inflate(R.layout.map_select_city_location_item, null);

                LinearLayout noLocationLl = convertView.findViewById(R.id.cur_city_no_data_ll);
                TextView getLocationTv = convertView.findViewById(R.id.cur_city_re_get_location_tv);
                curCityNameTv = convertView.findViewById(R.id.cur_city_name_tv);

                if (TextUtils.isEmpty(locationCity)) {
                    noLocationLl.setVisibility(View.VISIBLE);
                    curCityNameTv.setVisibility(View.GONE);
                    getLocationTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //获取定位

                        }
                    });
                } else {
                    noLocationLl.setVisibility(View.GONE);
                    curCityNameTv.setVisibility(View.VISIBLE);

                    curCityNameTv.setText(locationCity);
                    curCityNameTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!locationCity.equals(curSelCity)) {
                                //设置城市代码
                                String cityCode = "";
                                for (CityEntity cityEntity : curCityList) {
                                    if (cityEntity.getName().equals(locationCity)) {
                                        cityCode = cityEntity.getCityCode();
                                        break;
                                    }
                                }
                                showSetCityDialog(locationCity, cityCode);
                            } else {
                                ToastShow.s( "当前定位城市" + curCityNameTv.getText().toString());
                            }
                        }
                    });
                }
            } else if (viewType == 0) { //热门城市
                convertView = inflater.inflate(R.layout.map_select_city_recent_city_item, null);
                GridView hotCityGv = convertView.findViewById(R.id.recent_city_gv);
                hotCityListAdapter = new HotCityListAdapter(context, this.hotCityList);
                hotCityGv.setAdapter(hotCityListAdapter);
                hotCityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        CityEntity cityEntity = hotCityList.get(position);
                        showSetCityDialog(cityEntity);
                    }
                });
            } else {
                if (null == convertView) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.map_select_city_list_item_layout, null);
                    ViewBinder.bind(holder, convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                CityEntity cityEntity = totalCityList.get(position);
                holder.cityKeyTv.setVisibility(View.VISIBLE);
                holder.cityKeyTv.setText(getAlpha(cityEntity.getKey()));
                holder.cityNameTv.setText(cityEntity.getName());

                setIsSelect(holder.cityNameTv,totalCityList.get(position));
                if (position >= 1) {
                    CityEntity preCity = totalCityList.get(position - 1);
                    if (preCity.getKey().equals(cityEntity.getKey())) {
                        holder.cityKeyTv.setVisibility(View.GONE);

                    } else {
                        holder.cityKeyTv.setVisibility(View.VISIBLE);
                    }
                }

            }

            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    /**
     * 热门城市适配器
     */
    private class HotCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        HotCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.map_select_city_list_grid_item_layout, null);

                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CityEntity cityEntity = cityEntities.get(position);
            holder.cityNameTv.setText(cityEntity.getName());

            setIsSelect(holder.cityNameTv,cityEntity);
            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_list_grid_item_name_tv)
            TextView cityNameTv;
        }
    }

    /**
     * 搜索城市列表适配器
     */
    private class SearchCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        SearchCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.map_select_city_list_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityEntity cityEntity = cityEntities.get(position);
            setIsSelect(holder.cityNameTv,cityEntities.get(position));
            holder.cityKeyTv.setVisibility(View.GONE);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }


        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    /**
     * 获得首字母
     */
    private String getAlpha(String key) {
       if (key.equals("0")) {
            return "热门";
        } else {
            return key;
        }
//        if (key.equals("0")) {
//            return "定位";
//        } else if (key.equals("1")) {
//            return "热门";
//        } else {
//            return key;
//        }
    }


    /**
     * 选择的颜色
     * @return
     */
    private void setIsSelect(TextView textView ,  CityEntity cityEntity)
    {
        if(cityEntity.isSelect())
        {
            textView.setTextColor(Color.parseColor("#FF80B5FF"));
        }
        else
        {
            textView.setTextColor(Color.parseColor("#FF000000"));
        }
    }

    /**
     * 初始化汉语拼音首字母弹出提示框
     */
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.map_select_city_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }


    private class LetterListViewListener implements
            LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                totalCityLv.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟让overlay为不可见
                handler.postDelayed(overlayThread, 700);
            }
        }
    }

    /**
     * 设置overlay不可见
     */
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }


    private void  showSetCityDialog(CityEntity city)
    {
        city.setSelect(!city.isSelect());

        for (int i = 0; i < hotCityList.size(); i++) {
            if(hotCityList.get(i).getCityCode().equals(city.getCityCode()))
            {
                hotCityList.get(i).setSelect(city.isSelect());;
            }
        }

        for (int i = 0; i < totalCityList.size(); i++) {
            if(totalCityList.get(i).getCityCode().equals(city.getCityCode()))
            {
                totalCityList.get(i).setSelect(city.isSelect());;
            }
        }

        cityListAdapter.notifyDataSetChanged();
        searchCityListAdapter.notifyDataSetChanged();
        hotCityListAdapter.notifyDataSetChanged();
    }

    /**
     * 展示设置城市对话框
     */
    private void showSetCityDialog(final String curCity, final String cityCode) {


        if (curCity.equals(curSelCity)) {
            ToastShow.s(cityCode+"当前定位城市" + curCity);
            return;
        }



//        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
//        builder.setTitle("提示"); //设置标题
//        builder.setMessage(cityCode+"是否设置 " + curCity + " 为您的当前城市？"); //设置内容
//
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //选中之后做你的方法
//
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });

//        builder.create().show();
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 设置沉浸式状态栏
     */
    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}

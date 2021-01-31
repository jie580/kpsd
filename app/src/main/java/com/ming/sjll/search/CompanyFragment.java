package com.ming.sjll.search;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeanList;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.adapter.CompanyListItemAdapter;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.PagedListView;

/**
 * 公司列表
 */
public class CompanyFragment extends BaseActivity {


    private PagedListView listView;
    private CompanyListItemAdapter listAdapter;
    private GetInfoBeanList dataList;
    EditText searchEdit;

    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 5;
    private String search = "";

    /**
     * 是否显示加载更多
     */
    private boolean isShowlistViewFooter = true;

    /**
     * 从哪里跳转过来的
     * 1: 普通搜索列表
     * 2：PersonageOccupation，我的职业
     */
    private int comeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        comeType = getIntent().getIntExtra("comeType", 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_company_fragment);
        initView();
    }

    private void initView() {
        listView = (PagedListView) findViewById(R.id.control_main_listview);
        listView.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色
        listView.showFooter(isShowlistViewFooter);
        searchEdit = (EditText) findViewById(R.id.searchEdit);
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
                initGetList();
            }
        });
        initGetList();
    }

    private void initGetList() {
        if (isLoad) {
            return;
        }

        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page = 1;
        param.setParam("page", page);
        param.setParam("search", search);
        param.setParam("pageSize", pageSize);

        new HttpUtil().sendRequest(Constant.COMPANY_COMPANY_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoBeanList.class);
                listAdapter = new CompanyListItemAdapter(CompanyFragment.this, dataList.getData().getData(), false);

                listView.setAdapter(listAdapter);
                setListViewHeightBasedOnChildren(listView);

                //                分页加载数据
                listView.setListViewLoadInterface(new PagedListView.ListViewLoadListener() {
                    @Override
                    public void loadData() {
                        nextPage();
                    }
                });

                //                点击事件
                listAdapter.setOnClickListenerInterface(new CompanyListItemAdapter.setOnClickListener() {

                    @Override
                    public void onClick(int pos, @IdRes int id, View v) {

                        if(comeType == 2)
                        {
                            saveCompany(dataList.getData().getData().get(pos));
                        }

                    }
                });

            }

            @Override
            public void onFinal() {
                super.onFinal();
                listView.onLoadComplete();
                isLoad = false;
            }
        });
    }

    /**
     *公司
     */
    private void saveCompany(GetInfoBean.DataBean bean)
    {
        //                提示
        DialogTextTip dialog = new DialogTextTip(CompanyFragment.this, "您正在申请关联公司 "+bean.getCompany_name()+", 是否继续操作？");
        dialog.setTextColor(9,9+bean.getCompany_name().length());
        dialog.show(new CommonCallback() {
            @Override
            public void onNext() {
                super.onNext();

                loadingDailog.showLoading();
                MainActivity.saveInfo("company_id",bean.getCompany_id(),new CommonCallback(){
                    @Override
                    public void onSuccessJson(String data, int code) {
                        super.onSuccess(data, code);
                        MainActivity.updateUserInfo("company_name",bean.getCompany_name());
                        MainActivity.updateUserInfo("company_id",bean.getCompany_id());
                        CompanyFragment.this.finish();
                        ToastShow.s("申请成功，正在审核中");
                    }
                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });


            }
        });

    }


    private void nextPage()
    {

        if (isLoad) {
            return;
        }
        HttpParamsObject param = new HttpParamsObject();
        page++;
        param.setParam("page", page);
        param.setParam("search", search);
        param.setParam("pageSize", pageSize);

        new HttpUtil().sendRequest(Constant.COMPANY_COMPANY_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GetInfoBeanList temgData = new Gson().fromJson(data, GetInfoBeanList.class);
                for (int i = 0; i < temgData.getData().getData().size(); i++) {
                    dataList.getData().getData().add(temgData.getData().getData().get(i));
                }
                listAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFinal() {
                super.onFinal();
                listView.onLoadComplete();
                isLoad = false;
            }
        });

    }


}

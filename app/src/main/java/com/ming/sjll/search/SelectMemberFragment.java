package com.ming.sjll.search;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeanList;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.adapter.CompanyMemberListItemAdapter;
import com.ming.sjll.ui.PagedListView;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 公司搜索-成员
 */
public class SelectMemberFragment extends BaseActivity {


    private PagedListView listView;
    private CompanyMemberListItemAdapter listAdapter;
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
     * 2：公司添加人员搜搜
     */
    private int comeType;

    List<GetInfoBean.DataBean> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comeType = getIntent().getIntExtra("comeType", 1);
        userList = (List<GetInfoBean.DataBean>)getIntent().getSerializableExtra("userList");
        if(userList == null)
        {
            userList = new LinkedList<>();
        }
        setContentView(R.layout.search_company_fragment);
        initView();
        event();
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

        new HttpUtil().sendRequest(Constant.SEARCH_SEARCH_USER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoBeanList.class);
                for (int i = 0; i < dataList.getData().getData().size(); i++) {
                    if(isSelect(dataList.getData().getData().get(i)))
                    {
                        dataList.getData().getData().get(i).setSelect(true);
                    }
                }
                listAdapter = new CompanyMemberListItemAdapter(SelectMemberFragment.this, dataList.getData().getData(), false, true);

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
                listAdapter.setOnClickListenerInterface(new CompanyMemberListItemAdapter.setOnClickListener() {
                    @Override
                    public void onClick(int pos, @IdRes int id, View v) {

                        boolean flag = isSelect(dataList.getData().getData().get(pos));
                        dataList.getData().getData().get(pos).setSelect(!flag);
                        if(!flag)
                        {
                            GetInfoBean.DataBean  user =  new GetInfoBean.DataBean ();
                            user.setDefault_avatar(dataList.getData().getData().get(pos).getDefault_avatar());
                            user.setUser_id(dataList.getData().getData().get(pos).getUid());
                            userList.add(user);
                        }
                        else
                        {
                            removeUser(dataList.getData().getData().get(pos).getUid());
                        }
//                        Intent intent = new Intent();
//                        intent.putExtra("userList",(Serializable) userList);
//                        setResult(RESULT_OK, intent);

                        listAdapter.notifyDataSetChanged();
//                        if (comeType == 2) {
//                            setResult(RESULT_OK,getIntent());
//                            finish();
//                            saveCompany(dataList.getData().getData().get(pos));
//                        }

                    }
                });
                listView.onLoadComplete();
                isLoad = false;
            }
        });
    }

    /**
     * 取消选中用户
     * @param id
     */
    private void removeUser(String id )
    {
      for (GetInfoBean.DataBean user : userList)
      {
            if(user.getUser_id().equals(id))
            {
                userList.remove(user);
                break;
            }
      }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("userList",(Serializable) userList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void event()
    {
        findViewById(R.id.title_bar_IvLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userList",(Serializable) userList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    /**
     * 是否选中
     */

    private boolean isSelect(GetInfoBean.DataBean bean)
    {
            for (int j = 0; j < userList.size(); j++) {
                if(userList.get(j).getUser_id().equals(bean.getUid()))
                {
                    return true;
                }
            }

        return false;

    }
    /**
     * 公司
     */
    private void saveCompany(GetInfoBean.DataBean bean) {
        //                提示
//        DialogTextTip dialog = new DialogTextTip(CompanyMemberFragment.this, "您正在申请关联公司 " + bean.getCompany_name() + ", 是否继续操作？");
//        dialog.setTextColor(9, 9 + bean.getCompany_name().length());
//        dialog.show(new CommonCallback() {
//            @Override
//            public void onNext() {
//                super.onNext();

                loadingDailog.showLoading();

                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id", bean.getUid());
                new HttpUtil().sendRequest(Constant.COMPANY_ADD_MEMBER, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("添加成功，等待对方审核");
                        finish();
                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });

//                MainActivity.saveInfo("company_id", bean.getCompany_id(), new CommonCallback() {
//                    @Override
//                    public void onSuccessJson(String data, int code) {
//                        super.onSuccess(data, code);
//                        MainActivity.updateUserInfo("company_name", bean.getCompany_name());
//                        MainActivity.updateUserInfo("company_id", bean.getCompany_id());
//                        CompanyMemberFragment.this.finish();
//                        ToastShow.s("申请成功，正在审核中");
//                    }
//
//                    @Override
//                    public void onFinal() {
//                        super.onFinal();
//                        loadingDailog.hide();
//                    }
//                });


//            }
//        });

    }


    private void nextPage() {

        if (isLoad) {
            return;
        }
        HttpParamsObject param = new HttpParamsObject();
        page++;
        param.setParam("page", page);
        param.setParam("search", search);
        param.setParam("pageSize", pageSize);

        new HttpUtil().sendRequest(Constant.SEARCH_SEARCH_USER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GetInfoBeanList temgData = new Gson().fromJson(data, GetInfoBeanList.class);
                for (int i = 0; i < temgData.getData().getData().size(); i++) {
                    if(isSelect(temgData.getData().getData().get(i)))
                    {
                        temgData.getData().getData().get(i).setSelect(true);
                    }
                    dataList.getData().getData().add(temgData.getData().getData().get(i));
                }

                listAdapter.notifyDataSetChanged();
                listView.onLoadComplete();
                isLoad = false;
            }
        });

    }


}

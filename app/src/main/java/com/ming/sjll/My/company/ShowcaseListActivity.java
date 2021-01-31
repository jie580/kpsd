package com.ming.sjll.My.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.adapter.GoodsListAdapter2;
import com.ming.sjll.My.adapter.ShowCaseListAdapter;
import com.ming.sjll.My.common.Sample;
import com.ming.sjll.My.common.Setting;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.event.GoodsClassEditEvent;
import com.ming.sjll.base.event.GoodsEditEvent;
import com.ming.sjll.base.event.SampleEditEvent;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.ming.sjll.ui.DragFloatActionButton;
import com.ming.sjll.ui.StickyScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人中心-橱窗-商品列表
 */
public class ShowcaseListActivity extends BaseActivity {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;

    private String class_id;

    ImageView title_bar_IvRight;
    RecyclerView listView;
    GoodsListAdapter2 listAdapter;
    GoodsListItem dataList;
    StickyScrollView stickyScrollView;

    DragFloatActionButton addSample;
    EditText searchEdit;
    String search;

    CustomRoundAngleImageView backdrop;
    public String company_id,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_show_case_list);
        EventBus.getDefault().register(this);
        class_id = getIntent().getStringExtra("class_id");
        company_id = getIntent().getStringExtra("company_id");
        type = getIntent().getStringExtra("type");
        initView();
        event();
    }

    private boolean getIsShowDelete()
    {
        if(company_id == null || company_id.equals(""))
        {
            return true;
        }
        return  false;
    }

    private void initView() {
        addSample = (DragFloatActionButton) findViewById(R.id.addSample);
        listView = (RecyclerView) findViewById(R.id.recyclerList);
        title_bar_IvRight = (ImageView  ) findViewById(R.id.title_bar_IvRight);
        searchEdit = findViewById(R.id.searchEdit);
        backdrop = findViewById(R.id.backdrop);
//        new ImageHelper().displayBackgroundLoading(backdrop,
//                Constant.BASE_IMAGE + icon);
        if(getIsShowDelete())
        {
            title_bar_IvRight.setVisibility(View.VISIBLE);
            title_bar_IvRight.setBackgroundResource(R.mipmap.btn_save_top5);
        }
        else
        {
            addSample.setVisibility(View.GONE);
        }

        stickyScrollView = ((StickyScrollView) findViewById(R.id.stickyScrollView));

        getBackdrop();
        getList();
    }

    private void getBackdrop()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("class_id",class_id);
        new HttpUtil().sendRequest(Constant.SHOW_CASE_GET_CLASS_IMG, param, new CommonCallback() {

            @Override
            public void onSuccess(Object data, int code) {
                super.onSuccess(data, code);
                new ImageHelper().displayBackgroundLoading(backdrop,
                        Constant.BASE_IMAGE + ObjeGetValue.GetValu("icon", data).toString());

            }
        });

    }

    private void event() {

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
                getList();
            }
        });

        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogTextTip dialog = new DialogTextTip(ShowcaseListActivity.this, "删除分类组后，该组商品也将全部删除，请谨慎操作");
                dialog.show(new CommonCallback() {
                    @Override
                    public void onNext() {
                        super.onNext();
                        HttpParamsObject param = new HttpParamsObject();
                        param.setParam("class_id",class_id);

                        new HttpUtil().sendRequest(Constant.SHOW_CASE_DEL_CLASS, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                EventBus.getDefault().post(new GoodsClassEditEvent());
                                ToastShow.s("删除成功");
                                finish();
                                dialog.hide();
                            }
                        });

                    }
                });

            }
        });
        /**
         * 点击悬浮球添加
         */
        addSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("class_id", class_id);
                bundle.putString("type", type);

                Tools.jump(ShowcaseListActivity.this, ShowcaseAddActivity.class, bundle, false);
            }
        });



        stickyScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("<,,,,>", scrollX + ",,," + ",,,," + scrollY + ",,,,," + oldScrollX + ",,,,," + oldScrollY);
                Log.e("+++++++", v.getChildAt(0).getMeasuredHeight() + "========" + v.getMeasuredHeight());
                if (scrollY >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) - 200) {
                    nextPage();
                }
            }
        });
    }

    private void nextPage()
    {
        if(dataList == null || dataList.getData().getData().size() == 0)
        {
            getList();
            return;
        }
        if (isLoad) {
            return;
        }
        isLoad = true;

        HttpParamsObject param = new HttpParamsObject();
        page++;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        param.setParam("class_id", class_id);
        param.setParam("company_id", company_id);
        param.setParam("search",search);
        new HttpUtil().sendRequest(Constant.SHOW_CASE_COMPANY_GOODS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GoodsListItem temgData = new Gson().fromJson(data, GoodsListItem.class);
                for (int i = 0; i < temgData.getData().getData().size(); i++) {
                    dataList.getData().getData().add(temgData.getData().getData().get(i));
                }
                listAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });
    }
    public void getList() {
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page = 1;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        param.setParam("class_id", class_id);
        param.setParam("company_id", company_id);
        param.setParam("search",search);
        new HttpUtil().sendRequest(Constant.SHOW_CASE_COMPANY_GOODS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, GoodsListItem.class);
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().getData().add(dataList.getData().getData().get(0));
//                }
                listView.setLayoutManager(new GridLayoutManager(ShowcaseListActivity.this, 2));
                listAdapter = new GoodsListAdapter2(dataList.getData().getData(), getIsShowDelete());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.delete:
//                                ToastShow.s("删除商品");
                                DialogTextTip dialog = new DialogTextTip(ShowcaseListActivity.this, "是否确认删除该商品？");
                                dialog.show(new CommonCallback() {
                                    @Override
                                    public void onNext() {
                                        super.onNext();
                                        HttpParamsObject param = new HttpParamsObject();
                                        param.setParam("goodsId",dataList.getData().getData().get(position).getGoods_id());
                                        new HttpUtil().sendRequest(Constant.GOODS_DEL_GOODS, param, new CommonCallback() {
                                            @Override
                                            public void onSuccessJson(String data, int code) {
                                                ToastShow.s("删除成功");
                                                dataList.getData().getData().remove(position);
                                                listAdapter.notifyDataSetChanged();
                                                dialog.hide();
                                            }
                                        });

                                    }
                                });

                                break;
                            case R.id.wrap:
                                if(getIsShowDelete())
                                {
//                                    編輯
                                    Bundle bundle = new Bundle();
                                    bundle.putString("class_id", class_id);
                                    bundle.putString("goods_id", dataList.getData().getData().get(position).getGoods_id());
                                    bundle.putInt("oldPoint", position);
                                    bundle.putString("type", type);
                                    Tools.jump(ShowcaseListActivity.this, ShowcaseAddActivity.class, bundle, false);
                                }
                                else
                                {
//                                    查看详情
                                    Intent intent = new Intent(ShowcaseListActivity.this, ShowcaseInfoActivity.class);
                                    intent.putExtra("goodsId", dataList.getData().getData().get(position).getGoods_id());
                                    startActivity(intent);
                                }

                                break;
                        }

                    }
                });
            }
            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });

    }


    /**
     *
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GoodsEditEvent e) {

        if (dataList == null) {
            getList();
            return;
        }
        GoodsItem.DataBean param = e.param;

        if (e.oldPoint >= 0) {
            dataList.getData().getData().remove(e.oldPoint);
            dataList.getData().getData().add(e.oldPoint, param);

        } else {
            dataList.getData().getData().add(0, param);
        }
        listAdapter.notifyDataSetChanged();
    }
//
//    public void nextPage()
//    {
//        Log.e("Works","nexPagenexPagenexPagenexPagenexPagenexPagenexPagenexPage");
//
//    }


}

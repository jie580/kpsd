package com.ming.sjll.My.personage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.Bean.StringListBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.adapter.PersonageOccupationListAdapter;
import com.ming.sjll.My.adapter.ScheduleListUserHeadAdapter;
import com.ming.sjll.My.adapter.TagItemAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.dialog.EditTextDialog;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.CompanyFragment;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.rey.material.app.BottomSheetDialog;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-个人-标签
 */
public class PersonageTag extends BaseActivity {

    TagItemAdapter adapter;
    List<TagItemAdapter.tagClass> dataList;

    RecyclerView recyclerList;
    ImageView editBtn;

    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_personage_tag);
        initView();
    }

    private void initView()
    {
        type = getIntent().getStringExtra("type");
        myList = (List<String>) getIntent().getSerializableExtra("tagsList");

        if(type != null && type.equals("projectRecruit"))
        {
            ( (TextView)findViewById(R.id.title)).setText("请添加您需要招募人员的要求标签，接收到该通告的人员会通过标签确认是否满足该项目。");
        }
        recyclerList = findViewById(R.id.recyclerList);
        editBtn = findViewById(R.id.editBtn);
        getList();
        event();
    }

    private void event()
    {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextDialog editTextDialog = EditTextDialog.newInstance("");
                editTextDialog.setMaxLength(10);
                editTextDialog.setOnSaveListener(new EditTextDialog.setOnSaveListener(){
                    @Override
                    public void save(String s) {
                        if(s.equals(""))
                        {
                            ToastShow.s("数据不能为空");
                            return;
                        }
                        if(inData(dataList,s) >= 0)
                        {
                                ToastShow.s("标签已存在");
                        }
                        else
                        {
                            loadingDailog.showLoading();
                            TagItemAdapter.tagClass tempTag = new TagItemAdapter.tagClass();
                            tempTag.name = s;
                            tempTag.isSelect = true;
                            dataList.add(tempTag);
                            if(type.equals("projectRecruit"))
                            {
                                editTextDialog.dismiss();
                                adapter.notifyDataSetChanged();
                                loadingDailog.hide();
                                return;
                            }
                            MainActivity.saveInfo("tags",getSelectTags(),new CommonCallback(){
                                @Override
                                public void onSuccessJson(String data, int code) {
                                    super.onSuccess(data, code);
                                    editTextDialog.dismiss();
                                    adapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onFinal() {
                                    super.onFinal();
                                    loadingDailog.hide();
                                }
                            });



                        }
                        adapter.notifyDataSetChanged();

                    }
                }).show(PersonageTag.this);
            }
        });


        findViewById(R.id.title_bar_IvLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("selectTags",(Serializable) getSelectTags());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private int  inData(List<TagItemAdapter.tagClass> list , String str)
    {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).name.equals(str))
            {
                return i;
            }
        }
        return -1;
    }

    List<String> myList;
    private void getList()
    {
        if(type != null && !type.equals("projectRecruit"))
        {
            myList = MainActivity.userInfo.getData().getTags();
        }
        if(myList == null)
        {
            myList = new LinkedList<>();
        }


        dataList = new LinkedList<>();
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_TAGS_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                StringListBean defaultList = new Gson().fromJson(data,StringListBean.class);

                for (int i = 0; i < defaultList.getData().size(); i++) {
                    TagItemAdapter.tagClass tempTag = new TagItemAdapter.tagClass();
                    tempTag.name =  defaultList.getData().get(i);
                    tempTag.isSelect = false;
                    dataList.add(tempTag);
                }
                for (int i = 0; i < myList.size(); i++) {

                    int flag = inData(dataList,myList.get(i));
                    if(flag >= 0 )
                    {
                        dataList.get(flag).isSelect = true;
                        continue;
                    }

                    TagItemAdapter.tagClass tempTag = new TagItemAdapter.tagClass();
                    tempTag.name =  myList.get(i);
                    tempTag.isSelect = true;
                    dataList.add(tempTag);

                }


                AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(PersonageTag.this,true);
                recyclerList.setLayoutManager(layout);
                adapter = new TagItemAdapter(dataList);
                recyclerList.setAdapter(adapter);

                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if(dataList.get(position).isSelect)
                        {
                            dataList.get(position).isSelect = false;
                        }
                        else {
                                dataList.get(position).isSelect = true;
                        }

                        if(type.equals("projectRecruit"))
                        {
                            adapter.notifyDataSetChanged();
                            loadingDailog.hide();
                            return;
                        }

                        loadingDailog.showLoading();
                        if(!dataList.get(position).isSelect && dataList.size() == 0)
                        {

//                            如果只剩一个，直接删除
                            HttpParamsObject param2 = new HttpParamsObject();
                            param2.setParam("type","del");
                            param2.setParam("title",dataList.get(position).name);
                            new HttpUtil().sendRequest(Constant.MEMBER_SAVE_TAGS, param2, new CommonCallback() {
                                @Override
                                public void onSuccessJson(String data, int code) {
                                    MainActivity.updateUserInfo("tags",new LinkedList<>());
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFinal() {
                                    super.onFinal();
                                    loadingDailog.hide();
                                }
                            });
                        }
                        else
                        {
                            MainActivity.saveInfo("tags",getSelectTags(),new CommonCallback(){
                                @Override
                                public void onSuccessJson(String data, int code) {
                                    super.onSuccess(data, code);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFinal() {
                                    super.onFinal();
                                    loadingDailog.hide();
                                }
                            });
                        }



//                        MainActivity.saveInfo( "tags",dataList.get(position) );
                    }
                });




            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("selectTags",(Serializable) getSelectTags());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    /**
     * 保存
     * @return
     */
    private List<String> getSelectTags()
    {
        List<String> tempList = new LinkedList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i).isSelect)
            {
                tempList.add(dataList.get(i).name);
            }
        }
        return tempList;
    }





}

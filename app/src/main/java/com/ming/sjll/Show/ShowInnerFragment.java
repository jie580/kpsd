package com.ming.sjll.Show;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.Show.adapter.ShowAdapter;
import com.ming.sjll.Show.adapter.SwipViewAdapter;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.adapter.CoverBgAdapter;
import com.ming.sjll.project.bean.ProjecCoverBg;
import com.ming.sjll.ui.AntoLineLayoutManager;

//import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
//import me.yuqirong.cardswipelayout.CardLayoutManager;
//import me.yuqirong.cardswipelayout.OnSwipeListener;

public class ShowInnerFragment extends BaseFragment {


    public String occupationId = "0";
    private boolean isLoad = false;
    private WorkListItem.DataBean dataList;


    private SwipeFlingAdapterView recyclerview;
    private ShowAdapter mAdapter;
    LinearLayout linearLayout;
    LinearLayout occupationWrapLayout;
    LayoutInflater l_Inflater;
    ImageView showOccupation;


    public static ShowInnerFragment newInstance() {
        return new ShowInnerFragment();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.show_home_ineer_fragemt);
        recyclerview = (SwipeFlingAdapterView) findViewById(R.id.recyclerview);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        occupationWrapLayout = (LinearLayout) findViewById(R.id.occupationWrapLayout);
        showOccupation = (ImageView) findViewById(R.id.showOccupation);
        l_Inflater = LayoutInflater.from(getContext());


        getOccupationList();
        getList();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * 获取职业列表
     */
    private void getOccupationList() {

        showOccupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOccupation.setVisibility(View.GONE);
                occupationWrapLayout.setVisibility(View.VISIBLE);
            }
        });


        occupationWrapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOccupation.setVisibility(View.VISIBLE);
                occupationWrapLayout.setVisibility(View.GONE);
            }
        });

        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.PROJECT_GETOCCUPATION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                OccupationList occList = new Gson().fromJson(data, OccupationList.class);

                for (int j = 0; j < occList.getData().size(); j++) {
                    View occupationView = l_Inflater.inflate(R.layout.show_home_ineer_occupation, null);
                    ImageView imageItem = (ImageView) occupationView.findViewById(R.id.imageItem);
                    imageItem.setTag(occList.getData().get(j).getId());
                    new ImageHelper().displayBackgroundLoading(imageItem,
                            Constant.BASE_API + occList.getData().get(j).getIcon());
                    imageItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int indexOf = (int) v.getTag();
//                            dataList = null;
                            occupationId = indexOf + "";
//                            recyclerview.clearAll();
                            if(dataList != null && dataList.getData() != null)
                            for (int i = dataList.getData().size() -1; i >= 0 ; i--) {
                                dataList.getData().remove(i);
                            }


//                            dataList = null;
                            recyclerview.removeAllViewsInLayout();
                            adapter.notifyDataSetChanged();

//                            adapter.notifyDataSetChanged();

                            showOccupation.setVisibility(View.VISIBLE);
                            occupationWrapLayout.setVisibility(View.GONE);
                            getList();
                        }
                    });
                    linearLayout.addView(occupationView);

                }

            }
        });
    }


    /**
     * 获取show列表
     */
    SwipViewAdapter adapter;

    private void getList() {
//        showLoadDialog();
        if (isLoad) {
            return;
        }

        isLoad = true;

//        adapter = null;

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("occupationId", occupationId);
        new HttpUtil().sendRequest(Constant.HOME_SHOW, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

               dataList = new Gson().fromJson(data, WorkListItem.DataBean.class);
                final boolean flag =  dataList.getData().size() > 0;
//                    dataList = dataList2;
//                    mAdapter = new ShowAdapter(dataList.getData());
                    adapter = new SwipViewAdapter(getContext(), dataList.getData());
                    recyclerview.setAdapter(adapter);
//                    recyclerview.init(getContext(),adapter);
                    adapter.setListener(new SwipViewAdapter.ListenerClick() {
                        @Override
                        public void onClick(int pos, @IdRes int id, View v) {
                            if(id == R.id.headImage)
                            {
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", dataList.getData().get(pos).getUid()+"");
                                Tools.jump(getActivity(), HomepageActivity.class, bundle, false);
                            }
                            else {
                                Log.e("TAG", "點擊了");
                                if (dataList.getData().get(pos).getIs_show_work()) {
                                    Log.e("TAG", "隱藏");
                                    dataList.getData().get(pos).setIs_show_work(false);
//                                if(dataList.getData().size()>0)
//                                    dataList.getData().remove(0);
                                } else {
                                    Log.e("TAG", "显示");
                                    dataList.getData().get(pos).setIs_show_work(true);
                                }
                                adapter.notifyDataSetChanged();
                                recyclerview.changView(pos);
                            }



                        }
                    });

                    recyclerview.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                        @Override
                        public void removeFirstObjectInAdapter() {
                            // this is the simplest way to delete an object from the Adapter (/AdapterView)
                            Log.d("LIST", "removed object!");
                            Log.e("onRightCardExit", ",,,,removed object!");
                            if (dataList.getData().size() > 0)
                                dataList.getData().remove(0);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onLeftCardExit(Object dataObject) {
                            //Do something on the left!
                            //You also have access to the original object.
                            //If you want to use it just cast it (String) dataObject
                            Log.e("onRightCardExit", ",,,,Left");
//                            makeToast(ShowInnerFragment.this.getContext(), "Left!");
                        }

                        @Override
                        public void onRightCardExit(Object dataObject) {
                            Log.e("onRightCardExit", ",,,,Right");
//                            makeToast(ShowInnerFragment.this.getContext(), "Right!");
                        }

                        @Override
                        public void onAdapterAboutToEmpty(int itemsInAdapter) {

                            // Ask for more data here
//                            dataList.getData().add("XML ".concat(String.valueOf(i)));
//
                            if (itemsInAdapter ==  1 ) {
                                Log.e("onAdapterAboutToEmpty", ",,,," + itemsInAdapter);
                                addToAdapter();
                            }

//                            i++;
                        }

                        @Override
                        public void onScroll(float scrollProgressPercent) {
                            Log.e("onScroll", ",,,," + scrollProgressPercent);
                            View view = recyclerview.getSelectedView();
//                            view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                            view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                        }
                    });
                    // Optionally add an OnItemClickListener
                    recyclerview.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClicked(int itemPosition, Object dataObject) {
                            Log.e("onItemClicked", itemPosition + "onItemClicked");
//                            makeToast(ShowInnerFragment.this.getContext(), "Clicked!");
                        }
                    });

                adapter.notifyDataSetChanged();
                isLoad = false;
//                dismissDialog();
            }
        });
    }

    private void addToAdapter() {
        //        showLoadDialog();
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("occupationId", occupationId);
        new HttpUtil().sendRequest(Constant.HOME_SHOW, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                WorkListItem.DataBean dataList2 = new Gson().fromJson(data, WorkListItem.DataBean.class);
                for (int i = 0; i < dataList2.getData().size(); i++) {
                    dataList.getData().add(dataList2.getData().get(i));
                }
                adapter.notifyDataSetChanged();
                isLoad = false;
            }
        });
    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();

    }


}

package com.ming.sjll.project;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.manager.AppManager;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ProjectReleaseProjectNextBinding;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.search.SelectMemberFragment;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.ui.WrapContentHeightViewPager;
import com.rey.material.app.BottomSheetDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 发布项目
 */
public class ReleaseProjectNext extends BaseActivity {


    ProjectReleaseProjectNextBinding binding;
    //    总数据
    HttpParamsObject param;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.project_release_project_next);
        binding = DataBindingUtil.setContentView(this, R.layout.project_release_project_next);


        initView();

        event();
    }

    OccupationList occList;
    List<GetInfoBean.DataBean> userList;

    private void initView() {
        param = new HttpParamsObject();
        param = (HttpParamsObject) getIntent().getSerializableExtra("param");

        userList = (List<GetInfoBean.DataBean>) getIntent().getSerializableExtra("userList");

        Log.e("传送获取到param:", param.getUrlParam());
        getOccupationList();
        initUserList();
    }


    /**
     * 初始化用戶推送列表
     */
    private void initUserList() {

        if (userList == null) {
            userList = new LinkedList<>();
        }
        LayoutInflater l_Inflater = LayoutInflater.from(ReleaseProjectNext.this);
        binding.userLayout.removeAllViews();
        for (int i = 0; i < userList.size(); i++) {

            View occupationView = l_Inflater.inflate(R.layout.release_project_next_occupation_image, null);
            occupationView.setTag(userList.get(i).getUser_id());
            occupationView.findViewById(R.id.content).setVisibility(View.VISIBLE);
            occupationView.findViewById(R.id.iv_add).setVisibility(View.GONE);
            new ImageHelper().displayBackgroundLoading(occupationView.findViewById(R.id.iv_img),
                    Constant.BASE_IMAGE + userList.get(i).getDefault_avatar());

            occupationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String indexOf = v.getTag().toString();
                    for (int j = 0; j < userList.size(); j++) {
                        if (userList.get(j).getUser_id().equals(indexOf)) {
                            userList.remove(userList.get(j));
                        }
                    }

                    initUserList();
                }
            });

            binding.userLayout.addView(occupationView);
        }
    }

    private void getOccupationList() {
        HttpParamsObject paramTemp = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.PROJECT_GETOCCUPATION, paramTemp, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                occList = new Gson().fromJson(data, OccupationList.class);
                initOccupation();
            }
        });
    }

    //    初始化职业
    private void initOccupation() {
        binding.occupationLayout.removeAllViews();
        List<String> list = param.getStringListParam("push");
        LayoutInflater l_Inflater = LayoutInflater.from(ReleaseProjectNext.this);


//        View occupationAddView = l_Inflater.inflate(R.layout.release_project_next_occupation_image, null);
//        occupationAddView.findViewById(R.id.content).setVisibility(View.GONE);
//        occupationAddView.findViewById(R.id.iv_add).setVisibility(View.VISIBLE);
//        occupationAddView.setTag(-1);
//        occupationAddView.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 int indexOf = (int) v.getTag();
//                 openOccupation();
//             }
//         });
//        binding.occupationLayout.addView(occupationAddView);

        List<String> removeList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            OccupationList.DataBean bean = returnOccupation(Integer.parseInt(list.get(i)));
            if(bean == null)
            {
                removeList.add(list.get(i));
                continue;

            }
            View occupationView = l_Inflater.inflate(R.layout.release_project_next_occupation_image, null);
            occupationView.setTag(list.get(i));
            occupationView.findViewById(R.id.content).setVisibility(View.VISIBLE);
            occupationView.findViewById(R.id.iv_add).setVisibility(View.GONE);
            new ImageHelper().displayBackgroundLoading(occupationView.findViewById(R.id.iv_img),
                    Constant.BASE_IMAGE + bean.getSmall_icon());

            occupationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexOf = Integer.parseInt(v.getTag().toString());
                    removeOccupationParam(indexOf);
                    initOccupation();
                }
            });

            binding.occupationLayout.addView(occupationView);
        }
        list.removeAll(removeList);

    }

    /**
     * 职业是否已经选中
     *
     * @param id
     * @return
     */
    private boolean isIntOccupationParam(int id) {
        List<String> list = param.getStringListParam("push");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(id + "")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 选中职业
     *
     * @param id
     */
    private void installOccupationParam(int id) {
        List<String> list = param.getStringListParam("push");
        list.add(id + "");
        param.setParam("push", list);
    }

    /**
     * 取消选中职业
     *
     * @param id
     */
    private void removeOccupationParam(int id) {
        List<String> list = param.getStringListParam("push");
        list.remove(id + "");
        param.setParam("push", list);
    }


    /**
     * 打开职业
     */
    private void openOccupation() {
        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(this);
        bottomInterPasswordDialog.setContentView(R.layout.dialog_occupation_select);
        bottomInterPasswordDialog.inDuration(300);
        bottomInterPasswordDialog.outDuration(200);
        bottomInterPasswordDialog.show();
        initOpenOccupation(bottomInterPasswordDialog);

        bottomInterPasswordDialog.findViewById(R.id.occ1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(2)) {
                    removeOccupationParam(2);
                } else {
                    installOccupationParam(2);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });

        bottomInterPasswordDialog.findViewById(R.id.occ2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(4)) {
                    removeOccupationParam(4);
                } else {
                    installOccupationParam(4);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });


        bottomInterPasswordDialog.findViewById(R.id.occ3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(3)) {
                    removeOccupationParam(3);
                } else {
                    installOccupationParam(3);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });

        bottomInterPasswordDialog.findViewById(R.id.occ4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(5)) {
                    removeOccupationParam(5);
                } else {
                    installOccupationParam(5);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });


        bottomInterPasswordDialog.findViewById(R.id.occ5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(8)) {
                    removeOccupationParam(8);
                } else {
                    installOccupationParam(8);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });

        bottomInterPasswordDialog.findViewById(R.id.occ6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(1)) {
                    removeOccupationParam(1);
                } else {
                    installOccupationParam(1);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });

        bottomInterPasswordDialog.findViewById(R.id.occ10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(6)) {
                    removeOccupationParam(6);
                } else {
                    installOccupationParam(6);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });


        bottomInterPasswordDialog.findViewById(R.id.occ11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIntOccupationParam(7)) {
                    removeOccupationParam(7);
                } else {
                    installOccupationParam(7);
                }
                initOccupation();
                initOpenOccupation(bottomInterPasswordDialog);
            }
        });

    }

    /**
     * 初始化弹窗职业
     */
    private void initOpenOccupation(BottomSheetDialog bottomInterPasswordDialog) {
        if (isIntOccupationParam(2)) {
            bottomInterPasswordDialog.findViewById(R.id.occ1).setBackgroundResource(R.mipmap.home_occ_1_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ1).setBackgroundResource(R.mipmap.home_occ_1);
        }

        if (isIntOccupationParam(4)) {
            bottomInterPasswordDialog.findViewById(R.id.occ2).setBackgroundResource(R.mipmap.home_occ_2_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ2).setBackgroundResource(R.mipmap.home_occ_2);
        }


        if (isIntOccupationParam(3)) {
            bottomInterPasswordDialog.findViewById(R.id.occ3).setBackgroundResource(R.mipmap.home_occ_3_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ3).setBackgroundResource(R.mipmap.home_occ_3);
        }


        if (isIntOccupationParam(5)) {
            bottomInterPasswordDialog.findViewById(R.id.occ4).setBackgroundResource(R.mipmap.home_occ_4_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ4).setBackgroundResource(R.mipmap.home_occ_4);
        }


        if (isIntOccupationParam(8)) {
            bottomInterPasswordDialog.findViewById(R.id.occ5).setBackgroundResource(R.mipmap.home_occ_5_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ5).setBackgroundResource(R.mipmap.home_occ_5);
        }

        if (isIntOccupationParam(1)) {
            bottomInterPasswordDialog.findViewById(R.id.occ6).setBackgroundResource(R.mipmap.home_occ_6_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ6).setBackgroundResource(R.mipmap.home_occ_6);
        }

        if (isIntOccupationParam(6)) {
            bottomInterPasswordDialog.findViewById(R.id.occ10).setBackgroundResource(R.mipmap.home_occ_10_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ10).setBackgroundResource(R.mipmap.home_occ_10);
        }

        if (isIntOccupationParam(7)) {
            bottomInterPasswordDialog.findViewById(R.id.occ11).setBackgroundResource(R.mipmap.home_occ_11_select);
        } else {
            bottomInterPasswordDialog.findViewById(R.id.occ11).setBackgroundResource(R.mipmap.home_occ_11);
        }

    }


    private OccupationList.DataBean returnOccupation(int id) {
        for (int i = 0; i < occList.getData().size(); i++) {
            if (occList.getData().get(i).getId() == id) {
                return occList.getData().get(i);
            }
        }
        return null;
    }

    private void event() {

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> userListTemp = new LinkedList<>();
                for (int i = 0; i < userList.size(); i++) {
                    userListTemp.add(userList.get(i).getUser_id());
                }
                param.setParam("pushUser",userListTemp);
                Log.e("提交", "保存项目" + param.getUrlParam());

                new HttpUtil().sendRequest(Constant.PROJECT_SAVE_PROJECT, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("发布成功");
                        AppManager.getAppManager().finishActivity(ReleaseProject.class);
//                        Intent intent = new Intent(ReleaseProjectNext.this, MyProjectFragment.class);
                        Intent intent = new Intent(ReleaseProjectNext.this, MainActivity.class);
                        startActivityForResult(intent,44);
//                        Tools.jump(ReleaseProjectNext.this, MyProjectFragment.class, true);
                    }
                });

            }
        });
        binding.addOccupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOccupation();
            }
        });

        binding.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.init(ReleaseProjectNext.this);
                Intent intent = new Intent(ReleaseProjectNext.this, SelectMemberFragment.class);
                intent.putExtra("userList", (Serializable) userList);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.MAP_HEAD_REQUEST) {
            userList = (List<GetInfoBean.DataBean>) data.getSerializableExtra("userList");
            initUserList();
        }


    }


}

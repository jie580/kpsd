package com.ming.sjll.My.common;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.login.LoginActivity;
import com.ming.sjll.login.WelcomeActivity;

/**
 * 个人中心-酷拍商店
 */
public class Home extends BaseFragment {

    public int point;

    public static Home newInstance() {
        return new Home();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_home);
        initView();
        event();
    }


    private void event() {
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(getActivity(), Setting.class, false);
            }
        });


        TextView changeTypeTitle = (TextView) findViewById(R.id.changeTypeTitle);
        if (Myfragemt.isPersonage) {
            changeTypeTitle.setText("将此账号变为公司账号");
        } else {
            changeTypeTitle.setText("将此账号变为个人账号");
        }
        changeTypeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                if (Myfragemt.isPersonage) {
                    param.setParam("roleType", "company ");
                } else {
                    param.setParam("roleType", "user");
                }
//                提示
                DialogTextTip dialog = new DialogTextTip(getContext(), "公司账号带有橱窗及公司员工排期功能，可根据您的实际情况进行选择，在点击继续后，系统会退出至登录页面，请重新登录");
                dialog.show(new CommonCallback() {
                    @Override
                    public void onNext() {
                        super.onNext();
//                        ToastShow.s("确定");
                        new HttpUtil().sendRequest(Constant.MEMBER_SELECT_ROLE, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                /**
                                 * 重新登录
                                 */
                                Myfragemt.newInstance().checkUserType();
                                dialog.hide();
//                                Tools.jump(getActivity(), LoginActivity.class, true);
                            }
                        });
                    }
                });
//                new HttpUtil().sendRequest(Constant.MEMBER_SELECT_ROLE, param, new CommonCallback() {
//                    @Override
//                    public void onSuccessJson(String data, int code) {
//                        /**
//                         * 重新登录
//                         */
////                        Myfragemt.newInstance().checkUserType();
//                    }
//                });
            }
        });
    }

    private void initView() {

        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this, point);
        }
    }


}

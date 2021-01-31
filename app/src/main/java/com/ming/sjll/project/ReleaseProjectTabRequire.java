package com.ming.sjll.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.dialog.ProjectDate;
import com.ming.sjll.toolPage.DateFrame;
import com.rey.material.app.BottomSheetDialog;

/**
 * 发布项目-合作要求
 */
public class ReleaseProjectTabRequire extends BaseFragment {

    HttpParamsObject commParam;
    boolean isLook;

    public static ReleaseProjectTabRequire newInstance(boolean isLook) {
        Bundle args = new Bundle();
        ReleaseProjectTabRequire fragment = new ReleaseProjectTabRequire();
        fragment.setArguments(args);
        fragment.isLook = isLook;
        return fragment;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_release_project_tab_require);
        initView();
        event();
    }

    public void setCommParam(HttpParamsObject param)
    {
        this.commParam = param;


    }
    public boolean getCommParam()
    {
        EditText brief = (EditText)findViewById(R.id.brief);

        if(brief.getText().toString().equals("") || commParam.getStringMapParam("closingTime").equals(""))
        {
            ToastShow.s("信息不能为空");
            return false;
        }
        commParam.setParam("brief",brief.getText().toString());

        return true;
    }




    private void initView() {

        RelativeLayout closingTime = (RelativeLayout)findViewById(R.id.closingTime);
        TextView closingTimeText = (TextView)findViewById(R.id.closingTimeText);



        if( ((ImageView)findViewById(R.id.isScheme)) == null)
        {
            return;
        }
        if(commParam.getStringMapParam("isScheme").equals("0"))
        {
            ((ImageView)findViewById(R.id.isScheme)).setBackgroundResource(R.mipmap.btn_switch_n);
        }
        else
        {
            ((ImageView)findViewById(R.id.isScheme)).setBackgroundResource(R.mipmap.btn_switch_y);
        }

        if(commParam.getStringMapParam("isLocal").equals("0"))
        {
            ((ImageView)findViewById(R.id.isLocal)).setBackgroundResource(R.mipmap.btn_switch_n);
        }
        else
        {
            ((ImageView)findViewById(R.id.isLocal)).setBackgroundResource(R.mipmap.btn_switch_y);

        }

        if(commParam.getStringMapParam("isScheme").equals(""))
        {
            commParam.setParam("isScheme","1");
        }
        if(commParam.getStringMapParam("isLocal").equals(""))
        {
            commParam.setParam("isLocal","1");
        }
        if(!commParam.getStringMapParam("closingTime").equals(""))
        {
            ((TextView)findViewById(R.id.closingTimeText)).setText( Tools.getDateformat(Long.parseLong(commParam.getStringMapParam("closingTime"))));
        }


        ((EditText)findViewById(R.id.brief)).setText(commParam.getStringMapParam("brief"));

//        时间选择
        closingTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isLook)
                {
                    return;
                }
                ProjectDate p = new ProjectDate(getContext());

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(p.getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_release_project_date_frame);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                DateFrame ateFrame = bottomInterPasswordDialog.findViewById(R.id.dateFrame);
                bottomInterPasswordDialog.setOnShowListener(new DialogInterface.OnShowListener(){
                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                //日期跳转
//                        Log.e(TAG,"11111111");
                        if(!commParam.getStringMapParam("closingTime").equals(""))
                        {
                            ateFrame.jumpDate(Tools.getDateformat(Long.parseLong(commParam.getStringMapParam("closingTime"))));
                        }

                    }
                });
//                Log.e(TAG,"222222222 ");
                bottomInterPasswordDialog.show();
                Button cancel = bottomInterPasswordDialog.findViewById(R.id.cancel);
                View save = bottomInterPasswordDialog.findViewById(R.id.save);
                cancel.setOnClickListener(new View.OnClickListener() {
                      public void onClick(View v) {
                          bottomInterPasswordDialog.hide();
                      }
                  });
                save.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String date = ateFrame.getCheckedDateString();
                        if(date.equals(""))
                        {
                            ToastShow.s("请选择日期");
                            return;
                        }
                        closingTimeText.setText(date);
                        commParam.setParam("closingTime",Tools.getLongformat(date));
                        bottomInterPasswordDialog.hide();

                    }
                });
            }
        });


    }

    private void event()
    {
        findViewById(R.id.isScheme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLook)
                {
                    return;
                }
                if(commParam.getStringMapParam("isScheme").equals("0"))
                {
                    ((ImageView)findViewById(R.id.isScheme)).setBackgroundResource(R.mipmap.btn_switch_y);
                    commParam.setParam("isScheme","1");
                }
                else
                {
                    ((ImageView)findViewById(R.id.isScheme)).setBackgroundResource(R.mipmap.btn_switch_n);
                    commParam.setParam("isScheme","0");
                }
            }
        });

        findViewById(R.id.isLocal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLook)
                {
                    return;
                }
                if(commParam.getStringMapParam("isLocal").equals("0"))
                {
                    ((ImageView)findViewById(R.id.isLocal)).setBackgroundResource(R.mipmap.btn_switch_y);
                    commParam.setParam("isLocal","1");
                }
                else
                {
                    ((ImageView)findViewById(R.id.isLocal)).setBackgroundResource(R.mipmap.btn_switch_n);
                    commParam.setParam("isLocal","0");
                }
            }
        });

    }


}

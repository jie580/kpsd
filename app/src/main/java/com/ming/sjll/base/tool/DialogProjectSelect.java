package com.ming.sjll.base.tool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.googlecode.mp4parser.authoring.Edit;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.ReleaseProject;
import com.ming.sjll.project.manage.HomeActivity;
import com.rey.material.app.BottomSheetDialog;

import java.util.List;

public class DialogProjectSelect {

    /**
     * 拒绝加入
     */
    public static final int REFUSED_JOIN_CODE = 100;


    /**
     * 拒绝合作
     */
    public static final int REFUSED_COOPERATION_CODE = 101;

    /**
     * 编辑
     */
    public static final int EDIT = 102;

    /**
     * 删除项目
     */
    public static final int DELETE_PROJECT = 103;



    private String mainId;
    private String mainName;
    private String applyId;
    private List<String> applyIdList;
    private String chatId;
    private String chatName;

    private String userType = "user";//拒绝加入类型
    private String itemType = "";

    //项目详情是否仅仅是查看
    private boolean isLook = false;

    private Activity context;

    public BottomSheetDialog bottomInterPasswordDialog;
    private LinearLayout openChat,projectManage,flow,orderAppeal,refusedJoin,appealStatus,refusedCooperation,projectInfo,edit,deleteProject;
    private ImageView line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;

    private CommonCallback cb;





    public void showDeleteProject()
    {
        deleteProject.setVisibility(View.VISIBLE);
    }
    public void showDeleteProjectLine()
    {
        line10.setVisibility(View.VISIBLE);
    }


    public void showEdit()
    {
        edit.setVisibility(View.VISIBLE);
    }
    public void showEditLine()
    {
        line9.setVisibility(View.VISIBLE);
    }

    public void showProjectInfo()
    {
        projectInfo.setVisibility(View.VISIBLE);
    }
    public void showProjectInfoLine()
    {
        line8.setVisibility(View.VISIBLE);
    }


    public void showOpenChat()
    {
        openChat.setVisibility(View.VISIBLE);
    }
    public void showOpenChatLine()
    {
        line1.setVisibility(View.VISIBLE);
    }

    public void showProjectManage()
    {
        projectManage.setVisibility(View.VISIBLE);
    }
    public void showProjectManageLine()
    {
        line2.setVisibility(View.VISIBLE);
    }


    public void showFlow()
    {
        flow.setVisibility(View.VISIBLE);
    }
    public void showFlowLine()
    {
        line3.setVisibility(View.VISIBLE);
    }


    public void showOrderAppeal()
    {
        orderAppeal.setVisibility(View.VISIBLE);
    }
    public void showOrderAppealLine()
    {
        line4.setVisibility(View.VISIBLE);
    }

    public void showRefusedJoin()
    {
        refusedJoin.setVisibility(View.VISIBLE);
    }
    public void showRefusedJoinLine()
    {
        line5.setVisibility(View.VISIBLE);
    }

    public void showAppealStatus()
    {
        appealStatus.setVisibility(View.VISIBLE);
    }
    public void showAppealStatusLine()
    {
        line6.setVisibility(View.VISIBLE);
    }

    public void showRefusedCooperation()
    {
        refusedCooperation.setVisibility(View.VISIBLE);
    }
    public void showRefusedCooperationLine()
    {
        line7.setVisibility(View.VISIBLE);
    }



    public void init(Activity context)
     {
          this.context = context;

         bottomInterPasswordDialog = new BottomSheetDialog(context);
         bottomInterPasswordDialog.setContentView(R.layout.dialog_project_click_select_item);
         bottomInterPasswordDialog.inDuration(300);
         bottomInterPasswordDialog.outDuration(200);
//         bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//         bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());

         openChat = bottomInterPasswordDialog.findViewById(R.id.openChat);
         projectManage = bottomInterPasswordDialog.findViewById(R.id.projectManage);
         flow = bottomInterPasswordDialog.findViewById(R.id.flow);
         orderAppeal = bottomInterPasswordDialog.findViewById(R.id.orderAppeal);
         refusedJoin = bottomInterPasswordDialog.findViewById(R.id.refusedJoin);
         appealStatus = bottomInterPasswordDialog.findViewById(R.id.appealStatus);
         refusedCooperation = bottomInterPasswordDialog.findViewById(R.id.refusedCooperation);
         projectInfo = bottomInterPasswordDialog.findViewById(R.id.projectInfo);
         edit = bottomInterPasswordDialog.findViewById(R.id.edit);
         deleteProject = bottomInterPasswordDialog.findViewById(R.id.deleteProject);

         openChat.setVisibility(View.GONE);
         projectManage.setVisibility(View.GONE);
         flow.setVisibility(View.GONE);
         orderAppeal.setVisibility(View.GONE);
         refusedJoin.setVisibility(View.GONE);
         appealStatus.setVisibility(View.GONE);
         refusedCooperation.setVisibility(View.GONE);
         projectInfo.setVisibility(View.GONE);
         edit.setVisibility(View.GONE);
         deleteProject.setVisibility(View.GONE);

         line1 = bottomInterPasswordDialog.findViewById(R.id.line1);
         line2 = bottomInterPasswordDialog.findViewById(R.id.line2);
         line3 = bottomInterPasswordDialog.findViewById(R.id.line3);
         line4 = bottomInterPasswordDialog.findViewById(R.id.line4);
         line5 = bottomInterPasswordDialog.findViewById(R.id.line5);
         line6 = bottomInterPasswordDialog.findViewById(R.id.line6);
         line7 = bottomInterPasswordDialog.findViewById(R.id.line7);
         line8 = bottomInterPasswordDialog.findViewById(R.id.line8);
         line9 = bottomInterPasswordDialog.findViewById(R.id.line9);
         line10 = bottomInterPasswordDialog.findViewById(R.id.line10);
         event();
     }



     private void event()
     {

         edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(cb != null)
                 {
                     cb.onSuccess(null,EDIT);
                 }
                 hide();

             }
         });

         deleteProject.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(cb != null)
                 {
                     cb.onSuccess(null,DELETE_PROJECT);
                 }
                 hide();

             }
         });

         projectInfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 项目详情
                 Bundle bundle = new Bundle();
                bundle.putString("id",mainId);
                 bundle.putBoolean("isLook",isLook);
                Tools.jump(context, ReleaseProject.class, bundle,false);
                bottomInterPasswordDialog.cancel();
             }
         });

         /**
          * 项目详情页
          */
         refusedCooperation.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Bundle bundle = new Bundle();
                 bundle.putString("id",mainId);
                 Tools.jump(context, ReleaseProject.class, bundle,false);
                 hide();
             }
         });


         /**
          * 拒绝服务和商品加入
          */
         refusedCooperation.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 HttpParamsObject param = new HttpParamsObject();
                 param.setParam("orderNo",mainId);
                 param.setParam("status",6);
                 param.setParam("type",getItemType());
                 new HttpUtil().sendRequest(Constant.ORDER_SAVE_GOODS_STATUS, param, new CommonCallback() {
                     @Override
                     public void onSuccessJson(String data, int code) {
                         if(cb != null)
                         {
                             cb.onSuccess(null,REFUSED_COOPERATION_CODE);
                         }
                     }
                 });
                 hide();
             }
         });

         openChat.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 RongImUtils.groupChat(context, chatId, chatName);
                 hide();
             }
         });

         projectManage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Bundle bundle = new Bundle();
                 bundle.putString("projectId", mainId);
                 Tools.jump(context, HomeActivity.class, bundle, false);
                 hide();
             }
         });


         flow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Bundle bundle = new Bundle();
                 bundle.putString("projectId", mainId);
                 Tools.jump(context, com.ming.sjll.project.flow.HomeActivity.class, bundle, false);
                 hide();
             }
         });


         orderAppeal.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         refusedJoin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(userType.equals("user"))
                 {

                     RongImUtils.sendConfirmCompanyCooperation(getApplyIdList(),false, new RongImUtils.onChatEven() {
                         @Override
                         public void onSuccess() {
                             if(cb != null)
                             {
                                 cb.onSuccess(null,REFUSED_JOIN_CODE);
                             }
                             hide();
                         }

                     });
                 }
                 else
                 {
                     RongImUtils.sendConfirmCooperation(getApplyId(),false, new RongImUtils.onChatEven() {
                         @Override
                         public void onSuccess() {
                             if(cb != null)
                             {
                                 cb.onSuccess(null,REFUSED_JOIN_CODE);
                             }
                             hide();
                         }

                     });
                 }


             }
         });


         appealStatus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 hide();
             }
         });

     }

     public void setCommonCallback(CommonCallback cb)
     {
        this.cb = cb;
     }
     public void hide()
     {
         bottomInterPasswordDialog.hide();
     }
    public void show()
    {
        bottomInterPasswordDialog.show();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getApplyIdList() {
        return applyIdList;
    }

    public void setApplyIdList(List<String> applyIdList) {
        this.applyIdList = applyIdList;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }
}

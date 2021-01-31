package com.ming.sjll.Message;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.OrderProjectListAdapter;
import com.ming.sjll.My.company.ShowcaseJoinProjectActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.RcMessageChatBinding;
import com.ming.sjll.project.manage.HomeActivity;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.PagedListView;
import com.orhanobut.logger.Logger;
import com.rey.material.app.BottomSheetDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import cn.sharesdk.framework.authorize.ResizeLayout;
import io.rong.common.RLog;
import io.rong.imageloader.utils.L;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.FileMessage;

public class MessageChatActivity extends BaseActivity {

    private RcMessageChatBinding viewDataBinding;

    private Conversation.ConversationType conversationType;
    private String targetId;
    private String title;
    private String type;
    private boolean isGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.rc_message_chat);

        try {
            Intent intent = getIntent();
            Uri dataUri = intent.getData();
            type = Cache.CacheValue("conversationType");
            if (dataUri != null) {
                initAndCacheUserInfo(dataUri);
                initChatFragmentPage();
                sendPdfFileFromWechat(intent, dataUri);
                event();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//
//        RongIM.lis();

//        if(type.equals("private"))
//        {
//
//        }


    }
    ProjectListItem projectDataList;
    PagedListView recyclerList;
    BottomSheetDialog bottomInterPasswordDialog;
    private void event()
    {

        viewDataBinding.openProjectManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("projectId", targetId);
                Tools.jump(getContext(), HomeActivity.class, bundle, false);

            }
        });

        viewDataBinding.openFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("projectId", targetId);
                Tools.jump(getContext(), com.ming.sjll.project.flow.HomeActivity.class, bundle, false);


            }
        });


        viewDataBinding.cooperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_list_two);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                recyclerList = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                showMake();
                bottomInterPasswordDialog.findViewById(R.id.project_list_type1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type1)).setTextColor(Color.parseColor("#80B5FF"));
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type2)).setTextColor(Color.parseColor("#000000"));
                        showMake();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.project_list_type2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type1)).setTextColor(Color.parseColor("#000000"));
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type2)).setTextColor(Color.parseColor("#80B5FF"));
                        showTake();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int i = 0; i < projectDataList.getData().getData().size(); i++) {
                            if (projectDataList.getData().getData().get(i).isSelect()) {

                                HttpParamsObject itemClickParam = new HttpParamsObject();
                                itemClickParam.setParam("projectId",projectDataList.getData().getData().get(i).getId());
                                itemClickParam.setParam("userId",targetId);
                                new HttpUtil().sendRequest(Constant.RC_COOPERATION, itemClickParam, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {

                                    }
                                });
                                bottomInterPasswordDialog.hide();
                                return;
                            }

                        }

                        ToastShow.s("请选择项目");


                    }
                });

            }
        });

        viewDataBinding.sendProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_list_two);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                recyclerList = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                showMake();
                bottomInterPasswordDialog.findViewById(R.id.project_list_type1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type1)).setTextColor(Color.parseColor("#80B5FF"));
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type2)).setTextColor(Color.parseColor("#000000"));
                        showMake();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.project_list_type2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type1)).setTextColor(Color.parseColor("#000000"));
                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.project_list_type2)).setTextColor(Color.parseColor("#80B5FF"));
                        showTake();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int i = 0; i < projectDataList.getData().getData().size(); i++) {
                            if (projectDataList.getData().getData().get(i).isSelect()) {

                                HttpParamsObject itemClickParam = new HttpParamsObject();

                                itemClickParam.setParam("share_type",5);
                                itemClickParam.setParam("share_id",projectDataList.getData().getData().get(i).getId());
                                itemClickParam.setParam("target_id",targetId);
                                itemClickParam.setParam("msg_type",1);

                                new HttpUtil().sendRequest(Constant.SHARE_SHARE_APP_MSG, itemClickParam, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {

                                    }
                                });
                                bottomInterPasswordDialog.hide();
                                return;
                            }

                        }

                        ToastShow.s("请选择项目");


                    }
                });

            }
        });
    }


    /**
     * 我接单的
     */
    private void showTake()
    {

        int page = 1;
        int pageSize = 20;

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("type", 2);
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        bottomInterPasswordDialog.findViewById(R.id.noneData).setVisibility(View.GONE);
        recyclerList.setVisibility(View.VISIBLE);
        new HttpUtil().sendRequest(Constant.PROJECT_MY_PROJECT, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                projectDataList = new Gson().fromJson(data, ProjectListItem.class);
                ProjectListItemAdapter projectAdapter = new ProjectListItemAdapter(getContext(), projectDataList.getData().getData(), false);
                recyclerList.setAdapter(projectAdapter);
                recyclerList.setNoData();
                if(projectDataList.getData().getData().size() == 0)
                {
                    bottomInterPasswordDialog.findViewById(R.id.noneData).setVisibility(View.VISIBLE);
                    recyclerList.setVisibility(View.GONE);

                }
                projectAdapter.setOnClickListenerInterface(new ProjectListItemAdapter.setOnClickListener() {
                    @Override
                    public void onClick(int pos, @IdRes int id, View v) {
                        switch (id) {
                            case R.id.projectWrap:
                                Log.e("点击", "点击了rc_wrap" + pos);
                                for (int i = 0; i < projectDataList.getData().getData().size(); i++) {
                                    projectDataList.getData().getData().get(i).setSelect(false);
                                }
                                projectDataList.getData().getData().get(pos).setSelect(true);
                                projectAdapter.notifyDataSetChanged();
                        }

                    }
                });


            }
        });

    }

    /**
     * 我发布的
     */
    private void showMake()
    {

        int page = 1;
        int pageSize = 20;

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("type", 1);
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        bottomInterPasswordDialog.findViewById(R.id.noneData).setVisibility(View.GONE);
        recyclerList.setVisibility(View.VISIBLE);
        new HttpUtil().sendRequest(Constant.PROJECT_MY_PROJECT, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                PagedListView recyclerList = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                projectDataList = new Gson().fromJson(data, ProjectListItem.class);
                ProjectListItemAdapter projectAdapter = new ProjectListItemAdapter(getContext(), projectDataList.getData().getData(), false);
                recyclerList.setAdapter(projectAdapter);
                recyclerList.setNoData();
                if(projectDataList.getData().getData().size() == 0)
                {
                    bottomInterPasswordDialog.findViewById(R.id.noneData).setVisibility(View.VISIBLE);
                    recyclerList.setVisibility(View.GONE);

                }
                projectAdapter.setOnClickListenerInterface(new ProjectListItemAdapter.setOnClickListener() {
                    @Override
                    public void onClick(int pos, @IdRes int id, View v) {
                        switch (id) {
                            case R.id.projectWrap:
                                Log.e("点击", "点击了rc_wrap" + pos);
                                for (int i = 0; i < projectDataList.getData().getData().size(); i++) {
                                    projectDataList.getData().getData().get(i).setSelect(false);
                                }
                                projectDataList.getData().getData().get(pos).setSelect(true);
                                projectAdapter.notifyDataSetChanged();
                        }

                    }
                });


            }
        });

    }


    private void initAndCacheUserInfo(Uri dataUri) {

        targetId = dataUri.getQueryParameter("targetId");
        title = dataUri.getQueryParameter("title");


        if (TextUtils.isEmpty(targetId)) {
            targetId = Cache.CacheValue("targetId");
            title = Cache.CacheValue("title");
        }

        if (TextUtils.isEmpty(title)) {
            title = targetId;
        }


        String path = dataUri.getPath();
        if (path.contains("/conversation/")) {
            type = path.replace("/conversation/", "");
        }

        //页面离开的时候缓存当前的用户和标题，为了给上传pdf使用，因为pdf是从微信里面进行应用选择打开的，这里接收的时候是在mainActivity进行接收
        Cache.CacheValue("targetId", targetId);
        Cache.CacheValue("title", title);


        viewDataBinding.singleChatBtn.setVisibility(View.GONE);
        viewDataBinding.groupChatBtn.setVisibility(View.GONE);
        if (TextUtils.equals(Conversation.ConversationType.PRIVATE.getName().toLowerCase(Locale.US), type)) {
            conversationType = Conversation.ConversationType.PRIVATE;
//            messageChatViewModel.setGroupInfoVisible(View.GONE);
            Cache.CacheValue("conversationType", "private");
            viewDataBinding.singleChatBtn.setVisibility(View.VISIBLE);
            isGroup = false;

        } else if (TextUtils.equals(Conversation.ConversationType.GROUP.getName().toLowerCase(Locale.US), type)) {
            conversationType = Conversation.ConversationType.GROUP;
//            messageChatViewModel.setGroupInfoVisible(View.VISIBLE);
            Cache.CacheValue("conversationType", "group");
            viewDataBinding.groupChatBtn.setVisibility(View.VISIBLE);
            isGroup = true;
        }

    }

    private void initChatFragmentPage() {
        //设置标题
        viewDataBinding.setTitleViewModel(new ToolbarViewModel(title));
        ConversationFragment conversationListFragment = createConversationFragment(targetId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, conversationListFragment)
                .commitAllowingStateLoss();
    }

    public ConversationFragment createConversationFragment(String mtargetId) {
        MessageConversationFragment fragement = new MessageConversationFragment();
        fragement.listener = new MessageConversationFragment.listenerExtension(){
            @Override
            public void onExtensionExpanded() {
                viewDataBinding.singleChatBtn.setVisibility(View.GONE);
                viewDataBinding.groupChatBtn.setVisibility(View.GONE);
                viewDataBinding.chatBtnWrap.requestLayout();
            }

            @Override
            public void onExtensionCollapsed() {
                viewDataBinding.singleChatBtn.setVisibility(View.GONE);
                viewDataBinding.groupChatBtn.setVisibility(View.GONE);
                if(isGroup)
                {
                    viewDataBinding.groupChatBtn.setVisibility(View.VISIBLE);

                }
                else
                {
                    viewDataBinding.singleChatBtn.setVisibility(View.VISIBLE);
                }
                viewDataBinding.chatBtnWrap.requestLayout();
            }
        };
        try {
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversation").appendPath(conversationType.getName().toLowerCase(Locale.US))
                    .appendQueryParameter("targetId", mtargetId).build();
            fragement.setUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragement;
    }


    private void sendPdfFileFromWechat(Intent intent, Uri dataUri) {
        //发送pdf信息
        boolean fromWechat = intent.getBooleanExtra("fromWechat", false);
        if (fromWechat) {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE).
                    onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            sendPdfMessage(dataUri);
                        }
                    }).
                    onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            ToastShow.s("无法获取手机权限，功能无法正常使用");
                        }
                    }).start();
        }
    }


    public void sendPdfMessage(Uri pdfUri) {
        if (pdfUri == null) {
            return;
        }
        ToastShow.showLong("正在发送pdf文件...");
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                File fileFromUri = getFileFromUri(pdfUri, MessageChatActivity.this);
                FileMessage fileMessage = null;
                if (fileFromUri != null) {
                    fileMessage = FileMessage.obtain( Uri.parse("file://"+fileFromUri.toString()));
                }

                if (fileMessage == null) {
                    return;
                }

                fileMessage.setType("pdf");
                io.rong.imlib.model.Message message = io.rong.imlib.model.Message.obtain(targetId, conversationType, fileMessage);
                RongIM.getInstance().sendMediaMessage(message, (String) null, (String) null, new IRongCallback.ISendMediaMessageCallback() {
                    @Override
                    public void onProgress(Message message, int i) {
                        Logger.i("pdf 传输  " + " , i = " + i);
                    }

                    @Override
                    public void onCanceled(Message message) {
                        Logger.i("pdf 传输  onCanceled");
                    }

                    @Override
                    public void onAttached(Message message) {
                        Logger.i("pdf 传输  onAttached");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        Logger.i("pdf 传输   onSuccess");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        Logger.i("pdf 传输  " + errorCode + "");
                    }
                });

                try {
                    Thread.sleep(400L);
                } catch (InterruptedException var7) {
                    RLog.e("FileInputProvider", "sendMediaMessage e:" + var7.toString());
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

    }



    public static File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    private static File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath = null;
        String fileName;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            try {
                filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            } catch (Exception e) {
            }
            fileName = cursor.getString(cursor.getColumnIndex(filePathColumn[1]));
            cursor.close();
            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
            if (file == null || !file.exists() || file.length() <= 0 || TextUtils.isEmpty(filePath)) {
                filePath = getPathFromInputStreamUri(context, contentUri, fileName);
            }
            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }

    public static String getPathFromInputStreamUri(Context context, Uri uri, String fileName) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                File file = createTemporalFileFrom(context, inputStream, fileName);
                filePath = file.getPath();

            } catch (Exception e) {
                L.e(e);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    L.e(e);
                }
            }
        }

        return filePath;
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream, String fileName)
            throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];
            //自己定义拷贝文件路径
            targetFile = new File(context.getCacheDir(), fileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }



}

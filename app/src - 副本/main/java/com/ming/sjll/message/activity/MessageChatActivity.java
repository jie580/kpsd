package com.ming.sjll.Message.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ActivityMessageChatBinding;
import com.ming.sjll.Message.dialog.ChangeProjectDialog;
import com.ming.sjll.Message.dialog.InviteMemberShareDialog;
import com.ming.sjll.Message.dialog.ReceivedConfirmDialog;
import com.ming.sjll.Message.fragment.MessageConversationFragment;
import com.ming.sjll.Message.message.ConfirmCooperationMessageContent;
import com.ming.sjll.Message.message.CooperationApplyMessageContent;
import com.ming.sjll.Message.message.CooperationMessageContent;
import com.ming.sjll.Message.message.ShareGoodsMessageContent;
import com.ming.sjll.Message.message.ShareServiceMessageContent;
import com.ming.sjll.Message.message.ShareUserMessageContent;
import com.ming.sjll.Message.message.ShareWorkMessageContent;
import com.ming.sjll.Message.presenter.MessageChatPresenter;
import com.ming.sjll.Message.presenter.UploadPdfMessagePresenter;
import com.ming.sjll.Message.utils.RongIMUtils;
import com.ming.sjll.Message.utils.ShareSdkUtils;
import com.ming.sjll.Message.view.MessageChatView;
import com.ming.sjll.Message.view.ShareView;
import com.ming.sjll.Message.viewmodel.GetChatProjectBean;
import com.ming.sjll.Message.viewmodel.GroupMemberBean;
import com.ming.sjll.Message.viewmodel.MessageChatViewModel;
import com.ming.sjll.my.activity.ComplainActivity;
import com.ming.sjll.my.activity.HomeageActivity;
import com.ming.sjll.my.activity.WorkDetailsActivity;
import com.ming.sjll.my.presenter.WorkDetailsPresenter;
import com.ming.sjll.Home.activity.DemandDetailsAcitivity;
import com.ming.sjll.Home.bean.ProjectManagementBean;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.rong.common.RLog;
import io.rong.imageloader.utils.L;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.UIMessage;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.FileMessage;


public class MessageChatActivity extends MvpActivity<MessageChatView, MessageChatPresenter> implements MessageChatView {

    private ActivityMessageChatBinding viewDataBinding;
    private MessageChatViewModel messageChatViewModel;
    private Conversation.ConversationType conversationType;
    private String targetId;
    private String title;
    private String type;
    private boolean isGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_message_chat);
        messageChatViewModel = new MessageChatViewModel();
        viewDataBinding.setViewModel(messageChatViewModel);
        viewDataBinding.setPresenter(mPresenter);

        try {
            Intent intent = getIntent();
            Uri dataUri = intent.getData();
            type = mSavePreferencesData.getStringData("conversationType");
            if (dataUri != null) {
                initAndCacheUserInfo(dataUri);
                initChatFragmentPage();
                connect(intent, dataUri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPresenter.getProjectByUser();
        //群聊获取接单人id，单聊不用获取
        mPresenter.getGroupConversationInfo();

    }

    private void initChatFragmentPage() {
        //设置标题
        viewDataBinding.setTitleViewModel(new ToolbarViewModel(title));
        ConversationFragment conversationListFragment = createConversationFragment(targetId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, conversationListFragment)
                .commitAllowingStateLoss();
    }

    private void connect(Intent intent, Uri dataUri) {
        //链接成功之后才发送消息
        String token = mPresenter.getRcToken();
        RongIMUtils.INSTANCE.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                sendPdfFileFromWechat(intent, dataUri);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void initAndCacheUserInfo(Uri dataUri) {
        targetId = dataUri.getQueryParameter("targetId");
        title = dataUri.getQueryParameter("title");

        if (TextUtils.isEmpty(targetId)) {
            targetId = mSavePreferencesData.getStringData("targetId");
            title = mSavePreferencesData.getStringData("title");
        }

        if (TextUtils.isEmpty(title)) {
            title = targetId;
        }


        String path = dataUri.getPath();
        if (path.contains("/conversation/")) {
            type = path.replace("/conversation/", "");
        }

        //页面离开的时候缓存当前的用户和标题，为了给上传pdf使用，因为pdf是从微信里面进行应用选择打开的，这里接收的时候是在mainActivity进行接收
        mSavePreferencesData.putStringData("targetId", targetId);
        mSavePreferencesData.putStringData("title", title);


        if (TextUtils.equals(Conversation.ConversationType.PRIVATE.getName().toLowerCase(Locale.US), type)) {
            conversationType = Conversation.ConversationType.PRIVATE;
            messageChatViewModel.setGroupInfoVisible(View.GONE);
            mSavePreferencesData.putStringData("conversationType", "private");
            isGroup = false;
        } else if (TextUtils.equals(Conversation.ConversationType.GROUP.getName().toLowerCase(Locale.US), type)) {
            conversationType = Conversation.ConversationType.GROUP;
            messageChatViewModel.setGroupInfoVisible(View.VISIBLE);
            mSavePreferencesData.putStringData("conversationType", "group");
            isGroup = true;
        }
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

    @Override
    public void hasProject(boolean hasProject) {
        if (hasProject) {
            messageChatViewModel.setProjectVisible(View.VISIBLE);
        } else {
            messageChatViewModel.setProjectVisible(View.GONE);
        }
    }

    public ConversationFragment createConversationFragment(String mtargetId) {
        ConversationFragment fragement = new MessageConversationFragment();
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

    @Override
    public void onShowProjectData(GetChatProjectBean bean) {
        messageChatViewModel.setProjectVisible(View.VISIBLE);
        messageChatViewModel.setGetChatProjectBean(bean);
        viewDataBinding.setViewModel(messageChatViewModel);
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
                    fileMessage = FileMessage.obtain(Uri.parse("file://" + fileFromUri.toString()));
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

    @Override
    public void uploadPdf() {
        Bundle bundle = new Bundle();
        bundle.putString(UploadPdfMessagePresenter.TARGETUSERID, targetId);
        Tools.jump(this, UploadPdfMessageActivity.class, bundle, false);
    }

    @Override
    public void sendCooperation() {
        //有相关的信息，需要透传下来
        if (mPresenter.hasProject()) {
            confirmCooperationDialog();
        } else { //如果当前没有项目id，弹框选择项目，然后再弹框
            changeProject(true);
        }
    }

    @Override
    public void sendCooperationApply() {
        String demand = "";
        String applyId = "";
        String projectId = "";
        RongIMUtils.INSTANCE.sendCooperationApply(conversationType, targetId, CooperationApplyMessageContent.obtain("", projectId, demand, applyId));
    }

    private void confirmCooperationDialog() {
        String demand = "";
        String projectId = "";
        try {
            demand = messageChatViewModel.getGetChatProjectBean().getData().getDemand();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            projectId = messageChatViewModel.getGetChatProjectBean().getData().getId() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = "你是否确定跟 " + title + " 合作 " + demand + " 新品拍摄吗？";
        String finalProjectId = projectId;
        ReceivedConfirmDialog.newInstance(content)
                .setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发送合作信息,自定义消息
//                        RongIMUtils.INSTANCE.sendCooperation(conversationType, targetId, CooperationMessageContent.obtain("", projectId, demand, applyId));
                        mPresenter.cooperation(finalProjectId);
                    }
                }).show(this);
    }

    public void changeProject(boolean isNeedSendMessage) {
        ChangeProjectDialog changeProjectDialog = ChangeProjectDialog.newInstance();
        changeProjectDialog.setOnClickProjectListener(new ChangeProjectDialog.onClickProjectListener() {
            @Override
            public void onClickProject(String type, int projectId, String projectName, ProjectManagementBean.DataBeanX.DataBean.ListBean listBean) {
                //弹框
                if (mPresenter != null) {
                    mPresenter.setChatProject(projectId);
                }
                if (isNeedSendMessage) {
                    confirmCooperationDialog();
                }
                changeProjectDialog.dismissAllowingStateLoss();
            }
        }).show(this);
    }

    @Override
    public void changeProject() {
        changeProject(false);
    }

    @Override
    public String getTargetUserId() {
        return targetId;
    }

    @Override
    public boolean isGroup() {
        return isGroup;
    }

    @Override
    public void complain() {
        //跳转到投诉页面
        Bundle bundle = new Bundle();
        Tools.jump(this, ComplainActivity.class, bundle, false);
    }

    @Override
    public void shareUserDialog() {
        //分享用户信息
        InviteMemberShareDialog.newInstance(mPresenter.getProjectId()).setShareView(new ShareView() {
            @Override
            public void share(String name) {
                ShareSdkUtils.userShare(name, "project_invite", mPresenter.getUserId(), mPresenter);

            }
        }).show(this);
    }

    @Override
    public void shareUser(String userId, String userName, String headImage, String occupation) {
        //分享用户信息
        InviteMemberShareDialog.newInstance(mPresenter.getProjectId()).setShareView(new ShareView() {
            @Override
            public void share(String name) {
                ShareSdkUtils.userShare(name, "project_invite", mPresenter.getUserId(), mPresenter);

            }
        }).show(this);
    }

    @Override
    public void shareWork(String userId, String userName, String headImage, String work_id) {
        //分享工作
        RongIMUtils.INSTANCE.sendShareWork(conversationType, targetId, ShareWorkMessageContent.obtain("", userId, userName, headImage, work_id));
    }

    @Override
    public void jumpToUser(ShareUserMessageContent shareUserMessageContent) {
        Bundle bundle = new Bundle();
        bundle.putString("uid", shareUserMessageContent.getUserId());
        Tools.jump(this, HomeageActivity.class, bundle, false);
    }

    @Override
    public void onCooperationApply(boolean isReceive, CooperationApplyMessageContent content, UIMessage message) {
        mPresenter.onCooperationApply(isReceive, content, message);
    }

    @Override
    public void onCooperationMessage(boolean isReceive, CooperationMessageContent content, UIMessage message) {
        mPresenter.onCooperationMessage(isReceive, content, message);
    }

    @Override
    public void showGroupMemberData(GroupMemberBean bean) {

        List<String> members = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++) {
            String default_avatar = bean.getData().get(i).getDefault_avatar();
            members.add(default_avatar);
        }
        messageChatViewModel.setMemberInfos(members);
    }


    @Override
    public void jumpToProduct(ShareWorkMessageContent workMessageContent) {
        Bundle bundle = new Bundle();
        bundle.putString(WorkDetailsPresenter.WORK_ID, workMessageContent.getworkId());
        Tools.jump(this, WorkDetailsActivity.class, bundle, false);
    }

    @Override
    public void jumpToService(ShareServiceMessageContent serviceMessageContent) {
        //todo 跳转到服务详情
    }

    @Override
    public void jumpToGoods(ShareGoodsMessageContent goodsMessageContent) {
        //todo 跳转到商品详情
    }

    @Override
    public void jumpToProject(String projectId) {
        //TODO 跳转到项目详情
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("project_id", Integer.parseInt(projectId));
            Tools.jump(this, DemandDetailsAcitivity.class, bundle, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jumpToProject(ConfirmCooperationMessageContent cooperationMessageContent) {
        //todo 遗留跳转到项目详情 h5
    }

    @Override
    public void jumpToProject(CooperationMessageContent cooperationMessageContent) {
        //todo 遗留跳转到项目详情 h5
    }

    @Override
    public void jumpToProject(CooperationApplyMessageContent cooperationApplyMessageContent) {
        //todo 遗留跳转到项目详情 h5
    }

    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static boolean CopySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }
}

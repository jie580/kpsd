package com.ming.sjll.Share;

import android.content.Context;

import com.google.gson.Gson;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.api.ApiService;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.bean.ShareBean;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.toolPage.LoadingDailog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareWx {

// 作品
    public final static String TYPE_SAMPLE = "user_works";


//    微信
    public final static String WX_FRIEND = "1";

    //    朋友圈
    public final static String WX_CIRCLE = "2";


    protected static LoadingDailog loadingDailog;

    /**
     * column 专题详情分享
     * <p>
     * goods 商品 / 服务详情分享
     * <p>
     * user 个人/公司资料 分享
     * <p>
     * project_flow 项目流程分享
     *
     * @param type
     * @param share_id
     */
    public static void userShare(String type , String wxType, int share_id, Context context) {
         loadingDailog = new LoadingDailog(context);
        loadingDailog.showLoading();
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("share_id", share_id);
        param.setParam("type", type);
        new HttpUtil().sendRequest(Constant.SHARE_USER_SHARE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                    ShareBean bean = new Gson().fromJson(data, ShareBean.class);
                    shareToWechat(bean, wxType);
            }

            @Override
            public void onFinal() {
                super.onFinal();
                loadingDailog.hide();
            }
        });

    }

    private static void shareToWechat(ShareBean bean, String name) {
        String imgUrl = Constant.BASE_API + bean.getData().getImg();
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(bean.getData().getTitle());
        sp.setText(bean.getData().getText());

        sp.setImageUrl(imgUrl);
        sp.setUrl(Constant.BASE_API + bean.getData().getUrl());
        Platform wechatMoments = null;
        if (name.equals(WX_FRIEND)) {
            wechatMoments = ShareSDK.getPlatform(Wechat.NAME);
        } else if (name.equals(WX_CIRCLE)) {

            wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        wechatMoments.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                ToastShow.s("1");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastShow.s("客户端未安装");

            }

            @Override
            public void onCancel(Platform platform, int i) {
//                ToastShow.s("1");
            }
        });

        wechatMoments.share(sp);
    }

}

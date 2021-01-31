package com.ming.sjll.Message.utils;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.bean.ShareBean;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.widget.ToastShow;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author luoming
 * created at 2019-12-14 11:36
 * 获取分享的参数并分享
 */
public class ShareSdkUtils {

    /**
     * column 专题详情分享
     * <p>
     * goods 商品 / 服务详情分享
     * <p>
     * user 个人/公司资料 分享
     * <p>
     * project_flow 项目流程分享
     *
     * @param name
     * @param type
     * @param share_id
     * @param presenter
     */
    public static void userShare(String name, String type, String share_id, MvpPresenter presenter) {
        presenter.getNetData(RetrofitManager.get().create(ApiService.class)
                        .userShare(presenter.getToken(), type, share_id),
                new ApiObserver<ShareBean>() {
                    @Override
                    public void onSuccess(ShareBean bean) {
                        if (name.equals("微信好友") || name.equals("朋友圈")) {
                            shareToWechat(bean, name);
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    public static void shareToWechat(ShareBean bean, String name) {
        String imgUrl = Constant.BASE_API + bean.getData().getImg();
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(bean.getData().getTitle());
        sp.setText(bean.getData().getText());

        sp.setImageUrl(imgUrl);
        sp.setUrl(Constant.BASE_API + bean.getData().getUrl());
        Platform wechatMoments = null;
        if (name.equals("微信好友")) {
            wechatMoments = ShareSDK.getPlatform(Wechat.NAME);
        } else if (name.equals("朋友圈")) {

            wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        wechatMoments.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                ToastShow.s("1");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastShow.s("客户端未安装");

            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastShow.s("1");
            }
        });

        wechatMoments.share(sp);
    }

}

package com.ming.sjll.Message.extend;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.My.company.CompanyInfo;
import com.ming.sjll.R;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.map.MapMainActivity;
import com.ming.sjll.map.tencent.MyTencentMap;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.utilities.PermissionCheckUtil;

public class CustomLocationPlugin extends DefaultLocationPlugin {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.chat_ext_btn3);
    }

    @Override
    public String obtainTitle(Context context) {
        return "位置";
    }


    @Override
    public void onClick(Fragment currentFragment, RongExtension extension) {
        String[] permissions = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE"};
        if (PermissionCheckUtil.checkPermissions(currentFragment.getActivity(), permissions)) {
                AppUtils.init(currentFragment.getActivity());
                Intent intent = new Intent(currentFragment.getActivity(), MyTencentMap.class);
                extension.startActivityForPluginResult(intent, 1, this);
            } else {
            extension.requestPermissionForPluginResult(permissions, 255, this);
        }
    }

    @Override
    public boolean onRequestPermissionResult(Fragment currentFragment, RongExtension extension, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionCheckUtil.checkPermissions(currentFragment.getActivity(), permissions)) {
            AppUtils.init(currentFragment.getActivity());
            Intent intent = new Intent(currentFragment.getActivity(), MyTencentMap.class);
            extension.startActivityForPluginResult(intent, 1, this);
        } else {
            extension.showRequestPermissionFailedAlter(PermissionCheckUtil.getNotGrantedPermissionMsg(currentFragment.getActivity(), permissions, grantResults));
        }

        return true;
    }


}

package com.ming.sjll.toolPage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.ming.sjll.R;
import com.ming.sjll.base.widget.ToastShow;
import com.rey.material.app.BottomSheetDialog;

//图片剪裁数据接口
public class CropPictureConfig {

    public Activity activity = null;
    public Context context = null;
    public int aspect_ratio_x = 1;// 裁剪比例
    public int aspect_ratio_y = 1;// 裁剪比例
    public boolean enableCrop = false;// 是否裁剪
    public int maxSelectNum = 1;// 最大图片选择数量
    public int forResult = 0;//回调code


    /**
     * 项目背景
     */
    private static final int PROJECT_X = 69;
    private static final int PROJECT_Y = 32;

    /**
     * 正方形头像
     */
    private static final int SQUARE_X = 1;
    private static final int SQUARE_Y = 1;


    /**
     * 长方形头像
     */
    private static final int RECTANGLE_X = 228;
    private static final int RECTANGLE_Y = 108;


    /**
     * 长方形头像
     */
    private static final int COVER_X = 375;
    private static final int COVER_Y = 350;

    public void setProjectXY()
    {
        enableCrop = true;
        aspect_ratio_x = PROJECT_X;
        aspect_ratio_y = PROJECT_Y;
    }

    public void setSquareXY()
    {
        enableCrop = true;
        aspect_ratio_x = SQUARE_X;
        aspect_ratio_y = SQUARE_Y;
    }


    public void setRectangleXY()
    {
        enableCrop = true;
        aspect_ratio_x = RECTANGLE_X;
        aspect_ratio_y = RECTANGLE_Y;
    }


    /**
     * 个人封面
     */
    public void setCoverXY()
    {
        enableCrop = true;
        aspect_ratio_x = COVER_X;
        aspect_ratio_y = COVER_Y;
    }

}


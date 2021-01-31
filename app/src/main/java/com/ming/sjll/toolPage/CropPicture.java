package com.ming.sjll.toolPage;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.ming.sjll.R;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.ReleaseProjectTabGenral;
import com.rey.material.app.BottomSheetDialog;

public class CropPicture {

    CropPictureConfig config;

    public CropPicture(CropPictureConfig config)
    {

        this.config = config;

        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(config.context);
        bottomInterPasswordDialog.setContentView(R.layout.dialog_upload_image_buttom);
        bottomInterPasswordDialog.inDuration(300);
        bottomInterPasswordDialog.outDuration(200);
//        bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//        bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
        bottomInterPasswordDialog.show();
        View selectCancel = bottomInterPasswordDialog.findViewById(R.id.selectCancel);
        selectCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomInterPasswordDialog.hide();
                ToastShow.s("取消了");
            }
        });
        View selectAlbum = bottomInterPasswordDialog.findViewById(R.id.selectAlbum);
        selectAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album();
                bottomInterPasswordDialog.hide();
                ToastShow.s("从相册中选择");

            }
        });

        View selectCamera = bottomInterPasswordDialog.findViewById(R.id.selectCamera);
        selectCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
                bottomInterPasswordDialog.hide();
                ToastShow.s("拍照");
            }
        });

    }

    public CropPicture(CropPictureConfig config,boolean openAlbum)
    {
        this.config = config;
        album();
    }

    private void album()
    {
            PictureSelector.create(config.activity)
            .openGallery(PictureMimeType.ofImage()).enableCrop(config.enableCrop).withAspectRatio(config.aspect_ratio_x, config.aspect_ratio_y)
            .isDragFrame(true).rotateEnabled(false).freeStyleCropEnabled(false).scaleEnabled(true).hideBottomControls(true).maxSelectNum(config.maxSelectNum).minSelectNum(1).forResult(config.forResult);
    }

    private void camera()
    {
        PictureSelector.create(config.activity)
                .openCamera(PictureMimeType.ofImage()).enableCrop(config.enableCrop).withAspectRatio(config.aspect_ratio_x, config.aspect_ratio_y)
                .isDragFrame(true).rotateEnabled(false).freeStyleCropEnabled(false).scaleEnabled(true).hideBottomControls(true).maxSelectNum(config.maxSelectNum).minSelectNum(1).forResult(config.forResult);
    }



}


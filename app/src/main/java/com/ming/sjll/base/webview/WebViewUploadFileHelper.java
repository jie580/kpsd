package com.ming.sjll.base.webview;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class WebViewUploadFileHelper {

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10011;//文件选择
    private Uri imageUri;
    private Activity activity;

    private WebViewUploadFileHelper() {
    }

    public WebViewUploadFileHelper(Activity activity) {
        this.activity = activity;
    }

    public void setUploadMessage(ValueCallback<Uri> uploadMessage) {
        this.uploadMessage = uploadMessage;
    }

    public void setUploadMessageAboveL(ValueCallback<Uri[]> uploadMessageAboveL) {
        this.uploadMessageAboveL = uploadMessageAboveL;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE) return;

        // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
        if (uploadMessage != null) {
            chooseBelow( resultCode, data );
        } else if (uploadMessageAboveL != null) {
            chooseAbove( resultCode, data );
        }

    }

    public void openImageActivity() {
        chooseImage( "image/*" );
    }

    public void openImageActivity(String acceptType) {
        chooseImage( acceptType );
    }

    public void openImageActivity(String acceptType, String capture) {
        if (TextUtils.equals( capture, "camera" )) {
            takePhoto();
        } else {
            chooseImage( acceptType );
        }
    }

    public void openImageActivity(String[] acceptType, boolean isCaptureEnabled) {
        if (isCaptureEnabled) {
            takePhoto();
        } else {
            chooseImage( acceptType );
        }
    }

    private void chooseBelow(int resultCode, Intent data) {

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    uploadMessage.onReceiveValue( uri );
                } else {
                    uploadMessage.onReceiveValue( null );
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                uploadMessage.onReceiveValue( imageUri );
            }
        } else {
            uploadMessage.onReceiveValue( null );
        }
        uploadMessage = null;
    }

    private void chooseAbove(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    uploadMessageAboveL.onReceiveValue( results );
                } else {
                    uploadMessageAboveL.onReceiveValue( null );
                }
            } else {
                uploadMessageAboveL.onReceiveValue( new Uri[]{imageUri} );
            }
        } else {
            uploadMessageAboveL.onReceiveValue( null );
        }
        uploadMessageAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
        intent.setData( imageUri );
        activity.sendBroadcast( intent );
    }

    //调用相机
    private void takePhoto() {
        String fileName = "IMG_" + DateFormat.format( "yyyyMMdd_hhmmss", Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
        // 步骤一：创建存储照片的文件
        String imagePath = activity.getFilesDir() + File.separator + "images" + File.separator + fileName;
        File file = new File( imagePath );
        //创建文件夹
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //步骤二：Android 7.0及以上获取文件 Uri
            imageUri = FileProvider.getUriForFile( activity, activity.getPackageName() + ".fileprovider", file );
        } else {
            //步骤三：获取文件Uri
            imageUri = Uri.fromFile( file );
        }

        Intent intent = new Intent();
        intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
        intent.setAction( MediaStore.ACTION_IMAGE_CAPTURE );//设置Action为拍照
        intent.putExtra( MediaStore.EXTRA_OUTPUT, imageUri );//将拍取的照片保存到指定URI
        activity.startActivityForResult( intent, FILE_CHOOSER_RESULT_CODE );
    }

    //图片选择器
    private void chooseImage(String[] acceptType) {
        Intent i = new Intent( Intent.ACTION_GET_CONTENT );
        i.addCategory( Intent.CATEGORY_OPENABLE );
        i.setType( "*/*" );
        i.putExtra( Intent.EXTRA_MIME_TYPES, acceptType );
        activity.startActivityForResult( i, FILE_CHOOSER_RESULT_CODE );
    }

    //图片选择器
    private void chooseImage(String acceptType) {
        Intent i = new Intent( Intent.ACTION_GET_CONTENT );
        i.addCategory( Intent.CATEGORY_OPENABLE );
        if (TextUtils.isEmpty( acceptType )) {
            i.setType( "*/*" );
        } else {
            i.setType( acceptType );
        }
        activity.startActivityForResult( Intent.createChooser( i, "Image Chooser" ), FILE_CHOOSER_RESULT_CODE );
    }

}

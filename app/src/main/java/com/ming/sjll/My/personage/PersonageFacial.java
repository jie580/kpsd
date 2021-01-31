package com.ming.sjll.My.personage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.Config;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.activity.BasePermissionAty;
import com.ming.sjll.base.permission.PermissionHelper;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyPersonageFaceBinding;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 人脸识别
 */
public class PersonageFacial extends BaseActivity {

    private MyPersonageFaceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_personage_face, null, false);
        setContentView(binding.getRoot());

        face();
    }
    private void face()
    {
        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);

        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PermissionHelper.reqCameraAndSDcard( PersonageFacial.this, new PermissionHelper.PermissionListener() {
//                    @Override
//                    public void onSuccess() {
                        Intent intent = new Intent(PersonageFacial.this, FaceDetectExpActivity.class);
                        PersonageFacial.this.startActivityForResult(intent, 1);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        ToastShow.s("权限获取失败");
//
//                    }
//                });

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PersonageFacial.RESULT_OK) {
            if (requestCode == 1) {
                binding.ivImg.setImageResource(R.mipmap.icon_recognition_complete);
                binding.tvText.setText("识别成功");
                binding.tvSave.setText("重新识别");
                String bestImage0 = data.getStringExtra("image");
//                上传人脸
//                mPresenter.uploadFiles(base64ToFile(bestImage0));
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(base64ToFile(bestImage0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String)json.get("data");
                            MainActivity.saveInfo("path",path);
                            MainActivity.updateUserInfo("face","true");
                            PersonageFacial.this.finish();
                        }catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }
                    @Override
                    public void onProgress(int currentProgress,long currentSize,long totalSize)
                    {
                        loadingDailog.setMsg(currentProgress+"%");

                        Log.e("进度：","currentProgress"+currentProgress+",,currentSize"+currentSize+",,totalSize"+totalSize);
                    }
                    @Override
                    public void onFinal()
                    {
                        loadingDailog.hide();
                    }

                });


            }
        }
    }


    /**
     * base64字符串转文件
     *
     * @param base64
     * @return
     */
    public static File base64ToFile(String base64) {
        File file = null;
        String fileName = "testFile.png";
        FileOutputStream out = null;
        try {
            // 解码，然后将字节转换为文件
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64Utils.decode(base64, Base64Utils.NO_WRAP);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread); // 文件写操作
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }


}

package com.ming.sjll.My.personage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceDetectActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.StatusBarUtil;
import com.ming.sjll.base.widget.ToastShow;

import java.util.HashMap;

public class FaceDetectExpActivity extends FaceDetectActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(FaceDetectExpActivity.this, ContextCompat.getColor(FaceDetectExpActivity.this,R.color.white));
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    public void onDetectCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onDetectCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            ToastShow.s("采集成功");
            Intent intent = new Intent();
            intent.putExtra("image", base64ImageMap.get("bestImage0"));
            setResult(RESULT_OK, intent);
            finish();
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            ToastShow.s("采集超时");
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

}

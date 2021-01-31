package com.ming.sjll;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


//import com.ming.sjll.base.activity.MvpActivity;

import butterknife.BindView;


//public class MainActivity extends MvpActivity<MainView, MainPresenter> {

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initView();
//        openPdfChatActivity(getIntent());
////        Bundle bundle = new Bundle();
////        bundle.putInt("project_id", 16);
////        Tools.jump(this, ProjectDetailsAcitivity.class, bundle, false);
//        initLib();

    }


}
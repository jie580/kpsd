package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.bean.BaseBean;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.view.AddSchdulingView;
import com.ming.sjll.Message.viewmodel.MessageChangeProjectBean;
import com.ming.sjll.my.bean.LongTimeBean;
import com.ming.sjll.my.bean.UserScheduleBean;
import com.ming.sjll.my.dialog.AddSchedulingDialog;

import retrofit2.http.Field;

public class AddSchdulingPresenter extends MvpPresenter<AddSchdulingView> {

    @Override
    public void attachView(AddSchdulingView view) {
        super.attachView(view);
        MessageChangeProjectBean.DataBeanX.DataBean dataBean = (MessageChangeProjectBean.DataBeanX.DataBean) getBundle().getSerializable(AddSchedulingDialog.SCHEDULING);
        if (dataBean != null) {
            getUserSchedule(dataBean.getSchedule_id());
        }

    }


    public void addSchedule(@Field("schedule_id") int schedule_id, @Field("demand") String demand,
                            @Field("brand") String brand, @Field("address") String address, @Field("start_time") String start_time,
                            @Field("value") int value) {
        getNetData(RetrofitManager.get().create(ApiService.class).addSchedule(getToken(), schedule_id, demand, brand, address, start_time, value),
                new ApiObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean data) {
                        getView().showLoading(data.getMsg());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);

                    }
                });
    }

    public void getLongTime(String start_time) {
        getNetData(RetrofitManager.get().create(ApiService.class).getLongTime(getToken(), "1", start_time, getUserId()),
                new ApiObserver<LongTimeBean>() {
                    @Override
                    public void onSuccess(LongTimeBean data) {
                        getView().setLongTimer(data.getData().getValue());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);

                    }
                });
    }

    public void getUserSchedule(int schedule_id) {
        getNetData(RetrofitManager.get().create(ApiService.class).getUserSchedule(getToken(), schedule_id),
                new ApiObserver<UserScheduleBean>() {
                    @Override
                    public void onSuccess(UserScheduleBean data) {
                        getView().setUserScheduleBean(data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);

                    }
                });
    }
}

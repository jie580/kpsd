package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.view.ChooseDayView;
import com.ming.sjll.my.bean.LongTimeBean;
import com.ming.sjll.my.dialog.DayDialog;

public class ChooseDayPresenter extends MvpPresenter<ChooseDayView> {
    private int goods_id;

    @Override
    public void attachView(ChooseDayView view) {
        super.attachView(view);
        goods_id = getBundle().getInt(DayDialog.GOODSID);
        getLongTime();

    }


    public void getLongTime() {
        getNetData(RetrofitManager.get().create(ApiService.class).getGoodsLongTime(getToken(), goods_id, "0"),
                new ApiObserver<LongTimeBean>() {
                    @Override
                    public void onSuccess(LongTimeBean data) {
                        getView().setLongTimer(data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);

                    }
                });
    }

}

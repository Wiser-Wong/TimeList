package com.wiser.timerlist;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.wiser.library.base.WISERBiz;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HomeBiz extends WISERBiz<HomeActivity> {

    private static final int UPDATE_TIME = 1110;

    private static class HomeHandler extends Handler {

        WeakReference<HomeBiz> reference;

        HomeHandler(HomeBiz homeBiz) {
            reference = new WeakReference<>(homeBiz);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (reference != null && reference.get() != null) {
                reference.get().notifyData();
            }
        }
    }

    private HomeHandler homeHandler;

    private List<TimerModel> timerModels = new ArrayList<>();

    @Override
    public void initBiz(Intent intent) {
        super.initBiz(intent);
        homeHandler = new HomeHandler(this);
    }

    public void addData() {
        if (timerModels == null) return;
        timerModels.clear();
        for (int i = 0; i < 300; i++) {
            TimerModel model = new TimerModel();
            model.time = i * 60 * 1000 * 1000;
            timerModels.add(model);
        }
        ui().setItems(timerModels);
        startTime();
    }

    private void notifyData() {
        if (timerModels == null) return;
        for (int i = 0; i < timerModels.size(); i++) {
            timerModels.get(i).time = timerModels.get(i).time - 1000;
        }
        if (isUi()) {
            ui().setItems(timerModels);
            startTime();
        }
    }

    private void startTime() {
        if (homeHandler != null) {
            homeHandler.sendEmptyMessageDelayed(UPDATE_TIME, 1000);
        }
    }

    private void onDetach() {
        if (homeHandler != null) {
            homeHandler.removeMessages(UPDATE_TIME);
            homeHandler = null;
        }
        if (timerModels != null) {
            timerModels.clear();
            timerModels = null;
        }
    }

    @Override
    public void detach() {
        super.detach();
        onDetach();
    }
}

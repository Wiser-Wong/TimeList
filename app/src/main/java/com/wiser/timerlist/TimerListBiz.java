package com.wiser.timerlist;

import com.wiser.library.base.WISERBiz;

import java.util.ArrayList;
import java.util.List;

public class TimerListBiz extends WISERBiz<TimerListActivity> {

    private List<TimerModel> timerModels = new ArrayList<>();

    public void addData() {
        if (timerModels == null) return;
        timerModels.clear();
        for (int i = 0; i < 300; i++) {
            TimerModel model = new TimerModel();
            model.time = i * 60 * 1000 * 1000;
            timerModels.add(model);
        }
        ui().setItems(timerModels);
    }

}

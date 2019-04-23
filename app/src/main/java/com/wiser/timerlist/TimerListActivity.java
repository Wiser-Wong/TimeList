package com.wiser.timerlist;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;

public class TimerListActivity extends WISERActivity<TimerListBiz> {

    private TimerListAdapter adapter;

    public static void intent() {
        WISERHelper.display().intent(TimerListActivity.class);
    }

    @Override
    protected WISERBuilder build(WISERBuilder builder) {
        builder.layoutId(R.layout.timer_activity);
        builder.recycleView().recycleViewId(R.id.rlv_timer);
        builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, true, null);
        builder.recycleView().recycleAdapter(adapter = new TimerListAdapter(this));
        return builder;
    }

    @Override
    protected void initData(Intent intent) {
        biz().addData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            if (adapter != null) {
                adapter.removeAdapter();
                adapter = null;
            }
        }
    }

}

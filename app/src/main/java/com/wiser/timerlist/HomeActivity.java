package com.wiser.timerlist;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;

import butterknife.OnClick;

public class HomeActivity extends WISERActivity<HomeBiz> {

    @Override
    protected WISERBuilder build(WISERBuilder builder) {
        builder.layoutId(R.layout.activity_main);
        builder.recycleView().recycleViewId(R.id.rlv_home_timer);
        builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, true, null);
        builder.recycleView().recycleAdapter(new HomeAdapter(this));
        return builder;
    }

    @Override
    protected void initData(Intent intent) {
        biz().addData();
    }

    @OnClick(value = {R.id.btn_skip})
    public void onClickView(View view) {
        TimerListActivity.intent();
    }

}

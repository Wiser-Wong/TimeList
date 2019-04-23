package com.wiser.timerlist;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.wiser.library.helper.WISERHelper;

public class TimerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WISERHelper.newBind().Inject(this, BuildConfig.DEBUG);

        //内存泄漏检测工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}

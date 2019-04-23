package com.wiser.timerlist;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MCountDownTimer extends CountDownTimer {

    private TextView tvTimer;

    private SimpleDateFormat sdf;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *                       to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                       is called.
     */
    public MCountDownTimer(long millisInFuture, SimpleDateFormat sdf, Handler handler, Runnable runnable, TextView tvTimer) {
        super(millisInFuture, 1000);
        this.sdf = sdf;
        this.tvTimer = tvTimer;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,1000);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //设置时间格式
//        long m = millisUntilFinished / 1000;
//        long hour = m / (60 * 60);
//        long minute = (m / 60) % 60;
//        long s = m % 60;
//        tvTimer.setText(MessageFormat.format("{0}:{1}:{2}", hour, minute, s));
        String time = sdf.format(millisUntilFinished);
        tvTimer.setText(time);
    }

    @Override
    public void onFinish() {
        tvTimer.setText("结束");
    }

    public void detach() {
        tvTimer = null;
        sdf = null;
    }
}

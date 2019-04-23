package com.wiser.timerlist;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wiser.library.adapter.WISERHolder;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;

public class TimerListAdapter extends WISERRVAdapter<TimerModel, TimerListAdapter.TimerHolder> {

    //根据TextView存储定时器对象
    private ConcurrentHashMap<TextView, MCountDownTimer> countDownTimers = new ConcurrentHashMap<>();

    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss", Locale.CHINA);

    //系统时间 用于记录定时系统时间加一秒
    private Date systemDate = new Date();
    //系统时间加上自己的时间 用于与系统时间定时加一秒做差
    private Date date = new Date();

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            systemDate.setTime(systemDate.getTime() + 1000);
            System.out.println("-------->>" + systemDate.getTime());
            handler.postDelayed(this, 1000);
        }
    };

    TimerListAdapter(WISERActivity mWiserActivity) {
        super(mWiserActivity);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public TimerHolder newViewHolder(ViewGroup viewGroup, int type) {
        return new TimerHolder(inflate(viewGroup, R.layout.item_timer));
    }

    class TimerHolder extends WISERHolder<TimerModel> {

        @BindView(R.id.tv_timer)
        TextView tvTimer;

        TimerHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(TimerModel timerModel, int position) {
            if (timerModel == null) return;
            MCountDownTimer countDownTimer = countDownTimers.get(tvTimer);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer.detach();
                countDownTimers.remove(tvTimer);
            }
            //存储定时器
            countDownTimers.put(tvTimer, (MCountDownTimer) new MCountDownTimer(getDistanceTimeLong(date.getTime() + timerModel.time, systemDate.getTime()), sdf, handler, runnable, tvTimer).start());
        }
    }

    //重置适配器移除全局定时加秒
    private void resetAdapter() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    //移除适配器
    public void removeAdapter() {
        resetAdapter();
        cancelAllTimer();
        handler = null;
        runnable = null;
        systemDate = null;
        date = null;
    }

    /**
     * 取消所有定时器
     */
    private void cancelAllTimer() {
        if (countDownTimers != null && countDownTimers.size() > 0) {
            for (Map.Entry<TextView, MCountDownTimer> entry : countDownTimers.entrySet()) {
                if (entry != null && entry.getValue() != null) {
                    entry.getValue().cancel();
                }
            }
            countDownTimers.clear();
        }
        countDownTimers = null;
    }

    /**
     * 时间工具，返回间隔时间长
     */
    private long getDistanceTimeLong(long time1, long time2) {
        long diff = 0L;
        try {
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

}

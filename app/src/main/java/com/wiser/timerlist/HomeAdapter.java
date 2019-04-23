package com.wiser.timerlist;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wiser.library.adapter.WISERHolder;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;

public class HomeAdapter extends WISERRVAdapter<TimerModel, HomeAdapter.TimerHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss", Locale.CHINA);

    HomeAdapter(WISERActivity mWiserActivity) {
        super(mWiserActivity);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
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
            String time = sdf.format(timerModel.time);
            tvTimer.setText(time);
        }
    }

}

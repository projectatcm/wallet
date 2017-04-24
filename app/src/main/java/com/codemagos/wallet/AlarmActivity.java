package com.codemagos.wallet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codemagos.wallet.Receiver.AlarmReceiver;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
Button alarm_add;
    TimePicker timePicker;
    final static int RQS_1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarm_add = (Button) findViewById(R.id.alarm_add);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute(),Toast.LENGTH_SHORT).show();
                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calSet.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                if (calSet.compareTo(calNow) <= 0) {
                    // Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                }

                setAlarm(calSet);
            }
        });
    }

    private void setAlarm(Calendar targetCal) {



        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

    }
}

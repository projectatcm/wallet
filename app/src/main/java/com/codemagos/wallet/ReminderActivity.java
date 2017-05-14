package com.codemagos.wallet;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.Receiver.AlarmReceiver;
import com.codemagos.wallet.Receiver.ReminderReceiver;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    Button alarm_add;
    TimePicker timePicker;
    EditText txt_message;
    String message = "";
    final static int RQS_1 = 1;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder); alarm_add = (Button) findViewById(R.id.alarm_add);
        dbHelper = new DbHelper(getApplicationContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();

        txt_message = (EditText) findViewById(R.id.txt_message);
        alarm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = txt_message.getText().toString();
                //Toast.makeText(getApplicationContext(),timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute(),Toast.LENGTH_SHORT).show();
                final Calendar calNow = Calendar.getInstance();
                final Calendar calSet = (Calendar) calNow.clone();

                new DatePickerDialog(ReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calSet.set(Calendar.YEAR,year);
                        calSet.set(Calendar.MONTH,month);
                        calSet.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calSet.set(Calendar.MINUTE, minute);
                                calSet.set(Calendar.SECOND, 0);
                                calSet.set(Calendar.MILLISECOND, 0);
                                if (calSet.compareTo(calNow) <= 0) {
                                    // Today Set time passed, count to tomorrow
                                    calSet.add(Calendar.DATE, 1);
                                }

                                setAlarm(calSet,message);
                            }
                        },calNow.get(Calendar.HOUR_OF_DAY),calNow.get(Calendar.MINUTE),false).show();
                    }
                },calNow.get(Calendar.YEAR),
                        calNow.get(Calendar.MONTH),
                        calNow.get(Calendar.DAY_OF_MONTH)).show();


            }
        });
    }

    private void setAlarm(Calendar targetCal,String message) {
        String time = targetCal.get(Calendar.DAY_OF_MONTH)+"/"+
                targetCal.get(Calendar.MONTH)+"/" +
                targetCal.get(Calendar.YEAR)+" - " +
                targetCal.get(Calendar.HOUR)+" : " +
                targetCal.get(Calendar.MINUTE);
        long alarm_id = dbHelper.addReminder(sqLiteDatabase,time,message);
        Intent intent = new Intent(getBaseContext(), ReminderReceiver.class);
        intent.putExtra("message",message);
        intent.putExtra("alarm_id",""+alarm_id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), (int) alarm_id  , intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

        Toast.makeText(getApplicationContext(),"Reminder Added",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();;

    }
}

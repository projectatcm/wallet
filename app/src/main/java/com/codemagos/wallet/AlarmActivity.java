package com.codemagos.wallet;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.Receiver.AlarmReceiver;
import com.codemagos.wallet.Spstore.SharedPreferenceStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
Button alarm_add;
    TimePicker timePicker;
    final static int RQS_1 = 1;
    SharedPreferenceStore spStore;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor expenseCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        dbHelper = new DbHelper(getApplicationContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        alarm_add = (Button) findViewById(R.id.alarm_add);
        spStore = new SharedPreferenceStore(getApplicationContext());
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        final Calendar mCalendar = Calendar.getInstance();

        alarm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute(),Toast.LENGTH_SHORT).show();Calendar calNow = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(AlarmActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       int selected_hour = timePicker.getCurrentHour();
                       int selected_minute= timePicker.getCurrentMinute();
                        int selected_year = year;
                        int selected_month = month;
                        int selected_day = dayOfMonth;

                       // TODO custom alarm Receiver
                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                        intent.putExtra("title","Hello ");
                        intent.putExtra("message","Reminder of your diary");
                        // TODO Adding alarm into database and getting its inserted id
                        String alarm_time = selected_hour + " : " + selected_minute + "\n"+selected_day + "/" + (selected_month + 1) + "/"+selected_year;
                        long alarm_id =  dbHelper.addAlarm(sqLiteDatabase,alarm_time);
                        PendingIntent pi = PendingIntent.getBroadcast(AlarmActivity.this, (int) alarm_id, intent, 0);
                        Calendar cal = Calendar.getInstance();
                        //calendar.add(Calendar.SECOND,10);
                        cal.set(Calendar.DATE,selected_day);  //1-31
                        cal.set(Calendar.MONTH,selected_month);  //first month is 0!!! January is zero!!!
                        cal.set(Calendar.YEAR,selected_year);//year...

                        cal.set(Calendar.HOUR_OF_DAY, selected_hour);  //HOUR
                        cal.set(Calendar.MINUTE, selected_minute);       //MIN
                        cal.set(Calendar.SECOND, 10);       //SEC

                        //cal.add(Calendar.MILLISECOND, (int) cal.getTimeInMillis());
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
                    }
                },mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }


}

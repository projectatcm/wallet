package com.codemagos.wallet;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AlarmRingActivity extends AppCompatActivity {
    Intent svc;
    TextView txt_count,txt_answer,txt_question;
    int limit = 3,count = 0,num1,num2,answer;
    public static final int NOTIFICATION_ID = 100;
    NotificationManager mNotificationmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
        txt_count = (TextView) findViewById(R.id.txt_count);
        txt_answer = (TextView) findViewById(R.id.txt_answer);
        txt_question = (TextView) findViewById(R.id.txt_question);


      /*  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();*/

      // TODO calling ringer service to play music
        Intent homeIntent = new Intent(getApplicationContext(), AlarmRingActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(AlarmRingActivity.class);
        stackBuilder.addNextIntent(homeIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //Toast.makeText(context, balance, Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext())
                // Set Icon
                .setSmallIcon(R.drawable.ic_wallet)
                // Set Ticker Message
                .setTicker("Wallet")
                // Set Title
                .setContentTitle("Alarm is ringing")
                // Set Text
                .setContentText("Click to stop Alarm")
                // Add an Action Button below Notification
                // Set PendingIntent into Notification
                .setContentIntent(pendingIntent)
                // Dismiss Notification
                .setAutoCancel(false);

        // Create Notification Manager
        mNotificationmanager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        mNotificationmanager.notify(NOTIFICATION_ID, builder.build());

      svc=new Intent(this, AlarmRingerService.class);
        startService(svc);


        // TODO making questions to answer
        generateQuestion();


    }

    public void stopAlarm(View v){
       // stopService(svc);
        int input =Integer.parseInt( txt_answer.getText().toString());
        if(input == answer){
            //Toast.makeText(getApplicationContext(),"Currect",Toast.LENGTH_SHORT).show();
            count++;
            generateQuestion();
        }else{
            txt_answer.setError("Incorrect !");
            txt_answer.setText("");
        }
    }


    public void generateQuestion(){
        if(count < limit){
            txt_answer.setText("");
            txt_count.setText("Question : "+(count + 1));
            num1 = getRandomNumber();
            num2 = getRandomNumber();
            txt_question.setText(num1 + " + " + num2);
            answer = num1 + num2;
        }else{
            Toast.makeText(getApplicationContext(),"Alarm Stoped",Toast.LENGTH_SHORT).show();
            mNotificationmanager.cancel(NOTIFICATION_ID);
finish();
            stopService(svc);
        }
    }

    public int getRandomNumber(){
        Random random = new Random();
        int randomNumber = random.nextInt(9) + 1;
        return randomNumber;
    }


}

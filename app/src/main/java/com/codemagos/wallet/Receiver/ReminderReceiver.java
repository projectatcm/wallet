package com.codemagos.wallet.Receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.codemagos.wallet.AlarmRingActivity;
import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.HomeActivity;
import com.codemagos.wallet.R;

/**
 * Created by prasanth on 29/3/17.
 */

public class ReminderReceiver extends BroadcastReceiver {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    NotificationManager mNotificationmanager;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Toast.makeText(context, "Reminder received! Messge : "+intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
        Log.e("message",intent.getStringExtra("message"));
        String alarm_id = intent.getStringExtra("alarm_id");
        String message = intent.getStringExtra("message");
        Intent homeIntent = new Intent(context, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(homeIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //Toast.makeText(context, balance, Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.ic_wallet)
                // Set Ticker Message
                .setTicker("Wallet")
                // Set Title
                .setContentTitle("Reminder")
                // Set Text
                .setContentText(message)
                // Add an Action Button below Notification
                // Set PendingIntent into Notification
                .setContentIntent(pendingIntent)
                // Dismiss Notification
                .setAutoCancel(false);

        // Create Notification Manager
        mNotificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        mNotificationmanager.notify(Integer.parseInt(alarm_id), builder.build());
        dbHelper.deleteReminder(sqLiteDatabase,alarm_id);
    }

}

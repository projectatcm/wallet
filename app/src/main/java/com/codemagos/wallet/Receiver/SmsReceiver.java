package com.codemagos.wallet.Receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.codemagos.wallet.HomeActivity;
import com.codemagos.wallet.R;
import com.codemagos.wallet.Spstore.SharedPreferenceStore;

import java.util.regex.Pattern;

/**
 * Created by prasanth on 24/4/17.
 */

public class SmsReceiver extends BroadcastReceiver {
    SharedPreferenceStore spStore;

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        spStore = new SharedPreferenceStore(context);
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";


            }
            String message = smsMessageStr;
            String[] parts = message.split(" ");
            String balance = "00.00";
            for (int i=0; i<parts.length;i++){
                String decimalPattern = "([0-9]*)\\.([0-9]*)";
                String word=parts[i];
                Log.e("words",word);
                boolean match = Pattern.matches(decimalPattern, word);
                if(!match){
                    continue;
                }
                balance = parts[i];
            }
            spStore.setBalance(balance);
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
                    .setContentTitle("Bank balace updated")
                    // Set Text
                    .setContentText(balance + " is your current balance")
                    // Add an Action Button below Notification
                    // Set PendingIntent into Notification
                   .setContentIntent(pendingIntent)
                    // Dismiss Notification
                    .setAutoCancel(true);

            // Create Notification Manager
            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            notificationmanager.notify(0, builder.build());
            //this will update the UI with message
           // SmsActivity inst = SmsActivity.instance();
           // inst.updateList(smsMessageStr);
        }
    }
}
package com.codemagos.wallet.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by prasanth on 29/3/17.
 */

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Reminder received! Messge : "+intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
        Log.e("message",intent.getStringExtra("message"));
    }

}

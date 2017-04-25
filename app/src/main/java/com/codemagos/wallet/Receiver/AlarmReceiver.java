package com.codemagos.wallet.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.codemagos.wallet.AlarmRingActivity;

/**
 * Created by prasanth on 29/3/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent k2) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
      Intent intent = new Intent(context, AlarmRingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}

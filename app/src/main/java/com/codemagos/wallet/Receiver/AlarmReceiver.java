package com.codemagos.wallet.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by prasanth on 29/3/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub
        Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();

    }

}

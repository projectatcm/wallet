package com.codemagos.wallet.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codemagos.wallet.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by Sree on 29-Sep-15.
 */
public class ReminderAdapter extends ArrayAdapter {
    ArrayList time;
    ArrayList messgae;
    Activity activity;
    public ReminderAdapter(Activity activity, ArrayList time, ArrayList messgae) {
        super(activity, R.layout.listview_alarm_row, time);
        this.time = time;
        this.messgae = messgae;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.listview_alarm_row, null);

        TextView txt_date = (TextView) rowView.findViewById(R.id.txt_date);
        TextView txt_time = (TextView) rowView.findViewById(R.id.txt_time);
        txt_time.setText(time.get(position).toString());
        txt_date.setText(messgae.get(position).toString());





        return rowView;
    }
}

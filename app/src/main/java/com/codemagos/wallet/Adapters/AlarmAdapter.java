package com.codemagos.wallet.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codemagos.wallet.R;

import java.util.ArrayList;


/**
 * Created by Sree on 29-Sep-15.
 */
public class AlarmAdapter extends ArrayAdapter {
    ArrayList time;
    Activity activity;
    public AlarmAdapter(Activity activity, ArrayList time) {
        super(activity, R.layout.listview_alarm_row, time);
        this.time = time;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.listview_alarm_row, null);

        TextView txt_date = (TextView) rowView.findViewById(R.id.txt_date);
        TextView txt_time = (TextView) rowView.findViewById(R.id.txt_time);
        txt_time.setText(time.get(position).toString());
        txt_date.setText(time.get(position).toString());





        return rowView;
    }
}

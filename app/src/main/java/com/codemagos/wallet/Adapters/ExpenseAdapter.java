package com.codemagos.wallet.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.codemagos.wallet.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Sree on 29-Sep-15.
 */
public class ExpenseAdapter extends ArrayAdapter {
    ArrayList description;
    ArrayList date;
    ArrayList category;
    ArrayList amount;
    Activity activity;
    ArrayList type;
    public ExpenseAdapter(Activity activity, ArrayList description, ArrayList date,ArrayList category,ArrayList amount,ArrayList type) {
        super(activity, R.layout.listview_expense_row, description);
        this.description = description;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.activity = activity;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.listview_expense_row, null);

        TextView txt_desc = (TextView) rowView.findViewById(R.id.txt_description);
        TextView txt_amount = (TextView) rowView.findViewById(R.id.txt_amount);
        TextView txt_date = (TextView) rowView.findViewById(R.id.txt_date);
        TextView txt_cat = (TextView) rowView.findViewById(R.id.txt_category);
        txt_date.setText(date.get(position).toString());
        txt_desc.setText(description.get(position).toString());
        if(description.get(position).toString().equals("")){
            txt_desc.setText(category.get(position).toString());
        }
        txt_cat.setText(category.get(position).toString());
        if(type.get(position).toString().toLowerCase().equals("expense")) {
            txt_amount.setText("-₹"+ amount.get(position).toString());
            txt_amount.setTextColor(Color.parseColor("#dd5044"));
        }else{
            txt_amount.setText("₹"+amount.get(position).toString());
            txt_amount.setTextColor(Color.parseColor("#2dcc70"));
        }





        return rowView;
    }
}

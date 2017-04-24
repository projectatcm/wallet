package com.codemagos.wallet.Adapters;

import android.content.Context;
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
public class CategoryAdapter extends ArrayAdapter {
    ArrayList name;

    public CategoryAdapter(Context context, ArrayList name) {
        super(context, R.layout.listview_category_row, name);
        this.name = name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_category_row, parent, false);
        TextView txt_name= (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(name.get(position).toString());
        return customView;
    }
}

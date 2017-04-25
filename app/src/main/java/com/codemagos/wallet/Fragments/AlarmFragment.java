package com.codemagos.wallet.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import com.codemagos.wallet.Adapters.ExpenseAdapter;
import com.codemagos.wallet.AlarmActivity;
import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.ExpenseAddActivity;
import com.codemagos.wallet.ExpenseUpdateActivity;
import com.codemagos.wallet.R;
import com.codemagos.wallet.ReminderActivity;

import java.util.ArrayList;


public class AlarmFragment extends Fragment {
    FloatingActionButton btn_new;
    ListView list_alarm;
    Dialog dialog;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor alarmCursor;
    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
/*-------------------*/
        dbHelper = new DbHelper(getContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        alarmCursor = dbHelper.getAlarm(sqLiteDatabase);
        list_alarm = (ListView) rootView.findViewById(R.id.list_alarm);
        // showing expense in listview
        final ArrayList ids = new ArrayList();
        ArrayList description = new ArrayList();
        ArrayList date = new ArrayList();
        ArrayList category = new ArrayList();
        final ArrayList type = new ArrayList();
        ArrayList amount = new ArrayList();
        if(alarmCursor.moveToFirst()) {
            do {
                Log.e("Alarm","id = "+alarmCursor.getString(0));
                Log.e("Alarm","time = "+alarmCursor.getString(1));
                ids.add(alarmCursor.getString(0));
                category.add(alarmCursor.getString(1));
                amount.add("");
                description.add("");
                type.add("");
                date.add("");
            } while (alarmCursor.moveToNext());
        }
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(getActivity(),description,date,category,amount,type);
        list_alarm.setAdapter(expenseAdapter);
        list_alarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Alarm")
                        .setMessage("Are you Sure to delete ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
/*-------------------*/
        btn_new = (FloatingActionButton) rootView.findViewById(R.id.btn_new);
        list_alarm = (ListView) rootView.findViewById(R.id.list_alarm);


        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(),AlarmActivity.class));
            }
        });
        return rootView;
    }
}

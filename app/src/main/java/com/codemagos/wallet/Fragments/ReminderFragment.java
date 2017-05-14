package com.codemagos.wallet.Fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codemagos.wallet.Adapters.AlarmAdapter;
import com.codemagos.wallet.Adapters.ExpenseAdapter;
import com.codemagos.wallet.Adapters.ReminderAdapter;
import com.codemagos.wallet.AlarmActivity;
import com.codemagos.wallet.AlarmRingerService;
import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.ExpenseAddActivity;
import com.codemagos.wallet.ExpenseUpdateActivity;
import com.codemagos.wallet.R;
import com.codemagos.wallet.Receiver.AlarmReceiver;
import com.codemagos.wallet.ReminderActivity;

import java.util.ArrayList;


public class ReminderFragment extends Fragment {
    FloatingActionButton btn_new;
    ListView list_alarm;
    Dialog dialog;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor alarmCursor;
    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_remider, container, false);
/*-------------------*/
        dbHelper = new DbHelper(getContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();

        list_alarm = (ListView) rootView.findViewById(R.id.list_alarm);
        // showing expense in listview
        updateList();
/*-------------------*/
        btn_new = (FloatingActionButton) rootView.findViewById(R.id.btn_new);

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ReminderActivity.class));
            }
        });

        return rootView;
    }



    public void updateList(){

        alarmCursor = dbHelper.getReminder(sqLiteDatabase);
        final ArrayList ids = new ArrayList();
        ArrayList time = new ArrayList();
        ArrayList messgae = new ArrayList();
        if(alarmCursor.moveToFirst()) {
            do {
                Log.e("Alarm","id = "+alarmCursor.getString(0));
                Log.e("Alarm","time = "+alarmCursor.getString(1));
                ids.add(alarmCursor.getString(0));
                time.add(alarmCursor.getString(1));
                messgae.add(alarmCursor.getString(2));

            } while (alarmCursor.moveToNext());
        }
        ReminderAdapter reminderAdapter = new ReminderAdapter(getActivity(),time,messgae);
        list_alarm.setAdapter(reminderAdapter);
        list_alarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Alarm")
                        .setMessage("Are you Sure to delete ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO deleting alarm from data base and unsetting alarm from ringing
                                dbHelper.deleteReminder(sqLiteDatabase, (String) ids.get(position));
                                AlarmManager aManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
                                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                                int alarm_id = Integer.parseInt(ids.get(position).toString());
                                PendingIntent pIntent = PendingIntent.getBroadcast(getContext(), alarm_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                aManager.cancel(pIntent);
                                updateList();
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
    }
}

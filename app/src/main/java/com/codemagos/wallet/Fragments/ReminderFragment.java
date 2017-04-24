package com.codemagos.wallet.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codemagos.wallet.AlarmActivity;
import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.R;
import com.codemagos.wallet.ReminderActivity;


public class ReminderFragment extends Fragment {
    FloatingActionButton btn_new;
    ListView list_expense;
    Dialog dialog;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor expenseCursor;
    public ReminderFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_remider, container, false);
/*-------------------*/
        dbHelper = new DbHelper(getContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        btn_new = (FloatingActionButton) rootView.findViewById(R.id.btn_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ReminderActivity.class));
            }
        });
        return rootView;
    }
}

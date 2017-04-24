package com.codemagos.wallet.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagos.wallet.BankUpdateAcivity;
import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.R;
import com.codemagos.wallet.Spstore.SharedPreferenceStore;

/**
 * Created by prasanth on 26/3/17.
 */

public class DashBoardFragment extends Fragment {
TextView txt_bank_balance,txt_balance,txt_cash_in_hand;
    SharedPreferenceStore spStore;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor expenseCursor;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        float income = 0,expense = 0,balance = 0;
        dbHelper = new DbHelper(getContext());

        sqLiteDatabase = dbHelper.getWritableDatabase();
        expenseCursor = dbHelper.getExpense(sqLiteDatabase);
        spStore = new SharedPreferenceStore(getContext());
        txt_bank_balance = (TextView) view.findViewById(R.id.txt_bank_balance);
        txt_balance = (TextView) view.findViewById(R.id.txt_balance);
        txt_cash_in_hand = (TextView) view.findViewById(R.id.txt_cash_in_hand);
        /*
        * TODO Upating bank balance in dashbord
        * */
        txt_bank_balance.setText(spStore.getBankBalance());
        if(expenseCursor.moveToFirst()) {
            do {
                String type = expenseCursor.getString(4);
                String amount = expenseCursor.getString(2);

                if(type.toLowerCase().equals("income")){
                    income += Float.parseFloat(amount);
                }else{
                    expense += Float.parseFloat(amount);
                }
            } while (expenseCursor.moveToNext());
        }
        float bank_balance = Float.parseFloat(spStore.getBankBalance());
        balance = (bank_balance + income) - expense;
        float inhand = income  - expense;
        Log.e("->","INCOME : "+income+" | EXPENSE : "+expense+" BALANCE : "+balance);

        txt_balance.setText(Float.toString(balance));
        txt_cash_in_hand.setText(Float.toString(inhand));
    }
}

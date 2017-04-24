package com.codemagos.wallet.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.codemagos.wallet.Adapters.ExpenseAdapter;
import com.codemagos.wallet.DbConnection.DbHelper;
import com.codemagos.wallet.ExpenseAddActivity;
import com.codemagos.wallet.ExpenseUpdateActivity;
import com.codemagos.wallet.R;

import java.util.ArrayList;

public class ExpenseFragment extends Fragment {
FloatingActionButton btn_new;
    ListView list_expense;
    Dialog dialog;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor expenseCursor;
    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
/*-------------------*/
        dbHelper = new DbHelper(getContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        expenseCursor = dbHelper.getExpense(sqLiteDatabase);
        list_expense = (ListView) rootView.findViewById(R.id.list_expense);
        // showing expense in listview
       final ArrayList ids = new ArrayList();
        ArrayList description = new ArrayList();
        ArrayList date = new ArrayList();
        ArrayList category = new ArrayList();
        final ArrayList type = new ArrayList();
        ArrayList amount = new ArrayList();
        if(expenseCursor.moveToFirst()) {
            do {
                Log.e("EXP","id = "+expenseCursor.getString(0));
                Log.e("EXP","category = "+expenseCursor.getString(1));
                Log.e("EXP","amount = "+expenseCursor.getString(2));
                Log.e("EXP","description = "+expenseCursor.getString(3));
                Log.e("EXP","type = "+expenseCursor.getString(4));
                Log.e("EXP","date = "+expenseCursor.getString(5));
                ids.add(expenseCursor.getString(0));
                category.add(expenseCursor.getString(1));
                amount.add(expenseCursor.getString(2));
                description.add(expenseCursor.getString(3));
                type.add(expenseCursor.getString(4));
                date.add(expenseCursor.getString(5));
            } while (expenseCursor.moveToNext());
        }
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(getActivity(),description,date,category,amount,type);
        list_expense.setAdapter(expenseAdapter);
        list_expense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), ExpenseUpdateActivity.class);
                intent.putExtra("id",ids.get(position).toString());
                intent.putExtra("type",type.get(position).toString());
                startActivity(intent);
            }
        });
/*-------------------*/
        btn_new = (FloatingActionButton) rootView.findViewById(R.id.btn_new);
        list_expense = (ListView) rootView.findViewById(R.id.list_expense);

dialog = new Dialog(getActivity());
        dialog.setTitle("Select Type");
        dialog.setContentView(R.layout.dillog_expense_add);
        Button btn_income = (Button) dialog.findViewById(R.id.btn_income);
        final Intent intent = new Intent(getContext(), ExpenseAddActivity.class);
        Button btn_expense = (Button) dialog.findViewById(R.id.btn_expense);
        btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","expense");
                startActivity(intent);
            }
        });
        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","income");
                startActivity(intent);
            }
        });
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        return rootView;
    }
}

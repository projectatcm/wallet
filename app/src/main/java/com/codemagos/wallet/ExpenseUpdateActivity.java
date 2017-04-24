package com.codemagos.wallet;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codemagos.wallet.DbConnection.DbHelper;

import java.util.ArrayList;

public class ExpenseUpdateActivity extends AppCompatActivity {
EditText txt_amount,txt_date,txt_category,txt_description;
    String amount,category,date,description,type;
    DbHelper dbHelper;
    Button btn_save;
    SQLiteDatabase sqLiteDatabase;
    Intent incommingIntent;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_add);
         /*------------------------*/
        dbHelper = new DbHelper(getApplicationContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        /*------------------------*/
        incommingIntent = getIntent();
        ID = incommingIntent.getStringExtra("id");
        type = incommingIntent.getStringExtra("type");
        txt_amount = (EditText) findViewById(R.id.txt_amount);
        txt_category = (EditText) findViewById(R.id.txt_category);
        txt_date = (EditText) findViewById(R.id.txt_date);
        txt_description = (EditText) findViewById(R.id.txt_description);
        btn_save = (Button) findViewById(R.id.btn_save);
        final java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
        final int day = mcurrentTime.get(java.util.Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(java.util.Calendar.MONTH) + 1;
        int year = mcurrentTime.get(java.util.Calendar.YEAR);

        Cursor expenseCursor = dbHelper.getExpense(sqLiteDatabase,ID);
        if(expenseCursor.moveToFirst()){
            category = expenseCursor.getString(1);
            txt_category.setText(category);
            txt_amount.setText(expenseCursor.getString(2));
            txt_description.setText(expenseCursor.getString(3));
            txt_date.setText(expenseCursor.getString(5));
        }
        txt_date.setText(day+"/"+month+"/"+year);



        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ExpenseUpdateActivity.this);
                dialog.setContentView(R.layout.dilaog_category_list);
                dialog.setTitle("Select Category");
                ListView list_category = (ListView) dialog.findViewById(R.id.list_category);
                final ArrayList names = new ArrayList();
                Cursor categoryCursor =dbHelper.getCategory(sqLiteDatabase,type);
                while(categoryCursor.moveToNext()){
                    names.add(categoryCursor.getString(1));
                }

                list_category.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.listview_category_row,names));
                list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        category = names.get(position).toString();
                        Toast.makeText(getApplicationContext(),category,Toast.LENGTH_LONG).show();
                        txt_category.setText(category);
                        dialog.hide();
                    }
                });
                dialog.show();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = txt_amount.getText().toString();
                date = txt_date.getText().toString();
               // category = txt_amount.getText().toString();
                description = txt_description.getText().toString();
                dbHelper.updateExpense(sqLiteDatabase,ID,category,amount,description,type,date);
                Toast.makeText(getApplicationContext(),"Updated !",Toast.LENGTH_LONG).show();
                Intent go = new Intent(getApplicationContext(),HomeActivity.class);

                // you pass the position you want the viewpager to show in the extra,
                // please don't forget to define and initialize the position variable
                // properly
                go.putExtra("viewpager_position", 1);

                startActivity(go);
            }
        });



    }
}

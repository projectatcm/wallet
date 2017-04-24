package com.codemagos.wallet;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codemagos.wallet.DbConnection.DbHelper;

public class CategoryActivity extends AppCompatActivity {
Spinner spinner_type;
    EditText txt_name;
    Button btn_save;
    String types[] = {"Income","Expense"};
    String name,type;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        /*------------------------*/
        dbHelper = new DbHelper(getApplicationContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        /*------------------------*/
        txt_name = (EditText) findViewById(R.id.txt_name);
        spinner_type = (Spinner) findViewById(R.id.spinner_type);
        btn_save = (Button) findViewById(R.id.btn_save);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        spinner_type.setAdapter(dataAdapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txt_name.getText().toString();
                if(!type.equals("") && !name.equals("")){
                    dbHelper.addCategory(sqLiteDatabase,name,type);
                }
                finish();
            }
        });

    }
}

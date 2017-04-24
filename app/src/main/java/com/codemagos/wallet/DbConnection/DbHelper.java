package com.codemagos.wallet.DbConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by prasanth on 8/3/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "CHANGER.DB";
    final static int DB_VERSION = 1;
    static SQLiteDatabase DB;
    Context context;
    String LOG = "Changer DB -> ";
    String CREATE_CATEGORY = "CREATE TABLE IF NOT EXISTS category(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "type TEXT" +
            ");";
    String CREATE_EXPENSE = "CREATE TABLE IF NOT EXISTS expense(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "category TEXT," +
            "amount TEXT," +
            "description TEXT," +
            "type TEXT," +
            "date TEXT" +
            ");";

    String CREATE_ALARM = "CREATE TABLE IF NOT EXISTS alarm(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "time TEXT" +
            ");";
    String CREATE_REMIDER = "CREATE TABLE IF NOT EXISTS reminder(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "time TEXT," +
            "message TEXT" +
            ");";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("DATABASE OPERATIONS", "Database Created / Opened :-)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating tables
        db.execSQL(CREATE_CATEGORY);
      db.execSQL(CREATE_EXPENSE);
        db.execSQL(CREATE_ALARM);
        db.execSQL(CREATE_REMIDER);
        Log.i(LOG,"Tables created !");
    }

    public void addReminder(SQLiteDatabase db,String time,String type){
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("message", type);
        db.insert("reminder", null, contentValues);
        Log.i(LOG,"reminder Added");
    }
    public Cursor getReminder(SQLiteDatabase db){
        String query = "Select * from reminder";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }

    public void addCategory(SQLiteDatabase db,String name,String type){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        db.insert("category", null, contentValues);
        Log.i(LOG,"Category Added");
    }
    public void addExpense(SQLiteDatabase db,String category,String amount,String description,String type,String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("amount", amount);
        contentValues.put("description", description);
        contentValues.put("type", type);
        contentValues.put("date", amount);
        db.insert("expense", null, contentValues);
        Log.i(LOG,"Category Added");
    }
    public void updateExpense(SQLiteDatabase db,String id,String category,String amount,String description,String type,String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("amount", amount);
        contentValues.put("description", description);
        contentValues.put("type", type);
        contentValues.put("date", amount);
        db.execSQL("update expense set category = '"+category+"',amount = '"+amount+"',description = '"+description+"',type = '"+type+"',date = '"+date+"' where id = '"+id+"'");
        db.update("expense",contentValues,"id="+id,null);
        Log.i(LOG,"Category Updated");
    }

    public Cursor getCategory(SQLiteDatabase db){
        String query = "Select * from category";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }
    public Cursor getCategory(SQLiteDatabase db,String type){
        String query = "Select * from category where type = '"+type+"'";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }
    public Cursor getExpense(SQLiteDatabase db){
        String query = "Select * from expense order by id desc";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }
    public Cursor getExpense(SQLiteDatabase db,String id){
        String query = "Select * from expense where id = '"+id+"'";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

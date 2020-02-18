package com.example.webpy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "Database_name";
    private static final String TABLE_NAME= "Table_name";
    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table= "create table "+TABLE_NAME+
                "(id INTEGER PRIMARY KEY, txt TEXT)";
        db.execSQL(create_table);
        Toast.makeText(context, "Databse Created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);


    }

    public boolean addText(String text){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("txt",text);
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues);
        return true;
    }
    public ArrayList getAlltext(){
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from "+TABLE_NAME , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("txt")));
            cursor.moveToNext();
        }
        return arrayList;
    }
}

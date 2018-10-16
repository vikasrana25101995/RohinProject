package com.example.rohin.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlLite extends SQLiteOpenHelper
{
    public static  final String DATABASE_NAME = "Birthday.db";
    public static  final String TABLE_NAME = "friends_info_table";
    public static  final String FIRST_NAME = "First_Name";
    public static  final String LAST_NAME = "Last_Name";
    public static  final String DOB = "DOB";
    public static  final String CONTACT = "Contact";


    public SqlLite(Context context) //,String name, SQLiteDatabase.CursorFactory factory, int version,DatabaseErrorHandler errorHandler)
    {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(("CREATE TABLE " + TABLE_NAME+ "(" +
                CONTACT+ " INTEGER PRIMARY KEY,"+
                FIRST_NAME + " TEXT," +
                LAST_NAME + " TEXT, " +
                DOB + " TEXT"+")"
        ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
    }

    public boolean InsertData(String First_Name,String Last_Name,String Dob,String Contact)
    {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues content_value = new ContentValues();
            content_value.put(FIRST_NAME,First_Name);
            content_value.put(LAST_NAME,Last_Name);
            content_value.put(DOB,Dob);
            content_value.put(CONTACT,Contact);
            long result = db.insert(TABLE_NAME,null,content_value);
            Log.d("result_DB",Long.toString(result));

            if(result==-1)
            {
                return false;
            }
            else
            {
                return true;
            }
    }
}

package com.example.rohin.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlLite extends SQLiteOpenHelper
{
    public static  final String DATABASE_NAME = "Birthday.db";
    public static  final String TABLE_NAME = "friends_info";
    public static  final String FIRST_NAME = "First_Name";
    public static  final String LAST_NAME = "Last_Name";
    public static  final String DOB = "DOB";
    public static  final String CONTACT = "Contact";
    private static final String IMAGE = "Image";
    private static final String SNO = "SNO";

    public SqlLite(Context context) //,String name, SQLiteDatabase.CursorFactory factory, int version,DatabaseErrorHandler errorHandler)
    {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(("CREATE TABLE " + TABLE_NAME+ "(" +
                CONTACT+ " INTEGER,"+
                FIRST_NAME + " TEXT," +
                LAST_NAME + " TEXT, " +
                DOB + " TEXT,"+
                IMAGE+ " BLOB,"+"SNO INTEGER PRIMARY KEY AUTOINCREMENT);"
        ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
    }

    public boolean InsertData(String First_Name,String Last_Name,String Dob,String Contact,byte[] Image)
    {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content_value = new ContentValues();
            content_value.put(FIRST_NAME,First_Name);
            content_value.put(LAST_NAME,Last_Name);
            content_value.put(DOB,Dob);
            content_value.put(CONTACT,Contact);
            content_value.put(IMAGE,Image);
            long result = db.insert(TABLE_NAME,null,content_value);

            if(result==-1)
            {
                return false;
            }
            else
            {
                return true;
            }
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery(" Select * from "+TABLE_NAME,null);
        return  res;
    }

    public Integer Delete_Data(int Contact)
    {
        String Cont = Integer.toString(Contact);
        Log.d("CONTACTVALUE",Cont);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"SNO =?", new String[] {Cont});
    }

    public Cursor getData(String SNO)
    {
        int sno = Integer.parseInt(SNO);
        int SSNO = sno+1;
        //Log.d("STRINGSSNO",SSNO);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery(" Select * from "+TABLE_NAME+" where SNO = "+SSNO+";",null);
        return  res;
    }

    public boolean Update_Data(String First_Name,String Last_Name,String Dob,String Contact,byte[] Image,String pos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_value = new ContentValues();
        content_value.put(FIRST_NAME,First_Name);
        content_value.put(LAST_NAME,Last_Name);
        content_value.put(DOB,Dob);
        content_value.put(CONTACT,Contact);
        content_value.put(IMAGE,Image);

        int index =  Integer.parseInt(pos);
        String Index = Integer.toString(index+1);
        db.update(TABLE_NAME,content_value,"SNO = ?", new String[] {Index});
        return  true;
    }
}

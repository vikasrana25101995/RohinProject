package com.example.rohin.project1;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import java.sql.Blob;

public class Show_Friend extends Fragment
{
    SqlLite myDb;
    Button button;
    RecyclerView list;
    String[] Name;
    String[] DOB;
    byte[][] GetImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.show_friend, container, false);
        myDb = new SqlLite(getActivity().getApplicationContext());
        list=(RecyclerView) rootView.findViewById(R.id.list);

        Name = Get_Name();
        DOB = Get_DOB();
        GetImage = GetImage();

        RecyclerView view = rootView.findViewById(R.id.list);
        Recycler_Veiw_Holder recycle_view = new Recycler_Veiw_Holder(rootView.getContext(),Name,DOB,GetImage);
        view.setAdapter(recycle_view);
        view.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }


    public String[] Get_Name()
    {
        Log.d("CLICKING","Inside the click");
        Cursor res =  myDb.getAllData();
        String [] Data = new String[res.getCount()];

        if(res.getCount()!=0)
        {
            int i = -1;
            while (res.moveToNext())
            {
                  i++;
                  Data[i] = res.getString(1)+" "+res.getString(2);
            }

        }
        return Data;
    }

    public String[] Get_DOB()
    {
        Log.d("CLICKING","Inside the click");
        Cursor res =  myDb.getAllData();
        String [] Data = new String[res.getCount()];
        if(res.getCount()!=0)
        {
            int i = -1;
            while (res.moveToNext())
            {
                i++;
                Data[i] = res.getString(3);
            }

        }
        return Data;
    }

    public byte[][] GetImage()
    {
        Log.d("CLICKING","Inside the click");
        Cursor res =  myDb.getAllData();

        byte[][] Data = new byte[res.getCount()][];

        int i=-1;
        if(res.getCount()!=0)
        {

            while (res.moveToNext())
            {
                i++;
                Data[i] = res.getBlob(4);
            }

        }

        return Data;
    }

}






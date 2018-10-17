package com.example.rohin.project1;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    ListView list;
    String[] Name;
    String[] DOB;
    byte[][] GetImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.show_friend, container, false);
        myDb = new SqlLite(getActivity().getApplicationContext());
        list=(ListView)rootView.findViewById(R.id.list);

        Name = Get_Name();
        DOB = Get_DOB();
        GetImage = GetImage();


        MyListAdapter customdesign = new MyListAdapter(container.getContext(),Name,DOB,GetImage);
        list.setAdapter(customdesign);
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

class MyListAdapter extends ArrayAdapter<String>
{
    private String[] Name;
    private String[] DOB;
    private byte[][] Image;

    private Activity context;

    public MyListAdapter(Context context,String[] Name,String[] DOB,byte[][] Image)
    {
        super(context,R.layout.custom_layout,Name);
        this.context = (Activity) context;
        this.Name = Name;
        this.DOB = DOB;
        this.Image = Image;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        View v = convertView;
        ViewHolder view_holder = null;

        if(v==null)
        {
            LayoutInflater layoutInflate = context.getLayoutInflater();
            v = layoutInflate.inflate(R.layout.custom_layout,null,true);
            view_holder = new ViewHolder(v);
            v.setTag(view_holder);
        }
        else
        {
            view_holder = (ViewHolder) v.getTag();
        }

        Bitmap Images = DbBitmapUtility.getImage(Image[position]);
        view_holder.Name_tv.setText(Name[position]);
        view_holder.DOB_tv.setText(DOB[position]);
        view_holder.Image.setImageBitmap(Images);
        return v;
    }
}

class ViewHolder
{
    TextView Name_tv;
    TextView DOB_tv;
    ImageView Image;

    ViewHolder(View v)
    {
        Name_tv = v.findViewById(R.id.Name);
        DOB_tv = v.findViewById(R.id.DOB);
        Image = v.findViewById(R.id.imageView);
    }
}


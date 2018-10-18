package com.example.rohin.project1;

import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;




public class Show_Friend extends Fragment
{
    SqlLite myDb;
    Button button;
    RecyclerView list;
    String[] Name;
    String[] DOB;
    String[] Contact;
    byte[][] GetImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.show_friend, container, false);
        myDb = new SqlLite(getActivity() .getApplicationContext());
        list=(RecyclerView) rootView.findViewById(R.id.list);

        Get_Data();

        RecyclerView view = rootView.findViewById(R.id.list);
        Recycler_Veiw_Holder recycle_view = new Recycler_Veiw_Holder(rootView.getContext(),Name,DOB,GetImage,Contact);
        view.setAdapter(recycle_view);
        view.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        return rootView;
    }


    public void Get_Data()
    {
        Log.d("CLICKING","Inside the click");
        Cursor res =  myDb.getAllData();

        String [] Name_Arr = new String[res.getCount()];
        String [] DOB_Arr = new String[res.getCount()];
        byte [][] Image_Arr = new byte[res.getCount()][];
        String [] Contact_Arr = new String[res.getCount()];

        if(res.getCount()!=0)
        {
            int i = -1;
            while (res.moveToNext())
            {
                  i++;
                  Name_Arr[i] = res.getString(1)+" "+res.getString(2);
                  DOB_Arr[i] = res.getString(3);
                  Image_Arr[i] = res.getBlob(4);
                  Contact_Arr[i] = res.getString(0);
            }

            Name = Name_Arr;
            DOB= DOB_Arr;
            GetImage = Image_Arr;
            Contact = Contact_Arr;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}






package com.example.rohin.project1;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Add_Friend extends Fragment
{
    SqlLite myDb;
    EditText FirstName,LastName,Dob,Contact;
    Button Submit_Button;
    ImageView Image;
    Calendar myCalendar = Calendar.getInstance();

    public static final int pickImage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.add_friend, container, false);
        myDb = new SqlLite(getActivity().getApplicationContext());
        FirstName = (EditText) rootView.findViewById(R.id.FirstNameTextBox);
        LastName = (EditText) rootView.findViewById(R.id.LastNameTextBox);
        Dob = (EditText) rootView.findViewById(R.id.DOBTextBox);
        Contact = (EditText) rootView.findViewById(R.id.ContactTextBox);
        Submit_Button = (Button)rootView.findViewById(R.id.button2);
        Image = rootView.findViewById(R.id.profile_image);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        Dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              new DatePickerDialog(rootView.getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        InsertData();
        GetImage();
        return rootView;
    }


    public void InsertData()
    {
        Submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean isInserted = myDb.InsertData(FirstName.getText().toString(),LastName.getText().toString(),Dob.getText().toString(),Contact.getText().toString());
                if(isInserted==true)
                {
                   Toast.makeText(getActivity(),"Data Inserted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Data Insertion false",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void GetImage()
    {
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    OpenGallery();
            }
        });
    }

    public void OpenGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery,pickImage);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==pickImage && resultCode == Activity.RESULT_OK && data!=null)
        {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Image.setImageBitmap(bitmap);
        }
    }

    private void updateLabel()
    {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Dob.setText(sdf.format(myCalendar.getTime()));
    }

}

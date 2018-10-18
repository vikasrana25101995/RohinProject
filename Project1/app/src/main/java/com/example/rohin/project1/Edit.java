package com.example.rohin.project1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.rohin.project1.Add_Friend.pickImage;

public class Edit extends AppCompatActivity
{
    SqlLite myDb;
    Button update;
    CircleImageView image;
    TextView FirstName;
    TextView LastName;
    TextView DOB;
    TextView Contact;
    CircleImageView Image;
    String SNO;
    Bitmap Images;
    Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        getIncomingIntents();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        DOB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(v.getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void getIncomingIntents()
    {
        Image = findViewById(R.id.eprofile_image);
        FirstName = findViewById(R.id.eFirstNameTextBox);
        LastName = findViewById(R.id.eLastNameTextBox);
        DOB = findViewById(R.id.eDOBTextBox);
        Contact = findViewById(R.id.eContactTextBox);
        update = findViewById(R.id.button2);

        if(getIntent().hasExtra("SNO"))
        {
            String SNO = getIntent().getStringExtra("SNO");
            Log.d("GETTING DATA",SNO);
            setData(SNO);
            GetImage();
            Update_Data();

        }
    }

    private void setData(String SNo)
    {
        myDb = new SqlLite(this.getApplicationContext());

        Cursor res = myDb.getData(SNo);
        res.moveToNext();
        Log.d("CHECKING123123",res.getString(5));
        byte[] getImage = res.getBlob(4);
        Log.d("IMAGEDATA",getImage.toString());
        Images = DbBitmapUtility.getImage(getImage);
        Image.setImageBitmap(Images);

        FirstName.setText(res.getString(1));
        LastName.setText(res.getString(2));
        DOB.setText(res.getString(3));
        Contact.setText(res.getString(0));
        SNO = SNo;
    }

    private void Update_Data()
    {
        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                byte[] Image =  DbBitmapUtility.getBytes(Images);
                boolean result = myDb.Update_Data(FirstName.getText().toString(),LastName.getText().toString(), DOB.getText().toString(),Contact.getText().toString(),Image,SNO);
                if(result)
                {
                    Toast.makeText(v.getContext(),"Data Updated Sucessfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(v.getContext(),"Please Check The Data",Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pickImage && resultCode == Activity.RESULT_OK && data!=null)
        {
            Uri uri = data.getData();
            Bitmap Img = null;
            try
            {
                Img = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Image.setImageBitmap(Img);
            Images =Img;
        }
    }

    private void updateLabel()
    {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        DOB.setText(sdf.format(myCalendar.getTime()));
    }
}


package com.example.rohin.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recycler_Veiw_Holder extends RecyclerView.Adapter<Recycler_Veiw_Holder.view_Holder>
{

    private String[] mName;
    private String[] mDOB;
    private String[] mContact;
    private byte[][] mImage;
    private Context mcontext;
    SqlLite myDb;
    CircleImageView Delete_Button;
    CircleImageView Edit_Button;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public Recycler_Veiw_Holder(Context context, String[] Name, String[] DOB, byte[][] Image, String[] Contact)
    {
        this.mcontext = context;
        this.mName = Name;
        this.mDOB = DOB;
        this.mImage = Image;
        this.mContact = Contact;
        viewBinderHelper.setOpenOnlyOne(true);
    }


    @Override
    public view_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout,viewGroup,false);
        view_Holder holder= new view_Holder(view);
        myDb = new SqlLite(viewGroup.getContext());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull view_Holder view_holder, int i)
    {
            Bitmap Images = DbBitmapUtility.getImage(this.mImage[i]);
            view_holder.Image.setImageBitmap(Images);
            view_holder.i_name.setText(mName[i]);
            view_holder.i_DOB.setText(mDOB[i]);
            Delete_Data(i);
            Edit_Data(i);
    }

    @Override
    public int getItemCount()
    {
        if(mName != null) {
            return mName.length;
        }
        else
        {
            return 0;
        }
    }


    public void Delete_Data(final int index) {
        Delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myDb.Delete_Data(mContact[index]);
            }
        });
    }

    public void Edit_Data(final int index)
    {
        Log.d("INDEX check",Integer.toString(index));
        Edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mcontext,Edit.class);
                intent.putExtra("Contact",mContact[index]);
                mcontext.startActivity(intent);
            }
        });
    }


    //----------CLASS-----

    public class view_Holder extends RecyclerView.ViewHolder {

        CircleImageView Image;
        TextView i_name;
        TextView i_DOB;

        public view_Holder(View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.imageView);
            i_name = itemView.findViewById(R.id.Name);
            i_DOB = itemView.findViewById(R.id.DOB);
            Delete_Button = itemView.findViewById(R.id.Delete_Button);
            Edit_Button = itemView.findViewById(R.id.Edit_Button);

        }
    }
}

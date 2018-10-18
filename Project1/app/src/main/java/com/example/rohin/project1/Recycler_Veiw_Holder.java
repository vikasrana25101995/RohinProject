package com.example.rohin.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private byte[][] mImage;
    private Context mcontext;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public Recycler_Veiw_Holder(Context context, String[] Name, String[] DOB, byte[][] Image)
    {
        this.mcontext = context;
        this.mName = Name;
        this.mDOB = DOB;
        this.mImage = Image;
        viewBinderHelper.setOpenOnlyOne(true);
    }


    @Override
    public view_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout,viewGroup,false);
        view_Holder holder= new view_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull view_Holder view_holder, int i)
    {
            Bitmap Images = DbBitmapUtility.getImage(this.mImage[i]);
            view_holder.Image.setImageBitmap(Images);
            view_holder.i_name.setText(mName[i]);
    }

    @Override
    public int getItemCount() {
        return mName.length;
    }

    public class view_Holder extends RecyclerView.ViewHolder
    {

        CircleImageView Image;
        TextView i_name;

        public view_Holder(@NonNull View itemView)
        {
            super(itemView);
            Image = itemView.findViewById(R.id.imageView);
            i_name = itemView.findViewById(R.id.Name);
        }
    }
}

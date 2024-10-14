package com.example.notefy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;


public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ImageViewHolder> {


    private Context mContext;


    @Override
    public int getItemCount() {
        return 2;
    }

    public AboutUsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.about_us_adapter, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {


        String Author1="Daksh Gandhi";
        String Author2="Akshat Jain";
        String des1="Daksh is 3rd Year Engineering Student whose idea of fun is Coding and Flying Drones ";
        String des2="Yooooo he Crayyyyy";
        if(position==0) {
            holder.textViewName1.setText(Author1);
            holder.textViewName2.setVisibility(View.INVISIBLE);
            holder.desc1.setText(des1);
            holder.desc2.setVisibility(View.INVISIBLE);
            holder.img1.setImageResource(R.drawable.daksh);
            holder.img2.setVisibility(View.INVISIBLE);
        }
        else {
            holder.textViewName2.setText(Author2);
            holder.textViewName1.setVisibility(View.INVISIBLE);
            holder.desc2.setText(des2);
            holder.desc1.setVisibility(View.INVISIBLE);
            holder.img2.setImageResource(R.drawable.akshat);
            holder.img1.setVisibility(View.INVISIBLE);

        }

    }




    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName1,textViewName2,desc1,desc2;
        public ImageView img1,img2;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName1 = itemView.findViewById(R.id.Author1);
            textViewName2 = itemView.findViewById(R.id.Author2);
            desc1=itemView.findViewById(R.id.desc1);
            desc2=itemView.findViewById(R.id.desc2);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);

        }

    }
}
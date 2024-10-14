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


public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ImageViewHolder> {


    private Context mContext;
    private File[] mFiles;
    private OnItemClickListener mListener;


    public DownloadAdapter(Context context, File[] files) {
        mContext = context;
        mFiles = files;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.file_items, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        File currentfile = mFiles[position];
        holder.textViewName.setText(currentfile.getName());

        String x = currentfile.getName();
        if (x.contains("png") || x.contains("jpg") || x.contains("image")) {
            holder.img.setImageResource(R.drawable.jpg);
        } else if (x.contains("pdf")) {
            holder.img.setImageResource(R.drawable.pdf);
        } else if (x.contains("ppt")) {
            holder.img.setImageResource(R.drawable.ppt);
        } else if (x.contains("zip") || x.contains("rar")) {
            holder.img.setImageResource(R.drawable.zip);
        } else if (x.contains(".doc")) {
            holder.img.setImageResource(R.drawable.docx);
        } else if (x.contains(".txt")) {
            holder.img.setImageResource(R.drawable.txt);
        } else {
            holder.img.setImageResource(R.drawable.all);
        }
    }

    @Override
    public int getItemCount() {
        if (mFiles != null) {
            return mFiles.length;
        }
        return 0;
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewName;
        public ImageView img;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.nameoffile);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
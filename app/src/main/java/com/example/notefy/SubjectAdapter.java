package com.example.notefy;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ImageViewHolder> {

    private Context mContext;
    private String[] mSub,mSub1;
    private OnItemClickListener mListener;
    int backgroundColor;
    int color[]=new int[40];



    public SubjectAdapter(Context context, String[] sub,String[] sub1) {
        mContext = context;
        mSub = sub;
        mSub1 = sub1;
        for (int i = 0; i < 17; i++) {
            color[i] = ContextCompat.getColor(mContext, R.color.color+i);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.subject_adapter, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String currentsub = mSub[position];
        holder.textViewName1.setText(currentsub);
        holder.textViewName.setText(mSub1[position]);
        Random r = new Random();
        int i1 = r.nextInt(16);
        holder.textViewName.setBackgroundColor(color[i1]);


    }

    @Override
    public int getItemCount() {
        if (mSub != null) {
            return mSub.length;
        }
        return 0;
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewName,textViewName1;
        public ImageView img;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.sub);
            textViewName1 = itemView.findViewById(R.id.subject);


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
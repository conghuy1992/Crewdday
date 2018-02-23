package com.dazone.crewdday.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.R;
import com.dazone.crewdday.activity.DDayActivity;

import java.util.List;


public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {
    String TAG = "RecyclerViewAdapter2";
    private List<String> moviesList;
    private int temp;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView img_delete;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);
        }
    }


    public RecyclerViewAdapter2(List<String> moviesList, int temp) {
        this.moviesList = moviesList;
        this.temp = temp;
    }

    public void updateTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (temp != 0) {
            holder.img_delete.setVisibility(View.VISIBLE);
        } else {
            holder.img_delete.setVisibility(View.GONE);
        }
        holder.title.setText(moviesList.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDayActivity.instance.startOrganization2();
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDayActivity.instance.remove_pos2(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

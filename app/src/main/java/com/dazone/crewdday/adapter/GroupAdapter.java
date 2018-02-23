package com.dazone.crewdday.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewdday.R;
import com.dazone.crewdday.model.GroupModel;
import com.dazone.crewdday.util.IconUtils;

import java.util.List;

/**
 * Created by maidinh on 22/7/2016.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    private List<GroupModel> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView img_menu;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_menu);
            img_menu = (ImageView) view.findViewById(R.id.img_menu);
        }
    }


    public GroupAdapter(List<GroupModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menu_change_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GroupModel movie = moviesList.get(position);
        holder.title.setText(movie.getName());
        holder.img_menu.setImageResource(IconUtils.getFlagDrawableId(movie.getTagNo()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
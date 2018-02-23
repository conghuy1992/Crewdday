package com.dazone.crewdday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewdday.R;
import com.dazone.crewdday.model.GroupModel;
import com.dazone.crewdday.util.IconUtils;

import java.util.List;

/**
 * Created by maidinh on 22/7/2016.
 */
public class GroupAdapter_v2 extends BaseAdapter {
    Context context;
    private List<GroupModel> moviesList;

    public GroupAdapter_v2(Context context, List<GroupModel> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_menu_change_group, null);
        TextView title = (TextView) convertView.findViewById(R.id.txt_menu);
        ImageView img_menu = (ImageView) convertView.findViewById(R.id.img_menu);
        GroupModel movie = moviesList.get(position);
        title.setText(movie.getName());
        img_menu.setImageResource(IconUtils.getFlagDrawableId(movie.getTagNo()));
        return convertView;
    }
}

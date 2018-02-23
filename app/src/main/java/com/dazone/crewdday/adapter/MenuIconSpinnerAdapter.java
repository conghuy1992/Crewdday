package com.dazone.crewdday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dazone.crewdday.R;
import com.dazone.crewdday.util.ImageUtils;

/**
 * Created by DAZONE on 16/03/16.
 */
public class MenuIconSpinnerAdapter extends BaseAdapter{


    Context context;
    String[] iconsName;

    public MenuIconSpinnerAdapter(Context context,String[] iconsName){
        this.context = context;
        this.iconsName  = iconsName;
    }
    @Override
    public int getCount() {
        return iconsName.length;
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_tag_icon, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.img_tag_icon);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int id = ImageUtils.getDrawableIdByName(iconsName[position],R.drawable.class);
        viewHolder.ivIcon.setImageDrawable(ImageUtils.getDrawable(context,id));

        return convertView;
    }

    private static class ViewHolder{
        ImageView ivIcon;

    }
}

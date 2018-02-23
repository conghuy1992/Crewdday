package com.dazone.crewdday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dazone.crewdday.R;

import java.util.ArrayList;

/**
 * Created by maidinh on 11/5/2016.
 */
public class Month_Adapter extends BaseAdapter {
    ArrayList<String> arrayList;
    Context context;

    public Month_Adapter(Context context, ArrayList<String> arr) {
        this.context = context;
        this.arrayList = arr;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        convertView = inflater.inflate(R.layout.custom_text_size, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(arrayList.get(position));
        return convertView;
    }
}

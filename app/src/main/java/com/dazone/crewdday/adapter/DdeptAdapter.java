package com.dazone.crewdday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;

import com.dazone.crewdday.R;
import com.dazone.crewdday.activity.AddUser;
import com.dazone.crewdday.activity.DetailViewActivity;

import java.util.ArrayList;

public class DdeptAdapter extends BaseAdapter {
    String TAG = "SpecialAdapter";
    ArrayList<String> arrayList;
    Context context;
    ListView listDdepts;
    DdeptAdapter adapter = this;
    String get_cur_date = "";
    String str = "";

    public DdeptAdapter(Context context, ArrayList<String> arrayList, ListView listDdepts) {
        this.context = context;
        this.arrayList = arrayList;
        this.listDdepts = listDdepts;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.ddepts_layout, null);
        ImageView img_remove = (ImageView) convertView.findViewById(R.id.img_remove);
        ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);
        if (DetailViewActivity.sp_ddaytype_int != 1) {
            if (arrayList.size() == 1) {
                if (position == 0) {
                    img_add.setVisibility(View.VISIBLE);
                }
            } else {
                if (position == 0) {
                } else if (position == arrayList.size() - 1) {
                    img_add.setVisibility(View.VISIBLE);
                    img_remove.setVisibility(View.VISIBLE);
                } else {
                    img_remove.setVisibility(View.VISIBLE);
                }
            }
        }
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser.instance.showText();
                arrayList.add("a");
                notifyDataSetChanged();
                setHeight();
            }
        });
        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyDataSetChanged();
                setHeight();
                if (arrayList.size() == 1)
                    AddUser.instance.hideText();
            }
        });

        return convertView;
    }

    private void setHeight() {
        View item = adapter.getView(0, null, listDdepts);
        item.measure(0, 0);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, (int) (arrayList.size() * item.getMeasuredHeight()));
        listDdepts.setLayoutParams(params);
        AddUser.instance.gotoEndScroll();
    }
}

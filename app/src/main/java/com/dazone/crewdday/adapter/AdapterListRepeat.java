package com.dazone.crewdday.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dazone.crewdday.R;
import com.dazone.crewdday.activity.DDayActivity;

import java.util.ArrayList;

/**
 * Created by maidinh on 24/5/2016.
 */
public class AdapterListRepeat extends BaseAdapter {
    String TAG = "AdapterListRepeat";
    Context context;
    ArrayList<String> arrayList;
    ListView lv;

    public AdapterListRepeat(Context context, ArrayList<String> arrayList, ListView lv) {
        this.context = context;
        this.arrayList = arrayList;
        this.lv = lv;
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
        convertView = inflater.inflate(R.layout.layout_list_repeat, null);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        ImageView img_delete = (ImageView) convertView.findViewById(R.id.img_delete);
        ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);
        tv_content.setText(arrayList.get(position));
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("TAG",DDayActivity.temp_list.get(position));
                if (DDayActivity.temp_list.get(position).equals("repeatOption_daily"))
                    DDayActivity.repeatOption_daily = "";
                else if (DDayActivity.temp_list.get(position).equals("repeatOption_week"))
                    DDayActivity.repeatOption_week = "";
                else if (DDayActivity.temp_list.get(position).equals("repeatOption_month"))
                    DDayActivity.repeatOption_month = "";
                else if (DDayActivity.temp_list.get(position).equals("repeatOption_year"))
                    DDayActivity.repeatOption_year = "";
                DDayActivity.temp_list.remove(position);
                DDayActivity.arrayList_repeat.remove(position);
                DDayActivity.adapterListRepeat.notifyDataSetChanged();
                int totalHeight = lv.getPaddingTop() + lv.getPaddingBottom();
                int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv.getWidth(), View.MeasureSpec.AT_MOST);
                DDayActivity.adapterListRepeat.notifyDataSetChanged();
                for (int i = 0; i < DDayActivity.adapterListRepeat.getCount(); i++) {
                    View listItem = DDayActivity.adapterListRepeat.getView(i, null, lv);
                    if (listItem != null) {
                        listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                        totalHeight += listItem.getMeasuredHeight();
                    }
                }
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = totalHeight + (lv.getDividerHeight() * (lv.getCount() - 1));
                lv.setLayoutParams(params);
                lv.requestLayout();
            }
        });
        if (arrayList.size() > 1) {
            if (position == arrayList.size() - 1) img_add.setVisibility(View.VISIBLE);
        } else {
            img_add.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(position).equals(context.getResources().getString(R.string.norepeat))) {
            img_delete.setVisibility(View.GONE);
        }
        return convertView;
    }
}

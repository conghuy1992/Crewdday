package com.dazone.crewdday.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dazone.crewdday.R;
import com.dazone.crewdday.model.MonthlyModel;
import com.dazone.crewdday.util.ImageUtils;

import java.util.ArrayList;

/**
 * Created by DAZONE on 11/04/16.
 */
public class ChangeTagIconAdapter extends BaseAdapter {

    String[] tagBackgroundColors;
    String[] tagBorderColors;

    private Context mContext;
    //private ArrayList<MonthlyModel> mList;
    //private int mPosition;

    public ChangeTagIconAdapter(Context context, String[] backgroundColors, String[] borderColors) {
        this.mContext = context;
        this.tagBackgroundColors = backgroundColors;
        this.tagBorderColors = borderColors;
        //this.mList = list;
       // this.mPosition = position;
    }


    @Override
    public int getCount() {
        return tagBackgroundColors.length;
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
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_dialog_change_color, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_view);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(tagBackgroundColors[position]));
        gd.setStroke(1, Color.parseColor(tagBorderColors[position]));

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            viewHolder.imageView.setBackgroundDrawable(gd);
        } else {
            viewHolder.imageView.setBackground(gd);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;

    }
}

package com.dazone.crewdday.viewholder;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.adapter.ChangeTagIconAdapter;
import com.dazone.crewdday.mInterface.MenuCallback;
import com.dazone.crewdday.mInterface.MenuItemClickListener;
import com.dazone.crewdday.util.PreferenceUtilities;

/**
 * Created by DAZONE on 02/03/16.
 */
public class HomeMenuViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    public TextView text, tv_count;
    public ImageView imageView;
    public CheckBox checkBox;
    private PreferenceUtilities mPref;
    public RelativeLayout backgroundContainer;
    private MenuItemClickListener clickListener;

    //Type of item view: 1-mainItemView / 2-subItemView / 3-otherView
    int viewType;
    MenuCallback menuCallback;
    String[] tagBackgroundColors;
    String[] tagBorderColors;

    public HomeMenuViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        mPref = _Application.getInstance().getPreferenceUtilities();
        text = (TextView) itemView.findViewById(R.id.txt_menu);

        if (viewType == Cons.MENU_TYPE_NORMAL || viewType == Cons.MENU_TYPE_SUB) {
            imageView = (ImageView) itemView.findViewById(R.id.img_menu);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            backgroundContainer = (RelativeLayout) itemView.findViewById(R.id.background_container);
        } else if (viewType == Cons.MENU_TYPE_OTHER) {
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_share_dday_show);
            checkBox.setOnClickListener(this);
        }
        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setMenuCallback(MenuCallback menuCallback) {
        this.menuCallback = menuCallback;
    }


    public void setTagBackgroundColors(String[] tagBackgroundColors) {
        this.tagBackgroundColors = tagBackgroundColors;
    }

    public void setTagBorderColors(String[] tagBorderColors) {
        this.tagBorderColors = tagBorderColors;
    }

    public void setClickListener(MenuItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public int getViewType() {
        return viewType;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(getAdapterPosition(), false);
        int id = v.getId();
        if (id == R.id.cb_share_dday_show) {
//            CheckBox checkBox = (CheckBox) v;
//            if (checkBox.isChecked()) {
//                mPref.setShareDdayShow(Cons.TRUE);
//            } else {
//                mPref.setShareDdayShow(Cons.FALSE);
//            }
//            menuCallback.refreshFragments();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        clickListener.onClick(getAdapterPosition(), true);
        return true;
    }

}

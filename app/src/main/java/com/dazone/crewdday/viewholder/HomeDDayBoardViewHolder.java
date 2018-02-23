package com.dazone.crewdday.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday.mInterface.MenuItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeDDayBoardViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
    public CircleImageView ivProfile;
    public ImageView ivTagIcon, img_check,img_1;
    public TextView tvSection;
    public TextView tvContent;
    public TextView tvName;
    public TextView tvDdayType;
    public TextView tvDate;
    public ProgressBar progressBar;
    public Button btn_try_again;

    int viewType;

    private MenuItemClickListener clickListener;

    public HomeDDayBoardViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        if (viewType == Cons.LIST_ITEMS) {
            img_check= (ImageView) itemView.findViewById(R.id.img_check);
            img_1 = (ImageView) itemView.findViewById(R.id.img_1);
            ivProfile = (CircleImageView) itemView.findViewById(R.id.img_profile);
            ivTagIcon = (ImageView) itemView.findViewById(R.id.img_tag_icon);
            tvName = (TextView) itemView.findViewById(R.id.txt_user_name);
            tvDdayType = (TextView) itemView.findViewById(R.id.txt_dday);
            tvDate = (TextView) itemView.findViewById(R.id.txt_date);
            tvContent = (TextView) itemView.findViewById(R.id.txt_dday_title);
        } else if (viewType == Cons.PROGRESS_BAR) {
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        } else {
            btn_try_again = (Button) itemView.findViewById(R.id.btn_try_again);
        }

        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setClickListener(MenuItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public int getViewType() {
        return viewType;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClick(getAdapterPosition(), false);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        clickListener.onClick(getAdapterPosition(), true);
        return true;
    }
}

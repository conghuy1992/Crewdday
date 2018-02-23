package com.dazone.crewdday.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.DetailViewActivity;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;
import com.dazone.crewdday.mInterface.DeleteCompletedRecordSuccess;
import com.dazone.crewdday.mInterface.DeleteCoveredDaySuccess;
import com.dazone.crewdday.mInterface.DeleteSuccess;
import com.dazone.crewdday.mInterface.InsertCoveredDaySuccess;
import com.dazone.crewdday.mInterface.MakeComplete;
import com.dazone.crewdday.mInterface.MenuItemClickListener;
import com.dazone.crewdday.mInterface.OnLoadMoreListener;
import com.dazone.crewdday.mInterface.UpdateGroupCalllback;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.model.GroupModel;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.viewholder.HomeDDayBoardViewHolder;

import java.util.ArrayList;

public class HomeDDayBoardAdapter extends RecyclerView.Adapter<HomeDDayBoardViewHolder> {
    String TAG = "HomeDDayBoardAdapter";
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;
    boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private View.OnClickListener mOnClickListenerForTryAgain;

    public ArrayList<DDayModel> list;
    private Context context;
    PreferenceUtilities mPref;
    int directorNo = 0;
    int temp;

    private String mCompanyDomain;

    private int mColor_COLOR_DAY_PLUS, mColor_COLOR_CAN_HIDDEN, mColor_COLOR_RED, mColor_COLOR_DDAY;

    public void update_new_data(DDayDetailModel index){
        int n  = list.size();
        for(int i=0;i<n;i++){
            DDayModel obj = list.get(i);
            if(obj.getDdayId()==index.getDdayId()){
                obj.setCanHide(index.isCanHide());
                obj.setCanManage(index.isCanManage());
                this.notifyItemChanged(i);
                break;
            }
        }
    }

    public HomeDDayBoardAdapter(Context context, final ArrayList<DDayModel> list, RecyclerView recyclerView, int temp) {
        this.list = list;
        this.context = context;
        mPref = _Application.getInstance().getPreferenceUtilities();

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }

                    loading = true;
                }
            }
        });

        this.temp = temp;

        mCompanyDomain = new PreferenceUtilities().getCurrentCompanyDomain();

        mColor_COLOR_DAY_PLUS = Color.parseColor(Cons.COLOR_DAY_PLUS);
        mColor_COLOR_CAN_HIDDEN = Color.parseColor(Cons.COLOR_CAN_HIDDEN);
        mColor_COLOR_RED = Color.parseColor(Cons.COLOR_RED);
        mColor_COLOR_DDAY = Color.parseColor(Cons.COLOR_DDAY);
    }

    @Override
    public HomeDDayBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        HomeDDayBoardViewHolder holder;

        if (viewType == Cons.LIST_ITEMS) {
            v = LayoutInflater.from(context).inflate(R.layout.home_dday_board_items, parent, false);
            holder = new HomeDDayBoardViewHolder(v, Cons.LIST_ITEMS);
        } else if (viewType == Cons.PROGRESS_BAR) {
            v = LayoutInflater.from(context).inflate(R.layout.progressbar_load_list, parent, false);
            holder = new HomeDDayBoardViewHolder(v, Cons.PROGRESS_BAR);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.list_more_try_again, parent, false);
            holder = new HomeDDayBoardViewHolder(v, Cons.TRY_AGAIN);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeDDayBoardViewHolder holder, final int position) {
        final DDayModel dDayModel = list.get(position);

        if (dDayModel != null) {
            directorNo = dDayModel.getDirectorNo();
        }

        if (holder.getViewType() == Cons.LIST_ITEMS) {
            int remainingDays = dDayModel.getRemainingDays();
            String title = dDayModel.getTitle();
            holder.tvContent.setText(title);

            if (dDayModel.isNew()) {
                holder.tvContent.setTypeface(null, Typeface.BOLD);
            } else {
                holder.tvContent.setTypeface(null, Typeface.NORMAL);
            }

            if (dDayModel.isHidden() || dDayModel.isCompleted()) {
                holder.img_check.setVisibility(View.VISIBLE);
            } else {
                holder.img_check.setVisibility(View.GONE);
            }

            if (remainingDays > 0) {
                holder.tvDdayType.setText("D+" + remainingDays);

                if (temp == 2) {
                    holder.tvDdayType.setTextColor(mColor_COLOR_DAY_PLUS);
                    if (remainingDays == 1) {
                        holder.tvDdayType.setText(context.getResources().getString(R.string.startPlus));
                    }
                } else {
                    boolean canHidden = dDayModel.isCanHide();

                    if (canHidden) {
                        holder.tvDdayType.setTextColor(mColor_COLOR_CAN_HIDDEN);
                    } else {
                        holder.tvDdayType.setTextColor(mColor_COLOR_RED);
                    }
                }
            } else if (remainingDays < 0) {
                holder.tvDdayType.setText("D" + remainingDays);
                holder.tvDdayType.setTextColor(mColor_COLOR_DAY_PLUS);
            } else {
                holder.tvDdayType.setText("D-Day");
                holder.tvDdayType.setTextColor(mColor_COLOR_DDAY);
            }

            holder.tvDate.setText(dDayModel.getDdayDate());

            if (directorNo == 0) {
                holder.img_1.setVisibility(View.GONE);
            } else {
                holder.img_1.setVisibility(View.VISIBLE);
                holder.tvName.setText(dDayModel.getDirectorName());

                Glide.with(context)
                        .load("http://" + mCompanyDomain + dDayModel.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new GlideDrawableImageViewTarget(holder.img_1) {
                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                Glide.with(context).load(mCompanyDomain + "/Images/avatar.jpg").into(holder.img_1);
                            }
                        });
            }

            //int id = IconUtils.getFlagDrawableId(dDayModel.getTagId());
            //holder.ivTagIcon.setImageDrawable(ImageUtils.getDrawable(context, id));

            holder.setClickListener(new MenuItemClickListener() {
                @Override
                public void onClick(int itemPosition, boolean isLongClick) {
                    if (!isLongClick) {
                        dDayModel.setIsNew(false);
                        notifyItemChanged(itemPosition);
//                        _edit(dDayModel);
                        if (HomeActivity.instance != null) HomeActivity.instance._edit(dDayModel);
                    } else {
                        select_options(dDayModel);
                    }
                }
            });
        } else if (holder.getViewType() == Cons.PROGRESS_BAR) {
            holder.progressBar.setIndeterminate(true);
        } else {
            holder.btn_try_again.setOnClickListener(mOnClickListenerForTryAgain);
        }
    }

//    public void _edit(DDayModel dDayModel) {
//        Intent intent = new Intent(context, DetailViewActivity.class);
//        intent.putExtra(Cons.KEY_DDAY_ID, dDayModel.getDdayId());
//        intent.putExtra(Cons.KEY_DDAY_REMAINING_DAYS, dDayModel.getRemainingDays());
//        intent.putExtra(Cons.KEY_DDAY_DATE, dDayModel.getDdayDate());
//        intent.putExtra(Cons.KEY_DDAY_DIRECTOR_NAME, dDayModel.getDirectorName());
//        intent.putExtra(Cons.KEY_DDAY_GROUP_ID, dDayModel.getGroupId());
//        intent.putExtra(Cons.KEY_CAN_HIDE, dDayModel.isCanHide());
//        intent.putExtra(Cons.KEY_IS_COMPLETED, dDayModel.isCompleted());
//        intent.putExtra(Cons.KEY_IS_COMPLETED_RECORD, dDayModel.getCompletedRecordNo());
//        intent.putExtra(Cons.KEY_IS_HIDDEN, dDayModel.isHidden());
//        intent.putExtra(Cons.KEY_IS_HIDDEN_NO, dDayModel.getHiddenDataNo());
//        intent.putExtra(Cons.KEY_MANAGER, dDayModel.isCanManage());
//        intent.putExtra(Cons.KEY_IS_NEW, dDayModel.isNew());
//
//        context.startActivity(intent);
//    }

    public void _insertCompletedRecord(final DDayModel dDayModel, final Dialog dia, final int pos) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setTitle(context.getString(R.string.Dday));
        adb.setMessage(context.getString(R.string.movehid));
        adb.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConnectionUtils.getInstance().insertCompletedRecord(dDayModel.getDdayId(), dDayModel.getDdayDate(), "content", new MakeComplete() {
                    @Override
                    public void onSuccessMakeComplete(long dataNo) {
                        dDayModel.setCompletedRecordNo(dataNo);

                        if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                            list.remove(dDayModel);
                            notifyItemRemoved(pos);
                        } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                            dDayModel.setIsCompleted(true);
                            notifyItemChanged(pos);
                        }

                        dia.dismiss();
                    }
                });
            }
        });

        adb.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.create().show();
    }

    public void _insertCoveredDay(final DDayModel dDayModel, final Dialog dia, final int pos) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle(context.getString(R.string.Dday));
        adb.setMessage(context.getString(R.string.movehid));

        adb.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConnectionUtils.getInstance().InsertCoveredDay(dDayModel.getDdayId(), dDayModel.getDdayDate(), new InsertCoveredDaySuccess() {
                    @Override
                    public void onInsertCoveredDaySuccess(long dataNo) {
                        dDayModel.setHiddenDataNo(dataNo);

                        if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                            list.remove(dDayModel);
                            notifyItemRemoved(pos);
                        } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                            dDayModel.setIsHidden(true);
                            notifyItemChanged(pos);
                        }

                        dia.dismiss();
                    }
                });
            }
        });

        adb.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.create().show();
    }

    public void _deleteDay(final DDayModel dDayModel, final Dialog d) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle(context.getString(R.string.Dday));
        adb.setMessage(context.getString(R.string.sure_delete));
        adb.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConnectionUtils.getInstance().delete_v2(dDayModel.getDdayId(), new DeleteSuccess() {
                    @Override
                    public void onDeleteSuccess() {
                        ArrayList<DDayModel> listOfModels = new ArrayList<>();
                        long ddayNo = dDayModel.getDdayId();
                        for (DDayModel model : list) {
                            if (model.getDdayId() == ddayNo)
                                listOfModels.add(model);
                        }
                        if (listOfModels.size() == list.size()) {
                            if (HomeActivity.currentFragmentPos == 0) {
                                FragmentHomeAll.instance.refreshList();
                            } else if (HomeActivity.currentFragmentPos == 1) {
                                FragmentHomePlus.instance.refreshList();
                            }
                        } else if (list.size() - listOfModels.size() < 30) {
                            int index;
                            for (DDayModel model : listOfModels) {
                                index = list.indexOf(model);
                                list.remove(model);

                                notifyItemRemoved(index);
                            }

                            if (HomeActivity.currentFragmentPos == 0) {
                                FragmentHomeAll.instance.showMoreProgressBar(false);
                            }
                        } else {
                            int index;
                            for (DDayModel model : listOfModels) {
                                index = list.indexOf(model);
                                list.remove(model);

                                notifyItemRemoved(index);
                            }
                        }

                        d.dismiss();
                    }
                });
            }
        });

        adb.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.create().show();
    }

    public void select_options(final DDayModel dDayModel) {
        boolean canHide = dDayModel.isCanHide();
        boolean canManage = dDayModel.isCanManage();
        boolean isHidden = dDayModel.isHidden();
        boolean isCompleted = dDayModel.isCompleted();

        ArrayList<String> sp_Name = new ArrayList<>();
        if (canManage) {
            if (isHidden || isCompleted) {
                if (isHidden) {
                    sp_Name.add(context.getResources().getString(R.string.unhide));
                } else {
                    sp_Name.add(context.getResources().getString(R.string.uncomp));
                }
            } else {
                if (canHide) {
                    sp_Name.add(context.getResources().getString(R.string.hidec));
                } else {
                    if (HomeActivity.currentFragmentPos != 1) {
                        sp_Name.add(context.getResources().getString(R.string.make_complete));
                    }
                }
            }
            sp_Name.add(context.getResources().getString(R.string.editc));
            sp_Name.add(context.getResources().getString(R.string.delete));
            sp_Name.add(String.format("%s (%s)", context.getResources().getString(R.string.changegroup), getGroupName(dDayModel.getGroupId())));
            display_dialog(dDayModel, sp_Name);
        } else {
            if (!canHide) {
                sp_Name.add(String.format("%s (%s)", context.getResources().getString(R.string.changegroup), getGroupName(dDayModel.getGroupId())));
                display_dialog(dDayModel, sp_Name);
            } else {
                if (!isHidden)
                    sp_Name.add(context.getResources().getString(R.string.hidec));
                else
                    sp_Name.add(context.getResources().getString(R.string.unhide));
                sp_Name.add(String.format("%s (%s)", context.getResources().getString(R.string.changegroup), getGroupName(dDayModel.getGroupId())));
                display_dialog(dDayModel, sp_Name);
            }
        }
    }

    public void display_dialog(final DDayModel dDayModel, final ArrayList<String> sp_Name) {
        final int pos = list.indexOf(dDayModel);
        final Dialog dialog;

        if (Build.VERSION.SDK_INT >= 21) {
            dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new Dialog(context);
        }

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(dDayModel.getTitle());
        dialog.setContentView(R.layout.dialog_group);

        ListView list_gr = (ListView) dialog.findViewById(R.id.list_gr);
        ArrayAdapter adapter = new ArrayAdapter<>(context, R.layout.custom_tv_dialog_gr, sp_Name);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        list_gr.setAdapter(adapter);
        list_gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sp_Name.get(position).equals(context.getResources().getString(R.string.hidec))) {
                    boolean canManage = dDayModel.isCanManage();

                    if (canManage) {
                        _insertCompletedRecord(dDayModel, dialog, pos);
                    } else {
                        _insertCoveredDay(dDayModel, dialog, pos);
                    }
                } else if (dDayModel.isHidden() && sp_Name.get(position).equals(context.getResources().getString(R.string.unhide))) {
                    ConnectionUtils.getInstance().DeleteCoveredDay(dDayModel.getHiddenDataNo(), new DeleteCoveredDaySuccess() {
                        @Override
                        public void onDeleteCoveredDaySuccess() {
                            if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                                list.remove(dDayModel);
                                notifyItemRemoved(pos);
                            } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                                dDayModel.setIsHidden(false);
                                notifyItemChanged(pos);
                            }

                            dialog.dismiss();
                        }
                    });
                } else if (sp_Name.get(position).equals(context.getResources().getString(R.string.make_complete))) {
                    showDialog(dDayModel, dialog, pos);
                } else if (dDayModel.isCompleted() && sp_Name.get(position).equals(context.getResources().getString(R.string.uncomp))) {
                    ConnectionUtils.getInstance().DeleteCompletedRecord(dDayModel.getCompletedRecordNo(), new DeleteCompletedRecordSuccess() {
                        @Override
                        public void onDeleteCompletedRecordSuccess() {
                            if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                                list.remove(dDayModel);
                                notifyItemRemoved(pos);
                            } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                                dDayModel.setIsCompleted(false);
                                notifyItemChanged(pos);
                            }

                            dialog.dismiss();
                        }
                    });
                } else if (sp_Name.get(position).equals(context.getResources().getString(R.string.editc))) {
//                    _edit(dDayModel);
                    if (HomeActivity.instance != null) HomeActivity.instance._edit(dDayModel);
                    dialog.dismiss();
                } else if (sp_Name.get(position).equals(context.getResources().getString(R.string.delete))) {
                    _deleteDay(dDayModel, dialog);
                } else if (sp_Name.get(position).equals(String.format("%s (%s)", context.getResources().getString(R.string.changegroup), getGroupName(dDayModel.getGroupId())))) {
                    dialogChangeGroup(dDayModel, dialog);
                }
            }
        });
        dialog.show();
    }

    public void dialogChangeGroup(final DDayModel dDayModel, final Dialog dia) {
        final Dialog dialog;

        if (Build.VERSION.SDK_INT >= 21) {
            dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new Dialog(context);
        }

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(String.format("%s (%s)", context.getResources().getString(R.string.changegroup), getGroupName(dDayModel.getGroupId())));
        dialog.setContentView(R.layout.layout_change_group);

        ListView list_view = (ListView) dialog.findViewById(R.id.list);
        GroupAdapter_v2 adapter = new GroupAdapter_v2(context, HomeActivity.groupModelsHome);

        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long groupNo = HomeActivity.groupModelsHome.get(position).getGroupNo();
                ConnectionUtils.getInstance().UpdateGroupNoOfDay(dDayModel.getDdayId(), groupNo, new UpdateGroupCalllback() {
                    @Override
                    public void onUpdateSuccess() {
                        if (dDayModel.isNew()) {
                            dDayModel.setIsNew(false);
                            HomeActivity.instance.refreshCountOfUnreadDays();
                        }

                        int pos = list.indexOf(dDayModel);
                        dDayModel.setGroupId(groupNo);

                        int selectedGroupPosition = Integer.parseInt(mPref.getSelectedGroup());

                        if (selectedGroupPosition == 1 || HomeActivity.groupModelsHome.get(selectedGroupPosition - 2).getGroupNo() == groupNo) {
                            notifyItemChanged(pos);
                        } else {
                            ArrayList<DDayModel> listOfModels = new ArrayList<>();

                            long ddayNo = dDayModel.getDdayId();

                            for (DDayModel model : list) {
                                if (model.getDdayId() == ddayNo)
                                    listOfModels.add(model);
                            }

                            if (listOfModels.size() == list.size()) {
                                if (HomeActivity.currentFragmentPos == 0) {
                                    FragmentHomeAll.instance.refreshList();
                                } else if (HomeActivity.currentFragmentPos == 1) {
                                    FragmentHomePlus.instance.refreshList();
                                }
                            } else if (list.size() - listOfModels.size() < 30) {
                                int index;
                                for (DDayModel model : listOfModels) {
                                    index = list.indexOf(model);
                                    list.remove(model);

                                    notifyItemRemoved(index);
                                }

                                if (HomeActivity.currentFragmentPos == 0) {
                                    FragmentHomeAll.instance.showMoreProgressBar(false);
                                }
                            } else {
                                int index;
                                for (DDayModel model : listOfModels) {
                                    index = list.indexOf(model);
                                    list.remove(model);

                                    notifyItemRemoved(index);
                                }
                            }
                        }

                        dialog.dismiss();
                        dia.dismiss();
                    }

                    @Override
                    public void onUpdateFail() {
                        Toast.makeText(context, "Change Group Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void showDialog(final DDayModel dDayModel, final Dialog dia, final int pos) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_make_completion);

        final TextView tvCancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        final TextView tvConfirm = (TextView) dialog.findViewById(R.id.txt_confirm);
        final EditText edtComment = (EditText) dialog.findViewById(R.id.edt_comment);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtComment.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    edtComment.setError("Please type something");
                } else {
                    ConnectionUtils.getInstance().insertCompletedRecord(dDayModel.getDdayId(), dDayModel.getDdayDate(), content, new MakeComplete() {
                        @Override
                        public void onSuccessMakeComplete(long dataNo) {
                            dDayModel.setCompletedRecordNo(dataNo);

                            if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                                list.remove(dDayModel);
                                notifyItemRemoved(pos);
                            } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                                dDayModel.setIsCompleted(true);
                                notifyItemChanged(pos);
                            }

                            dialog.dismiss();
                            dia.dismiss();
                        }
                    });
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        DDayModel dDayModel = list.get(position);

        if (dDayModel != null) {
            return dDayModel.getType();
        }

        return Cons.PROGRESS_BAR;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnClickListenerForTryAgain(View.OnClickListener clickListener) {
        mOnClickListenerForTryAgain = clickListener;
    }

    public void setLoaded() {
        loading = false;
    }

    private String getGroupName(long groupNo) {
        for (GroupModel group : HomeActivity.groupModelsHome) {
            if (group.getGroupNo() == groupNo) {
                return group.getName();
            }
        }

        return "";
    }
}
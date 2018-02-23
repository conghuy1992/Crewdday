package com.dazone.crewdday.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;
import com.dazone.crewdday.mInterface.MenuCallback;
import com.dazone.crewdday.mInterface.MenuItemClickListener;
import com.dazone.crewdday.mInterface.deleteGroupCallBack;
import com.dazone.crewdday.mInterface.insertGroupCallBack;
import com.dazone.crewdday.mInterface.updateGroupNameCallBack;
import com.dazone.crewdday.mInterface.updateGroupTagCallBack;
import com.dazone.crewdday.model.GroupModel;
import com.dazone.crewdday.model.MenuItemModel;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.IconUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.viewholder.HomeMenuViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuViewHolder> {
    String TAG = "HomeMenuAdapter";
    List<MenuItemModel> list = Collections.emptyList();
    Context context;
    String[] tagIconArray;
    MenuCallback menuCallback;
    PreferenceUtilities mPref;
    String[] tagBackgroundColors;
    String[] tagBorderColors;

    public HomeMenuAdapter(Context context, List<MenuItemModel> list, MenuCallback callback) {
        this.list = list;
        this.context = context;
        this.menuCallback = callback;
        this.mPref = _Application.getInstance().getPreferenceUtilities();
        this.tagIconArray = context.getResources().getStringArray(R.array.tag_icon_array);
        this.tagBorderColors = context.getResources().getStringArray(R.array.tag_border_color);
        this.tagBackgroundColors = context.getResources().getStringArray(R.array.tag_background_color);
    }

    @Override
    public HomeMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = null;
        HomeMenuViewHolder holder = null;
        if (viewType == Cons.MENU_TYPE_NORMAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_menu_item_normal, parent, false);
            holder = new HomeMenuViewHolder(v, Cons.MENU_TYPE_NORMAL);
        } else if (viewType == Cons.MENU_TYPE_SUB) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_menu_item_normal, parent, false);
            holder = new HomeMenuViewHolder(v, Cons.MENU_TYPE_SUB);
        } else if (viewType == Cons.MENU_TYPE_OTHER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_menu_item_has_checkbox, parent, false);
            holder = new HomeMenuViewHolder(v, Cons.MENU_TYPE_OTHER);
        }

        holder.setMenuCallback(menuCallback);
        holder.setTagBorderColors(tagBorderColors);
        holder.setTagBackgroundColors(tagBackgroundColors);

        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeMenuViewHolder holder, final int position) {

        final MenuItemModel menuItemModel = list.get(position);

        if (holder.getViewType() == Cons.MENU_TYPE_NORMAL || holder.getViewType() == Cons.MENU_TYPE_SUB) {
            holder.text.setText(menuItemModel.getText());

            if (position == 0) {
                holder.tv_count.setVisibility(View.GONE);
            }

            if (menuItemModel.getGroupId() == -2) {
                holder.imageView.setImageResource(R.drawable.mnuleft_ic_new);
                holder.tv_count.setTextColor(Color.RED);
                holder.tv_count.setText(String.valueOf(menuItemModel.getCountOfDays()));
            } else {
                holder.tv_count.setTextColor(Color.parseColor("#4391dd"));
                holder.imageView.setImageResource(menuItemModel.getImageId());
                holder.tv_count.setText("");
            }

            if (HomeActivity.isManagerTool) {
                if (position == list.size() - 1) {
                    holder.imageView.setVisibility(View.INVISIBLE);
                } else {
                    holder.imageView.setVisibility(View.VISIBLE);
                }
            }
            if (position == Integer.parseInt(mPref.getSelectedGroup())) {
                holder.backgroundContainer.setBackgroundColor(Color.parseColor("#ededed"));
            } else {
                holder.backgroundContainer.setBackgroundColor(Color.parseColor("#ffffff"));
            }

//            if (position > 1) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position > 2) {
                        if (list.get(position).getGroupId() != -2 && list.get(position).getGroupId() != 0)
                            showChangeColorDialog(position);
                    }
                }
            });
//            }

        } else if (holder.getViewType() == Cons.MENU_TYPE_OTHER) {
            holder.text.setText(menuItemModel.getText());
            //holder.checkBox.setChecked(menuItemModel.isChecked());
        }


        holder.setClickListener(new MenuItemClickListener() {
            @Override
            public void onClick(int itemPosition, boolean isLongClick) {
                if (!isLongClick) {
                    //Set click for "Add Group" button
                    if (itemPosition == 0) {
                        //Create group
                        showDialog(false, 0);
                    }
                    //Set click for group items
                    else {
                        if (holder.getViewType() == Cons.MENU_TYPE_SUB) {
                            if (HomeActivity.isManagerTool) {
                                if (itemPosition == list.size() - 1) {
                                    HomeActivity.instance.startOrganizationActivity();
                                } else {
                                    setClick(holder, itemPosition);
                                }
                            } else {
                                setClick(holder, itemPosition);
                            }
                        }
                    }
                }
                //Set long click for group items
                else {
                    long groupNo = list.get(itemPosition).getGroupId();
                    Log.d(TAG, "groupNo:" + groupNo);
                    if (groupNo != -1000 && groupNo != -1 && groupNo != -2 && groupNo != 0)
                        setLongClick(holder, itemPosition);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        MenuItemModel menuItem = list.get(position);
        if (menuItem != null)
            return menuItem.getType();

        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setClickAfterBadge(int position) {
        if (!String.valueOf(position).equals(mPref.getSelectedGroup())) {
            if (HomeActivity.currentFragmentPos == 0) {
                if (FragmentHomeAll.instance != null)
                    FragmentHomeAll.instance.startLoading();
            } else {
                if (FragmentHomePlus.instance != null)
                    FragmentHomePlus.instance.startLoad();
            }
        }
        resetSectionTitle();
        MenuItemModel menuItemModel_1 = list.get(position);
        int id = (int) menuItemModel_1.getGroupId();
        if (id == -1) {
            HomeActivity.group_id = 0;
            HomeActivity.group_name = HomeActivity.name_gr;
        } else if (id == -2) {
            HomeActivity.group_id = 0;
            HomeActivity.group_name = HomeActivity.newgr;
        } else {
            HomeActivity.group_id = id;
            HomeActivity.group_name = menuItemModel_1.getText();
        }
        mPref.setSelectedGroup(String.valueOf(position));
        notifyDataSetChanged();
        String groupId = String.valueOf(id);
        HomeActivity.GroupId = groupId;
        String text = menuItemModel_1.getText();
        menuCallback.refreshFragments(text, groupId);
    }

    public void setClick(HomeMenuViewHolder holder, int position) {
        if (!String.valueOf(position).equals(mPref.getSelectedGroup())) {
            if (HomeActivity.currentFragmentPos == 0) {
                if (FragmentHomeAll.instance != null)
                    FragmentHomeAll.instance.startLoading();
            } else {
                if (FragmentHomePlus.instance != null)
                    FragmentHomePlus.instance.startLoad();
            }
        }

        resetSectionTitle();

        MenuItemModel menuItemModel_1 = list.get(position);
        int id = (int) menuItemModel_1.getGroupId();

        if (id == -1) {
            HomeActivity.group_id = 0;
            HomeActivity.group_name = HomeActivity.name_gr;
        } else if (id == -2) {
            HomeActivity.group_id = 0;
            HomeActivity.group_name = HomeActivity.newgr;
        } else {
            HomeActivity.group_id = id;
            HomeActivity.group_name = menuItemModel_1.getText();
        }

        mPref.setSelectedGroup(String.valueOf(position));
        notifyDataSetChanged();

        String groupId = String.valueOf(id);
        HomeActivity.GroupId = groupId;
        String text = menuItemModel_1.getText();
        menuCallback.refreshFragments(text, groupId);
    }

    public void show_notify(String str, final int position, final Dialog dia) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle(context.getString(R.string.Dday));
        adb.setMessage(str);
        adb.setPositiveButton(context.getString(R.string.del_group_y), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuItemModel menuItemModel = list.get(position);
                remove(menuItemModel);
                ConnectionUtils.getInstance().deleteGroup(menuItemModel, new deleteGroupCallBack() {
                    @Override
                    public void onSuccess() {
                        dia.dismiss();
                        HomeActivity.instance.getNewgroupModelsHome();
                    }
                });
//                try {
//                    ConnectionUtils.getInstance().deleteGroup(menuItemModel);
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
        adb.setNegativeButton(context.getString(R.string.del_group_n), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.create().show();
    }

    private void setLongClick(HomeMenuViewHolder holder, final int position) {
        if (position > 2) {
            if (holder.getViewType() == Cons.MENU_TYPE_SUB) {
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                final Dialog dialog;
                if (Build.VERSION.SDK_INT >= 21) {
                    dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    dialog = new Dialog(context);
                }
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.setTitle(list.get(position).getText());
                dialog.setContentView(R.layout.dialog_group);
                ListView list_gr = (ListView) dialog.findViewById(R.id.list_gr);
                ArrayList<String> sp_Name = new ArrayList<String>();
                sp_Name.add(context.getString(R.string.edit));
                sp_Name.add(context.getString(R.string.delete));

                ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.custom_tv_dialog_gr, sp_Name);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                list_gr.setAdapter(adapter);
                list_gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        if (pos == 0) {
                            showDialog(true, position);
                            dialog.dismiss();
                        } else if (pos == 1) {
                            show_notify(context.getString(R.string.del_group_q), position, dialog);
                        }
                    }
                });

                dialog.show();

            }
        }
    }

    private void showDialog(final boolean isEdit, final int position) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_group);

        final TextView tvCancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        final TextView tvConfirm = (TextView) dialog.findViewById(R.id.txt_confirm);
        final EditText edtGroupName = (EditText) dialog.findViewById(R.id.edt_groupname);
        final Spinner spinTagIcon = (Spinner) dialog.findViewById(R.id.spin_tag_icon);


        MenuIconSpinnerAdapter adapter = new MenuIconSpinnerAdapter(context, tagIconArray);
        spinTagIcon.setAdapter(adapter);

        if (isEdit) {
            edtGroupName.setText(list.get(position).getText());
            edtGroupName.setSelection(edtGroupName.getText().length());
            spinTagIcon.setSelection(list.get(position).getTagNo());
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nameChange = edtGroupName.getText().toString().trim();
                    if (nameChange.equals("Undefined") || nameChange.equalsIgnoreCase("Undefined")) {
                        Toast.makeText(context, "Please input other name", Toast.LENGTH_SHORT).show();
                    } else {
                        if (TextUtils.isEmpty(nameChange)) {
//                            edtGroupName.setError("Please type group name");
                            Toast toast = Toast.makeText(context, context.getResources().getString(R.string.typeip), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            int tagNo = spinTagIcon.getSelectedItemPosition();

                            //Update group on phone
                            MenuItemModel menuItemModel = list.get(position);
                            menuItemModel.setText(nameChange);
                            menuItemModel.setTagNo(tagNo);
                            menuItemModel.setImageId(IconUtils.getFlagDrawableId(tagNo));
                            //Update group on web
                            ConnectionUtils.getInstance().updateGroupTag(menuItemModel, new updateGroupTagCallBack() {
                                @Override
                                public void onSuccess() {

                                }
                            });
                            ConnectionUtils.getInstance().updateGroupName(menuItemModel, new updateGroupNameCallBack() {
                                @Override
                                public void onSuccess() {
                                    HomeActivity.instance.getNewgroupModelsHome();
                                }
                            });
//                            try {
//                                ConnectionUtils.getInstance().updateGroupTag(menuItemModel);
//                                ConnectionUtils.getInstance().updateGroupName(menuItemModel);
//                            } catch (XmlPullParserException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                }
            });

        } else {
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String groupName = edtGroupName.getText().toString().trim();

                    if (groupName.equals("Undefined") || groupName.equalsIgnoreCase("Undefined")) {
                        Toast.makeText(context, "Please input other name", Toast.LENGTH_SHORT).show();
                    } else {
                        if (TextUtils.isEmpty(groupName)) {
                            Toast toast = Toast.makeText(context, context.getResources().getString(R.string.typeip), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            int tagNo = spinTagIcon.getSelectedItemPosition();

                            //Create group on phone
                            final MenuItemModel menuItemModel = new MenuItemModel();
                            menuItemModel.setImageId(IconUtils.getFlagDrawableId(tagNo));
                            menuItemModel.setText(groupName);
                            menuItemModel.setType(Cons.MENU_TYPE_SUB);
                            menuItemModel.setTagNo(tagNo);

                            ConnectionUtils.getInstance().insertGroup(menuItemModel, new insertGroupCallBack() {
                                @Override
                                public void onSuccess(long groupNo) {
                                    HomeActivity.instance.getNewgroupModelsHome();

                                    menuItemModel.setGroupId(groupNo);

                                    if (HomeActivity.isManagerTool) {
                                        insert(list.size() - 1, menuItemModel);
                                    } else {
                                        insert(list.size(), menuItemModel);
                                    }
                                }
                            });

                            dialog.dismiss();
//                            try {
//                                ConnectionUtils.getInstance().insertGroup(menuItemModel);
//                            } catch (XmlPullParserException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                }
            });

        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    public void removeNewGroup() {
        for (MenuItemModel model : list) {
            if (model.getGroupId() == -2) {
                remove(model);
                notifyDataSetChanged();

                break;
            }
        }
    }

    public void update(int CountOfDays) {
        for (MenuItemModel model : list) {
            if (model.getGroupId() == -2) {
                model.setCountOfDays(CountOfDays);
                notifyDataSetChanged();

                break;
            }
        }
    }

    public void insert(int position, GroupModel groupModel) {
        int imageId = IconUtils.getFlagDrawableId(groupModel.getTagNo());
        int type = Cons.MENU_TYPE_SUB;
        int tagNo = groupModel.getTagNo();
        String name = groupModel.getName();
        int countOfDays = groupModel.getCountOfDays();

        MenuItemModel menuItem = new MenuItemModel(name, imageId, type, countOfDays);
        menuItem.setGroupId(groupModel.getGroupNo());
        menuItem.setTagNo(tagNo);
        list.add(position, menuItem);
        notifyItemInserted(position);
    }

    public void insert(int position, MenuItemModel menuItem) {
        list.add(position, menuItem);
        notifyItemInserted(position);
    }

    public void remove(MenuItemModel menuItem) {
        int position = list.indexOf(menuItem);
        list.remove(position);
        notifyItemRemoved(position);
    }

    private void showChangeColorDialog(int position) {
        final Dialog gridViewDialog = new Dialog(context);
        gridViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gridViewDialog.setContentView(R.layout.dialog_change_color);
        GridView gridView = ((GridView) gridViewDialog.findViewById(R.id.grid_change_color));
        ChangeTagIconAdapter adapter = new ChangeTagIconAdapter(context, tagBackgroundColors, tagBorderColors);
        gridView.setAdapter(adapter);
        gridViewDialog.show();


        final MenuItemModel menuItemModel = list.get(position);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                menuCallback.refresh(menuItemModel, position);
                menuItemModel.setTagNo(position);
                menuItemModel.setImageId(IconUtils.getFlagDrawableId(position));
                ConnectionUtils.getInstance().updateGroupTag(menuItemModel, new updateGroupTagCallBack() {
                    @Override
                    public void onSuccess() {
                        HomeActivity.instance.getNewgroupModelsHome();
                    }
                });
//                try {
//                    ConnectionUtils.getInstance().updateGroupTag(menuItemModel);
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                notifyDataSetChanged();
                gridViewDialog.dismiss();
            }
        });
    }

    private void resetSectionTitle() {
        mPref.setSectionFragmentAll("");
        mPref.setSectionFragmentMinus("");
        mPref.setSectionFragmentComplete("");
        mPref.setSectionFragmentPlus("");
    }
}
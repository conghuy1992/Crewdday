package com.dazone.crewdday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.NewOrganizationChart;
import com.dazone.crewdday.other.PersonData;
import com.dazone.crewdday.other.Statics;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by maidinh on 5/19/2017.
 */

public class AdapterOrganizationChart extends RecyclerView.Adapter<AdapterOrganizationChart.MyViewHolder> {
    private String TAG = "OrganizationChart";
    private List<PersonData> list = new ArrayList<>();
    private List<PersonData> listTemp = new ArrayList<>();
    private List<PersonData> listTemp_2 = new ArrayList<>();

    private ArrayList<PersonData> selectedPersonList = new ArrayList<>();
    private int isSearch = 0; // 0 -> normal : 1 -> search
    private Context context;
    private int BUNDLE_TYPE;

    private int myId;
    private NewOrganizationChart instance;

    private int mg = 0;

    public List<PersonData> getCurrentList() {
        return list;
    }

    public void updateIsSearch(int a) {
        isSearch = a;
    }

//    boolean isAddSearch(List<PersonData> lst, int id) {
//        for (PersonData obj : lst) {
//            if (obj.getUserNo() == id)
//                return false;
//        }
//        return true;
//    }


//    public void actionSearch(String key) {
//        List<PersonData> lst = new ArrayList<>();
//        for (PersonData obj : listTemp_3) {
//            if (obj.getType() == 2) {
//                if ((obj.getFullName().toUpperCase().contains(key.toUpperCase()))
//                        || (obj.getPhoneNumber() != null && obj.getPhoneNumber().toUpperCase().contains(key.toUpperCase()))
//                        || (obj.getPhoneNumber() != null && obj.getPhoneNumber().toUpperCase().replace("-", "").contains(key.toUpperCase()))
//                        || (obj.getCompanyNumber() != null && obj.getCompanyNumber().toUpperCase().contains(key.toUpperCase()))
//                        || (obj.getCompanyNumber() != null && obj.getCompanyNumber().toUpperCase().replace("-", "").contains(key.toUpperCase()))) {
//                    if (isAddSearch(lst, obj.getUserNo())) {
//                        lst.add(obj);
//                    }
//                }
//            }
//        }
//        this.list = lst;
//        this.notifyDataSetChanged();
//    }

    public void updateListSearch(List<PersonData> lst) {
        Log.d(TAG, "updateListSearch");
        this.list = lst;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout iconWrapper, item_org_wrapper;
        public ImageView avatar;
        public ImageView folderIcon;
        public TextView name, position;
        public CheckBox row_check;
        public RelativeLayout relAvatar;

        public MyViewHolder(View view) {
            super(view);
            item_org_wrapper = (LinearLayout) view.findViewById(R.id.item_org_wrapper);

            avatar = (ImageView) view.findViewById(R.id.avatar);
            folderIcon = (ImageView) view.findViewById(R.id.ic_folder);
            relAvatar = (RelativeLayout) view.findViewById(R.id.relAvatar);
            iconWrapper = (LinearLayout) view.findViewById(R.id.icon_wrapper);

            name = (TextView) view.findViewById(R.id.name);
            position = (TextView) view.findViewById(R.id.position);
            row_check = (CheckBox) view.findViewById(R.id.row_check);
        }

        public void handler(final PersonData treeUserDTO, final int index) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = treeUserDTO.getMargin();
            if (isSearch == 0) {
                margin = treeUserDTO.getMargin();
            } else {
                margin = mg;
            }
            params.setMargins(margin, 0, 0, 0);
            item_org_wrapper.setLayoutParams(params);

            folderIcon.setImageResource(treeUserDTO.isFlag() ? R.drawable.ic_folder_open : R.drawable.ic_folder_close);
            String nameString = treeUserDTO.getFullName();
            String namePosition = "";
            try {
                namePosition = treeUserDTO.getPositionName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (treeUserDTO.getType() == 2) {
                String url = "http://" + new PreferenceUtilities().getCurrentCompanyDomain() + treeUserDTO.getUrlAvatar();

                avatar.setImageResource(R.drawable.avatar_l);
                ImageLoader.getInstance().displayImage(url, avatar, Statics.options2);

                position.setVisibility(View.VISIBLE);
                position.setText(namePosition);
                folderIcon.setVisibility(View.GONE);
                relAvatar.setVisibility(View.VISIBLE);


            } else {
                position.setVisibility(View.GONE);
                relAvatar.setVisibility(View.GONE);
                folderIcon.setVisibility(View.VISIBLE);
            }
            name.setText(nameString);
            item_org_wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (treeUserDTO.getType() != 2) {
                        if (treeUserDTO.isFlag()) {
                            Log.d(TAG, "collapse");
                            collapse(index, treeUserDTO);
                            treeUserDTO.setFlag(false);
                        } else {
                            Log.d(TAG, "expand");
                            boolean flag = false;
                            if (index == list.size() - 1) {
                                flag = true;
                            }
                            expand(index, treeUserDTO, flag);
                            treeUserDTO.setFlag(true);
                        }
                    } else {
                        if (BUNDLE_TYPE == Cons.ADMIN_TYPE && treeUserDTO.getUserNo() == myId) {
                            Log.d(TAG, "can not choose my id");
                        } else {
                            boolean flag_2 = treeUserDTO.isCheck();
//                        setCheckLayout(treeUserDTO, row_check, flag_2, index);
                            if (BUNDLE_TYPE == Cons.PERSON_CONCERNED_TYPE) {
                                if (flag_2) {
                                    setCheckLayout(treeUserDTO, row_check, flag_2, index);
                                } else {
                                    if (isCanCheck(listTemp)) {
                                        setCheckLayout(treeUserDTO, row_check, flag_2, index);
                                    } else {
                                        row_check.setChecked(false);
                                        Toast.makeText(context, context.getResources().getString(R.string.chooseone), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
//                        BUNDLE_TYPE = 1 , 3
                                setCheckLayout(treeUserDTO, row_check, flag_2, index);
                            }
                        }
                    }
                }
            });
//            if (treeUserDTO.getUserNo() == myId) {
//                row_check.setEnabled(false);
//            }

            if (BUNDLE_TYPE == Cons.SHARER_TYPE) {
                row_check.setEnabled(true);
            } else {
                if (treeUserDTO.getType() == 2) {
                    row_check.setEnabled(true);
                } else {
                    row_check.setEnabled(false);
                }

                if (BUNDLE_TYPE == Cons.ADMIN_TYPE) {
                    if (treeUserDTO.getType() == 2 && treeUserDTO.getUserNo() == myId) {
                        row_check.setEnabled(false);

                    }
                }
            }

            row_check.setChecked(treeUserDTO.isCheck());
            row_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "row_check onClick");
                    boolean flag = row_check.isChecked();
                    if (BUNDLE_TYPE == Cons.PERSON_CONCERNED_TYPE) {
//                        if (!isCanCheck(listTemp) && flag) {
//                            // can not check >= 2 personal
//                            row_check.setChecked(false);
//                            Toast.makeText(context, context.getResources().getString(R.string.chooseone), Toast.LENGTH_SHORT).show();
//                        } else {
//                            setCheckBox(treeUserDTO, flag, index);
//                        }

                        if (!flag) {
                            setCheckBox(treeUserDTO, flag, index);
                        } else {
                            if (isCanCheck(listTemp)) {
                                setCheckBox(treeUserDTO, flag, index);
                            } else {
                                row_check.setChecked(false);
                                Toast.makeText(context, context.getResources().getString(R.string.chooseone), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
//                        BUNDLE_TYPE = 1 , 3
                        setCheckBox(treeUserDTO, flag, index);
                    }

                }
            });
        }
    }

    public void setCheckLayout(PersonData treeUserDTO, CheckBox row_check, boolean flag_2, int index) {
        boolean flag = false;
        if (flag_2) {
            flag = false;
            row_check.setChecked(false);
            treeUserDTO.setIsCheck(false);
        } else {
            flag = true;
            row_check.setChecked(true);
            treeUserDTO.setIsCheck(true);
        }

        int LEVEL = treeUserDTO.getLevel();
        int LEVEL_TEMP = treeUserDTO.getLevel();

        if (flag) {
            for (int i = 0; i < listTemp.size(); i++) {
                PersonData obj = listTemp.get(i);

                if (treeUserDTO.getUserNo() == obj.getUserNo()
                        && treeUserDTO.getDepartNo() == obj.getDepartNo()
                        && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                        && treeUserDTO.getType() == obj.getType()
                        && treeUserDTO.getSortNo() == obj.getSortNo()
                        && treeUserDTO.getFullName().equals(obj.getFullName())) {

                    listTemp.get(i).setIsCheck(flag);

                    break;
                }
            }
        } else {
            list.get(index).setIsCheck(false);
            for (int i = index; i >= 0; i--) {
                PersonData obj = list.get(i);
                if (LEVEL > obj.getLevel()) {
                    list.get(i).setIsCheck(false);
                    LEVEL = obj.getLevel();

                }
            }

            int k = -10;
            for (int i = 0; i < listTemp.size(); i++) {
                PersonData obj = listTemp.get(i);
                if (treeUserDTO.getUserNo() == obj.getUserNo()
                        && treeUserDTO.getDepartNo() == obj.getDepartNo()
                        && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                        && treeUserDTO.getType() == obj.getType()
                        && treeUserDTO.getSortNo() == obj.getSortNo()
                        && treeUserDTO.getFullName().equals(obj.getFullName())) {
                    k = i;
                    listTemp.get(i).setIsCheck(false);
                    break;
                }
            }
            if (k >= 0) {

                for (int i = k; i >= 0; i--) {
                    PersonData obj = listTemp.get(i);
                    if (LEVEL_TEMP > obj.getLevel()) {

                        listTemp.get(i).setIsCheck(false);
                        LEVEL_TEMP = obj.getLevel();
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setCheckBox(PersonData treeUserDTO, boolean flag, int index) {
        treeUserDTO.setIsCheck(flag);
        int LEVEL = treeUserDTO.getLevel();
        int LEVEL_TEMP = treeUserDTO.getLevel();

        if (treeUserDTO.getType() == 2) {
            if (flag) {
                for (int i = 0; i < listTemp.size(); i++) {
                    PersonData obj = listTemp.get(i);

                    if (treeUserDTO.getUserNo() == obj.getUserNo()
                            && treeUserDTO.getDepartNo() == obj.getDepartNo()
                            && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                            && treeUserDTO.getType() == obj.getType()
                            && treeUserDTO.getSortNo() == obj.getSortNo()
                            && treeUserDTO.getFullName().equals(obj.getFullName())) {

                        listTemp.get(i).setIsCheck(flag);

                        break;
                    }
                }
            } else {
                list.get(index).setIsCheck(false);
                for (int i = index; i >= 0; i--) {
                    PersonData obj = list.get(i);
                    if (LEVEL > obj.getLevel()) {
                        list.get(i).setIsCheck(false);
                        LEVEL = obj.getLevel();

                    }
                }

                int k = -10;
                for (int i = 0; i < listTemp.size(); i++) {
                    PersonData obj = listTemp.get(i);
                    if (treeUserDTO.getUserNo() == obj.getUserNo()
                            && treeUserDTO.getDepartNo() == obj.getDepartNo()
                            && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                            && treeUserDTO.getType() == obj.getType()
                            && treeUserDTO.getSortNo() == obj.getSortNo()
                            && treeUserDTO.getFullName().equals(obj.getFullName())) {
                        k = i;
                        listTemp.get(i).setIsCheck(false);
                        break;
                    }
                }
                if (k >= 0) {

                    for (int i = k; i >= 0; i--) {
                        PersonData obj = listTemp.get(i);
                        if (LEVEL_TEMP > obj.getLevel()) {

                            listTemp.get(i).setIsCheck(false);
                            LEVEL_TEMP = obj.getLevel();
                        }
                    }
                }
            }

        } else {
            if (flag) {
                int a = index + 1;
                if (a < list.size()) {
                    for (int i = a; i < list.size(); i++) {
                        PersonData obj = list.get(i);
                        if (LEVEL < obj.getLevel()) {
                            list.get(i).setIsCheck(true);
                        } else {
                            break;
                        }
                    }
                }
                int temp = 0;
                for (int i = 0; i < listTemp.size(); i++) {
                    PersonData obj = listTemp.get(i);
                    if (treeUserDTO.getUserNo() == obj.getUserNo()
                            && treeUserDTO.getDepartNo() == obj.getDepartNo()
                            && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                            && treeUserDTO.getType() == obj.getType()
                            && treeUserDTO.getSortNo() == obj.getSortNo()
                            && treeUserDTO.getFullName().equals(obj.getFullName())) {
                        listTemp.get(i).setIsCheck(flag);
                        temp = i;
                        break;
                    }
                }
                int c = temp + 1;
                if (c < listTemp.size()) {
                    for (int i = c; i < listTemp.size(); i++) {
                        PersonData obj = listTemp.get(i);
                        if (LEVEL < obj.getLevel()) {
                            listTemp.get(i).setIsCheck(true);
                        } else {
                            break;
                        }
                    }
                }

            } else {
                if (LEVEL == 0) {
                    int a = index + 1;
                    if (a < list.size()) {
                        for (int i = a; i < list.size(); i++) {
                            PersonData obj = list.get(i);
                            if (LEVEL < obj.getLevel()) {
                                list.get(i).setIsCheck(false);
                            } else {
                                break;
                            }
                        }
                    }
                    int temp = 0;
                    for (int i = 0; i < listTemp.size(); i++) {
                        PersonData obj = listTemp.get(i);
                        if (treeUserDTO.getUserNo() == obj.getUserNo()
                                && treeUserDTO.getDepartNo() == obj.getDepartNo()
                                && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                                && treeUserDTO.getType() == obj.getType()
                                && treeUserDTO.getSortNo() == obj.getSortNo()
                                && treeUserDTO.getFullName().equals(obj.getFullName())) {
                            listTemp.get(i).setIsCheck(flag);
                            temp = i;
                            break;
                        }
                    }
                    int c = temp + 1;
                    if (c < listTemp.size()) {
                        for (int i = c; i < listTemp.size(); i++) {
                            PersonData obj = listTemp.get(i);
                            if (LEVEL < obj.getLevel()) {
                                listTemp.get(i).setIsCheck(false);
                            } else {
                                break;
                            }
                        }
                    }
                } else {

                    for (int i = index; i >= 0; i--) {
                        PersonData obj = list.get(i);
                        if (LEVEL > obj.getLevel()) {
                            list.get(i).setIsCheck(false);
                            LEVEL = obj.getLevel();
                        }
                    }

                    int temp = 0;
                    for (int i = 0; i < listTemp.size(); i++) {
                        PersonData obj = listTemp.get(i);
                        if (treeUserDTO.getUserNo() == obj.getUserNo()
                                && treeUserDTO.getDepartNo() == obj.getDepartNo()
                                && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                                && treeUserDTO.getType() == obj.getType()
                                && treeUserDTO.getSortNo() == obj.getSortNo()
                                && treeUserDTO.getFullName().equals(obj.getFullName())) {
                            listTemp.get(i).setIsCheck(flag);
                            temp = i;
                            break;
                        }
                    }
                    for (int i = temp; i >= 0; i--) {
                        PersonData obj = listTemp.get(i);
                        if (LEVEL > obj.getLevel()) {
                            listTemp.get(i).setIsCheck(false);
                            LEVEL = obj.getLevel();
                        }
                    }
                    int level = treeUserDTO.getLevel();
                    for (int i = temp + 1; i < listTemp.size(); i++) {
                        PersonData obj = listTemp.get(i);
                        if (level < obj.getLevel()) {
                            listTemp.get(i).setIsCheck(false);
                        } else {
                            break;
                        }
                    }
                    // for child list
                    if (index + 1 < list.size()) {
                        for (int i = index + 1; i < list.size(); i++) {
                            PersonData obj = list.get(i);
                            if (level < obj.getLevel()) {
                                list.get(i).setIsCheck(false);
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    boolean isCanCheck(List<PersonData> lst) {
        for (PersonData obj : lst) {
            if (obj.isCheck())
                return false;
        }
        return true;
    }

    boolean isAdd(List<PersonData> lst, PersonData treeUserDTO) {
//        for (TreeUserDTO obj : lst) {
//            if (treeUserDTO.getUserNo() == obj.getUserNo()
//                    && treeUserDTO.getDepartNo() == obj.getDepartNo()
//                    && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
//                    && treeUserDTO.getType() == obj.getType()
//                    && treeUserDTO.getSortNo() == obj.getSortNo()
//                    && treeUserDTO.getFullName().equals(obj.getFullName()))
//                return false;
//        }
        return true;
    }


    void addList(PersonData obj, int margin, int level) {
        margin += Utils.getDimenInPx(R.dimen.dimen_20_40);
        obj.setMargin(margin);
        level += 1;
        obj.setLevel(level);

        int DepartNo = obj.getDepartNo();
        int UserNo = obj.getUserNo();
        int ParentNo = obj.getDepartmentParentNo();


        obj.setIsCheck(false);
        if (selectedPersonList != null) {
            for (PersonData a : selectedPersonList) {
                if (a.getUserNo() == UserNo && a.getDepartNo() == DepartNo && a.getDepartmentParentNo() == ParentNo) {
                    obj.setIsCheck(true);
                    break;
                }
            }
        }
        obj.setFlag(true);


        this.listTemp.add(obj);
        this.listTemp_2.add(obj);

        if (obj.getPersonList() != null && obj.getPersonList().size() != 0) {
            for (PersonData dto1 : obj.getPersonList()) {
                addList(dto1, margin, level);
            }
        }
    }

    public void updateList(List<PersonData> list) {
        if (list != null && list.size() > 0) {
            Log.d(TAG, "start updateList");
            final int tempMargin = Utils.getDimenInPx(R.dimen.dimen_20_40) * -1;
            for (PersonData obj : list) {
                addList(obj, tempMargin, -1);
            }
            Log.d(TAG, "finish addListTemp");
            this.list = this.listTemp_2;
            this.notifyDataSetChanged();
//            int k = 0;
//            for (int i = 0; i < this.list.size(); i++) {
//                if (this.list.get(i).getUserNo() == myId) {
//                    k = i;
//                    break;
//                }
//            }
//            if (instance != null)
//                instance.scrollToEndList(k);
            Log.d(TAG, "notifyDataSetChanged");

        }

    }

    public List<PersonData> getList() {
        return listTemp;
    }

    public AdapterOrganizationChart(Context context, List<PersonData> list, NewOrganizationChart instance,
                                    ArrayList<PersonData> userNos, int BUNDLE_TYPE) {
        this.context = context;
        this.list = list;
        this.instance = instance;
        this.mg = Utils.getDimenInPx(R.dimen.dimen_20_40) * 2;
        this.myId = _Application.getInstance().getPreferenceUtilities().getId();
        this.selectedPersonList = userNos;
        this.BUNDLE_TYPE = BUNDLE_TYPE;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_organization_chart_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PersonData treeUserDTO = list.get(position);
        holder.handler(treeUserDTO, position);

    }

    void collapse(final int position, final PersonData treeUserDTO) {
        int levelCur = list.get(position).getLevel();
        Log.d(TAG, "levelCur:" + levelCur);
        int a = position + 1;
        if (a < list.size()) {
            for (int i = a; i < list.size(); i++) {
                PersonData obj = list.get(i);
                int level = obj.getLevel();
                if (levelCur < level) {
                    Log.d(TAG, "remove: " + obj.getFullName());
                    list.remove(i);
                    i--;
                } else {
                    break;
                }
            }
            notifyDataSetChanged();
        }


    }

    private void expand(int position, PersonData treeUserDTO, boolean flag) {
        int levelCur = treeUserDTO.getLevel();
        int index = position + 1;
        // get index of list
        int indexListTemp = 0;
        for (int i = 0; i < listTemp.size(); i++) {
            PersonData obj = listTemp.get(i);
            if (obj.getType() != 2) {
                if (treeUserDTO.getUserNo() == obj.getUserNo()
                        && treeUserDTO.getDepartNo() == obj.getDepartNo()
                        && treeUserDTO.getDepartmentParentNo() == obj.getDepartmentParentNo()
                        && treeUserDTO.getType() == obj.getType()
                        && treeUserDTO.getSortNo() == obj.getSortNo()
                        && treeUserDTO.getFullName().equals(obj.getFullName())) {
                    indexListTemp = i;
                    break;
                }
            }
        }

        int a = indexListTemp + 1;
        if (a < listTemp.size()) {
            for (int i = a; i < listTemp.size(); i++) {
                PersonData object = listTemp.get(i);
                if (levelCur < object.getLevel()) {
                    object.setFlag(true);
                    if (isAdd(this.list, object)) {
                        list.add(index, object);
                        index++;
                    }
                } else {
                    break;
                }
            }
        }

        notifyDataSetChanged();
        if (flag) {
            Log.d(TAG, "scrollToEndList");
            if (instance != null)
                instance.scrollToEndList(position + 1);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
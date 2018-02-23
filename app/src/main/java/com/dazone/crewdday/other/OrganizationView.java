package com.dazone.crewdday.other;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.*;
import com.dazone.crewdday.Cons;
import com.dazone.crewdday.activity.AddUser;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.mInterface.IF_UpdateDepartment_Enabled;
import com.dazone.crewdday.mInterface.IF_UpdateDepartment_Name;
import com.dazone.crewdday.mInterface.InsertDepartmentCallback;
import com.dazone.crewdday.util.WebClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sherry on 12/31/15.
 */
public class OrganizationView {
    int i = 0;
    String TAG = "OrganizationView";
    private ArrayList<PersonData> mPersonList = new ArrayList<>();
    private ArrayList<PersonData> mSelectedPersonList;
    private Context mContext;
    private int displayType = 0; // 0 folder structure , 1
    private OnOrganizationSelectedEvent mSelectedEvent;

    public OrganizationView(Context context, ArrayList<PersonData> selectedPersonList, boolean isDisplaySelectedOnly, ViewGroup viewGroup) {
        this.mContext = context;

        if (selectedPersonList != null) {
            Log.d(TAG,"selectedPersonList != null:"+selectedPersonList.size());
            this.mSelectedPersonList = selectedPersonList;
        } else {Log.d(TAG,"selectedPersonList == null");
            this.mSelectedPersonList = new ArrayList<>();
        }

        if (isDisplaySelectedOnly) {

            initSelectedList(selectedPersonList, viewGroup);
        } else {

            initWholeOrganization(viewGroup);
        }
    }

    /**
     * this function automatically set all to selected
     *
     * @param selectedPersonList
     */
//    private void initSelectedList(ArrayList<PersonData> selectedPersonList, ViewGroup viewGroup) {
//        mPersonList = new ArrayList<>();
//        for (PersonData selectedPerson : selectedPersonList) {
//            selectedPerson.setIsCheck(true);
//            if (selectedPerson.getType() == 2) {
//                mPersonList.add(selectedPerson);
//            }
//        }
//        createRecursiveList(mPersonList, mPersonList);
//        displayAsFolder(viewGroup);
//    }
    private void initSelectedList(ArrayList<PersonData> selectedPersonList, ViewGroup viewGroup) {
        mPersonList = new ArrayList<>();
        for (PersonData selectedPerson : selectedPersonList) {
            selectedPerson.setIsCheck(true);
            if (selectedPerson.getType() == 2) {
                mPersonList.add(selectedPerson);
            }
        }
        createRecursiveList(mPersonList, mPersonList);
        displayAsFolder(viewGroup);
    }

    private void initWholeOrganization(final ViewGroup viewGroup) {
        PersonData.getDepartmentAndUser(new OnGetAllOfUser() {
            @Override
            public void onGetAllOfUserSuccess(ArrayList<PersonData> list) {
                mPersonList = new ArrayList<>(list);
                // set selected for list before create recursive list
                for (PersonData selectedPerson : mSelectedPersonList) {

                    int indexOf = mPersonList.indexOf(selectedPerson);
                    if (indexOf != -1) {
                        mPersonList.get(indexOf).setIsCheck(true);
                    }
                }
                createRecursiveList(list, mPersonList);
                displayAsFolder(viewGroup);
            }

            @Override
            public void onGetAllOfUserFail(ErrorDto errorData) {

            }
        });
    }

    public void setOnSelectedEvent(OnOrganizationSelectedEvent selectedEvent) {
        this.mSelectedEvent = selectedEvent;
    }

//    private void createRecursiveList(ArrayList<PersonData> list, ArrayList<PersonData> parentList) {
//        //create recursive list
//        Iterator<PersonData> iter = list.iterator();
//        while (iter.hasNext()) {
//            PersonData tempPerson = iter.next();
//            for (PersonData person : parentList) {
//                if (person.getType() == 1) {
//                    if (tempPerson.getType() == 1 && person.getDepartNo() == tempPerson.getDepartmentParentNo()) {
////                         department compare by departNo and parentNo
//                        person.addChild(tempPerson);
//                        iter.remove();
//                        parentList.remove(tempPerson);
//                        break;
//                    } else if (tempPerson.getType() == 2 && person.getDepartNo() == tempPerson.getDepartNo()) {
//                        // member , compare by departNo and departNo
//                        person.addChild(tempPerson);
//                        iter.remove();
//                        parentList.remove(tempPerson);
//                        break;
//                    }
//                    if (person.getPersonList() != null && person.getPersonList().size() > 0) {
//                        // not in root list , search in child list
//                        ArrayList<PersonData> test = new ArrayList<>();
//                        test.add(tempPerson);
//                        createRecursiveList(test, person.getPersonList());
//                    }
//                }
//            }
//        }
//    }

    private void createRecursiveList(ArrayList<PersonData> list, ArrayList<PersonData> parentList) {

        //create recursive list
        Iterator<PersonData> iter = list.iterator();
        while (iter.hasNext()) {

            PersonData tempPerson = iter.next();
//            Log.e(TAG,tempPerson.toString());
            for (PersonData person : parentList) {
//                Log.e(TAG,"aaaaa "+ person.toString());
                if (person.getType() == 1) {
                    if (tempPerson.getType() == 1 && person.getDepartNo() == tempPerson.getDepartmentParentNo()) {
                        // department compare by departNo and parentNo
                        person.addChild(tempPerson);
                        iter.remove();
                        parentList.remove(tempPerson);
                        break;
                    } else if (tempPerson.getType() == 2 && person.getDepartNo() == tempPerson.getDepartNo()) {
                        // member , compare by departNo and departNo
                        person.addChild(tempPerson);
                        iter.remove();
                        parentList.remove(tempPerson);
                        break;
                    }
                    if (person.getPersonList() != null && person.getPersonList().size() > 0) {
                        // not in root list , search in child list
                        ArrayList<PersonData> test = new ArrayList<>();
                        test.add(tempPerson);
                        createRecursiveList(test, person.getPersonList());
                    }
                }
            }
        }
    }

    public void setDisplayType(int type) {
        this.displayType = type;
    }

    public void displayAsFolder(ViewGroup viewGroup) {
        this.displayType = 0;
        for (PersonData personData : mPersonList) {
            if (personData.getPersonList() != null && personData.getType() != 2 && personData.getDepartmentParentNo() == 0) {
                draw(personData, viewGroup, false, 0);
            }
        }
    }


//    public void displayMatchQuery(ViewGroup viewGroup, String query) {
//        ArrayList<PersonData> resultList = new ArrayList<>();
//        getPersonDataWithQuery(query, resultList, mPersonList);
//        for (PersonData personData : resultList) {
//            draw(personData, viewGroup, false, 0);
//        }
//    }

    private void getPersonDataWithQuery(String query, ArrayList<PersonData> searchResultList, ArrayList<PersonData> searchList) {
        for (PersonData personData : searchList) {
            if (personData.getType() == 2) {
                if ((personData.getFullName() != null && personData.getFullName().toLowerCase().contains(query))
                        || (personData.getEmail() != null && personData.getEmail().toLowerCase().contains(query))) {
                    searchResultList.add(personData);

                }
            }
            if (personData.getPersonList() != null && personData.getPersonList().size() > 0) {
                getPersonDataWithQuery(query, searchResultList, personData.getPersonList());
            }
        }
    }

    private void draw(final PersonData personData, final ViewGroup layout, final boolean checked, final int iconMargin) {
        final LinearLayout child_list;
        final ImageView folderIcon;
        final ImageView avatar;
        final TextView name;
        final CheckBox row_check;
        final LinearLayout item_org_wrapper;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (HomeActivity.gotoOrganizationActivity == 1) {
            view = inflater.inflate(R.layout.item_organization, null);
        } else {
            view = inflater.inflate(R.layout.item_organization_manager, null);
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(view);
        item_org_wrapper = (LinearLayout) view.findViewById(R.id.item_org_wrapper);
        child_list = (LinearLayout) view.findViewById(R.id.child_list);
        folderIcon = (ImageView) view.findViewById(R.id.icon);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        name = (TextView) view.findViewById(R.id.name);
        row_check = (CheckBox) view.findViewById(R.id.row_check);
        row_check.setChecked(personData.isCheck());
        if (HomeActivity.isManagerTool && HomeActivity.gotoOrganizationActivity == 0) {
            if (!personData.isEnabled())
                name.setTextColor(Color.RED);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) folderIcon.getLayoutParams();
        LinearLayout.LayoutParams params_av = new LinearLayout.LayoutParams(Utils.getDimenInPx(R.dimen.size), Utils.getDimenInPx(R.dimen.size));
        if (displayType == 0) // set margin for icon if it's company type
        {
            params.leftMargin = iconMargin;
            params_av.leftMargin = iconMargin;
        }
        folderIcon.setLayoutParams(params);
        avatar.setLayoutParams(params_av);


       /* if(personData.getUserNo()==UserData.getUserInformation().getId())
        {
            row_check.setChecked(false);
            row_check.setEnabled(false);
        }*/
        String nameString = personData.getFullName();
        if (personData.getType() == 2) {
            nameString += !TextUtils.isEmpty(personData.getPositionName()) ? "\n" + personData.getPositionName() : "";
            ImageUtils.showImage_custom(personData, avatar);
            folderIcon.setVisibility(View.GONE);
        } else {
            avatar.setVisibility(View.GONE);
            folderIcon.setVisibility(View.VISIBLE);
        }
        i++;
        name.setText(nameString);

        final int tempMargin = iconMargin + Utils.getDimenInPx(R.dimen.activity_login_user_margin);
        row_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (HomeActivity.intShare == 1) {
                    if (!isChecked && personData.getType() == 2) {
                        ViewGroup parent = ((ViewGroup) layout.getParent());
                        unCheckBoxParent(parent);
                    } else {
                        if (buttonView.getTag() != null && !(Boolean) buttonView.getTag()) {
                            buttonView.setTag(true);
                        } else {
                            personData.setIsCheck(isChecked);
                            if (personData.getPersonList() != null && personData.getPersonList().size() != 0) {
                                int index = 0;
                                for (PersonData dto1 : personData.getPersonList()) {
                                    dto1.setIsCheck(isChecked);
                                    View childView = child_list.getChildAt(index);
                                    CheckBox childCheckBox = (CheckBox) childView.findViewById(R.id.row_check);
                                    if (childCheckBox != null) {
                                        if (childCheckBox.isEnabled()) {
                                            childCheckBox.setChecked(dto1.isCheck());
                                        }
                                    } else {
                                        break;
                                    }
                                    index++;
                                }
                            }
                        }
                    }
                    if (mSelectedEvent != null) {
                        mSelectedEvent.onOrganizationCheck(isChecked, personData);
                    }
                } else if (HomeActivity.intShare == 2) {
                    if (personData.getType() != 2) {
                        buttonView.setChecked(false);
                    } else {
                        //flag:true -> can check
                        //flag:false -> can't check
                        boolean flag = _Application.getInstance().getPrefManager().isChooseCorcerne();
                        if (isChecked) {
                            if (flag) {
                                checkCountChoosePerson(isChecked, personData, layout, buttonView, child_list);
                                _Application.getInstance().getPrefManager().setChooseCorcerned(false);
                            } else {
                                buttonView.setChecked(false);
                                Toast.makeText(mContext, mContext.getString(R.string.chooseone), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            checkCountChoosePerson(isChecked, personData, layout, buttonView, child_list);
                            _Application.getInstance().getPrefManager().setChooseCorcerned(true);
                        }
                    }
                } else {
                    if (personData.getUserNo() != _Application.getInstance().getPreferenceUtilities().getId()) {
                        if (personData.getType() == 2) {
                            if (!isChecked && personData.getType() == 2) {
                                ViewGroup parent = ((ViewGroup) layout.getParent());
                                unCheckBoxParent(parent);
                            } else {
                                if (buttonView.getTag() != null && !(Boolean) buttonView.getTag()) {
                                    buttonView.setTag(true);
                                } else {
                                    personData.setIsCheck(isChecked);
                                    if (personData.getPersonList() != null && personData.getPersonList().size() != 0) {
                                        int index = 0;
                                        for (PersonData dto1 : personData.getPersonList()) {
                                            dto1.setIsCheck(isChecked);
                                            View childView = child_list.getChildAt(index);
                                            CheckBox childCheckBox = (CheckBox) childView.findViewById(R.id.row_check);
                                            if (childCheckBox != null) {
                                                if (childCheckBox.isEnabled()) {
                                                    childCheckBox.setChecked(dto1.isCheck());
                                                }
                                            } else {
                                                break;
                                            }
                                            index++;
                                        }
                                    }
                                }
                            }
                            if (mSelectedEvent != null) {
                                mSelectedEvent.onOrganizationCheck(isChecked, personData);
                            }
                        } else {
                            buttonView.setChecked(false);
                        }
                    } else {
                        buttonView.setChecked(false);
                    }
                }
            }
        });

        item_org_wrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (HomeActivity.isManagerTool && HomeActivity.gotoOrganizationActivity == 0) {
                    if (personData.getType() == 1) {

                        dialogLongTouch(name, personData);
                    } else if (personData.getType() == 2) {
                        dialogLongTouchEmployee(personData.getFullName());
                    }
                }
                return true;
            }
        });
        item_org_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personData.getType() == 2 && HomeActivity.isManagerTool && HomeActivity.gotoOrganizationActivity == 0) {
                    Log.d(TAG, personData.toString());
                    Intent intent = new Intent(mContext, AddUser.class);
                    intent.putExtra(Cons.ADD_USER, 0);
//                    intent.putExtra(Cons.USERNO, personData.getUserNo());
//                    intent.putExtra(Cons.NAME, personData.getFullName());
//                    intent.putExtra(Cons.NAME_EN, personData.getNameEn());
//                    intent.putExtra(Cons.MAIL, personData.getmEmail());


                    intent.putExtra(Cons.OBJECT_PERSON, new Gson().toJson(personData));

                    mContext.startActivity(intent);
                }
            }
        });
        if (personData.getPersonList() != null && personData.getPersonList().size() != 0) {

            folderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHideSubMenuView(child_list, folderIcon);
                }
            });
            item_org_wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHideSubMenuView(child_list, folderIcon);
                }
            });
            for (PersonData dto1 : personData.getPersonList()) {
                draw(dto1, child_list, false, tempMargin);
            }
//            for (int i = 0; i < personData.getPersonList().size(); i++) {
//                draw(personData.getPersonList().get(i), child_list, false, tempMargin);
//            }
        }
    }

    private void unCheckBoxParent(ViewGroup view) {
        if (view.getId() == R.id.item_org_main_wrapper || view.getId() == R.id.item_org_wrapper) {
            CheckBox parentCheckBox = (CheckBox) view.findViewById(R.id.row_check);
            if (parentCheckBox.isChecked()) {
                parentCheckBox.setTag(false);
                parentCheckBox.setChecked(false);
            }
            if ((view.getParent()).getParent() instanceof ViewGroup) {
                try {
                    ViewGroup parent = (ViewGroup) (view.getParent()).getParent();
                    unCheckBoxParent(parent);
                } catch (Exception e) {
                }
            }
        }
    }

    public void checkCountChoosePerson(boolean isChecked, PersonData personData, ViewGroup layout, CompoundButton buttonView, LinearLayout child_list) {
        if (!isChecked && personData.getType() == 2) {
            ViewGroup parent = ((ViewGroup) layout.getParent());
            unCheckBoxParent(parent);
        } else {
            if (buttonView.getTag() != null && !(Boolean) buttonView.getTag()) {
                buttonView.setTag(true);
            } else {
                personData.setIsCheck(isChecked);
                if (personData.getPersonList() != null && personData.getPersonList().size() != 0) {
                    int index = 0;
                    for (PersonData dto1 : personData.getPersonList()) {
                        dto1.setIsCheck(isChecked);
                        View childView = child_list.getChildAt(index);
                        CheckBox childCheckBox = (CheckBox) childView.findViewById(R.id.row_check);
                        if (childCheckBox != null) {
                            if (childCheckBox.isEnabled()) {
                                childCheckBox.setChecked(dto1.isCheck());
                            }
                        } else {
                            break;
                        }
                        index++;
                    }
                }
            }
        }
        if (mSelectedEvent != null) {
            mSelectedEvent.onOrganizationCheck(isChecked, personData);
        }
    }

    public void dialogLongTouchEmployee(String Title) {
        final Dialog dialog;
        if (Build.VERSION.SDK_INT >= 21) {
            dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new Dialog(mContext);
        }
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(Title);
        dialog.setContentView(R.layout.long_touch_layout_employee);
        TextView tvEdit = (TextView) dialog.findViewById(R.id.tvEdit);
        TextView tvEnable = (TextView) dialog.findViewById(R.id.tvEnable);
        TextView tvDisable = (TextView) dialog.findViewById(R.id.tvDisable);
        dialog.show();
    }

    public void dialogConfirm(String msg, final PersonData personData, final String _domain, final boolean updateEnOrDis, final TextView tvname, final int type) {
//        type=0-> enalble   type=1-> disable
        AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        adb.setTitle(mContext.getResources().getString(R.string.Dday));
        adb.setMessage(msg);
        adb.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebClient.UpdateDepartment_Enabled(personData.getDepartNo(), _domain, updateEnOrDis, new IF_UpdateDepartment_Enabled() {
                    @Override
                    public void onSuccess() {
                        personData.setIsEnabled(updateEnOrDis);
                        if (type == 0)
                            tvname.setTextColor(Color.BLACK);
                        else tvname.setTextColor(Color.RED);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        });
        adb.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.create().show();
    }

    public void dialogLongTouch(final TextView tvname, final PersonData personData) {
        final Dialog dialog;
        if (Build.VERSION.SDK_INT >= 21) {
            dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new Dialog(mContext);
        }
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(personData.getFullName());
        dialog.setContentView(R.layout.long_touch_layout);
        final FrameLayout frEnable = (FrameLayout) dialog.findViewById(R.id.frEnable);
        final FrameLayout frDisable = (FrameLayout) dialog.findViewById(R.id.frDisable);
        final TextView tvEnable = (TextView) dialog.findViewById(R.id.tvEnable);
        final TextView tvDisable = (TextView) dialog.findViewById(R.id.tvDisable);
        final String _domain = _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
        Log.d(TAG, "personData:" + personData.isEnabled());
        boolean enabled = false;
        if (personData.isEnabled()) {
            frEnable.setEnabled(false);
            tvEnable.setTextColor(mContext.getResources().getColor(R.color.gray_light));
            tvDisable.setTextColor(mContext.getResources().getColor(R.color.black));
            enabled = false;
        } else {
            frDisable.setEnabled(false);
            tvDisable.setTextColor(mContext.getResources().getColor(R.color.gray_light));
            tvEnable.setTextColor(mContext.getResources().getColor(R.color.black));
            enabled = true;
        }
        final boolean updateEnOrDis = enabled;
        frEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialogConfirm(mContext.getResources().getString(R.string.sure_enable), personData, _domain, updateEnOrDis, tvname, 0);
//                WebClient.UpdateDepartment_Enabled(personData.getDepartNo(), _domain, updateEnOrDis, new IF_UpdateDepartment_Enabled() {
//                    @Override
//                    public void onSuccess() {
//                        personData.setIsEnabled(updateEnOrDis);
//                        tvname.setTextColor(Color.BLACK);
//                    }
//
//                    @Override
//                    public void onFail() {
//
//                    }
//                });
            }
        });
        frDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogConfirm(mContext.getResources().getString(R.string.sure_disable), personData, _domain, updateEnOrDis, tvname, 1);
//                WebClient.UpdateDepartment_Enabled(personData.getDepartNo(), _domain, updateEnOrDis, new IF_UpdateDepartment_Enabled() {
//                    @Override
//                    public void onSuccess() {
//                        personData.setIsEnabled(updateEnOrDis);
//                        tvname.setTextColor(Color.RED);
//                    }
//
//                    @Override
//                    public void onFail() {
//
//                    }
//                });
            }
        });
        ListView listLongTouch = (ListView) dialog.findViewById(R.id.listLongTouch);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(mContext.getResources().getString(R.string.editName));
        arrayList.add(mContext.getResources().getString(R.string.addUser));
        arrayList.add(mContext.getResources().getString(R.string.addChild));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);
        listLongTouch.setAdapter(adapter);
        listLongTouch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    showDialog(personData.getFullName(), personData.getDepartNo(), 1, tvname);
                else if (position == 1)
                    BaseActionActivity.instance.addUserLayout();
                else if (position == 2)
                    showDialog(personData.getFullName(), personData.getDepartNo(), 2, tvname);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void show_notify(String str) {
        AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        adb.setTitle(mContext.getResources().getString(R.string.Dday));
        adb.setMessage(str);
        adb.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.create().show();
    }

    public void showDialog(final String Title, final int departNo, final int action, final TextView tvName) {
        // action = 1; EditName
        // action = 2; InsertDepartment
//        LayoutInflater inflater = getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(R.layout.add_department_layout, null);
        final EditText editText = (EditText) alertLayout.findViewById(R.id.editText);
        AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        if (action == 1) {
            editText.setText(Title);
            adb.setTitle(mContext.getString(R.string.edit));
        } else if (action == 2) {
            adb.setTitle(mContext.getString(R.string.addChild));
        }

        adb.setView(alertLayout);
        adb.setPositiveButton(mContext.getString(R.string.Confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String _domain = _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
                final String name = editText.getText().toString().trim();
                if (action == 1) {
                    if (name.length() > 0 && !name.equals(Title)) {
                        WebClient.UpdateDepartment_Name(departNo, name, _domain, new IF_UpdateDepartment_Name() {
                            @Override
                            public void onSuccess() {
                                tvName.setText(name);
                            }

                            @Override
                            public void Fail() {

                            }
                        });
                    } else {
                        show_notify(mContext.getResources().getString(R.string.inputDepartmentName));
                    }
                } else if (action == 2) {
                    if (name.length() == 0) {
                        show_notify(mContext.getResources().getString(R.string.inputDepartmentName));
                    } else {
                        BaseActionActivity.instance.showProgressbar();
                        WebClient.InsertDepartment(departNo, name, _domain, new InsertDepartmentCallback() {
                            @Override
                            public void onInsertDepartmentSuccess() {
//                                OrganizationUserDBHelper.clearData();
//                                HttpRequest.getInstance().getDepartment(null);
                                BaseActionActivity.instance.updateListAfterNewDepartment();
                            }

                            @Override
                            public void Fail() {

                            }
                        });
                    }
                }
            }
        });
        adb.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.create().show();
    }

    private void showHideSubMenuView(LinearLayout child_list, ImageView icon) {
        if (child_list.getVisibility() == View.VISIBLE) {
            child_list.setVisibility(View.GONE);
            icon.setImageResource(R.drawable.ic_folder_close);

        } else {
            child_list.setVisibility(View.VISIBLE);
            icon.setImageResource(R.drawable.ic_folder_open);

        }
    }

}

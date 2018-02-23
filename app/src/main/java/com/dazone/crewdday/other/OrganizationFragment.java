package com.dazone.crewdday.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;

import java.util.ArrayList;

/**
 * Created by Dinh Huynh on 12/30/15.
 */
public class OrganizationFragment extends BaseFragment implements OnOrganizationSelectedEvent {
    String TAG = "OrganizationFragment";
    private View mView;
    private ArrayList<PersonData> selectedPersonList;
    private TextView sharedPersonTv;
    private LinearLayout mSharePersonContent;
    private OrganizationView orgView;
    private boolean mIsDisplaySelectedOnly = false;
    private int mType;
    public static OrganizationFragment instance = null;

    public static OrganizationFragment newInstance(ArrayList<PersonData> selectedPerson, boolean isDisplaySelectedOnly, int type) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("selectedPerson", selectedPerson);
        args.putBoolean("isDisplaySelectedOnly", isDisplaySelectedOnly);
        args.putInt("type", type);
        OrganizationFragment fragment = new OrganizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        try {
            selectedPersonList = getArguments().getParcelableArrayList("selectedPerson");
            mIsDisplaySelectedOnly = getArguments().getBoolean("isDisplaySelectedOnly");
            mType = getArguments().getInt("type");
        } catch (Exception e) {

        }
        if (selectedPersonList == null) {
            selectedPersonList = new ArrayList<>();
        }
        setHasOptionsMenu(true);
    }

    public void clearList() {
        mSharePersonContent.removeAllViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_organization, container, false);
        sharedPersonTv = (TextView) mView.findViewById(R.id.shared_person_name);
        mSharePersonContent = (LinearLayout) mView.findViewById(R.id.share_list_content);

        initOrganizationTree();

        setSelectedPersonName();
        initShareTextViewAction();
        return mView;
    }

    public void initOrganizationTree() {
        orgView = new OrganizationView(getActivity(), selectedPersonList, mIsDisplaySelectedOnly, mSharePersonContent);
        orgView.setOnSelectedEvent(this);
        if (!mIsDisplaySelectedOnly) {
            mView.findViewById(R.id.share_information_wrapper).setVisibility(View.VISIBLE);
        } else {
            mView.findViewById(R.id.share_information_wrapper).setVisibility(View.GONE);
        }

    }

    private void initShareTextViewAction() {
        sharedPersonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                Intent intent = new Intent(getActivity(), OrganizationActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList(Statics.BUNDLE_LIST_PERSON, selectedPersonList);
//                bundle.putBoolean(Statics.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, true);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, Statics.ORGANIZATION_DISPLAY_SELECTED_ACTIVITY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Statics.ORGANIZATION_DISPLAY_SELECTED_ACTIVITY:
                    ArrayList<PersonData> resultList = data.getExtras().getParcelableArrayList(Statics.BUNDLE_LIST_PERSON);
                    selectedPersonList.clear();
                    if (resultList != null)
                        selectedPersonList.addAll(resultList);
                    mSharePersonContent.removeAllViews();
                    orgView = new OrganizationView(getActivity(), selectedPersonList, mIsDisplaySelectedOnly, mSharePersonContent);
                    orgView.setOnSelectedEvent(this);
                    break;
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.check_menu, menu);
    }

    public void getListChoose() {
        Log.d(TAG,"getListChoose");
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra(Statics.BUNDLE_LIST_PERSON, selectedPersonList);
        resultIntent.putExtra(Cons.BUNDLE_TYPE, mType);
        getActivity().setResult(Activity.RESULT_OK, resultIntent);
        getActivity().finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_check:
                Log.e(TAG, "menu_check");
                getListChoose();
                break;
        }
        return true;
    }

    private void unCheckParentData(PersonData personData) {
        if (mIsDisplaySelectedOnly) {
            PersonData needRemovePerson = null;
            for (PersonData selectedPerson : selectedPersonList) {
                if (personData.getType() == 2 && selectedPerson.getType() == 1 && selectedPerson.getDepartNo() == personData.getDepartNo()) {
                    needRemovePerson = selectedPerson;
                    break;
                } else if (personData.getType() == 1 && selectedPerson.getType() == 1 && selectedPerson.getDepartNo() == personData.getDepartmentParentNo()) {
                    needRemovePerson = selectedPerson;
                    break;
                }
            }
            if (needRemovePerson != null) {
                selectedPersonList.remove(needRemovePerson);
                if (needRemovePerson.getDepartmentParentNo() > 0) {
                    unCheckParentData(needRemovePerson);
                }
            }
        }
    }

    @Override
    public void onOrganizationCheck(boolean isCheck, PersonData personData) {
        int indexOf = selectedPersonList.indexOf(personData);

        if (indexOf != -1) {
            if (!isCheck) {
                selectedPersonList.remove(indexOf);
                unCheckParentData(personData);
            } else {
                selectedPersonList.get(indexOf).setIsCheck(true);
            }
        } else {
            if (isCheck) {
                selectedPersonList.add(personData);
            }
        }
        if (!mIsDisplaySelectedOnly) {
            setSelectedPersonName();
        }
    }

    private void setSelectedPersonName() {
        String shareString = "";
//        for (PersonData selectedPerson : selectedPersonList) {
//            if (!TextUtils.isEmpty(shareString)) {
//                shareString += "; ";
//            }
//            shareString += selectedPerson.getFullName();
//        }

        if (selectedPersonList != null && selectedPersonList.size() != 0) {
            for (int i = 0; i < selectedPersonList.size(); i++) {
                PersonData data = selectedPersonList.get(i);
                int UserNo = data.getUserNo();
                if (UserNo == 0) {
                    if (!ListUtils.check_root_folder(selectedPersonList, data))
                        shareString += data.getFullName() + ", ";
                } else {
                    if (!ListUtils.check_root_user(selectedPersonList, data))
                        shareString += data.getFullName() + ", ";
                }
            }
            shareString = shareString.substring(0, shareString.length() - 2);
        }

        if (TextUtils.isEmpty(shareString)) {
            sharedPersonTv.setVisibility(View.GONE);
        } else {
            sharedPersonTv.setVisibility(View.VISIBLE);
            sharedPersonTv.setText(shareString);
        }
    }
}

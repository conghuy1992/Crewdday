package com.dazone.crewdday.other;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;

import java.util.ArrayList;

/**
 * Created by nguyentiendat on 1/26/16.
 */
public class OrganizationActivity extends BaseActionActivity {
    public static OrganizationActivity getInstance() {
        return new OrganizationActivity();
    }

    @Override
    protected void addFragment(Bundle bundle) {
        if (bundle == null) {
            refreshActivity();
        }
    }

    @Override
    protected void setupFab() {

    }

    public void refreshActivity() {
        Bundle myBundle = getIntent().getExtras();
        ArrayList<PersonData> selectedPerson = myBundle.getParcelableArrayList(Statics.BUNDLE_LIST_PERSON);
        int type = myBundle.getInt(Cons.BUNDLE_TYPE, 0);
        boolean isDisplaySelectedOnly = myBundle.getBoolean(Statics.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);
        OrganizationFragment fm = OrganizationFragment.newInstance(selectedPerson, isDisplaySelectedOnly, type);
        Utils.addFragmentToActivity(getSupportFragmentManager(), fm, R.id.content_base_action, false);
    }

}

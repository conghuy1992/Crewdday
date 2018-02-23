package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.AnnuallyModel;
import com.dazone.crewdday.model.MonthlyModel;
import com.dazone.crewdday.model.SpecialDateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAZONE on 28/03/16.
 */
public interface UpdateListCallback {
    void updateSpecialModelList(ArrayList<SpecialDateModel> list);

    void updateMonthlyModelList(ArrayList<MonthlyModel> list);

    void updateAnnuallyModelList(ArrayList<AnnuallyModel> list);
}

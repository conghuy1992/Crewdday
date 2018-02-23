package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.MenuItemModel;

/**
 * Created by DAZONE on 04/04/16.
 */
public interface MenuCallback {
    void refreshFragments(String text,String groupId);
    void refreshFragments();
    void refresh(MenuItemModel model,int position);
}

package com.dazone.crewdday.mInterface;

/**
 * Created by DAZONE on 12/04/16.
 */
public interface UpdateFragmentTag {
    //Use for current fragment
    void updateTag(int oldTag,int newTag);
    //Use for others
    void refreshTagList();
}

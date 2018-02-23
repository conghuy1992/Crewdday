package com.dazone.crewdday.model;

import java.util.Comparator;

/**
 * Created by DAZONE on 02/03/16.
 */
public class MenuItemModel implements Comparator<MenuItemModel> {
    String text;
    long groupId;
    int imageId;
    int type;
    int tagNo;
    int countOfDays;
    boolean isClicked;
    boolean isChecked;


    public MenuItemModel() {
    }

    public MenuItemModel(String text, int imageId, int type,int countOfDays) {
        this.text = text;
        this.imageId = imageId;
        this.type = type;
        this.countOfDays=countOfDays;
    }

    public int getCountOfDays() {
        return countOfDays;
    }

    public void setCountOfDays(int countOfDays) {
        this.countOfDays = countOfDays;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getTagNo() {
        return tagNo;
    }

    public void setTagNo(int tagNo) {
        this.tagNo = tagNo;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int compare(MenuItemModel menuItem1, MenuItemModel menuItem2) {
        if (menuItem1.getType() == 2 && menuItem2.getType() == 2) {
            return menuItem1.getText().compareTo(menuItem2.getText());
        }
        return 0;
    }
}

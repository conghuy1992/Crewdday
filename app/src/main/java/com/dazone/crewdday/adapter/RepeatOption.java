package com.dazone.crewdday.adapter;

/**
 * Created by maidinh on 9/5/2016.
 */
public class RepeatOption {
   public String TypeName;
    public String SpecificDay;
    public  int Lunar;

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getSpecificDay() {
        return SpecificDay;
    }

    public void setSpecificDay(String specificDay) {
        SpecificDay = specificDay;
    }

    public int getLunar() {
        return Lunar;
    }

    public void setLunar(int lunar) {
        Lunar = lunar;
    }
}

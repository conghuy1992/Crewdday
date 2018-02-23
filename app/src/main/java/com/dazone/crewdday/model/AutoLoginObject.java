package com.dazone.crewdday.model;

/**
 * Created by maidinh on 1/8/2016.
 */
public class AutoLoginObject {
    String userID;
    String FullName;
    int Id;
    String session;
    String avatar;
    int PermissionType;
    String NameCompany;
    String MailAddress;
    int CompanyNo;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPermissionType() {
        return PermissionType;
    }

    public void setPermissionType(int permissionType) {
        PermissionType = permissionType;
    }

    public String getNameCompany() {
        return NameCompany;
    }

    public void setNameCompany(String nameCompany) {
        NameCompany = nameCompany;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    public int getCompanyNo() {
        return CompanyNo;
    }

    public void setCompanyNo(int companyNo) {
        CompanyNo = companyNo;
    }
}

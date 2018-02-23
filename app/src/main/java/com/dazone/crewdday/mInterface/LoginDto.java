package com.dazone.crewdday.mInterface;

/**
 * Created by maidinh on 4/24/2017.
 */

public class LoginDto {
    String userID = "";
    String MailAddress = "";
    String session = "";
    String FullName = "";
    String NameCompany = "";
    int CompanyNo = 0;
    int Id = 0;
    int PermissionType = 0;
    String avatar = "";

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getNameCompany() {
        return NameCompany;
    }

    public void setNameCompany(String nameCompany) {
        NameCompany = nameCompany;
    }

    public int getCompanyNo() {
        return CompanyNo;
    }

    public void setCompanyNo(int companyNo) {
        CompanyNo = companyNo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPermissionType() {
        return PermissionType;
    }

    public void setPermissionType(int permissionType) {
        PermissionType = permissionType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

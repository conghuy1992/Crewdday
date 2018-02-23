package com.dazone.crewdday.other;

public interface Urls {
    String URL_ROOT_SERVICE = "/UI/WebService/WebServiceCenter.asmx/";
//        String URL_GET_ALL_USER = URL_ROOT_SERVICE +"GetAllOfUsers";
    String URL_GET_ALL_USER = URL_ROOT_SERVICE + "GetAllUsersWithBelongs";
    String URL_GET_DEPARTMENT = URL_ROOT_SERVICE + "GetDepartments";
    String URL_GET_USER = URL_ROOT_SERVICE + "GetUser";

}
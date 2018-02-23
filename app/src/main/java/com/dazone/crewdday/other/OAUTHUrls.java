package com.dazone.crewdday.other;

public interface OAUTHUrls {
    String URL_ROOT = "/UI/WebService/WebServiceCenter.asmx/";
    String URL_DEFAULT_API = "http://dazone.crewcloud.net";
    String URL_INSERT_PHONE_TOKEN = URL_DEFAULT_API+ "/UI/WebService/WebServiceCenter.asmx/AddPhoneTokens";
    String URL_CHECK_SESSION = URL_ROOT + "CheckSessionUser_v2";
    String URL_CHECK_DEVICE_TOKEN =URL_DEFAULT_API+ "/UI/WebService/WebServiceCenter.asmx/CheckPhoneToken";

}
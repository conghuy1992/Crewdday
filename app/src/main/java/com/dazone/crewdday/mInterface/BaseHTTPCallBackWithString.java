package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.other.ErrorDto;

public interface BaseHTTPCallBackWithString {
    void onHTTPSuccess(String message);
    void onHTTPFail(ErrorDto errorDto);
}
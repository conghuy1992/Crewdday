package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.other.ErrorDto;

/**
 * Created by maidinh on 4/24/2017.
 */

public interface LoginCallback {
    void onSuccess(LoginDto loginDto);
    void onFail(ErrorDto errorDto);
}

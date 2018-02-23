package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.ProfileUserDTO;
import com.dazone.crewdday.other.ErrorDto;

/**
 * Created by maidinh on 5/22/2017.
 */

public interface OnGetUserCallback {
    void onHTTPSuccess(ProfileUserDTO user);
    void onHTTPFail(ErrorDto errorDto);
}

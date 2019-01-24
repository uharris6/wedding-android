package com.uharris.wedding.presentation.base;

import android.app.Activity;

/**
 * Created by andrestorres on 3/14/16.
 */
public interface BaseViewContract {

    Activity getActivity();
    void showLoader();
    void hideLoader();
    void showMessage(String message);

}

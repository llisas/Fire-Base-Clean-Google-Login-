package com.jesuscuevas.app.getmyfilm.ui.mvp.views;


import android.content.Intent;

public interface MainActivityView {
    void showDialogProgress();
    void onGoogleSignError(String string);
    void moveToNextActivity();
}

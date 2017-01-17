package com.jesuscuevas.app.getmyfilm.ui.mvp.presenters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Jesús Cuevas on 17/01/2017.
 */

public interface MainActivityPresenter {
    void signIn(GoogleApiClient mClient);
    void configureGoogleSign(FragmentActivity fragment,
                             GoogleApiClient.OnConnectionFailedListener listener);
    void startAuthStateListener();
    void onActivityResult(int requestcode, Intent data);
    void fireBaseAuthentification(GoogleSignInAccount acct, FirebaseAuth mAuth);
}

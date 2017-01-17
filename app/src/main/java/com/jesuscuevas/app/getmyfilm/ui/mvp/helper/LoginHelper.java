package com.jesuscuevas.app.getmyfilm.ui.mvp.helper;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Jesús Cuevas on 17/01/2017.
 */

public interface LoginHelper {
    void signInHelper(Intent intent);
    void configureGoogleSignHelper(GoogleApiClient mGoogleApiClient);
    void startAuthStateListenerHelper(FirebaseAuth.AuthStateListener mAuthListener);
    void fireBaseAuthWithGoogleHelper(GoogleSignInAccount acct);
}

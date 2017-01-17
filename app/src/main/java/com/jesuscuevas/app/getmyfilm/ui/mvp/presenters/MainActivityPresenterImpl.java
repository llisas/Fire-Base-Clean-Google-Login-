package com.jesuscuevas.app.getmyfilm.ui.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jesuscuevas.app.getmyfilm.R;
import com.jesuscuevas.app.getmyfilm.ui.activities.MainActivity;
import com.jesuscuevas.app.getmyfilm.ui.mvp.helper.LoginHelper;
import com.jesuscuevas.app.getmyfilm.ui.mvp.views.MainActivityView;
import com.jesuscuevas.app.getmyfilm.utils.Constans;


public class MainActivityPresenterImpl implements MainActivityPresenter{

    private MainActivityView mView;
    private LoginHelper mLoginHelper;
    private Context mContext;


    public MainActivityPresenterImpl(MainActivityView view,
                                     LoginHelper loginHelper,
                                     Context context){
        mView = view;
        mLoginHelper = loginHelper;
        mContext = context;
    }

    @Override
    public void signIn(GoogleApiClient mClient) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mClient);
        mLoginHelper.signInHelper(signInIntent);
    }

    @Override
    public void configureGoogleSign(FragmentActivity fragment,
                                    GoogleApiClient.OnConnectionFailedListener listener) {
        GoogleApiClient mGoogleApiClient;
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(mContext.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage( fragment/* FragmentActivity */,listener /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mLoginHelper.configureGoogleSignHelper(mGoogleApiClient);
    }

    @Override
    public void startAuthStateListener() {
        FirebaseAuth.AuthStateListener mAuthListener = null;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
        mLoginHelper.startAuthStateListenerHelper(mAuthListener);
    }

    @Override
    public void onActivityResult(int requestCode, Intent data) {
        if (requestCode == Constans.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                mLoginHelper.firebaseAuthWithGoogleHelper(account);
            } else {
               mView.onGoogleSignError("An Error ");
            }
        }
    }

    @Override
    public void fireBaseAuthentification(GoogleSignInAccount acct, FirebaseAuth mAuth) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity)mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Constans.TAG, "LOGIN SUCCESS");
                        mView.moveToNextActivity();
                        if (!task.isSuccessful()) {
                            Log.w(Constans.TAG, "LOGIN ERROR");
                            mView.onGoogleSignError("ERROR LOGIN");
                        }
                    }
                });
    }


}

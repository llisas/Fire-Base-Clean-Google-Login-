package com.jesuscuevas.app.getmyfilm.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.jesuscuevas.app.getmyfilm.base.BaseActivity;
import com.jesuscuevas.app.getmyfilm.R;
import com.jesuscuevas.app.getmyfilm.ui.mvp.helper.LoginHelper;
import com.jesuscuevas.app.getmyfilm.ui.mvp.presenters.MainActivityPresenter;
import com.jesuscuevas.app.getmyfilm.ui.mvp.presenters.MainActivityPresenterImpl;
import com.jesuscuevas.app.getmyfilm.ui.mvp.views.MainActivityView;
import butterknife.OnClick;
import static com.jesuscuevas.app.getmyfilm.utils.Constans.RC_SIGN_IN;


public class MainActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener, MainActivityView, LoginHelper {

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MainActivityPresenter mPresenter;

    @Override
    protected void getPresenter() {
        mPresenter = new MainActivityPresenterImpl(this, this, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        configureGoogleSign();
        startInitializeAuth();
        startAuthStateListener();
    }

    //[ACTIVITY LIFECYCLE]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void configureGoogleSign() {
        mPresenter.configureGoogleSign(this, this);
    }

    private void startInitializeAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void startAuthStateListener() {
        mPresenter.startAuthStateListener();
    }

    @OnClick(R.id.sign_in_button)
    void onSignInClick() {
        mPresenter.signIn(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        onGoogleSignError(connectionResult.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, data);
    }

    //[PRESENTER METHODS]
    @Override
    public void showDialogProgress() {

    }

    @Override
    public void onGoogleSignError(String string) {
        Toast.makeText(MainActivity
                .this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToNextActivity() {
        Intent intent = new Intent(MainActivity.this, HeadActivy.class);
        startActivity(intent);
    }

    //[HELPER METHODS]
    @Override
    public void signInHelper(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void configureGoogleSignHelper(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    @Override
    public void startAuthStateListenerHelper(FirebaseAuth.AuthStateListener listener) {
        mAuthListener = listener;
    }

    @Override
    public void fireBaseAuthWithGoogleHelper(GoogleSignInAccount acct) {
        mPresenter.fireBaseAuthentification(acct, mAuth);
    }
}

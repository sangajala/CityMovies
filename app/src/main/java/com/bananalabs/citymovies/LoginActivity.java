package com.bananalabs.citymovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mShouldResolve;

    private ConnectionResult connectionResult;

    private CallbackManager mCallbackManager;
    private Profile profile;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;


    private LinearLayout CM_linearlogin;
    private SignInButton CM_btn_gplus;
    private LoginButton CM_btn_fb;
    private ProgressDialog _dialog;
    private AppConstants objappcontants;
    private TextView CM_txt_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        objappcontants = new AppConstants(LoginActivity.this);

        mCallbackManager = CallbackManager.Factory.create();

        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                //textView.setText(displayMessage(profile1));
                Log.e("after login ", " after login");
            }
        };

        tokenTracker.startTracking();
        profileTracker.startTracking();

        initViews();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


    }

    public void initViews() {

        CM_linearlogin = (LinearLayout) findViewById(R.id.CM_linearlogin);
        CM_btn_gplus = (SignInButton) findViewById(R.id.CM_btn_gplus);
        CM_btn_fb = (LoginButton) findViewById(R.id.CM_btn_fb);

        CM_txt_signup = (TextView) findViewById(R.id.CM_txt_signup);

        setGooglePlusButtonText(CM_btn_gplus, "Login");




//        CM_btn_fb.setBackgroundResource(R.drawable.fb);
        CM_btn_fb.setText("Login");
        CM_btn_fb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);



        CM_btn_fb.setReadPermissions("user_friends");

        CM_btn_fb.registerCallback(mCallbackManager, mFacebookCallback);




        CM_btn_gplus.setOnClickListener(this);

        CM_linearlogin.setOnClickListener(this);
        CM_txt_signup.setOnClickListener(this);

        _dialog = new ProgressDialog(LoginActivity.this);
        _dialog.setCancelable(false);
        _dialog.setCanceledOnTouchOutside(false);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                //tv.setTypeface(null, Typeface.NORMAL);
                tv.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                tv.setText(buttonText);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.SVN_txtview_text_size));
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == CM_linearlogin.getId()) {

        } else if (v.getId() == CM_btn_gplus.getId()) {

            _dialog.show();
            onSignInClicked();

        } else if (v.getId() == CM_txt_signup.getId()) {


            Intent objintent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(objintent);
            finish();

        }

    }

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();

//            GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                @Override
//                public void onCompleted(JSONObject user, GraphResponse graphResponse) {
//
//
//
//
//
//
////                    fbUser.setEmail(user.optString("email"));
////                    fbUser.setName(user.optString("name"));
////                    fbUser.setId(user.optString("id"));
//                }
//            }).executeAsync();


            profile = Profile.getCurrentProfile();


            AppConstants.svnFBName(profile.getName(), "");
            AppConstants.svnGplLogin(false);
            AppConstants.svnFBLogin(true);

            //textView.setText(displayMessage(profile));

            Toast.makeText(getApplicationContext(),
                    "You are Logged In " + profile.getName(), Toast.LENGTH_LONG).show();

            Intent objintent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(objintent);
            finish();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;

            }

            mIntentInProgress = false;


            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Profile profile = Profile.getCurrentProfile();
        //textView.setText(displayMessage(profile));
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        profileTracker.stopTracking();
        tokenTracker.stopTracking();
    }


    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    private void onSignInClicked() {
        if (!mGoogleApiClient.isConnecting()) {
            mShouldResolve = true;
            resolveSignInError();
        }
    }

    private void resolveSignInError() {
        if (connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    @Override
    public void onConnected(Bundle bundle) {

        mShouldResolve = false;
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

                _dialog.dismiss();

                Person person = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = person.getDisplayName();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

//                tvName.setText(personName);
//                tvMail.setText(email);
                AppConstants.svnFBLogin(false);
                AppConstants.svnGplLogin(true);
                AppConstants.svnGplName(personName, email);

                Intent objintent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(objintent);
                finish();

                //updateUI(true);


                Toast.makeText(getApplicationContext(),
                        "You are Logged In " + personName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Couldnt Get the Person Info", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {

            connectionResult = result;

            if (mShouldResolve) {

                resolveSignInError();
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent objintent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(objintent);
        finish();

    }
}

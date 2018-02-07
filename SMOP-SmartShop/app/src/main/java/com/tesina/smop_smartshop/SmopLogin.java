package com.tesina.smop_smartshop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/*
* todo: mettere la conessione al server con conseguenti rispooste i una classe a parte per essere disponibile in modo migliore
* todo: costruttore della classe sa StringRequest = ...
* todo: agguingere nella smop register la connessione al database + test
* */

public class SmopLogin extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    TextView register;
    AutoCompleteTextView email;
    EditText password;
    Button signIn;
    CheckBox remberMe;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smop_login);

        if (isLoginAlreadyDone()){
            SharedPreferences userInformations = getApplicationContext().getSharedPreferences("SignIn",Context.MODE_PRIVATE);
            startMainActivity(userInformations.getString("username",""));
        }

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(SmopLogin.this,SmopRegister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        remberMe = (CheckBox) findViewById(R.id.remember_me);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        signIn = (Button) findViewById(R.id.email_sign_in_button);
        context = getApplicationContext();

        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                atteptLogin();
            }
        });

    }

    //This method try to log in
    private void atteptLogin() {
        final String strEmail = email.getText().toString();
        final String strPassword = password.getText().toString();
        if (!isInputCorrect()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //This class connect the app with the database anc sends data
        DatabaseConnection databaseConnection = new DatabaseConnection(new String[]{"action","username","password"}, new String[]{"sign_in",strEmail,strPassword},getApplicationContext()){
            @Override
            public Response.Listener responseListener() {
                Response.Listener<String> stringListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.equals("true")) {
                            if (remberMe.isChecked()){
                                setRememberMe(true,email.getText().toString());
                            }
                            startMainActivity(email.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "L'account non esiste", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                return stringListener;
            }

            @Override
            public Response.ErrorListener errorResponseListener() {
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Qualcosa è andato storto: " + error.networkResponse, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                };
                return errorListener;
            }
        };
        databaseConnection.startConnection();
    }

    private void startMainActivity(String account) {
        Intent launchMainActivity = new Intent(SmopLogin.this, MainActivity.class);
        launchMainActivity.putExtra("username",account);
        startActivity(launchMainActivity);
        finishAffinity();
    }

    public boolean isLoginAlreadyDone() {
        SharedPreferences loginDone = getApplicationContext().getSharedPreferences("SignIn",Context.MODE_PRIVATE);
        boolean x = loginDone.getBoolean("rememberMe",false);
        return loginDone.getBoolean("rememberMe", false);
    }

    private void setRememberMe( boolean remember, String account) {
        SharedPreferences rememberLogin = getApplicationContext().getSharedPreferences("SignIn",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = rememberLogin.edit();
        editor.putBoolean("rememberMe",remember);
        editor.putString("username",account);
        editor.commit();
    }

    public boolean isInputCorrect() {
        boolean checked = true;

        if (!isPasswordLengthCorrect(password.getText().toString())){
            password.setError(getResources().getString(R.string.error_invalid_password));
            checked = false;
        }

        if (!isMailContainsCharacter(email.getText().toString())){
            email.setError(getResources().getString(R.string.error_invalid_email));
            checked = false;
        }

        return checked;
    }

    private boolean isPasswordLengthCorrect(String password){
        return  password.length() >= 6;
    }

    private boolean isMailContainsCharacter(String mail){
        if (mail.contains("@")){
            String endingPart = mail.substring(mail.indexOf("@"),mail.length());
            return endingPart.contains(".");
        }
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

}
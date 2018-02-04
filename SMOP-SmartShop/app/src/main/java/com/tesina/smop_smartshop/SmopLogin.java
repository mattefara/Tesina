package com.tesina.smop_smartshop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
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
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class SmopLogin extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    final String SERVER_URL = "http://smopapp.altervista.org/login.php";

    AutoCompleteTextView email;
    EditText password;
    Button signIn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smop_login);

        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        signIn = (Button) findViewById(R.id.email_sign_in_button);

        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strEmail = email.getText().toString();
                final String strPassword = password.getText().toString();
                if (!isMailCorrect(strEmail) || !isPasswordCorrect(strPassword)) { return;}
                progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Qualcosa è andato storto: " + error.networkResponse,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> data = new HashMap<>();
                        data.put("username",strEmail);
                        data.put("password",strPassword);
                        return data;
                    }
                };
                LoginSingleton.getMySingletonInstance(SmopLogin.this).addToRequestQue(stringRequest);


            }
        });

    }

    private boolean isPasswordCorrect(String strPassword){
        if (!isPasswordLengthCorrect(strPassword)){
            password.setError(getResources().getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    private boolean isPasswordLengthCorrect(String password){
        return  password.length() >= 6;
    }

    private boolean isMailCorrect(String strEmail) {
        if (!isMailContainsCharacter(strEmail)){
            email.setError(getResources().getString(R.string.error_invalid_email));
            return false;
        }
        return true;
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
}


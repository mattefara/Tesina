package com.tesina.smop_smartshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
* todo: gestione migliore degli errori con messaggi più specifici (da fare in un secondo momento)*/

public class Login extends AppCompatActivity /*implements LoaderCallbacks<Cursor>*/ {

    FirebaseAuth authentication;

    TextView registerButton;
    AutoCompleteTextView textViewEmail;
    EditText textViewPassword;
    Button signIn;
    ProgressBar progressBar;
    Context context;
    SharedPreferences userData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authentication = FirebaseAuth.getInstance();

        // Check if the user has opened before the app
        //userData = getApplicationContext().getSharedPreferences("SingIn",Context.MODE_PRIVATE);
       // userData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       /* if (isLoginAlreadyDone()){
            startActivity(new Intent(Login.this,MainActivity.class));
            this.finish();
        }*/

        // Start login page
        setContentView(R.layout.activity_smop_login);

        registerButton = (TextView) findViewById(R.id.register);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(Login.this,Register.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        textViewEmail= (AutoCompleteTextView) findViewById(R.id.email);
        textViewPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        signIn = (Button) findViewById(R.id.email_sign_in_button);
        context = getApplicationContext();

        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    //This method try to log in
    private void attemptLogin() {
        final String strEmail = textViewEmail.getText().toString();
        final String strPassword = textViewPassword.getText().toString();

        if (!isInputCorrect()) { return; }
        progressBar.setVisibility(View.VISIBLE);

        //This class connect the app with the database anc sends data
        login(strEmail,strPassword);
    }

    private void login(String mail,String password) {
        authentication.signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(Login.this,MainActivity.class));
                        } else {
                            textViewEmail.setError(getString(R.string.error_login_email));
                            textViewPassword.setError(getString(R.string.error_login));
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void setRememberMe( boolean remember, String account) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putBoolean("rememberMe",remember);
        editor.putString("username",account);
        editor.commit();
    }

    public boolean isInputCorrect() {
        boolean checked = true;

        if (!isPasswordLengthCorrect(textViewPassword.getText().toString())){
            textViewPassword.setError(getResources().getString(R.string.error_invalid_password));
            checked = false;
        }

        if (!isMailContainsCharacter(textViewEmail.getText().toString())){
            textViewEmail.setError(getResources().getString(R.string.error_invalid_email));
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
    public void onBackPressed() {
        this.finishAffinity();
    }

}
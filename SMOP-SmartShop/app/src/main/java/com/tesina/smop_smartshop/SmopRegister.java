package com.tesina.smop_smartshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

public class SmopRegister extends AppCompatActivity {
    TextView name, lastname, email, password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smop_register);

        name = (TextView) findViewById(R.id.sign_up_name);
        lastname = (TextView) findViewById(R.id.sign_up_last_name);
        email = (TextView) findViewById(R.id.sign_up_mail);
        password = (TextView) findViewById(R.id.sign_up_password);
        signUp = (Button) findViewById(R.id.sign_up_button);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


    }

    private void register() {
        if (checkInput()){
            DatabaseConnection databaseConnection = new DatabaseConnection(
                    new String[]{"action","email","password","name","lastName"},
                    new String[]{"sign_up",email.getText().toString(), password.getText().toString(),name.getText().toString(),lastname.getText().toString()},
                    getApplicationContext()
            ){
                @Override
                public Response.Listener responseListener() {
                    Response.Listener<String> response = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            setRememberMe();
                            startMainActivity();
                        }
                    };
                    return response;
                }
            };
            databaseConnection.startConnection();
        }
    }

    private void setRememberMe() {
        SharedPreferences rememberSignUp = getApplicationContext().getSharedPreferences("SignIn", Context.MODE_PRIVATE);
        SharedPreferences.Editor data = rememberSignUp.edit();
        data.putBoolean("rememberMe",true);
        data.putString("username",email.getText().toString());
        data.putString("name",name.getText().toString());
        data.putString("lastname",name.getText().toString());
        data.commit();
    }

    private void startMainActivity() {
        Intent mainActivity = new Intent(SmopRegister.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private boolean checkInput() {
        boolean checked = true;

        if (!isCorrectName(name.getText().toString())) {
            checked = false;
            name.setError(getResources().getString(R.string.error_invalid_name));
        }

        if (!isCorrectName(lastname.getText().toString())){
            checked = false;
            lastname.setError(getResources().getString(R.string.error_invalid_lastname));
        }

        if (!isCorrectEmail(email.getText().toString())){
            checked = false;
            email.setError(getResources().getString(R.string.error_invalid_email));
        }

        if (!isCorrectPassword(password.getText().toString())){
            checked = false;
            password.setError(getResources().getString(R.string.error_invalid_password));
        }

        return checked;
    }



    private boolean isCorrectPassword(String s) {
        return s.length()>=6;
    }

    private boolean isCorrectEmail(String s) {
        if (!isMailContainsCharacter(s)){
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

    private boolean isCorrectName(String s) {
        return (!s.isEmpty() && s.length()<255);
    }


    @Override
    public void onBackPressed() {
        Intent main = new Intent(SmopRegister.this,SmopLogin.class);
        startActivity(main);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        this.finish();
    }
}

package com.tesina.smop_app.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tesina.smop_app.MainActivity;
import com.tesina.smop_app.R;

public class UserSignUp extends AppCompatActivity {
    TextView name, lastname, textViewEmail, textViewPassword,confirmPassword;
    Button signUp;
    FirebaseAuth authentication;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authentication = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        name = (TextView) findViewById(R.id.sign_up_name);
        lastname = (TextView) findViewById(R.id.sign_up_last_name);
        textViewEmail = (TextView) findViewById(R.id.sign_up_mail);
        textViewPassword = (TextView) findViewById(R.id.sign_up_password);
        confirmPassword = (TextView) findViewById(R.id.sign_up_confirm_password);
        signUp = (Button) findViewById(R.id.sign_up_button);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(textViewEmail.getText().toString(),textViewPassword.getText().toString());
            }
        });


    }

    private void register(final String email, String password) {
        if (checkInput()){
            authentication.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                DatabaseReference userReference = database.getReference("/users/" + authentication.getCurrentUser().getUid() + "/info");
                                userReference.setValue(new UserInformations(name.getText().toString(),lastname.getText().toString()));
                                sendEmailVerificaition();
                                startMainActivity();
                            } else {
                                textViewEmail.setError(getResources().getString(R.string.email_already_used));
                            }
                        }
                    });


        }
    }

    public void sendEmailVerificaition(){
        FirebaseUser user = authentication.getCurrentUser();
        if (user != null)
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("Email verification" , String.valueOf(task.isSuccessful()));
                }
            });
    }

    private void startMainActivity() {
        Intent mainActivity = new Intent(UserSignUp.this, MainActivity.class);
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

        if (!isCorrectEmail(textViewEmail.getText().toString())){
            checked = false;
            textViewEmail.setError(getResources().getString(R.string.error_invalid_email));
        }
        if (!isCorrectPassword(textViewPassword.getText().toString())){
            checked = false;
            textViewPassword.setError(getResources().getString(R.string.error_invalid_password));
        }


        if (! textViewPassword.getText().toString().equals(confirmPassword.getText().toString()) || confirmPassword.getText().toString().isEmpty()){
            checked = false;
            confirmPassword.setError(getResources().getString(R.string.error_passwrd_not_equal));
        }

        return checked;
    }

    private boolean isEmpty(TextView t){
        return t.getText().toString().length() > 0;
    }

    private boolean isCorrectPassword(String s) {
        return s.length()>=6;
    }

    private boolean isCorrectEmail(String s) {
        if (!isMailContainsCharacter(s)){
            textViewEmail.setError(getResources().getString(R.string.error_invalid_email));
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
        Intent main = new Intent(UserSignUp.this,UserSingIn.class);
        startActivity(main);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        this.finish();
    }
}

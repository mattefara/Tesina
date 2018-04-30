package tesina.smop_smart_shop.SingUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tesina.smop_smart_shop.Account.SmopAccount;
import tesina.smop_smart_shop.MainActivity;
import tesina.smop_smart_shop.R;
import tesina.smop_smart_shop.SignIn.Login;

/*
* todo: gestione migliore degli errori con messaggi più specifici (da fare in un secondo momento)
* todo: modifica del databese con email uniche e gestire errore*/

public class Register extends AppCompatActivity {
    TextView name, lastname, textViewEmail, textViewPassword,confirmPassword;
    Button signUp;
    FirebaseAuth authentication;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smop_register);

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
                                userReference.setValue(new SmopAccount(name.getText().toString(),lastname.getText().toString()));
                                startMainActivity();
                            } else {
                                textViewEmail.setError(getResources().getString(R.string.email_already_used));
                            }
                        }
                    });
        }
    }



    private void startMainActivity() {
        Intent mainActivity = new Intent(Register.this, MainActivity.class);
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
        Intent main = new Intent(Register.this,Login.class);
        startActivity(main);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        this.finish();
    }
}

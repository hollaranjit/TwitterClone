package com.example.actwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLoginActivity, btnSignUpLoginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        btnSignUpLoginActivity = findViewById(R.id.btnSignUpLoginActivity);

        btnLoginActivity.setOnClickListener(this);
        btnSignUpLoginActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {

            ParseUser.getCurrentUser().logOut();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
           case R.id.btnLoginActivity:

               if(edtLoginEmail.getText().toString().equals("")|| edtLoginPassword.getText().toString().equals(""))
               {
                   FancyToast.makeText(this,
                           "Email,Password is required!",
                           Toast.LENGTH_SHORT, FancyToast.INFO,
                           false).show();
               }else{
               ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                   @Override
                   public void done(ParseUser user, ParseException e) {


                       if(user == null)
                       {
                               FancyToast.makeText(LoginActivity.this,
                                       "Login Failed",
                                       Toast.LENGTH_SHORT, FancyToast.INFO,
                                       false).show();

                       }
                       else if(user.getUsername() != null && e == null)
                       {
                           FancyToast.makeText(LoginActivity.this,
                                   user.getUsername() + " is Logged in successfully",
                                   Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                   false).show();
                           transitionToSocialMediaActivity();
                       }
                   }
               });}
               break;
            case  R.id.btnSignUpLoginActivity:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void transitionToSocialMediaActivity() {

        Intent intent = new Intent(LoginActivity.this, TwitterUsers.class);
        startActivity(intent);
        finish();
    }

}
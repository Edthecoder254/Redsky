package com.marvedie.redskyshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            //start profile activity

            finish();
            startActivity(new Intent(getApplicationContext(),SendEmailActivity.class));
        }
        editTextEmail=  findViewById(R.id.editTextEmail);
        editTextPassword =  findViewById(R.id.editTextPassword);
        textViewSignup = findViewById(R.id.textViewSignup);
        buttonSignin =  findViewById(R.id.buttonSignin);

        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()){
            //email is empty
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            //stopping the function execution further
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum password length is 6");
            editTextPassword.requestFocus();
            return;
        }
        //if validation is okay we will show progress bar here because it is an internet activity
        //taking time


        progressDialog.setMessage("Please Wait as we log you into the system");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), SendEmailActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"Please Try again and Ensure email is already Registered",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignin:
                userLogin();
                break;
            case R.id.textViewSignup:
                //start signup activity
                finish();
                startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
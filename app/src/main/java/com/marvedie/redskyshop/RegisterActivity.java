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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // view objects
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;
    //firebase authentication object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            //start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), SendEmailActivity.class));
        }
        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextPassword =  findViewById(R.id.editTextPassword);
        textViewSignin = findViewById(R.id.textViewSignin);



        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }
    // register user method
    private void registerUser() {
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
        progressDialog.setMessage("Registering User");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            /*user is successfully registered and logged in
                            we will start the profile activity here
                            right now lets display a toast */
                            Toast.makeText(getApplicationContext(),"User Register Successful",Toast.LENGTH_SHORT).show();
                            //open new activity.
                            finish();
                            startActivity(new Intent(getApplicationContext(), SendEmailActivity.class));

                        } else {
                            //to check if email is already register
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "You are already Registered", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Error Occured, Check Credentials and Internet connectivity then Try Again",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        //if register button is pressed
        switch (view.getId()){
            case R.id.buttonRegister:
                registerUser();
                break;

            case R.id.textViewSignin:
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
    }
}


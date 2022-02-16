package com.example.learningsquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText edittext_email;
    EditText edittext_password;
    Button button_login;
    TextView textview_register;
    TextView textview_reset_password;

    FirebaseAuth fAuth;

    String AppID = "application-0-rsnkx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        edittext_email = (EditText) findViewById(R.id.edittext_Email1);
        edittext_password = (EditText) findViewById(R.id.edittext_password);
        button_login = (Button) findViewById(R.id.login_button);
        textview_reset_password= (TextView) findViewById(R.id.textview_reset_password);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
            finish();
        }

        textview_register = (TextView) findViewById(R.id.textview_register);

        textview_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        textview_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Reset_password.class));
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edittext_password.getText().toString().trim().length()<7){
                    edittext_password.setError("The passwords length should be more than 6 characters.");
                    return;
                }
                if(edittext_password.getText().toString().equals("")){
                    edittext_password.setError("The passwords is required.");
                    return;
                }

                if(edittext_email.getText().toString().equals("")){
                    edittext_email.setError("The Email is required.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(edittext_email.getText().toString().trim(),edittext_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "The user Logged in.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                    }
                });
            }
        });
    }

    //Exit application and releasing resources
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Hope to see you soon!", Toast.LENGTH_LONG).show();
        finishAffinity();
        System.exit(0);  // Releasing resources
    }
}
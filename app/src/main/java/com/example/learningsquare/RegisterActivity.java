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

public class RegisterActivity extends AppCompatActivity {

    EditText edittext_username;
    EditText edittext_password;
    EditText edittext_confirm_password;
    EditText edittext_email;
    Button button_register;
    TextView textview_login;
    FirebaseAuth fAuth;
    int confirmation=1;
    int pass=0;
    int user=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edittext_username = (EditText) findViewById(R.id.edittext_username);
        edittext_password = (EditText) findViewById(R.id.edittext_password);
        edittext_confirm_password = (EditText) findViewById(R.id.edittext_confirm_password);
        edittext_email = (EditText) findViewById(R.id.edittext_Email);
        button_register = (Button) findViewById(R.id.register_button);
        textview_login = (TextView) findViewById(R.id.textview_login);

        fAuth = FirebaseAuth.getInstance();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edittext_username.getText().toString().equals("") || edittext_password.getText().toString().equals("")||
                        edittext_confirm_password.getText().toString().equals("")
                ||edittext_email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Fill all the parts.", Toast.LENGTH_LONG).show();

                }
                else {
                    if (edittext_password.getText().toString().equals(edittext_confirm_password.getText().toString())) {
                        confirmation = 1;
                    } else {
                        confirmation = 0;
                    }
                    if (confirmation == 1) {
                        if (true)/*check if the username is unique*/ {
                            Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(LoginIntent);
                            user = 1;
                        } else {
                            edittext_username.setText("");
                            //  Toast.makeText(getApplicationContext(), "This username is chosen. Choose another username.", Toast.LENGTH_SHORT).show();
                            edittext_username.setError("This username is chosen");
                            user = 0;
                        }
                    } else {
                        edittext_password.setText("");
                        edittext_confirm_password.setText("");
                        edittext_confirm_password.setError("The passwords don't match.");

                    }
                    if (edittext_password.getText().toString().trim().length() < 7) {
                        edittext_password.setError("The passwords length should be more than 6 characters.");
                        pass = 0;
                    } else {
                        pass = 1;
                    }

                    if (user == 1 && pass == 1) {
                        fAuth.createUserWithEmailAndPassword(edittext_email.getText().toString().trim(), edittext_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "The user created.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                } else {

                                    Toast.makeText(getApplicationContext(), "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                                }
                            }
                        });
                    }
                }
                }

        });

        textview_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });
    }
}
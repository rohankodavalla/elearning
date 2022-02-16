package com.example.learningsquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_password extends AppCompatActivity {

    EditText edittext_email2;
    Button button_reset;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        fAuth = FirebaseAuth.getInstance();
        edittext_email2 = (EditText) findViewById(R.id.edittext_Email2);
        button_reset = (Button) findViewById(R.id.reset_button);

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = edittext_email2.getText().toString().trim();

                if (userEmail.isEmpty()){
                    edittext_email2.setError("The Email is required.");
                    return;
                }
                else{
                    fAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Go to your email and reset the password", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Reset_password.class));
                            }
                        }
                    });
                }
            }
        });
    }
}
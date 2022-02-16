package com.example.learningsquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPasswordActivity extends AppCompatActivity {

    EditText edittext_newPass;
    EditText edittext_rewrite_pass;
    Button button_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        edittext_newPass = (EditText) findViewById(R.id.edittext_password);
        edittext_rewrite_pass = (EditText) findViewById(R.id.edittext_confirm_Newpassword);
        button_reset = (Button) findViewById(R.id.set_newPass_button);

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edittext_newPass.getText().toString().equals(edittext_rewrite_pass.getText().toString()) &&
                        edittext_newPass.getText().toString().equals("")==false &&
                        edittext_rewrite_pass.getText().toString().equals("")==false){
                    Intent NewPasswordIntent = new Intent(NewPasswordActivity.this,LoginActivity.class);
                    startActivity(NewPasswordIntent);
                }
                else{
                    //Toast is the pop up message
                    Toast.makeText(getApplicationContext(), "Fill the parts correctly",
                            Toast.LENGTH_LONG).show();
                            edittext_newPass.setText("");
                            edittext_rewrite_pass.setText("");
                }
            }
        });
    }
}
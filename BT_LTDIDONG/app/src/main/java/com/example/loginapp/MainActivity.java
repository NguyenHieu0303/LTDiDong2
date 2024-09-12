package com.example.loginapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String ten = username.getText().toString();
                String pass = password.getText().toString();
                Log.i("Test credentials", "Username: $username and Password: $password");

            }
        });
    }
}
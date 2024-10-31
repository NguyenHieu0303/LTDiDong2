package com.example.constraintlayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button register;
    private EditText email;
    private EditText conformpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        TextView btn = findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        register = findViewById(R.id.btn_register);
        email =findViewById(R.id.input_Email);
        conformpassword = findViewById(R.id.inputInformPassword);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ten = username.getText().toString();
                String pass = password.getText().toString();
                String email_string = email.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Đăng ký thành công")
                        .setMessage("Nguyễn Thị Hiếu 22115053122114\n" + "Username: " + ten + "\nPassword: " + pass + "\nEmail: " + email_string)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
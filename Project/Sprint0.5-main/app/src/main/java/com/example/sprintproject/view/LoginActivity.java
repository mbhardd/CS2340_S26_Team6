package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.MainActivity;
import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button createAccountButton = findViewById(R.id.createAccountButton);
        Button quitButton = findViewById(R.id.quitButton);

        quitButton.setOnClickListener(v -> {
            finishAffinity();
        });

        createAccountButton.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        loginButton.setOnClickListener(v -> {

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!viewModel.isInputValid(email, password)) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.login(email, password, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Login failed",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
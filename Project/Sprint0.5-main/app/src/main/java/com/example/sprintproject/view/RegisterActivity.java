package com.example.sprintproject.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox staffCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        staffCheckBox = findViewById(R.id.staffCheckBox);

        Button registerButton = findViewById(R.id.createAccountButton);
        Button backButton = findViewById(R.id.quitButton);

        registerButton.setOnClickListener(v -> handleRegistration());

        backButton.setOnClickListener(v -> finish());
    }

    private void handleRegistration() {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isStaff = staffCheckBox.isChecked();

        if (!viewModel.isInputValid(email, password)) {
            Toast.makeText(this,
                    "Email and password cannot be empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.register(email, password, isStaff, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this,
                        "Account created successfully!",
                        Toast.LENGTH_SHORT).show();

                finish();

            } else {
                Toast.makeText(this,
                        "Registration failed: " + task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
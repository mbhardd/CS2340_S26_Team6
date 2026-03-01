package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.RegisterViewModel;
//import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;  // Reference to the RegisterViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);  // get the layout xml for Register Activity

        // Initialize the ViewModel, register viewmodel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Get references to the views in the layout from xml file
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        CheckBox staffCheckbox = findViewById(R.id.staffCheckbox);
        Button registerButton = findViewById(R.id.registerButton);
        TextView errorText = findViewById(R.id.errorText);

        // handle any error messages from the ViewModel
        registerViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                errorText.setText(error);
                errorText.setVisibility(View.VISIBLE);
            } else {
                errorText.setVisibility(View.GONE);
            }
        });

        // see the registration status, whether the user was successfully registered
        registerViewModel.isRegistered().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistered) {
                if (isRegistered != null && isRegistered) {
                    // If registration is successful, go to the LoginActivity
                    finish();  // Close the RegisterActivity
                }
            }
        });

        // Button click listener for registration
        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            // Check if the "I am a Staff Member" checkbox is checked
            boolean isStaff = staffCheckbox.isChecked();
            // Call the register method in ViewModel
            registerViewModel.register(email, password, isStaff);
        });
    }
}
package com.example.sprintproject.view;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sprintproject.R;
import com.example.sprintproject.Bottom_Navigation;
import com.example.sprintproject.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;  // Reference to the LoginViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Setting the layout for Login Activity

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Get id's from the xml files
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView createAccountButton = findViewById(R.id.createAccountButton);
        TextView errorText = findViewById(R.id.errorText);

        // Handle error messages from ViewModel
        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    // Display error message if login fails
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(errorMessage);
                }
            }
        });

        // Case for the current user having a successful login
        loginViewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    // If login is successful, navigate to the next screen (bottom navigation, now)
                    Intent intent = new Intent(LoginActivity.this, Bottom_Navigation.class);
                    startActivity(intent);
                    finish();  // Close LoginActivity so the user can't navigate back to it
                }
            }
        });

        // Button click listener for login
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            loginViewModel.login(email, password);  // Call the login method in ViewModel
        });

        // Link to create an account (go to RegisterActivity)
        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

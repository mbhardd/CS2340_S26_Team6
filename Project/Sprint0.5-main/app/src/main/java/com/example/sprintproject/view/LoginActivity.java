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
import com.example.sprintproject.BottomNavigation;
import com.example.sprintproject.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        loginViewModel.getIsStaffUser().observe(this, isStaff -> {
<<<<<<< HEAD
            //TODO: Here is where we can navigate to different things depending on user or staff
            if (isStaff) {
                // Go to StaffActivity
                startActivity(new Intent(this, BottomNavigation.class));
            } else {
                // Go to NormalUserActivity
                startActivity(new Intent(this, BottomNavigation.class));
            }
=======
            //Here is where we can navigate to different things depending on user or staff

            Intent intent = new Intent(LoginActivity.this, Bottom_Navigation.class);
            intent.putExtra("isStaff", isStaff);
            startActivity(intent);
>>>>>>> f992afa19ca0a6cc4f3d1fbb01a6944bd0a349fa
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

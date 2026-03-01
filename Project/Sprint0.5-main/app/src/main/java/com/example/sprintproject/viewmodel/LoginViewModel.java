package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.example.sprintproject.model.AuthRepository;

public class LoginViewModel extends ViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> currentUser;  // To get the current logged-in user
    private MutableLiveData<String> errorMessage;       // To get possible error messages

    // Constructor to initialize the repository and LiveData
    public LoginViewModel() {
        authRepository = new AuthRepository();
        currentUser = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    // Method to handle login logic
    public void login(String email, String password) {
        // Input validation, pdf has requirements
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Email and password cannot be empty.");
            return;
        }

        // Call the AuthRepository to log the user in
        authRepository.loginUser(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = authRepository.getCurrentUser();
                        currentUser.setValue(user);  // Set the logged-in user to LiveData
                    } else {
                        // Login failed
                        errorMessage.setValue("Login failed: " + task.getException().getMessage());
                    }
                });
    }

    // Getter for current user (LiveData)
    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    // Getter for error message (LiveData)
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
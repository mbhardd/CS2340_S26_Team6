package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.example.sprintproject.model.AuthRepository;

public class RegisterViewModel extends ViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<Boolean> isRegistered;     // To get successful registration
    private MutableLiveData<String> errorMessage;      // To get error messages

    // Constructor to initialize repository and LiveData
    public RegisterViewModel() {
        authRepository = new AuthRepository();
        isRegistered = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    // Method to handle registration logic
    public void register(String email, String password, boolean isStaff) {
        // Input validation
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Email and password cannot be empty.");
            return;
        }

        // Call the AuthRepository to register the user
        authRepository.registerUser(email, password, isStaff)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = authRepository.getCurrentUser();
                        if (user != null) {
                            isRegistered.setValue(true);  // Notify that registration is successful
                        }
                    } else {
                        // Registration failed
                        errorMessage.setValue("Registration failed: " + task.getException().getMessage());
                    }
                });
    }

    // Getter for isRegistered (LiveData kind)
    public LiveData<Boolean> isRegistered() {
        return isRegistered;
    }

    // Getter for error message (LiveData kind)
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
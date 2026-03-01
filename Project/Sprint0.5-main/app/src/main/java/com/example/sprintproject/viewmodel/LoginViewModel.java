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
    private MutableLiveData<Boolean> isStaffUser = new MutableLiveData<>();

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

        authRepository.loginAndCheckStaff(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        boolean staff = task.getResult();
                        isStaffUser.setValue(staff);

                    } else {
                        errorMessage.setValue("Login failed: " + task.getException().getMessage());
                    }
                });
    }


    // Getter for if staff (LiveData kind)
    public LiveData<Boolean> getIsStaffUser() {
        return isStaffUser;
    }

    // Getter for current user (LiveData kind)
    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    // Getter for error message (LiveData kind)
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
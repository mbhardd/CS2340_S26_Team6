package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.repository.AuthRepository;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends ViewModel {

    private final AuthRepository repository;

    public LoginViewModel() {
        repository = new AuthRepository();
    }

    public boolean isInputValid(String email, String password) {

        if (email == null || password == null) return false;

        email = email.trim();
        password = password.trim();

        return !email.isEmpty() && !password.isEmpty();
    }

    public void login(String email, String password,
                      com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {

        repository.login(email, password, listener);
    }
}

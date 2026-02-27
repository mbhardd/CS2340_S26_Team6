package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.repository.AuthRepository;
import com.google.firebase.auth.AuthResult;

public class RegisterViewModel extends ViewModel {

    private final AuthRepository repository;

    public RegisterViewModel() {
        repository = new AuthRepository();
    }

    public boolean isInputValid(String email, String password) {

        if (email == null || password == null) return false;

        email = email.trim();
        password = password.trim();

        return !email.isEmpty() && !password.isEmpty();
    }

    public void register(String email, String password, boolean isStaff,
                         com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {

        repository.register(email, password, isStaff, listener);
    }
}

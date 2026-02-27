package com.example.sprintproject.repository;

import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AuthRepository {

    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseReference;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    // LOGIN
    public void login(String email, String password,
                      com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    // REGISTER
    public void register(String email, String password, boolean isStaff,
                         com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        String uid = firebaseAuth.getCurrentUser().getUid();
                        User user = new User(uid, email, isStaff);

                        databaseReference.child(uid).setValue(user);
                    }

                    listener.onComplete(task);
                });
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
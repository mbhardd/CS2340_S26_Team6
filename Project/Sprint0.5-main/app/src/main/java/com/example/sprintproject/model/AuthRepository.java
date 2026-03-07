package com.example.sprintproject.model;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//Note: The yellow lines throughout are that there could be null pointer exceptions
public class AuthRepository {

    private FirebaseAuth mAuth;
    private static volatile AuthRepository instance;
    private DatabaseReference database;

    private AuthRepository() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    public static AuthRepository getInstance() {
        if (instance == null) {
            synchronized (AuthRepository.class) { // Thread-safe
                if (instance == null) {
                    instance = new AuthRepository();
                }
            }
        }
        return instance;
        }


    // Register user
    public Task<AuthResult> registerUser(String email, String password, boolean isStaff) {
        return mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {

                            User newUser = new User(email, isStaff);

                            database.child(user.getUid()).setValue(newUser)
                                    .addOnFailureListener(e ->
                                            Log.w("AuthRepository", "Error saving user data", e));
                        }
                    } else {
                        Log.w("AuthRepository", "Registration failed", task.getException());
                    }
                });
    }

    // Login user
    public Task<AuthResult> loginUser(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    //Login + check staff identity method
    public Task<Boolean> loginAndCheckStaff(String email, String password) {

        TaskCompletionSource<Boolean> result = new TaskCompletionSource<>();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(authTask -> {

                    if (!authTask.isSuccessful()) {
                        result.setException(authTask.getException());
                        return;
                    }

                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user == null) {
                        result.setResult(false);
                        return;
                    }

                    database.child(user.getUid())
                            .get()
                            .addOnCompleteListener(dbTask -> {

                                if (dbTask.isSuccessful()) {
                                    DataSnapshot snapshot = dbTask.getResult();
                                    Boolean isStaff = snapshot.child("staff").
                                            getValue(Boolean.class);
                                    result.setResult(isStaff != null && isStaff);
                                } else {
                                    result.setException(dbTask.getException());
                                }
                            });
                });

        return result.getTask();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void logout() {
        mAuth.signOut();
    }
}
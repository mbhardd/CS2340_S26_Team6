package com.example.sprintproject.model;

import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {

    private FirebaseAuth mAuth;             // FirebaseAuth instance used for the authentication
    private FirebaseFirestore firestore;    // FirebaseFirestore instance to store the user's data

    public AuthRepository() {
        mAuth = FirebaseAuth.getInstance();             // Initialize FirebaseAuth
        firestore = FirebaseFirestore.getInstance();   // Initialize Firestore
    }

    // Register a new user with email, password, and staff status (bool)
    public Task<AuthResult> registerUser(String email, String password, boolean isStaff) {
        // Attempt to create a new user using FirebaseAuth
        return mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Create a User object to store in Firestore
                            DocumentReference userRef = firestore.collection("users").document(user.getUid());
                            User newUser = new User(email, isStaff);  // Create User object with email and isStaff

                            // Save details to Firestore (realtime db)
                            userRef.set(newUser)
                                    .addOnCompleteListener(firestoreTask -> {
                                        if (!firestoreTask.isSuccessful()) {
                                            Log.w("AuthRepository", "Error storing user data.", firestoreTask.getException());
                                        }
                                    });
                        }
                    } else {
                        Log.w("AuthRepository", "Registration failed.", task.getException());
                    }
                });
    }

    // Login an existing user with email and password
    public Task<AuthResult> loginUser(String email, String password) {
        // Attempt to log in the user with FirebaseAuth
        return mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Handle login success
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // User successfully logged in
                            Log.d("AuthRepository", "Login successful for user: " + user.getEmail());
                        }
                    } else {
                        // Handle login failure
                        Log.w("AuthRepository", "Login failed.", task.getException());
                    }
                });
    }

    // Check if the current user is logged in
    public FirebaseUser getCurrentUser() {
        // Returns the currently logged-in Firebase user, or null if not logged in
        return mAuth.getCurrentUser();
    }

    // Logout the current user, signout defined in fb
    public void logout() {
        mAuth.signOut();  // Sign out the current user from Firebase
    }

    // Helper method to check if the user is a staff member (from Firestore)
    //This is diff from the getStatus function in the user class.
    public Task<Boolean> checkIfStaff(String userId) {
        DocumentReference userRef = firestore.collection("users").document(userId);
        return userRef.get().continueWith(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Boolean isStaff = task.getResult().getBoolean("isStaff");
                return isStaff != null && isStaff;
            }
            return false;
        });
    }
}
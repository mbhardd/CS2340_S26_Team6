package com.example.sprintproject.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.AuthRepository;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import java.util.HashMap;
import java.util.Map;


public class IssueCreationViewModel extends ViewModel {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AuthRepository authRepository;
    public IssueCreationViewModel() {
        authRepository = AuthRepository.getInstance();

    }
    public void submitIssue(String title, String category, String priority, String location,
                            String description) {
        String uID = authRepository.getCurrentUser().getUid();
        Map<String, Object> issue = new HashMap<>();
        issue.put("title", title);
        issue.put("category", category);
        issue.put("priority", priority);
        issue.put("location", location);
        issue.put("description", description);
        issue.put("status", "open");
        issue.put("creatorUid", uID);
        issue.put("timestamp", System.currentTimeMillis());

        db.collection("issues").add(issue);

    }
}


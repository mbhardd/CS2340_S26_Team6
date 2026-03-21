package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.AuthRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;


public class IssueCreationViewModel extends ViewModel {

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    private AuthRepository authRepository;
    public IssueCreationViewModel() {
        authRepository = AuthRepository.getInstance();

    }
    public void submitIssue(String title, String category, String priority, String location,
                                               String description, String initials) {
        String uID = authRepository.getCurrentUser().getUid();
        Map<String, Object> issue = new HashMap<>();
        issue.put("title", title);
        issue.put("category", category);
        issue.put("priority", priority);
        issue.put("location", location);
        issue.put("description", description);
        issue.put("initials", initials);
        issue.put("status", "Not Started");
        issue.put("creatorUid", uID);
        issue.put("timestamp", System.currentTimeMillis());
        db.child("issues").push().setValue(issue);

    }
}


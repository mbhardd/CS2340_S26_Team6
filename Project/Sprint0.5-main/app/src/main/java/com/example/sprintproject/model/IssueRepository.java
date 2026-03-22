package com.example.sprintproject.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueRepository {

    private static IssueRepository instance;
    private final DatabaseReference issuesRef;

    private IssueRepository() {
        issuesRef = FirebaseDatabase.getInstance().getReference("issues");
    }

    public static synchronized IssueRepository getInstance() {
        if (instance == null) {
            instance = new IssueRepository();
        }
        return instance;
    }

    public LiveData<List<Issue>> getIssues() {
        MutableLiveData<List<Issue>> issuesLiveData = new MutableLiveData<>();

        issuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Issue> issueList = new ArrayList<>();

                for (DataSnapshot issueSnapshot : snapshot.getChildren()) {
                    Issue issue = issueSnapshot.getValue(Issue.class);

                    if (issue != null) {
                        issue.setID(issueSnapshot.getKey());
                        issueList.add(issue);
                    }
                }

                Collections.sort(issueList, (issue1, issue2) -> {
                    Long t1 = issue1.getTimestamp() != null ? issue1.getTimestamp() : 0L;
                    Long t2 = issue2.getTimestamp() != null ? issue2.getTimestamp() : 0L;
                    return t2.compareTo(t1); // newest first
                });

                issuesLiveData.setValue(issueList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                issuesLiveData.setValue(new ArrayList<>());
            }
        });

        return issuesLiveData;
    }
}

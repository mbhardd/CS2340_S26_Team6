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
import java.util.HashMap;
import java.util.Map;

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

    public void addUpdateToIssue(String issueId, IssueUpdate update) {
        String updateId = issuesRef.child(issueId)
                .child("updates")
                .push()
                .getKey();

        if (updateId != null) {
            issuesRef.child(issueId)
                    .child("updates")
                    .child(updateId)
                    .setValue(update);
        }
    }

    public void updateLastUpdated(String issueId, long time) {
        issuesRef.child(issueId)
                .child("lastUpdated")
                .setValue(time);
    }
    public LiveData<List<IssueUpdate>> getUpdatesForIssue(String issueId) {
        MutableLiveData<List<IssueUpdate>> updatesLiveData = new MutableLiveData<>();

        issuesRef.child(issueId).child("updates")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        List<IssueUpdate> updates = new ArrayList<>();

                        for (DataSnapshot child : snapshot.getChildren()) {
                            IssueUpdate update = child.getValue(IssueUpdate.class);
                            if (update != null) {
                                update.setId(child.getKey());
                                updates.add(update);
                            }
                        }

                        Collections.sort(updates, (u1, u2) ->
                                Long.compare(u1.getTimestamp(), u2.getTimestamp()));

                        updatesLiveData.setValue(updates);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        updatesLiveData.setValue(new ArrayList<>());
                    }
                });
        return updatesLiveData;
    }
    
    public void updateAssignedStaff(String issueId, String staffEmail) {
        issuesRef.child(issueId).child("assignedStaff").setValue(staffEmail);
    }
    
    public void updateStatus(String issueId, String newStatus) {
        issuesRef.child(issueId).child("status").setValue(newStatus);
    }

    public void updateStatusWithHistory(String issueId, String newStatus,
                                        IssueUpdate update) {
        String updateId = issuesRef.child(issueId).child("updates").push().getKey();

        if (updateId == null) {
            return;
        }

        Map<String, Object> changes = new HashMap<>();
        changes.put(issueId + "/status", newStatus);
        changes.put(issueId + "/lastUpdated", System.currentTimeMillis());
        changes.put(issueId + "/updates/" + updateId, update);

        issuesRef.updateChildren(changes);
    }

    public LiveData<List<User>> getStaffUsers() {
        MutableLiveData<List<User>> staffLiveData = new MutableLiveData<>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> staffList = new ArrayList<>();

                DataSnapshot snapshot = task.getResult();

                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    User user = userSnap.getValue(User.class);

                    if (user != null && user.isStaff()) {
                        staffList.add(user);
                    }
                }

                staffLiveData.setValue(staffList);
            } else {
                staffLiveData.setValue(new ArrayList<>());
            }
        });

        return staffLiveData;
    }

}

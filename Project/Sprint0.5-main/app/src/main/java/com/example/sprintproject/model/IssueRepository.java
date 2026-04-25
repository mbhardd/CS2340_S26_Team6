package com.example.sprintproject.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IssueRepository {

    private static IssueRepository instance;
    private final DatabaseReference issuesRef;
    private static final String UPDATES_NODE = "updates";

    private IssueRepository() {
        issuesRef = FirebaseDatabase.getInstance().getReference("issues");
    }

    public static synchronized IssueRepository getInstance() {
        if (instance == null) {
            instance = new IssueRepository();
        }
        return instance;
    }

    public LiveData<List<Issue>> getIssues(String userId) {
        MutableLiveData<List<Issue>> issuesLiveData = new MutableLiveData<>();
        DatabaseReference upvotedRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("upvotedIssues");

        issuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Issue> issueList = new ArrayList<>();

                upvotedRef.get().addOnCompleteListener(upvoteTask -> {
                    Set<String> upvotedIds = new HashSet<>();
                    if (upvoteTask.isSuccessful()) {
                        for (DataSnapshot child : upvoteTask.getResult().getChildren()) {
                            upvotedIds.add(child.getKey());
                        }
                    }
                    for (DataSnapshot issueSnapshot : snapshot.getChildren()) {
                        Issue issue = issueSnapshot.getValue(Issue.class);
                        if (issue != null) {
                            issue.setID(issueSnapshot.getKey());
                            issue.setUpvoted(upvotedIds.contains(issue.getId()));
                            issueList.add(issue);
                        }
                    }

                    Collections.sort(issueList, (issue1, issue2) -> {
                        Long t1 = issue1.getTimestamp() != null ? issue1.getTimestamp() : 0L;
                        Long t2 = issue2.getTimestamp() != null ? issue2.getTimestamp() : 0L;
                        return t2.compareTo(t1);
                    });

                    issuesLiveData.setValue(issueList);
                });
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
                .child(UPDATES_NODE)
                .push()
                .getKey();

        if (updateId != null) {
            issuesRef.child(issueId)
                    .child(UPDATES_NODE)
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

        issuesRef.child(issueId).child(UPDATES_NODE)
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
        String updateId = issuesRef.child(issueId).child(UPDATES_NODE).push().getKey();

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

    private void saveIssue(Issue issue) {
        String issueId = issuesRef.push().getKey();

        if (issueId != null) {
            issue.setID(issueId);
            issuesRef.child(issueId).setValue(issue);
        }
    }

    public void createIssueWithWeather(Issue issue) {
        WeatherRepository weatherRepository = new WeatherRepository();

        weatherRepository.getCurrentWeather(new WeatherCallback() {
            @Override
            public void onSuccess(WeatherData weatherData) {
                issue.setWeatherSummary(weatherData.getSummary());
                issue.setTemperature(weatherData.getTemperature());
                issue.setWeatherCondition(weatherData.getCondition());

                saveIssue(issue);
            }

            @Override
            public void onFailure(String errorMessage) {
                issue.setWeatherSummary(errorMessage);
                issue.setWeatherCondition("Unavailable");
                issue.setTemperature(null);

                saveIssue(issue);
            }
        });
    }
    public void toggleUpvote(String issueId, String userId, Runnable onComplete) {
        DatabaseReference upvoteRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("upvotedIssues")
                .child(issueId);

        DatabaseReference countRef = issuesRef.child(issueId).child("upvoteCount");

        //prevent race conditions
        upvoteRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData data) {
                if (data.getValue() == null) {

                    data.setValue(true);
                } else {

                    data.setValue(null);
                }
                return Transaction.success(data);
            }

            @Override
            public void onComplete(DatabaseError error, boolean committed,
                                   DataSnapshot snapshot) {
                if (!committed || error != null) {
                    onComplete.run();
                    return;
                }


                boolean nowUpvoted = snapshot.exists();

                countRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData data) {
                        Integer count = data.getValue(Integer.class);
                        if (nowUpvoted) {
                            data.setValue(count == null ? 1 : count + 1);
                        } else {
                            data.setValue(count == null || count <= 0 ? 0 : count - 1);
                        }
                        return Transaction.success(data);
                    }

                    @Override
                    public void onComplete(DatabaseError error, boolean committed,
                                           DataSnapshot snapshot) {
                        onComplete.run();
                    }
                });
            }
        });
    }
}

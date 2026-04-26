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

import java.util.HashSet;
import java.util.Set;

public class WatchRepository {

    private static WatchRepository instance;
    private final DatabaseReference usersRef;
    private final DatabaseReference issuesRef;

    private WatchRepository() {
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        issuesRef = FirebaseDatabase.getInstance().getReference("issues");
    }

    public static synchronized WatchRepository getInstance() {
        if (instance == null) {
            instance = new WatchRepository();
        }
        return instance;
    }


    public LiveData<Set<String>> getWatchedIssueIds(String userId) {
        MutableLiveData<Set<String>> liveData = new MutableLiveData<>();

        usersRef.child(userId).child("watchedIssues")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Set<String> ids = new HashSet<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            ids.add(child.getKey());
                        }
                        liveData.setValue(ids);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        liveData.setValue(new HashSet<>());
                    }
                });

        return liveData;
    }


    public void toggleWatch(String userId, String issueId, Runnable onComplete) {
        issuesRef.child(issueId).get().addOnCompleteListener(issueTask -> {
            if (!issueTask.isSuccessful() || !issueTask.getResult().exists()) {
                if (onComplete != null) {
                    onComplete.run();
                }
                return;
            }

            DatabaseReference watchRef = usersRef
                    .child(userId)
                    .child("watchedIssues")
                    .child(issueId);

            watchRef.runTransaction(new Transaction.Handler() {
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
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            });
        });
    }


    public LiveData<Boolean> isWatching(String userId, String issueId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();

        usersRef.child(userId).child("watchedIssues").child(issueId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        liveData.setValue(snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        liveData.setValue(false);
                    }
                });

        return liveData;
    }
}
package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Issue;

public class StaffFragment extends Fragment {

    private RecyclerView rvStaffIssues;
    private StaffAdapter adapter;

    public StaffFragment() {
        super(R.layout.fragment_staff);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvStaffIssues = view.findViewById(R.id.rvStaffIssues);

        adapter = new StaffAdapter();

        rvStaffIssues.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvStaffIssues.setAdapter(adapter);

        // fake data so we can see it working
        // (Ria you can remove this later when connecting to Firebase)
        adapter.setIssueList(java.util.Arrays.asList(
                new Issue(
                        "Ceiling Leak",        // title
                        "Maintenance",         // category
                        "High",                // priority
                        "JD",                  // initials
                        "Building A",          // location
                        "Water dripping",      // description
                        "user123",             // creatorUid
                        "SUBMITTED",           // status
                        System.currentTimeMillis() // timestamp
                ),
                new Issue(
                        "Broken AC",
                        "HVAC",
                        "Medium",
                        "AB",
                        "Room 204",
                        "AC not working",
                        "user456",
                        "IN_PROGRESS",
                        System.currentTimeMillis()
                )
        ));
    }
}
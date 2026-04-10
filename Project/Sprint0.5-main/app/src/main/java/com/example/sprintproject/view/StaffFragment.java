package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.StaffViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends Fragment {

    private RecyclerView rvStaffIssues;
    private StaffAdapter adapter;
    private StaffViewModel viewModel;
    private TextView tvEmptyState;
    private final List<User> staffList = new ArrayList<>();
    private User currentUser;

    public StaffFragment() {
        super(R.layout.fragment_staff);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvStaffIssues = view.findViewById(R.id.rvStaffIssues);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        currentUser = getCurrentAppUser();

        adapter = new StaffAdapter(staffList);
        rvStaffIssues.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvStaffIssues.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);

        viewModel.getIssues().observe(getViewLifecycleOwner(), issues -> {
            adapter.setIssueList(issues);

            if (issues == null || issues.isEmpty()) {
                tvEmptyState.setVisibility(View.VISIBLE);
                rvStaffIssues.setVisibility(View.GONE);
            } else {
                tvEmptyState.setVisibility(View.GONE);
                rvStaffIssues.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getStaffUsers().observe(getViewLifecycleOwner(), users -> {
            staffList.clear();
            if (users != null) {
                staffList.addAll(users);
            }
            adapter.notifyDataSetChanged();
        });

        // Example usage once your buttons / inputs are wired:
        // if (currentUser != null) {
        //     viewModel.addComment(issueId, currentUser, "Test comment");
        //     viewModel.addStaffNote(issueId, currentUser, "Test staff note");
        //     viewModel.changeStatus(issueId, currentUser, oldStatus, newStatus);
        // }
    }

    private User getCurrentAppUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            return null;
        }

        String email = firebaseUser.getEmail();
        if (email == null) {
            email = "";
        }

        return new User(email, true);
    }
}
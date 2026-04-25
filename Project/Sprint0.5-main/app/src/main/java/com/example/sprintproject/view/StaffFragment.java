package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.StaffViewModel;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends Fragment {

    private RecyclerView rvStaffIssues;
    private StaffAdapter adapter;
    private StaffViewModel viewModel;
    private TextView tvEmptyState;
    private final List<User> staffList = new ArrayList<>();

    public StaffFragment() {
        super(R.layout.fragment_staff);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvStaffIssues = view.findViewById(R.id.rvStaffIssues);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);

        adapter = new StaffAdapter(staffList, viewModel);
        rvStaffIssues.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvStaffIssues.setAdapter(adapter);


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

        viewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                //resetting so it doesn't fire again
                viewModel.clearSuccessMessage();
            }
        });
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                //resetting so it doesn't fire again
                viewModel.clearErrorMessage();
            }
        });

    }


}
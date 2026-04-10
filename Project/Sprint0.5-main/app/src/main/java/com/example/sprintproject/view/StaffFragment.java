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


import com.example.sprintproject.viewmodel.StaffViewModel;

public class StaffFragment extends Fragment {

    private RecyclerView rvStaffIssues;
    private StaffAdapter adapter;
    private StaffViewModel viewModel;
    private TextView tvEmptyState;

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
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);

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
    }
}
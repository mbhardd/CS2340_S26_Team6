package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.AllIssuesFilter;
import com.example.sprintproject.viewmodel.CategoryFilter;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;
import com.example.sprintproject.viewmodel.MostRecentUpdateStrategy;
import com.example.sprintproject.viewmodel.OpenIssuesFilter;
import com.example.sprintproject.viewmodel.PriorityFilter;
import com.example.sprintproject.viewmodel.PriorityStrategy;
import com.example.sprintproject.viewmodel.RecentStrategy;
import com.example.sprintproject.viewmodel.UpvoteSortStrategy;
import com.example.sprintproject.viewmodel.WatchedIssuesFilter;
import com.google.android.material.button.MaterialButton;

import android.widget.TextView;


public class IssueFeedFragment extends Fragment {

    private RecyclerView rvIssues;
    private IssueFeedAdapter adapter;
    private IssueFeedViewModel viewModel;
    private TextView tvEmptyState;
    private MaterialButton btnFilterBy;

    public IssueFeedFragment() {
        super(R.layout.fragment_issue_feed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        btnFilterBy = view.findViewById(R.id.btnFilterBy);
        rvIssues = view.findViewById(R.id.rvIssues);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        viewModel = new ViewModelProvider(this).get(IssueFeedViewModel.class);

        adapter = new IssueFeedAdapter(viewModel, getViewLifecycleOwner());
        rvIssues.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvIssues.setAdapter(adapter);

        viewModel.getIssues().observe(getViewLifecycleOwner(), issues -> {
            adapter.setIssueList(issues);

            if (issues == null || issues.isEmpty()) {
                tvEmptyState.setVisibility(View.VISIBLE);
                rvIssues.setVisibility(View.GONE);
            } else {
                tvEmptyState.setVisibility(View.GONE);
                rvIssues.setVisibility(View.VISIBLE);
            }
        });

        btnFilterBy.setOnClickListener(v -> showFilterDialog());
    }

    private void showFilterDialog() {
        String[] options = {
            "All Issues",
            "Open Issues",
            "Watched Issues",
            "Filter by Priority",
            "Filter by Category",
            "Sort by Recent",
            "Sort by Priority",
            "Sort by Recently Updated",
            "Sort by Most Upvotes"
        };

        new AlertDialog.Builder(getContext())
                .setTitle("Filter By")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                    case 0:
                        viewModel.setFilter(new AllIssuesFilter());
                        break;
                    case 1:
                        viewModel.setFilter(new OpenIssuesFilter());
                        break;
                    case 2:
                        viewModel.setFilter(new WatchedIssuesFilter(viewModel.getWatchedIds()));
                        break;
                    case 3:
                        showPriorityDialog();
                        break;
                    case 4:
                        showCategoryDialog();
                        break;
                    case 5:
                        viewModel.setSort(new RecentStrategy());
                        break;
                    case 6:
                        viewModel.setSort(new PriorityStrategy());
                        break;
                    case 7:
                        viewModel.setSort(new MostRecentUpdateStrategy());
                        break;
                    case 8:
                        viewModel.setSort(new UpvoteSortStrategy());
                        break;
                    default:
                        break;
                    }
                })
                .show();
    }

    private void showPriorityDialog() {
        String[] priorities = {"Low", "Medium", "High"};

        new AlertDialog.Builder(getContext())
                .setTitle("Select Priority")
                .setItems(priorities, (dialog, which) -> {
                    viewModel.setFilter(new PriorityFilter(priorities[which]));
                })
                .show();
    }

    private void showCategoryDialog() {
        String[] categories = {"Plumbing", "Flooring", "Electrical", "Furniture", "Other"};

        new AlertDialog.Builder(getContext())
                .setTitle("Select Category")
                .setItems(categories, (dialog, which) -> {
                    viewModel.setFilter(new CategoryFilter(categories[which]));
                })
                .show();
    }
}
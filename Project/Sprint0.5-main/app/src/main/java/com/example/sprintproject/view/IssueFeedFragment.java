package com.example.sprintproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssueFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueFeedFragment extends Fragment {

    private RecyclerView rvIssues;
    private IssueFeedAdapter adapter;
    private IssueFeedViewModel viewModel;

    public IssueFeedFragment(){
        super(R.layout.fragment_issue_feed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        rvIssues = view.findViewById(R.id.rvIssues);

        adapter = new IssueFeedAdapter();
        rvIssues.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvIssues.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(IssueFeedViewModel.class);

        viewModel.getIssues().observe(getViewLifecycleOwner(), issues -> {
            adapter.setIssueList(issues);
        });
    }

}
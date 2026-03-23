package com.example.sprintproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sprintproject.R;
import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.viewmodel.IssueCreationViewModel;

/**
 * A simple {@link Fragment} subclass.

 */
public class IssueCreationFragment extends Fragment {

    private IssueCreationViewModel viewModel;
    private AuthRepository authRepository;

    private EditText inputTitle;
    private Spinner inputCategory;
    private Spinner inputPriority;
    private EditText inputLocation;
    private EditText inputDescription;
    private EditText inputInitials;
    private Button submitButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_creation, container, false);
        inputTitle = view.findViewById(R.id.inputTitle);
        inputCategory = view.findViewById(R.id.inputCategory);
        inputPriority = view.findViewById(R.id.inputPriority);

        String[] priorities = {"Low", "Medium", "High"};

        ArrayAdapter<String> adapter =
            new ArrayAdapter<>(requireContext(),
                    R.layout.spinner_item,
                    priorities);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        inputPriority.setAdapter(adapter);

        String[] categories = {"Plumbing", "Flooring", "Electrical", "Furniture", "Other"};

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(requireContext(),
                        R.layout.spinner_item,
                        categories);

        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        inputCategory.setAdapter(categoryAdapter);

        inputLocation = view.findViewById(R.id.inputLocation);
        inputDescription = view.findViewById(R.id.inputDescription);
        inputInitials = view.findViewById(R.id.inputInitials);
        submitButton = view.findViewById(R.id.btnSubmitIssue);

        authRepository = AuthRepository.getInstance();
        viewModel = new ViewModelProvider(this).get(IssueCreationViewModel.class);
        submitButton.setOnClickListener(v -> submitForm());
        return view;
    }

    public boolean error(String fieldName, String value, EditText input) {
        if (value.isEmpty()) {
            input.setError(fieldName + " is required");
            return true;
        }
        return false;
    }
    public void submitForm() {
        boolean valid = true;
        String title = inputTitle.getText().toString().trim();
        String category = inputCategory.getSelectedItem().toString();
        String priority = inputPriority.getSelectedItem().toString();
        String location = inputLocation.getText().toString().trim();
        String initials = inputInitials.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();
        if (error("Title", title, inputTitle)) {
            valid = false;
        }

        if (priority.isEmpty()) {
            valid = false;
        }
        if (error("Location", location, inputLocation)) {
            valid = false;
        }
        if (error("Description", description, inputDescription)) {
            valid = false;
        }
        if (error("Initials", initials, inputInitials)) {
            valid = false;
        }
        if (!valid) {
            return;
        }
        viewModel.submitIssue(title, category, priority, location, description, initials);
        inputTitle.setText("");
        inputCategory.setSelection(0);
        inputPriority.setSelection(0);
        inputLocation.setText("");
        inputDescription.setText("");
        inputInitials.setText("");

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_issueCreationFragment_to_issueFeedFragment);

    }
}
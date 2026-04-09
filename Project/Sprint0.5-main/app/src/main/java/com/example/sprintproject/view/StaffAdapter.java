package com.example.sprintproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Issue;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private List<Issue> issueList = new ArrayList<>();

    public void setIssueList(List<Issue> issues) {
        this.issueList = issues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_staff_issue, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        Issue issue = issueList.get(position);

        holder.tvIssueTitle.setText(issue.getTitle());
        holder.tvStatus.setText(issue.getStatus());
        holder.tvCategory.setText(issue.getCategory());
        holder.tvAssignedStaff.setText("Assigned: " + issue.getAssignedStaff());
        holder.tvStaffUpdate.setText("Update: " + issue.getLatestUpdate());

        holder.btnStatus.setOnClickListener(v -> {

            View dropdownView = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_status_dropdown, null);

            PopupWindow popupWindow = new PopupWindow(
                    dropdownView,
                    holder.btnStatus.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );

            popupWindow.setElevation(10f);

            dropdownView.findViewById(R.id.optionSubmitted).setOnClickListener(view -> {
                holder.btnStatus.setText("Submitted");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionReview).setOnClickListener(view -> {
                holder.btnStatus.setText("In Review");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionProgress).setOnClickListener(view -> {
                holder.btnStatus.setText("In Progress");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionResolved).setOnClickListener(view -> {
                holder.btnStatus.setText("Resolved");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionClosed).setOnClickListener(view -> {
                holder.btnStatus.setText("Closed");
                popupWindow.dismiss();
            });

            popupWindow.showAsDropDown(holder.btnStatus, 0, 8);
        });

        holder.btnAssign.setOnClickListener(v -> {

            View dropdownView = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_assign_dropdown, null);

            PopupWindow popupWindow = new PopupWindow(
                    dropdownView,
                    holder.btnAssign.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );

            popupWindow.setElevation(10f);

            dropdownView.findViewById(R.id.optionJohn).setOnClickListener(view -> {
                holder.btnAssign.setText("John");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionSarah).setOnClickListener(view -> {
                holder.btnAssign.setText("Sarah");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionMike).setOnClickListener(view -> {
                holder.btnAssign.setText("Mike");
                popupWindow.dismiss();
            });

            dropdownView.findViewById(R.id.optionEmma).setOnClickListener(view -> {
                holder.btnAssign.setText("Emma");
                popupWindow.dismiss();
            });

            popupWindow.showAsDropDown(holder.btnAssign, 0, 8);
        });
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    static class StaffViewHolder extends RecyclerView.ViewHolder {

        TextView tvIssueTitle;
        TextView tvStatus;
        TextView tvCategory;
        TextView tvAssignedStaff;
        TextView tvStaffUpdate;
        MaterialButton btnAssign;
        MaterialButton btnStatus;
        EditText inputStaffNote;

        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIssueTitle = itemView.findViewById(R.id.tvIssueTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAssignedStaff = itemView.findViewById(R.id.tvAssignedStaff);
            tvStaffUpdate = itemView.findViewById(R.id.tvStaffUpdate);

            btnAssign = itemView.findViewById(R.id.btnAssign);
            btnStatus = itemView.findViewById(R.id.btnStatus);
            inputStaffNote = itemView.findViewById(R.id.addUpdateNote);
        }
    }
}
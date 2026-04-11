package com.example.sprintproject.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.StaffViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private List<Issue> issueList = new ArrayList<>();
    private List<User> staffList;

    private StaffViewModel viewModel;

    private AuthRepository authRepository;

    public StaffAdapter(List<User> staffList, StaffViewModel viewModel) {
        this.staffList = staffList;
        authRepository = AuthRepository.getInstance();
        this.viewModel = viewModel;
    }

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
        holder.inputStaffNote.setText("");

        holder.tvIssueTitle.setText(issue.getTitle());
        holder.tvStatus.setText(issue.getStatus());
        holder.tvCategory.setText(issue.getCategory());
        holder.tvAssignedStaff.setText("Assigned: " + issue.getAssignedStaff());
        holder.btnAssign.setText("Assign");


        holder.btnUpdate.setOnClickListener(v -> {
            String text = holder.inputStaffNote.getText().toString();
            viewModel.addStaffNote(issue.getId(), authRepository.getCachedUser(), text);
            holder.tvStaffUpdate.setText("");

        });


        viewModel.getUpdatesForIssue(issue.getId()).observeForever(updates -> {
            String latestNote = null;

            for (int i = updates.size() - 1; i >= 0; i--) {
                if (updates.get(i).getType().equals("STAFF_NOTE")) {
                    latestNote = updates.get(i).getContent();
                    break;
                }
            }

            if (latestNote != null) {
                holder.tvStaffUpdate.setText("Update: " + latestNote);
            } else {
                holder.tvStaffUpdate.setText("No updates yet");
            }
        });


        holder.btnStatus.setOnClickListener(v -> {

            LinearLayout layout = new LinearLayout(v.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);
            layout.setBackgroundColor(Color.WHITE);

            PopupWindow popupWindow = new PopupWindow(
                    layout,
                    holder.btnStatus.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );

            for (IssueStatus status: IssueStatus.values()) {

                TextView option = new TextView(v.getContext());
                option.setText(viewModel.formatStatus(status));
                option.setPadding(20, 20, 20, 20);

                option.setOnClickListener(view -> {
                   viewModel.changeStatus(issue.getId(), authRepository.getCachedUser(),
                           issue.getStatusEnum(), status);
                   holder.tvStatus.setText(issue.getStatus());
                   popupWindow.dismiss();
                });

                layout.addView(option);
            }

            popupWindow.showAsDropDown(holder.btnStatus, 0, 8);
        });

        holder.btnAssign.setOnClickListener(v -> {

            LinearLayout layout = new LinearLayout(v.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);
            layout.setBackgroundColor(Color.WHITE);

            PopupWindow popupWindow = new PopupWindow(
                    layout,
                    holder.btnAssign.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );

            for (User user : staffList) {

                TextView option = new TextView(v.getContext());
                option.setText(user.getEmail());
                option.setPadding(20, 20, 20, 20);

                option.setOnClickListener(view -> {
                    viewModel.changeAssignedStaff(issue.getId(), user.getEmail());
                    holder.tvAssignedStaff.setText("Assigned: " + issue.getAssignedStaff());
                    popupWindow.dismiss();
                });

                layout.addView(option);
            }

            popupWindow.showAsDropDown(holder.btnAssign, 0, 8);
        });
    }



    @Override
    public int getItemCount() {
        return issueList.size();
    }

    static class StaffViewHolder extends RecyclerView.ViewHolder {

        private TextView tvIssueTitle;
        private TextView tvStatus;
        private TextView tvCategory;

        private TextView tvAssignedStaff;
        private TextView tvStaffUpdate;
        private MaterialButton btnAssign;
        private MaterialButton btnStatus;

        private MaterialButton btnUpdate;
        private EditText inputStaffNote;

        public EditText getInputStaffNote() {
            return inputStaffNote;
        }

        public void setInputStaffNote(EditText inputStaffNote) {
            this.inputStaffNote = inputStaffNote;
        }

        public MaterialButton getBtnUpdate() {
            return btnUpdate;
        }

        public void setBtnUpdate(MaterialButton btnUpdate) {
            this.btnUpdate = btnUpdate;
        }

        public MaterialButton getBtnStatus() {
            return btnStatus;
        }

        public void setBtnStatus(MaterialButton btnStatus) {
            this.btnStatus = btnStatus;
        }

        public MaterialButton getBtnAssign() {
            return btnAssign;
        }

        public void setBtnAssign(MaterialButton btnAssign) {
            this.btnAssign = btnAssign;
        }

        public TextView getTvStaffUpdate() {
            return tvStaffUpdate;
        }

        public void setTvStaffUpdate(TextView tvStaffUpdate) {
            this.tvStaffUpdate = tvStaffUpdate;
        }

        public TextView getTvAssignedStaff() {
            return tvAssignedStaff;
        }

        public void setTvAssignedStaff(TextView tvAssignedStaff) {
            this.tvAssignedStaff = tvAssignedStaff;
        }

        public TextView getTvCategory() {
            return tvCategory;
        }

        public void setTvCategory(TextView tvCategory) {
            this.tvCategory = tvCategory;
        }

        public TextView getTvStatus() {
            return tvStatus;
        }

        public void setTvStatus(TextView tvStatus) {
            this.tvStatus = tvStatus;
        }

        public TextView getTvIssueTitle() {
            return tvIssueTitle;
        }

        public void setTvIssueTitle(TextView tvIssueTitle) {
            this.tvIssueTitle = tvIssueTitle;
        }




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
            btnUpdate = itemView.findViewById(R.id.btnSubmitUpdate);
        }
    }
}
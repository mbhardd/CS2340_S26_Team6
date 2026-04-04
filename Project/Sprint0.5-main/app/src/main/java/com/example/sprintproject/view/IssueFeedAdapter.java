package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.viewmodel.IssueFeedStatusLogic;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class IssueFeedAdapter extends RecyclerView.Adapter<IssueFeedAdapter.IssueViewHolder> {

    private List<Issue> issueList = new ArrayList<>();
    private IssueFeedViewModel viewModel;
    private int expandedPosition = -1;

    public IssueFeedAdapter(IssueFeedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public int toggleExpandedPosition(int currentExpandedPosition, int clickedPosition) {
        if (currentExpandedPosition == clickedPosition) {
            return -1;
        }
        return clickedPosition;
    }

    public String formatTimestamp(Long timestamp) {
        if (timestamp == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("MM/dd/yy, hh:mm a", java.util.Locale.US);
        return "Creation Time:  " + sdf.format(new java.util.Date(timestamp));
    }

    public String formatCreatorUid(String uid) {
        if (uid == null) {
            return "";
        }
        return "UID:  " + uid;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_issue_feed, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        Issue issue = issueList.get(position);

        String normalizedStatus = IssueFeedStatusLogic.normalizeStatus(issue.getStatus());

        holder.tvIssueTitle.setText(issue.getTitle() != null ? issue.getTitle() : "");
        holder.tvStatus.setText(normalizedStatus);
        holder.tvIssueCategory.setText(issue.getCategory() != null ? issue.getCategory() : "");

        String priority = issue.getPriority();
        holder.tvPriority.setText(viewModel.formatPriorityCheck(priority));
        if ("High".equals(priority)) {
            holder.tvPriority.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),
                    android.R.color.holo_red_dark));
        } else if ("Medium".equals(priority)) {
            holder.tvPriority.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),
                    android.R.color.holo_orange_dark));
        } else {
            holder.tvPriority.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),
                    android.R.color.holo_green_dark));
        }

        holder.tvIssueInitials.setText(issue.getInitials() != null ? issue.getInitials() : "");

        int colorRes = IssueFeedStatusLogic.getStatusColorRes(normalizedStatus);
        int statusColor = ContextCompat.getColor(holder.itemView.getContext(), colorRes);

        holder.tvStatus.setTextColor(statusColor);
        holder.tvIssueTitle.setTextColor(statusColor);

        holder.itemView.setOnClickListener(v -> {
            expandedPosition = toggleExpandedPosition(expandedPosition,
                    holder.getAdapterPosition());
            notifyDataSetChanged();
        });

        if (position == expandedPosition) {

            holder.layoutExpandable.setVisibility(View.VISIBLE);

            Long timestamp = issue.getTimestamp();
            if (timestamp != null) {
                holder.tvCreationTime.setText(formatTimestamp(timestamp));
            } else {
                holder.tvCreationTime.setText("No timestamp");
            }

            String uid = issue.getCreatorUid();
            if (uid != null) {
                holder.tvCreatorUid.setText(formatCreatorUid(uid));
            } else {
                holder.tvCreatorUid.setText("Unknown user");
            }

        } else {
            holder.layoutExpandable.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    static class IssueViewHolder extends RecyclerView.ViewHolder {
        public View btnAddComment;
        private TextView tvIssueTitle;
        private TextView tvStatus;
        private TextView tvIssueCategory;
        private TextView tvPriority;
        private TextView tvIssueInitials;
        private TextView tvCreationTime;
        private TextView tvCreatorUid;
        private LinearLayout layoutExpandable;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIssueTitle = itemView.findViewById(R.id.tvIssueTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvIssueCategory = itemView.findViewById(R.id.tvIssueCategory);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvIssueInitials = itemView.findViewById(R.id.tvIssueInitials);
            layoutExpandable = itemView.findViewById(R.id.layoutExpandable);
            tvCreationTime = itemView.findViewById(R.id.tvCreationTime);
            tvCreatorUid = itemView.findViewById(R.id.tvCreatorUid);
            btnAddComment = itemView.findViewById(R.id.btnAddComment);
        }
    }
}

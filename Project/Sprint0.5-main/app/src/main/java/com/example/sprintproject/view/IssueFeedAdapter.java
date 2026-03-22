package com.example.sprintproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.viewmodel.IssueFeedStatusLogic;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;

import java.util.ArrayList;
import java.util.List;

public class IssueFeedAdapter extends RecyclerView.Adapter<IssueFeedAdapter.IssueViewHolder> {

    private List<Issue> issueList = new ArrayList<>();
    private IssueFeedViewModel viewModel;
    private int expandedPosition = -1;

    public IssueFeedAdapter(IssueFeedViewModel viewModel) {
        this.viewModel = viewModel;
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
            if (expandedPosition == holder.getAdapterPosition()) {
                expandedPosition = -1; // collapse
            } else {
                expandedPosition = holder.getAdapterPosition(); // expand
            }
            notifyDataSetChanged();
        });

        if (position == expandedPosition) {
            holder.tvCreationTime.setVisibility(View.VISIBLE);
            holder.tvCreatorUid.setVisibility(View.VISIBLE);

            Long timestamp = issue.getTimestamp();
            if (timestamp != null) {
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("MM/dd/yy, hh:mm a");
                String formattedTime = sdf.format(new java.util.Date(timestamp));
                holder.tvCreationTime.setText("Creation Time:  " + formattedTime);
            }

            String uid = issue.getCreatorUid();
            if (uid != null) {
                holder.tvCreatorUid.setText("UID:  " + uid);
            }
        } else {
            holder.tvCreationTime.setVisibility(View.GONE);
            holder.tvCreatorUid.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    static class IssueViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIssueTitle;
        private TextView tvStatus;
        private TextView tvIssueCategory;
        private TextView tvPriority;
        private TextView tvIssueInitials;
        private TextView tvCreationTime;
        private TextView tvCreatorUid;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIssueTitle = itemView.findViewById(R.id.tvIssueTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvIssueCategory = itemView.findViewById(R.id.tvIssueCategory);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvIssueInitials = itemView.findViewById(R.id.tvIssueInitials);
            tvCreationTime = itemView.findViewById(R.id.tvCreationTime);
            tvCreatorUid = itemView.findViewById(R.id.tvCreatorUid);
        }
    }
}

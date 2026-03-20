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

import java.util.ArrayList;
import java.util.List;

public class IssueFeedAdapter extends RecyclerView.Adapter<IssueFeedAdapter.IssueViewHolder> {

    private List<Issue> issueList = new ArrayList<>();

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

        String normalizedStatus = normalizeStatus(issue.getStatus());

        holder.tvIssueTitle.setText(issue.getTitle() != null ? issue.getTitle() : "");
        holder.tvStatus.setText(normalizedStatus);
        holder.tvIssueCategory.setText(issue.getCategory() != null ? issue.getCategory() : "");
        holder.tvPriority.setText(issue.getPriority() != null ? issue.getPriority() : "");
        holder.tvIssueInitials.setText(issue.getInitials() != null ? issue.getInitials() : "");

        int statusColor = getStatusColor(holder, normalizedStatus);
        holder.tvStatus.setTextColor(statusColor);
        holder.tvIssueTitle.setTextColor(statusColor);
    }

    private String normalizeStatus(String status) {
        if (status == null) {
            return "Not Started";
        }

        String normalized = status.trim().toLowerCase();

        switch (normalized) {
            case "not started":
            case "not-started":
            case "open":
                return "Not Started";

            case "in progress":
            case "in_progress":
                return "In Progress";

            case "finished":
                return "Finished";

            default:
                return "Not Started";
        }
    }

    private int getStatusColor(IssueViewHolder holder, String status) {
        switch (status) {
            case "In Progress":
                return ContextCompat.getColor(holder.itemView.getContext(), R.color.in_progress_gold);

            case "Finished":
                return ContextCompat.getColor(holder.itemView.getContext(), R.color.finished_green);

            case "Not Started":
            default:
                return ContextCompat.getColor(holder.itemView.getContext(), R.color.not_started_red);
        }
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    static class IssueViewHolder extends RecyclerView.ViewHolder {
        TextView tvIssueTitle;
        TextView tvStatus;
        TextView tvIssueCategory;
        TextView tvPriority;
        TextView tvIssueInitials;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIssueTitle = itemView.findViewById(R.id.tvIssueTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvIssueCategory = itemView.findViewById(R.id.tvIssueCategory);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvIssueInitials = itemView.findViewById(R.id.tvIssueInitials);
        }
    }
}

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

        String normalizedStatus = IssueFeedStatusLogic.normalizeStatus(issue.getStatus());

        holder.tvIssueTitle.setText(issue.getTitle() != null ? issue.getTitle() : "");
        holder.tvStatus.setText(normalizedStatus);
        holder.tvIssueCategory.setText(issue.getCategory() != null ? issue.getCategory() : "");
        String priority = issue.getPriority() != null ? issue.getPriority() : "";
        holder.tvPriority.setText(priority);

        if (priority.equals("High")) {
            holder.tvPriority.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_dark));
            holder.tvPriority.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.tvPriority.setText("🔴 High");
        } else if (priority.equals("Medium")) {
            holder.tvPriority.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_orange_dark));
            holder.tvPriority.setText("🟡 Medium");
        } else if (priority.equals("Low")) {
            holder.tvPriority.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark));
            holder.tvPriority.setText("🟢 Low");
        }
        holder.tvIssueInitials.setText(issue.getInitials() != null ? issue.getInitials() : "");

        int colorRes = IssueFeedStatusLogic.getStatusColorRes(normalizedStatus);
        int statusColor = ContextCompat.getColor(holder.itemView.getContext(), colorRes);

        holder.tvStatus.setTextColor(statusColor);
        holder.tvIssueTitle.setTextColor(statusColor);
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

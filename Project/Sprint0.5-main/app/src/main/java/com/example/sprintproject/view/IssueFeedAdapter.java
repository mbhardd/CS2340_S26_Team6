package com.example.sprintproject.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.viewmodel.IssueFeedStatusLogic;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;

import java.util.ArrayList;
import java.util.List;

public class IssueFeedAdapter extends RecyclerView.Adapter<IssueFeedAdapter.IssueViewHolder> {

    private List<Issue> issueList = new ArrayList<>();
    private IssueFeedViewModel viewModel;
    private LifecycleOwner lifecycleOwner;
    private int expandedPosition = -1;
    private AuthRepository authRepository;

    public IssueFeedAdapter(IssueFeedViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        authRepository = AuthRepository.getInstance();
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

        String normalized = IssueFeedStatusLogic.normalizeStatus(normalizedStatus);
        Log.d("STATUS_DEBUG", "Raw: " + normalizedStatus + " | Normalized: " + normalized);

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
            holder.tvCreationTime.setText(
                    timestamp != null ? formatTimestamp(timestamp) : "No timestamp"
            );

            holder.tvWeather.setText(
                    issue.getWeatherSummary() != null
                            ? "Weather: " + issue.getWeatherSummary()
                            : "Weather: unavailable"
            );

            String uid = issue.getCreatorUid();
            holder.tvCreatorUid.setText(
                    uid != null ? formatCreatorUid(uid) : "Unknown user"
            );

            holder.commentsContainer.removeAllViews();
            holder.staffContainer.removeAllViews();

            viewModel.getUpdatesForIssue(issue.getId())
                    .observeForever(updates -> {
                        holder.commentsContainer.removeAllViews();
                        holder.staffContainer.removeAllViews();

                        if (updates == null) {
                            return;
                        }

                        for (IssueUpdate update : updates) {
                            String type = update.getType();
                            if (type == null) {
                                continue;
                            }

                            if (!isRelevantType(type)) {
                                continue;
                            }

                            TextView tv = createBaseTextView(holder, update);

                            if ("COMMENT".equalsIgnoreCase(type)) {
                                holder.commentsContainer.addView(tv);
                            } else if (isStaffType(type)) {
                                styleAsStaff(tv);
                                holder.staffContainer.addView(tv);
                            }
                        }
                    });

            holder.btnAddUpdate.setOnClickListener(v -> {
                holder.btnAddUpdate.setVisibility(View.GONE);
                holder.etComment.setVisibility(View.VISIBLE);
                holder.btnSubmit.setVisibility(View.VISIBLE);
            });

            holder.btnSubmit.setOnClickListener(v -> {
                String comment = holder.etComment.getText().toString().trim();

                if (!comment.isEmpty()) {
                    viewModel.addComment(issue.getId(),
                            authRepository.getCachedUser(), comment);

                    holder.etComment.setText("");

                    holder.etComment.setVisibility(View.GONE);
                    holder.btnSubmit.setVisibility(View.GONE);
                    holder.btnAddUpdate.setVisibility(View.VISIBLE);
                }
            });

        } else {
            holder.layoutExpandable.setVisibility(View.GONE);
            holder.etComment.setVisibility(View.GONE);
            holder.btnSubmit.setVisibility(View.GONE);
            holder.btnAddUpdate.setVisibility(View.VISIBLE);
        }

        bindWatch(holder, issue);
        bindUpvote(holder, issue);
    }


    private void bindWatch(IssueViewHolder holder, Issue issue) {
        holder.btnWatch.setTag(null);

        viewModel.isWatching(issue.getId()).observe(lifecycleOwner, isWatching -> {
            if (isWatching != null && isWatching) {
                holder.btnWatch.setText("Watching");
                holder.btnWatch.setBackgroundResource(R.drawable.bg_watching_green);
                holder.btnWatch.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.eye_open, 0, 0, 0);
            } else {
                holder.btnWatch.setText("Watch");
                holder.btnWatch.setBackgroundResource(R.drawable.bg_watch_grey);
                holder.btnWatch.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.eye_closed, 0, 0, 0);
            }
        });

        holder.btnWatch.setOnClickListener(v -> {
            holder.btnWatch.setEnabled(false);
            viewModel.toggleWatch(issue.getId(), () -> {
                holder.btnWatch.setEnabled(true);
            });
        });
    }

    private void bindUpvote(IssueViewHolder holder, Issue issue) {
        holder.tvUpvoteCount.setText(String.valueOf(issue.getUpvoteCount()));

        int color = ContextCompat.getColor(
                holder.itemView.getContext(),
                issue.isUpvoted() ? R.color.upvote_active : R.color.upvote_inactive
        );

        holder.ivUpvote.setColorFilter(color);
        holder.tvUpvoteCount.setTextColor(color);

        holder.layoutUpvote.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) {
                return;
            }

            Issue current = issueList.get(pos);
            holder.layoutUpvote.setEnabled(false);

            current.toggleUpvote();
            notifyItemChanged(pos);
            viewModel.toggleUpvote(issue.getId(), () -> {
                holder.layoutUpvote.setEnabled(true);
            });
        });
    }

    private TextView createBaseTextView(IssueViewHolder holder, IssueUpdate update) {
        TextView tv = new TextView(holder.itemView.getContext());
        tv.setText("• " + update.getContent());
        tv.setTextSize(16f);
        return tv;
    }

    private void styleAsStaff(TextView tv) {
        tv.setTextColor(android.graphics.Color.parseColor("#AB0000"));
    }

    private boolean isRelevantType(String type) {
        return "COMMENT".equalsIgnoreCase(type)
                || "STAFF_NOTE".equalsIgnoreCase(type)
                || "STATUS_CHANGE".equalsIgnoreCase(type);
    }

    private boolean isStaffType(String type) {
        return "STAFF_NOTE".equalsIgnoreCase(type)
                || "STATUS_CHANGE".equalsIgnoreCase(type);
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    static class IssueViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutUpvote;
        private ImageView ivUpvote;
        private TextView tvUpvoteCount;
        private TextView tvIssueTitle;
        private TextView tvStatus;
        private TextView tvIssueCategory;
        private TextView tvPriority;
        private TextView tvIssueInitials;
        private TextView tvCreationTime;
        private TextView tvWeather;
        private TextView tvCreatorUid;
        private LinearLayout layoutExpandable;
        private LinearLayout commentsContainer;
        private LinearLayout staffContainer;
        private Button btnAddUpdate;
        private Button btnSubmit;
        private EditText etComment;
        private AppCompatButton btnWatch;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIssueTitle = itemView.findViewById(R.id.tvIssueTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvIssueCategory = itemView.findViewById(R.id.tvIssueCategory);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvIssueInitials = itemView.findViewById(R.id.tvIssueInitials);
            layoutExpandable = itemView.findViewById(R.id.layoutExpandable);
            tvCreationTime = itemView.findViewById(R.id.tvCreationTime);
            tvWeather = itemView.findViewById(R.id.tvWeather);
            tvCreatorUid = itemView.findViewById(R.id.tvCreatorUid);
            commentsContainer = itemView.findViewById(R.id.commentsContainer);
            staffContainer = itemView.findViewById(R.id.staffContainer);
            btnAddUpdate = itemView.findViewById(R.id.btnAddUpdate);
            btnSubmit = itemView.findViewById(R.id.btnSubmit);
            etComment = itemView.findViewById(R.id.etComment);
            btnWatch = itemView.findViewById(R.id.btnWatch);
            ivUpvote = itemView.findViewById(R.id.ivUpvote);
            tvUpvoteCount = itemView.findViewById(R.id.tvUpvoteCount);
            layoutUpvote = itemView.findViewById(R.id.layoutUpvote);
        }
    }
}
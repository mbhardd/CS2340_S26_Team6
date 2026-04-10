package com.example.sprintproject.viewmodel;

import com.example.sprintproject.R;

public final class IssueFeedStatusLogic {

    private IssueFeedStatusLogic() {
    }

    public static String normalizeStatus(String status) {
        if (status == null) {
            return "SUBMITTED";
        }

        try {
            return status.trim().toUpperCase();
        } catch (Exception e) {
            return "SUBMITTED";
        }
    }

    public static int getStatusColorRes(String status) {
        switch (normalizeStatus(status)) {
            case "IN_REVIEW":
                return R.color.in_review_blue;

            case "IN_PROGRESS":
                return R.color.in_progress_gold;

            case "RESOLVED":
                return R.color.resolved_purple;

            case "CLOSED":
                return R.color.closed_gray;

            case "SUBMITTED":
            default:
                return R.color.submitted_red;
        }
    }
}
package com.example.sprintproject.viewmodel;

import com.example.sprintproject.R;

public final class IssueFeedStatusLogic {

    private IssueFeedStatusLogic() {
    }

    public static String normalizeStatus(String status) {
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

    public static int getStatusColorRes(String status) {
        switch (normalizeStatus(status)) {
            case "In Progress":
                return R.color.in_progress_gold;
            case "Finished":
                return R.color.finished_green;
            case "Not Started":
            default:
                return R.color.not_started_red;
        }
    }
}
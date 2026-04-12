package com.example.sprintproject.viewmodel;



import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;

public class StaffUpdateStrategy implements IssueUpdateStrategy {
    public UpdateResult execute(String issueId, User user, String content) {
        if (content == null || content.trim().isEmpty()) {
            return new UpdateResult(false, "Note cannot be empty", null, null);
        }
        IssueUpdate update = new IssueUpdate(
                user.getEmail(),
                System.currentTimeMillis(),
                UpdateType.STAFF_NOTE.name(),
                content.trim(),
                null,
                null
        );
        return new UpdateResult(true, "Staff note added", update, null);
    }
}

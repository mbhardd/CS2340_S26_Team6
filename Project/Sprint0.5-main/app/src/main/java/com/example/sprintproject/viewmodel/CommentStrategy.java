package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.IssueRepository;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;

public class CommentStrategy implements IssueUpdateStrategy{
    @Override
    public UpdateResult execute(String issueId, User user, String content) {
        IssueUpdate update = new IssueUpdate(
                user.getEmail(),
                System.currentTimeMillis(),
                UpdateType.COMMENT.name(),
                content,
                null,
                null
        );

        return new UpdateResult(true, "Comment added", update, null);
    }
}

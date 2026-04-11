package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.StaffUpdateStrategy;
import com.example.sprintproject.viewmodel.StatusChangeStrategy;

import org.junit.Before;
import org.junit.Test;

public class Sprint3_Ria_Tests {
    @Before
    public void setUp() {
    }
    @Test
    public void staffUpdateStrategy_allowsStaffNoteCreation() {

        StaffUpdateStrategy strategy = new StaffUpdateStrategy();

        User staffUser = new User("staff@email.com", true);

        UpdateResult result =
                strategy.execute("issue123", staffUser, "Hello");

        assertTrue(result.success);
        assertEquals("Staff note added", result.message);
        assertEquals(UpdateType.STAFF_NOTE.name(), result.update.getType());
    }

    @Test
    public void statusChangeStrategy_formatsMessageCorrectly() {

        StatusChangeStrategy strategy =
                new StatusChangeStrategy(IssueStatus.IN_REVIEW, IssueStatus.IN_PROGRESS);

        User user = new User("staff@email.com", true);

        UpdateResult result =
                strategy.execute("issue123", user, null);

        assertTrue(result.success);

        String expectedMessage =
                "Status changed from In Review to In Progress";
        assertEquals(expectedMessage, result.getUpdate().getContent());
    }
}

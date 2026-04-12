package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.viewmodel.IssueFeedStatusLogic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sprint3_Ananya_Tests {
    @Test
    public void commentsAndStaffNotesSeparation() {

        List<IssueUpdate> updates = Arrays.asList(
                new IssueUpdate("student1@gmail.com", 1L, "COMMENT",
                        "There are multiple leaky faucets", null, null),

                new IssueUpdate("staff@gmail.com", 2L, "STAFF_NOTE",
                        "Students should avoid this area", null, null),

                new IssueUpdate("student2@gmail.com", 3L, "COMMENT",
                        "Please have this resolved ASAP", null, null),

                new IssueUpdate("staff@gmail.com", 4L, "STAFF_NOTE",
                        "Students are clear to enter", null, null)
        );

        List<String> studentComments = new ArrayList<>();
        List<String> staffNotes = new ArrayList<>();

        for (IssueUpdate update : updates) {
            if ("COMMENT".equalsIgnoreCase(update.getType())) {
                studentComments.add(update.getContent());
            } else if ("STAFF_NOTE".equalsIgnoreCase(update.getType())) {
                staffNotes.add(update.getContent());
            }
        }

        assertEquals(2, studentComments.size());
        assertEquals("There are multiple leaky faucets", studentComments.get(0));
        assertEquals("Please have this resolved ASAP", studentComments.get(1));

        assertEquals(2, staffNotes.size());
        assertEquals("Students should avoid this area", staffNotes.get(0));
        assertEquals("Students are clear to enter", staffNotes.get(1));

        assertFalse(studentComments.contains("Students should avoid this area"));
        assertFalse(studentComments.contains("Students are clear to enter"));
        assertFalse(staffNotes.contains("There are multiple leaky faucets"));
        assertFalse(staffNotes.contains("Please have this resolved ASAP"));
    }

    @Test
    public void normalizeStatus_handlesAllValidInputs() {

        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus("submitted"));
        assertEquals("In Review", IssueFeedStatusLogic.normalizeStatus("in_review"));
        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus("open"));
        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus("not-started"));

        assertEquals("In Progress", IssueFeedStatusLogic.normalizeStatus("in_progress"));
        assertEquals("In Progress", IssueFeedStatusLogic.normalizeStatus("in progress"));

        assertEquals("Resolved", IssueFeedStatusLogic.normalizeStatus("resolved"));
        assertEquals("Closed", IssueFeedStatusLogic.normalizeStatus("closed"));
        assertEquals("Finished", IssueFeedStatusLogic.normalizeStatus("finished"));
    }

    @Test
    public void normalizeStatus_handlesEdgeCases() {

        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus(null));
        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus(""));
        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus("   "));
        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus("unknown_status"));

        assertEquals("In Progress", IssueFeedStatusLogic.normalizeStatus("  IN_PROGRESS "));
    }
}

package comp3350.dailysprint.tests.objects;

import junit.framework.TestCase;
import comp3350.dailysprint.objects.Activity;

public class ActivityTest extends TestCase{

    public ActivityTest(String arg0) {super(arg0);}

    public void testActivityComparisonCases() {
        Activity activity;

        System.out.println("\nStarting testActivityComparisonCases");

        activity = new Activity(30, "running");
        assertNotNull(activity);

        assertEquals(30, activity.getTime());
        assertEquals("running", activity.getActivity());

        Activity newActivity;
        newActivity = new Activity(30, "running");
        assertNotNull(newActivity);

        assertTrue(newActivity.equals(activity));
        assertEquals(newActivity.toString(),activity.toString());

        newActivity = new Activity(30, "walking");
        assertNotNull(newActivity);

        assertFalse(newActivity.equals(activity));

        System.out.println("Finished testActivityComparisonCases");
    }
}

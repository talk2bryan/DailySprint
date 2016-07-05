package comp3350.dailysprint.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.business.AccessActivities;
import comp3350.dailysprint.objects.Activity;
import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.tests.persistence.DataAccessStub;


public class AccessActivitiesTest extends TestCase{

    private static String dbName = Main.dbName;
    public AccessActivitiesTest(String arg0) {super(arg0);}

    public void test1() {
        AccessActivities newActivityAccess;
        Activity activity;
        ActivityInformation actvInfo;
        ArrayList<ActivityInformation> actvList;
        ActivityInformation newInfo;

        System.out.println("Starting test AccessActivities");

        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        Services.createDataAccess(dbName);


        activity = new Activity(45, "running");
        newActivityAccess = new AccessActivities();

        // AccessActivities()
        assertNotNull(newActivityAccess);
        assertEquals("running", activity.getActivity());
        assertEquals(45, activity.getTime());

        // getSequential()
        actvInfo = newActivityAccess.getSequential();
        assertNotNull(actvInfo);
        assertEquals(6.0, newActivityAccess.getMET("running, 4 mph (13 min/mile)"));

        //searchActivity()
        newInfo = newActivityAccess.searchActivity("walking, 2.5 mph, downhill");
        assertNotNull(newInfo);


        System.out.println("\nEnd AccessActivityiesTest");
    }
}

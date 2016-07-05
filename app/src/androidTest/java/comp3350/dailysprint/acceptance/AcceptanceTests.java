package comp3350.dailysprint.acceptance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AcceptanceTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Acceptance tests");
        suite.addTestSuite(CreateUserProfileTest.class);
        suite.addTestSuite(FriendTest.class);
        suite.addTestSuite(ScheduleTest.class);
        suite.addTestSuite(HistoryTest.class);
        suite.addTestSuite(WeightTest.class);
        return suite;
    }
}
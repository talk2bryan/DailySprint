package comp3350.dailysprint.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.dailysprint.tests.application.ServicesTest;
import comp3350.dailysprint.tests.business.AccessActivitiesTest;
import comp3350.dailysprint.tests.business.AccessUsersTest;
import comp3350.dailysprint.tests.business.CalculateTest;
import comp3350.dailysprint.tests.objects.ActivityInformationTest;
import comp3350.dailysprint.tests.objects.ActivityTest;
import comp3350.dailysprint.tests.objects.EventTest;
import comp3350.dailysprint.tests.objects.MessageTest;
import comp3350.dailysprint.tests.objects.UserProfileTest;
import comp3350.dailysprint.tests.persistence.DataAccessTest;
import comp3350.dailysprint.tests.integration.BusinessPersistenceSeamTest;
import comp3350.dailysprint.tests.integration.DataAccessHSQLDBTest;

public class AllTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        testPersistence();
        testApplication();
        testPersistence();
        testIntegration();
        return suite;
    }

    private static void testObjects()
    {
        suite.addTestSuite(ActivityTest.class);
        suite.addTestSuite(EventTest.class);
        suite.addTestSuite(UserProfileTest.class);
        suite.addTestSuite(MessageTest.class);
        suite.addTestSuite(ActivityInformationTest.class);

    }

    private static void testBusiness()
    {
        suite.addTestSuite(CalculateTest.class);
        suite.addTestSuite(AccessUsersTest.class);
        suite.addTestSuite(AccessActivitiesTest.class);
    }

    private static void testPersistence()
    {
        //suite.addTestSuite(DataAccessStubTest.class);
        suite.addTestSuite(DataAccessTest.class);
    }

    private static void testApplication()
    {
        suite.addTestSuite(ServicesTest.class);
    }

    private static void testIntegration() {
        suite.addTestSuite(BusinessPersistenceSeamTest.class);
        suite.addTestSuite(DataAccessHSQLDBTest.class);
    }
}

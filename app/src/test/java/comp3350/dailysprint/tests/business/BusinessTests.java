package comp3350.dailysprint.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.business.Calculate;

public class BusinessTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Business tests");
        suite.addTestSuite(AccessActivitiesTest.class);
        suite.addTestSuite(AccessUsersTest.class);
        suite.addTestSuite(CalculateTest.class);
        return suite;
    }
}

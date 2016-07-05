package comp3350.dailysprint.tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Object tests");
        suite.addTestSuite(ActivityInformationTest.class);
        suite.addTestSuite(ActivityTest.class);
        suite.addTestSuite(EventTest.class);
        suite.addTestSuite(MessageTest.class);
        suite.addTestSuite(UserProfileTest.class);
        
        return suite;
    }
}

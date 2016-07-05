package comp3350.dailysprint.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.dailysprint.tests.business.BusinessTests;
import comp3350.dailysprint.tests.objects.ObjectTests;
import comp3350.dailysprint.tests.persistence.PersistenceTests;

public class RunUnitTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Unit tests");
        suite.addTest(ObjectTests.suite());
        suite.addTest(BusinessTests.suite());
        suite.addTest(PersistenceTests.suite());
        return suite;
    }
}

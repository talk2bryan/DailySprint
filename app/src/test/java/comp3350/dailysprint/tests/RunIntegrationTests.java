package comp3350.dailysprint.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.dailysprint.tests.integration.IntegrationTests;

public class RunIntegrationTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Integration tests");
        suite.addTest(IntegrationTests.suite());
        return suite;
    }
}
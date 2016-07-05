package comp3350.dailysprint.tests.integration;

import junit.framework.TestCase;
import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.persistence.DataAccess;
import comp3350.dailysprint.tests.persistence.DataAccessTest;

public class DataAccessHSQLDBTest extends TestCase {
    private static String dbName = Main.dbName;

    public DataAccessHSQLDBTest(String arg0)
    {
        super(arg0);
    }

    public void testDataAccess(){
        DataAccess dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        Services.createDataAccess(dbName);
        dataAccess = Services.getDataAccess(dbName);

        DataAccessTest.dataAccessTest();

        System.out.println("Finished Integration test DataAccess (using default DB)");

    }
}

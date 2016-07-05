package comp3350.dailysprint.tests.application;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.persistence.DataAccess;
import comp3350.dailysprint.tests.persistence.DataAccessStub;

public class ServicesTest extends TestCase{
    public ServicesTest(String arg0) {super(arg0);}
    public void testServices(){
        System.out.println("\nStarting testService");

        String dbName;
        DataAccess dataAccess = null;
        UserProfile user;
        ArrayList<UserProfile> users = new ArrayList<>();

        Services.closeDataAccess();

        //HSQL
        dbName = Main.dbName;
        dataAccess = Services.createDataAccess(dbName);
        assertNotNull(dataAccess);
        dataAccess = Services.getDataAccess(dbName);
        assertNotNull(dataAccess);
        Services.closeDataAccess();


        //STUB
        dbName = "Test";
        Services.closeDataAccess();
        dataAccess = Services.createDataAccess(new DataAccessStub(dbName));
        assertNotNull(dataAccess);
        dataAccess = Services.getDataAccess(dbName);
        assertNotNull(dataAccess);
        Services.closeDataAccess();


        System.out.println("\nFinished testService");

    }
}

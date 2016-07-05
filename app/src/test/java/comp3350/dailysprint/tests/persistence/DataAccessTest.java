package comp3350.dailysprint.tests.persistence;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.persistence.DataAccess;

import junit.framework.TestCase;

import java.util.ArrayList;

public class DataAccessTest extends TestCase{


    public DataAccessTest(String arg0) {super(arg0);}
    private static String dbName = Main.dbName;


    public void testDataAccess()
    {
        DataAccess dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting Persistence test DataAccess (using stub)");

        dataAccess = Services.createDataAccess(new DataAccessStub(dbName));


        dataAccessTest();


        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    public static void dataAccessTest() {


        DataAccess dataAccess;

        ArrayList<ActivityInformation> activityInfoList;
        ArrayList<UserProfile> usrProfiles;

        UserProfile user1,user2,user3,user4,user5;

        String result;
        System.out.println("Entering getDataAccess(dbName)");
        dataAccess = Services.getDataAccess(dbName);

        usrProfiles = new ArrayList<UserProfile>();

        result = dataAccess.getUserSequential(usrProfiles);
        assertNull(result);
        assertNotNull(usrProfiles);
        assertEquals(4,usrProfiles.size());

        //test userProfile ArrayList
        user1 = usrProfiles.get(0);
        assertNotNull(user1);
        assertEquals("Bryan",user1.getUsername());

        user1 = usrProfiles.get(1);
        assertNotNull(user1);
        assertEquals("Jason",user1.getUsername());

        user1 = usrProfiles.get(2);
        assertNotNull(user1);
        assertEquals("Marc",user1.getUsername());

        user1 = usrProfiles.get(3);
        assertNotNull(user1);
        assertEquals("Reagan",user1.getUsername());


        try{
            usrProfiles.get(4);
            fail("Expected exception");
        }catch (IndexOutOfBoundsException indexOutOfBoundsException)  {
        }

        //insertUser
        user2 = new UserProfile("NewUser","password",15,169,140,true);
        result = dataAccess.insertUser(user2);
        assertNull(result);
        usrProfiles = new ArrayList<>();
        result = dataAccess.getUserSequential(usrProfiles);
        assertNull(result);

        //verify
        assertEquals(5,usrProfiles.size());

        user1 = usrProfiles.get(1);
        result = dataAccess.getUserName(user1);
        //result = dataAccess.getUserName(user2);
        assertNotNull(result);
        //assertEquals("NewUser",result);

        //deleteUser
        UserProfile user = new UserProfile("NewUser","password",15,169,140,true);
        result = dataAccess.deleteUser(user);
        usrProfiles = new ArrayList<>();
        result = dataAccess.getUserSequential(usrProfiles);
        assertNull(result);

        //verify
        result = dataAccess.getUserName(user);
        assertNull(result);
        assertEquals(4,usrProfiles.size());


        //updateUser by modifying password && weight
        user3 = new UserProfile("Bryan","new_password", 21, 140, 170, false);
        result = dataAccess.updateUser(user3);
        assertNull(result);
        usrProfiles = new ArrayList<>();
        result = dataAccess.getUserSequential(usrProfiles);

        user3 = usrProfiles.get(0);
        assertNotNull(user3);
        assertEquals("Bryan",user3.getUsername());
        assertEquals("new_password",user3.getPassword());


        //updateUser by modifying weight
        user4  = new UserProfile("Bryan","password", 21, 140, 10, false);
        result = dataAccess.updateUser(user4);
        assertNull(result);

        usrProfiles = new ArrayList<>();
        result = dataAccess.getUserSequential(usrProfiles);
        user4 = usrProfiles.get(0);
        assertNotNull(user4);
        assertEquals("Bryan",user4.getUsername());
        assertEquals("password",user4.getPassword());
        assertEquals(10,user4.getUserWeight());

        //undo weight change
        user5  = new UserProfile("Bryan","password", 21, 140, 170, false);
        result = dataAccess.updateUser(user5);
        assertNull(result);

        usrProfiles = new ArrayList<>();
        result = dataAccess.getUserSequential(usrProfiles);
        user5 = usrProfiles.get(0);
        assertNotNull(user5);
        assertEquals("Bryan",user5.getUsername());
        assertEquals(170,user5.getUserWeight());


        //test activityInfo arraylist
        activityInfoList = new ArrayList<ActivityInformation>();
        result = dataAccess.getActivitySequential(activityInfoList);
        assertNull(result);
        assertNotNull(activityInfoList);
        assertEquals(18,activityInfoList.size());


        Services.closeDataAccess();
    }


}

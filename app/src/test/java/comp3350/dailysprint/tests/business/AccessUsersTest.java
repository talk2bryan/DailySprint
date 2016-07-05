package comp3350.dailysprint.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.persistence.DataAccess;
import comp3350.dailysprint.tests.persistence.DataAccessStub;
import comp3350.dailysprint.objects.Message;

public class AccessUsersTest extends TestCase{

    private static String dbName = Main.dbName;

    public AccessUsersTest (String arg0) {super(arg0);}

    public void testAccessUsersSearchCase(){
        AccessUsers users;
        DataAccess dataAccess;
        Services.closeDataAccess();
        System.out.println("\nStarting testAccessUsersSearchCase");

        dataAccess = Services.createDataAccess(new DataAccessStub(dbName));

        users = new AccessUsers();

        UserProfile user1;
        UserProfile testUser = new UserProfile("Bryan", "password", 21, 140, 170, false);

        //getSequential
        user1 = users.getSequential();
        assertNotNull(user1);
        assertTrue(user1.equals(testUser));

        //refreshUsers
        assertNull(users.refreshUsers());

        //searchName
        assertNotNull(users.searchName("Bryan"));
        assertTrue((users.searchName("Bryan")).equals(testUser));
        assertNotNull(users.searchName(user1.getUsername()));

        UserProfile user2 = new UserProfile("New","",3,4,5,true);
        assertNull(users.searchName(user2.getUsername()));

        dataAccess.close();
        System.out.println("Finished testAccessUsersSearchCase\n");
    }

    public void testAccessUsersModifyUser() {
        AccessUsers users;
        Services.closeDataAccess();
        DataAccess dataAccess;
        System.out.println("\nStarting testAccessUsersModifyUser");

        dataAccess = Services.createDataAccess(new DataAccessStub(dbName));
        users = new AccessUsers();

        //insertUser
        UserProfile user1 = new UserProfile("New","password",4,5,6,false);
        assertNull(users.insertUser(user1));
        assertEquals(user1,users.searchName("New"));

        //updateUser
        UserProfile user2 = new UserProfile("New","new_password",4,5,6,false);
        String updateTest = users.updateUser(user2);
        assertNull(updateTest);
        assertEquals("new_password",(users.searchName(user1.getUsername()).getPassword()));

        //deleteUser
        assertNull(users.deleteUser(user1));
        assertNull(users.searchName("New"));

        dataAccess.close();
        System.out.println("Finished testAccessUsersModifyUser");
    }

    public void testAccessUsersMessageCase() {
        AccessUsers users;
        DataAccess dataAccess;
        Services.closeDataAccess();
        System.out.println("\nStarting testAccessUsersMessageCase");

        dataAccess = Services.createDataAccess(new DataAccessStub(dbName));
        users = new AccessUsers();
        UserProfile user1 = new UserProfile("User1","password",4,5,6,false);

        //send
        users.insertUser(user1);
        assertNull(users.send(new Message("User1", "Bryan", "message", "test")));

        //sendToFriends
        assertNull(users.sendToFriends(user1, "test"));

        dataAccess.close();
        System.out.println("Finished testAccessUsersMessageCase");
    }

    public void testAccessUsersGetters() {
        AccessUsers users;
        DataAccess dataAccess;
        Services.closeDataAccess();
        System.out.println("\nStarting testAccessUsersGetters");

        dataAccess = Services.createDataAccess(new DataAccessStub(dbName));

        users = new AccessUsers();
        UserProfile user1 = users.getSequential();


        assertNotNull(user1);

        //getUserName
        assertNotNull(user1.getUsername());
        assertEquals("Bryan",user1.getUsername());

        //getUserWeight
        assertNotNull(user1.getUserWeight());
        assertEquals(170,user1.getUserWeight());

        //getRequestFriends
        ArrayList<UserProfile> friendsTest = users.getRequestFriends(user1);

        assertNotNull(friendsTest);
        assertEquals(friendsTest.get(0).getUsername(), "Jason");

        //getUsers
        assertNotNull(users.getUsers());
        assertEquals(users.getUsers().get(0), user1);

        dataAccess.close();
        System.out.println("Finished testAccessUsersGetters");
    }
}

package comp3350.dailysprint.tests.integration;

import junit.framework.TestCase;

import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.business.AccessActivities;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.objects.Message;


public class BusinessPersistenceSeamTest extends TestCase
{
    public BusinessPersistenceSeamTest(String arg0)
    {
        super(arg0);
    }

    public void testAccessUsers()
    {
        AccessUsers accessUsers;
        UserProfile user;
        Message message;
        String result;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessUsers to persistence");

        Services.createDataAccess(Main.dbName);

        accessUsers = new AccessUsers();

        user = accessUsers.getSequential();
        assertTrue("Bryan".equals(user.getUsername()));
        assertTrue("password".equals(user.getPassword()));
        assertTrue(21 == user.getUserAge());
        assertTrue(170 == user.getUserWeight());
        assertTrue(140 == user.getUserHeight());
        assertTrue(!user.getUserGender());
        result = accessUsers.deleteUser(user);
        assertNull(result);
        assertNull(accessUsers.searchName("Bryan"));

        result = accessUsers.insertUser(user);
        assertNull(result);
        user = accessUsers.searchName("Bryan");
        assertTrue("Bryan".equals(user.getUsername()));

        user = accessUsers.searchName("Bob");
        assertNull(user);

        // Test getSequential
        user = accessUsers.getSequential();
        assertTrue("Bryan".equals(user.getUsername()));
        user = accessUsers.getSequential();
        assertTrue("Jason".equals(user.getUsername()));
        user = accessUsers.getSequential();
        assertTrue("Marc".equals(user.getUsername()));
        user = accessUsers.getSequential();
        assertTrue("Reagan".equals(user.getUsername()));
        user = accessUsers.getSequential();
        assertNull(user);
        user = accessUsers.getSequential();
        assertTrue("Bryan".equals(user.getUsername()));

        assertNull(accessUsers.refreshUsers());
        user = accessUsers.getSequential();
        assertTrue("Bryan".equals(user.getUsername()));

        // Test searchUser
        assertNull(accessUsers.refreshUsers());
        for(int i=0;i<4;i++)
        {
            user = accessUsers.getSequential();
            assertTrue(user.getUsername().equals(accessUsers.searchName(user.getUsername()).getUsername()));
            assertNull(accessUsers.searchName(i+""));
        }
        assertNull(accessUsers.refreshUsers());

        //Test Update
        assertNotNull(user = accessUsers.searchName("Marc"));
        for(int i=0;i<4;i++)
        {
            user.setUserWeight(i);
            accessUsers.updateUser(user);
            user = accessUsers.searchName("Marc");
            assertTrue(user.getUserWeight() == i);
        }

        //Test Add
        for(int i=0;i<4;i++)
        {
            user = new UserProfile(i+"","password",100,100,100,false);
            accessUsers.insertUser(user);
            assertNotNull(accessUsers.searchName(i+""));
        }

        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
        accessUsers = new AccessUsers();

        //Test Delete
        for(int i=0;i<4;i++)
        {
            assertNotNull(user = accessUsers.searchName(i+""));
            assertNull(accessUsers.deleteUser(user));
            assertNull(accessUsers.searchName(i+""));
        }

        //Test Send
        message = new Message("Bryan", "Marc", "message", "Hello");
        assertNull(accessUsers.send(message));
        user = accessUsers.searchName("Bryan");
        assertTrue(user.getMessageList().size() == 1);
        assertTrue(message.equals(user.getMessageList().get(0)));
        user.deleteMessage(message);
        accessUsers.updateUser(user);
        user = accessUsers.searchName("Bryan");
        assertTrue(user.getMessageList().size() == 0);

        message = new Message("Bob", "Marc", "message", "Hello");
        assertNotNull(accessUsers.send(message));

        //Test sendToFriend
        assertNull(accessUsers.sendToFriends(user,"Hello"));
        user.addFriend("Marc");
        user.addFriend("Jason");
        accessUsers.updateUser(user);
        assertNull(accessUsers.sendToFriends(user,"Hello"));
        user = accessUsers.searchName("Marc");
        assertTrue(user.getMessageList().get(0) != null);
        message = user.getMessageList().get(0);
        user.deleteMessage(message);
        accessUsers.updateUser(user);
        user = accessUsers.searchName("Jason");
        assertTrue(user.getMessageList().get(0) != null);
        message = user.getMessageList().get(0);
        user.deleteMessage(message);
        accessUsers.updateUser(user);
        user = accessUsers.searchName("Bryan");
        user.deleteFriend("Marc");
        user.deleteFriend("Jason");
        accessUsers.updateUser(user);

        // End Test
        Services.closeDataAccess();
        System.out.println("Finished Integration test of AccessUsers to persistence");
    }

    public void testAccessActivities()
    {
        AccessActivities accessActivity;
        ActivityInformation activity;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessActivities to persistence");

        Services.createDataAccess(Main.dbName);

        accessActivity = new AccessActivities();

        // Test getSequential
        activity = accessActivity.getSequential();
        assertTrue("jogging, general".equals(activity.getActivity()));
        assertTrue( 7.0 == activity.getMET());

        activity = accessActivity.getSequential();
        assertTrue("jogging, in place".equals(activity.getActivity()));
        assertTrue( 8.0 == activity.getMET());

        activity = accessActivity.getSequential();
        assertTrue("jogging, on a mini-tramp".equals(activity.getActivity()));
        assertTrue( 4.5 == activity.getMET());

        activity = accessActivity.getSequential();
        assertTrue("running, 4 mph (13 min/mile)".equals(activity.getActivity()));
        assertTrue( 6.0 == activity.getMET());

        activity = accessActivity.getSequential();
        assertTrue("running, 5.2 mph (11.5 min/mile)".equals(activity.getActivity()));
        assertTrue( 9.0 == activity.getMET());

        activity = accessActivity.searchActivity("Nothing");
        assertNull(activity);

        // Test searchUser
        assertNull(accessActivity.refreshActivities());
        for(int i=0;i<4;i++)
        {
            activity = accessActivity.getSequential();
            assertTrue(activity.getActivity().equals(accessActivity.searchActivity(activity.getActivity()).getActivity()));
            assertNull(accessActivity.searchActivity(i+""));
        }
        assertNull(accessActivity.refreshActivities());
        Services.closeDataAccess();
        System.out.println("Finished Integration test of AccessActivities to persistence");
    }
}

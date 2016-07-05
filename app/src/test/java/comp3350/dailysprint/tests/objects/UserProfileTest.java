package comp3350.dailysprint.tests.objects;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

import comp3350.dailysprint.objects.Activity;
import comp3350.dailysprint.objects.Event;
import comp3350.dailysprint.objects.Message;
import comp3350.dailysprint.objects.UserProfile;

import static java.lang.Math.sqrt;

public class UserProfileTest extends TestCase{
    public UserProfileTest(String arg0)
    {
        super(arg0);
    }

    public void testUserProfileGettersAndSetters() {
        UserProfile user;
        System.out.println("\nStarting testUserProfileGettersAndSetters");

        user = new UserProfile("Jason","password", 21, 176, 70, true);
        assertNotNull(user);

        assertTrue("Jason".equals(user.getUsername()));
        assertFalse("jason".equals(user.getUsername()));
        assertTrue("Jason".compareTo(user.getUsername()) == 0);
        assertEquals("password",user.getPassword());

        assertEquals(21,user.getUserAge());
        assertEquals(176,user.getUserHeight());
        assertEquals(70,user.getUserWeight());
        assertTrue(user.getUserGender());

        assertNotNull(user.getScheduleEventList());
        assertNotNull(user.getHistoryEventList());
        assertNotNull(user.getMessageList());
        assertNotNull(user.getFriendsList());
        assertNotNull(user.getRequestList());

        //set weight, and check if BMI updates
        assertEquals(70,user.getUserWeight());

        double userBMI = 70/sqrt(176/100.0);
        assertEquals(userBMI,user.getUserBMI());

        user.setUserWeight(50);
        assertEquals(50,user.getUserWeight());

        double newBMI = 50/sqrt(176/100.0);
        assertEquals(newBMI,user.getUserBMI());

        //set weight back to 70 and verify
        user.setUserWeight(70);
        assertEquals(70,user.getUserWeight());


        System.out.println("Finished testUserProfileGettersAndSetters");
    }

    public void testUserProfileDomainObjects() {

        System.out.println("\nStarting testUserProfileDomainObjects");

        UserProfile user;
        Date date = new Date();


        ArrayList<Event> history = new ArrayList<>();
        history.add(new Event(date,new Activity(1,"running")));
        history.add(new Event(date,new Activity(1,null)));

        user = new UserProfile("Reagan","password", 22, 160, 120, false,null,null,null,null,null);
        assertNotNull(user);

        assertTrue("Reagan".equals(user.getUsername()));
        assertFalse("reagan".equals(user.getUsername()));
        assertTrue("Reagan".compareTo(user.getUsername()) == 0);
        assertEquals("password",user.getPassword());

        assertEquals(22,user.getUserAge());
        assertEquals(160,user.getUserHeight());
        assertEquals(120,user.getUserWeight());
        assertFalse(user.getUserGender());

        double userBMI = 120/sqrt(160/100.0);
        assertEquals(userBMI,user.getUserBMI());

        assertNotNull(user.getScheduleEventList());
        assertNotNull(user.getHistoryEventList());
        assertNotNull(user.getMessageList());
        assertNotNull(user.getFriendsList());
        assertNotNull(user.getRequestList());

        UserProfile newUser = new UserProfile("Reagan","password", 22, 160, 120, false,null,null,null,null,null);
        assertNotNull(newUser);

        assertTrue(newUser.equals(user));
        assertTrue(user.equals((Object)newUser));

        //Friends: add, remove
        ArrayList<String> friends = new ArrayList<>();
        friends.add("Bryan");
        friends.add("Jason");

        user = new UserProfile("Reagan","password", 22, 160, 120, true,null,null,friends,null,null);
        assertNotNull(user);

        assertEquals(friends,user.getFriendsList());
        assertEquals(2,user.getFriendsList().size());
        assertTrue(user.getFriendsList().contains("Jason"));

        user.deleteFriend("Jason");
        assertEquals(1,user.getFriendsList().size());
        assertFalse(user.getFriendsList().contains("Jason"));

        //Schedule: replace schedule
        ArrayList<Event> schedule = new ArrayList<>();
        schedule.add(new Event(date,new Activity(1,"running")));

        //verify empty schedule list
        assertNotNull(user.getScheduleEventList());
        assertEquals(0,user.getScheduleEventList().size());

        //replace schedule list with schedule arrayList of 1 element
        user.replaceSchedule(schedule);
        assertNotNull(user.getScheduleEventList());
        assertEquals(1,user.getScheduleEventList().size());
        assertEquals(schedule,user.getScheduleEventList());

        //replace schedule list with null which should mean that
        //we have an empty schedule - verify by checking size == 0
        assertNotNull(user.getScheduleEventList());
        user.replaceSchedule(null);
        assertEquals(0,user.getScheduleEventList().size());
        //assertNull(user.getScheduleEventList());

        //History: add History Event
        assertNotNull(user.getHistoryEventList());
        assertEquals(0,user.getHistoryEventList().size());

        user.addHistoryEvent(date,"running",1);
        assertNotNull(user.getHistoryEventList());
        assertEquals(1,user.getHistoryEventList().size());
        assertTrue(history.get(0).equals(user.getHistoryEventList().get(0)));

        assertNotNull(user.getHistoryEventList());
        user.addHistoryEvent(date,null,1);
        assertNotNull(user.getHistoryEventList());
        assertEquals(2,user.getHistoryEventList().size());
        assertTrue(history.get(1).equals(user.getHistoryEventList().get(1)));

        //Requests
        //try adding a request to an empty list - rqstList == null
        String request = "Marc Wants To Be Your Friend";

        assertNotNull(user.getRequestList());

        assertEquals(0,user.getRequestList().size()); //no messages
        user.addRequest(request);

        assertNotNull(user.getRequestList());
        assertEquals(1,user.getRequestList().size());
        assertTrue(request.equals(user.getRequestList().get(0)));

        //Message: add, remove
        Message message = new Message("Marc","Reagan","Request","Accepted Your Request");
        assertNotNull(message);

        assertNotNull(user.getMessageList());
        assertEquals(0,user.getMessageList().size());

        user.addMessage(message);

        assertNotNull(user.getMessageList());
        assertEquals(1,user.getMessageList().size());
        assertTrue(message.equals(user.getMessageList().get(0)));

        user.deleteMessage(message);
        assertNotNull(user.getMessageList());
        assertEquals(0,user.getMessageList().size());
        assertFalse(user.getMessageList().contains(message));


        System.out.println("Finished testUserProfileDomainObjects");
    }

}
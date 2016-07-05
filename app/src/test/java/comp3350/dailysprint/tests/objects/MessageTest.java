package comp3350.dailysprint.tests.objects;


import junit.framework.TestCase;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.Message;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.persistence.DataAccess;
import comp3350.dailysprint.tests.persistence.DataAccessStub;

public class MessageTest extends TestCase{
    private static String dbName = Main.dbName;

    public MessageTest(String arg0) {super(arg0);}

    public void testMessageCase1(){
        System.out.println("\nStarting testMessageCase1");

        Message msg = new Message("To","From","Type","Message");
        assertNotNull(msg);
        assertEquals("To",msg.getTo());
        assertEquals("From",msg.getFrom());
        assertFalse("tYpe".equals(msg.getType()));
        assertEquals("Message",msg.getMessage());
        assertFalse("message".equals(msg.getMessage()));


        System.out.println("Finished testMessageCase1");
    }

    public void testMessageCase2(){
        System.out.println("\nStarting testMessageCase2");

        Message msg = new Message(null,null,null,null);
        assertNotNull(msg);
        assertEquals(null,msg.getMessage());
        System.out.println("Finished testMessageCase2");
    }

    public void testMessageCase3(){
        System.out.println("\nStarting testMessageCase3");

        Message msg1 = new Message(null,null,null,null);
        Message msg2 = new Message("To","From","Type","Message");
        Message msg3 = new Message("","","","");
        Message msg4 = new Message("To","From",null,"Message");


        System.out.println("Finished testMessageCase3");
    }

    public void testMessageCase4() {
        System.out.println("\nStarting testMessageCase4\n");

        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));



        AccessUsers accessUsers = new AccessUsers();
        UserProfile user = accessUsers.searchName("Bryan");


        assertNotNull(user);
        assertEquals(0,user.getMessageList().size());

        Message msg2 = new Message(user.getUsername(),"From","Type","Message");
        accessUsers.send(msg2);
        assertEquals(1,user.getMessageList().size());

        Message msg3 = new Message(user.getUsername(),"From","Type",null);
        accessUsers.send(msg3);
        assertEquals(2,user.getMessageList().size());

        Services.closeDataAccess();

        System.out.println("\nFinished testMessageCase4");


    }
}

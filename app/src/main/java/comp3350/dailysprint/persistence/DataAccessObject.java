package comp3350.dailysprint.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.dailysprint.objects.Activity;
import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.objects.Event;
import comp3350.dailysprint.objects.Message;
import java.util.Date;


public class DataAccessObject implements DataAccess{

    private Statement st1, st2;
    private Connection connection1;
    private ResultSet rs2, rs4;

    private String dbName;
    private String dbType;

    private String cmdString;
    private int updateCount;
    private String result;

    public DataAccessObject(String dbName)
    {
        this.dbName = dbName;
    }


    public String getUserSequential(List<UserProfile> userResult) {

        UserProfile user;

        result = null;
        try {
            cmdString = "SELECT * FROM UserProfile";
            rs2 = st1.executeQuery(cmdString);
        }

        catch (Exception e) {
            result = processSQLError(e);
        }

        try {
            if(rs2 !=null) {
                while (rs2.next()) {
                    String myName = rs2.getString("UserName");
                    String myPW = rs2.getString("Password");
                    int myAge = rs2.getInt("Age");
                    int myHeight = rs2.getInt("Height");
                    int myWeight = rs2.getInt("Weight");
                    boolean myGender = rs2.getBoolean("Gender");
                    ArrayList<Event> myHistory = getHistory(myName);
                    ArrayList<Event> mySchedule = getSchedule(myName);
                    ArrayList<String> myFriends = getFriends(myName);
                    ArrayList<String> myRequests = getRequests(myName);
                    ArrayList<Message> myMessages = getMessages(myName);

                    user = new UserProfile(myName, myPW, myAge, myHeight, myWeight, myGender, myHistory, mySchedule, myFriends, myRequests, myMessages);
                    userResult.add(user);
                }

                rs2.close();
            }
        }

        catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertUser(UserProfile currentUser) {
        String values;
        result = null;

        try {
            values =" '" + currentUser.getUsername() +
                    "', '" + currentUser.getPassword() +
                    "', '" + currentUser.getUserAge() +
                    "', '" + currentUser.getUserHeight() +
                    "', '" +currentUser.getUserWeight() +
                    "', '" + currentUser.getUserGender() +
                    "'";
            cmdString = "INSERT into UserProfile " +"(username,password,age,height,weight,gender)"+" VALUES(" +values +")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }

        catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String updateUser(UserProfile currentUser) {
        String values;
        String where;

        result = null;
        try {
            values = "Age = '" + currentUser.getUserAge()
                    + "', Height = '" + currentUser.getUserHeight()
                    + "', Password = '" + currentUser.getPassword()
                    + "', Weight = '" + currentUser.getUserWeight()
                    + "', Gender = '" + currentUser.getUserGender()
                    + "'";
            where = "WHERE UserName = " + "'" + currentUser.getUsername() + "'";
            cmdString = "UPDATE UserProfile SET " + values + " " + where;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            updateHistory(currentUser);
            updateSchedule(currentUser);
            updateFriends(currentUser);
            updateRequests(currentUser);
            updateMessages(currentUser);
        }
        catch(Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String deleteUser(UserProfile currentUser)
    {
        String values;
        result = null;

        try {
            values = "'" + currentUser.getUsername() + "'";
            cmdString = "DELETE FROM UserProfile WHERE UserName = "+values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            cmdString = "DELETE FROM History WHERE HName = "+values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            cmdString = "DELETE FROM Schedule WHERE SName = "+values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            cmdString = "DELETE FROM Friends WHERE FName = "+values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            cmdString = "DELETE FROM Requests WHERE RName = "+values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            cmdString = "DELETE FROM Messages WHERE MName = "+values;
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }

        catch (Exception e) {
            result = processSQLError(e);
        }
        return result;
    }

    public String getUserName(UserProfile currentUser)
    {
        result = null;
        String values;

        try {
            values = "'" + currentUser.getUsername() + "'";
            cmdString = "Select * From UserProfile WHERE UserName = "+values;

            rs2 = st1.executeQuery(cmdString);
            if(rs2 != null)
            {
                while(rs2.next())
                {
                    result = rs2.getString("UserName");
                }
                rs2.close();
            }
        }
        catch (Exception e) {
            result = processSQLError(e);
        }
        return result;

    }
    public void updateHistory(UserProfile user)
    {
        String values;
        String username = user.getUsername();
        ArrayList<Event> history = user.getHistoryEventList();
        try
        {
            cmdString = "DELETE FROM History WHERE HName = " + "'" + username + "'";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            for(int i = 0; i<history.size();i++)
            {
                Event event = history.get(i);
                values =" '" + username +
                        "', '" + event.getDate().toString() +
                        "', '" + event.getActivity().getTime() +
                        "', '" + event.getActivity().getActivity() +
                        "'";
                cmdString = "INSERT into History " +" VALUES(" +values +")";
                updateCount = st1.executeUpdate(cmdString);
                result = checkWarning(st1, updateCount);
            }
        }
        catch (Exception e) {
            processSQLError(e);
        }
    }

    public void updateSchedule(UserProfile user)
    {
        String values;
        String username = user.getUsername();
        ArrayList<Event> schedule = user.getScheduleEventList();
        try
        {
            cmdString = "DELETE FROM Schedule WHERE SName = " + "'" + username + "'";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            for(int i = 0; i< schedule.size(); i++)
            {
                Event event = schedule.get(i);
                values =" '" + username +
                        "', '" + event.getDate().toString() +
                        "', '" + event.getActivity().getTime() +
                        "', '" + event.getActivity().getActivity() +
                        "'";
                cmdString = "INSERT into Schedule " +" VALUES(" +values +")";
                updateCount = st1.executeUpdate(cmdString);
                result = checkWarning(st1, updateCount);
            }
        }
        catch (Exception e) {
            processSQLError(e);
        }
    }

    public void updateFriends(UserProfile user)
    {
        String values;
        String username = user.getUsername();
        ArrayList<String> friends = user.getFriendsList();
        try
        {
            cmdString = "DELETE FROM Friends WHERE FName = " + "'" + username + "'";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            for(int i = 0; i< friends.size(); i++)
            {
                values =" '" + username +
                        "', '" + friends.get(i) +
                        "'";
                cmdString = "INSERT into Friends " +" VALUES(" +values +")";
                updateCount = st1.executeUpdate(cmdString);
                result = checkWarning(st1, updateCount);
            }
        }
        catch (Exception e) {
            processSQLError(e);
        }
    }

    public void updateRequests(UserProfile user)
    {
        String values;
        String username = user.getUsername();
        ArrayList<String> requests = user.getRequestList();
        try
        {
            cmdString = "DELETE FROM Requests WHERE RName = " + "'" + username + "'";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            for(int i = 0; i< requests.size(); i++)
            {
                values =" '" + username +
                        "', '" + requests.get(i) +
                        "'";
                cmdString = "INSERT into Requests " +" VALUES(" +values +")";
                updateCount = st1.executeUpdate(cmdString);
                result = checkWarning(st1, updateCount);
            }
        }
        catch (Exception e) {
            processSQLError(e);
        }
    }

    public void updateMessages(UserProfile user)
    {
        String values;
        String username = user.getUsername();
        ArrayList<Message> messages = user.getMessageList();
        try
        {
            cmdString = "DELETE FROM Messages WHERE MName = " + "'" + username + "'";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);

            for(int i = 0; i< messages.size(); i++)
            {
                Message message = messages.get(i);
                values =" '" + username +
                        "', '" + message.getTo() +
                        "', '" + message.getFrom() +
                        "', '" + message.getType() +
                        "', '" + message.getMessage() +
                        "'";
                cmdString = "INSERT into Messages " +" VALUES(" +values +")";
                updateCount = st1.executeUpdate(cmdString);
                result = checkWarning(st1, updateCount);
            }
        }
        catch (Exception e) {
            processSQLError(e);
        }
    }


    public ArrayList<Event> getHistory(String username)
    {
        Date myDate;
        String myActivity, values;
        int myTime;
        ArrayList<Event> history = new ArrayList<>();

        try
        {
            values = "'" + username + "'";
            cmdString = "Select * from History Where HName = " + values;
            rs4 = st2.executeQuery(cmdString);
            if(rs4 != null)
            {
                while (rs4.next()) {
                    myDate = new Date(rs4.getString("Day"));
                    myActivity = rs4.getString("Activity");
                    myTime = rs4.getInt("Time");
                    history.add(new Event(myDate, new Activity(myTime, myActivity)));
                }
                rs4.close();
            }
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        return history;
    }

    public ArrayList<Event> getSchedule(String username)
    {
        Date myDate;
        String myActivity, values;
        int myTime;
        ArrayList<Event> schedule = new ArrayList<>();

        try
        {
            values = "'" + username + "'";
            cmdString = "Select * from Schedule Where SName = " + values;
            rs4 = st2.executeQuery(cmdString);
            if(rs4 != null)
            {
                while (rs4.next()) {
                    myDate = new Date(rs4.getString("Day"));
                    myActivity = rs4.getString("Activity");
                    myTime = rs4.getInt("Time");
                    schedule.add(new Event(myDate, new Activity(myTime, myActivity)));
                }
                rs4.close();
            }
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        return schedule;
    }

    public ArrayList<String> getFriends(String username)
    {
        String myFriend, values;
        ArrayList<String> friends = new ArrayList<>();
        try
        {
            values = "'" + username + "'";
            cmdString = "Select * from Friends Where FName = " + values;
            rs4 = st2.executeQuery(cmdString);
            if(rs4 != null)
            {
                while (rs4.next()) {
                    myFriend = rs4.getString("Friend");
                    friends.add(myFriend);
                }
                rs4.close();
            }
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        return friends;
    }

    public ArrayList<String> getRequests(String username)
    {
        String myRequest, values;
        ArrayList<String> requests = new ArrayList<>();
        try
        {
            values = "'" + username + "'";
            cmdString = "Select * from Requests Where RName = " + values;
            rs4 = st2.executeQuery(cmdString);
            if(rs4 != null)
            {
                while (rs4.next()) {
                    myRequest = rs4.getString("Friend");
                    requests.add(myRequest);
                }
                rs4.close();
            }
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        return requests;
    }

    public ArrayList<Message> getMessages(String username)
    {
        String myReceiver,mySender,myType,myMessage, values;
        ArrayList<Message> messages = new ArrayList<>();

        try
        {
            values = "'" + username + "'";
            cmdString = "Select * from Messages Where MName = " + values;
            rs4 = st2.executeQuery(cmdString);
            if(rs4 != null)
            {
                while (rs4.next()) {
                    myReceiver = rs4.getString("Receiver");
                    mySender = rs4.getString("Sender");
                    myType = rs4.getString("Type");
                    myMessage = rs4.getString("Message");
                    messages.add(new Message(myReceiver, mySender, myType, myMessage));
                }
                rs4.close();
            }
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        return messages;
    }


    public String getActivitySequential(ArrayList<ActivityInformation> activitiesResult){

        ActivityInformation activity;
        String name;
        double met;

        result = null;
        try {
            cmdString = "SELECT * FROM ActivityInformation";
            rs2 = st1.executeQuery(cmdString);
        }

        catch (Exception e) {
            result = processSQLError(e);
        }

        try {
            if(rs2 !=null) {
                while (rs2.next()) {
                    name = rs2.getString("Activity");
                    met = rs2.getDouble("MET");

                    activity = new ActivityInformation(name,met);
                    activitiesResult.add(activity);
                }

                rs2.close();
            }
        }

        catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String checkWarning(Statement st, int updateCount) {
        String result;
        result = null;
        try
        {
            SQLWarning warning = st.getWarnings();
            if (warning != null)
            {
                result = warning.getMessage();
            }
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        if (updateCount < 0)
        {
            result = "Tuple not inserted correctly.";
        }
        return result;
    }

    public String processSQLError(Exception e) {
        String result = "*** SQL Error: " + e.getMessage();

        e.printStackTrace();

        return result;
    }

    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            connection1 = DriverManager.getConnection(url, "SA", "");
            st1 = connection1.createStatement();
            st2 = connection1.createStatement();
            System.out.println("Opened " +dbType +" database " +dbPath);

        }

        catch(Exception e) {
            processSQLError(e);
        }

    }

    public void close() {
        try
        {	// commit all changes to the database
            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            connection1.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " +dbType +" database " +dbName);
    }


}

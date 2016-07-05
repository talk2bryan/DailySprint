package comp3350.dailysprint.objects;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.sqrt;

public class UserProfile
{
    private String username;
    private int userAge;
    private int userHeight;
    private int userWeight;
    private boolean userGender;
    private double BMI;
    private String userPassword;
    private ArrayList<Event> scheduleList;
    private ArrayList<Event> historyList;
    private ArrayList<String> friendsList;
    private ArrayList<String> requestList;
    private ArrayList<Message> messageList;

    public UserProfile(String name, String password, int age, int height, int weight, boolean gender)
    {
        username = name;
        userAge = age;
        userHeight = height;
        userWeight = weight;
        userGender = gender;
        userPassword = password;
        historyList = new ArrayList<>();
        scheduleList = new ArrayList<>();
        friendsList = new ArrayList<>();
        requestList = new ArrayList<>();
        messageList = new ArrayList<>();
        calculateBMI();
    }
    public UserProfile(String name, String password, int age, int height, int weight, boolean gender,
                       ArrayList<Event> history, ArrayList<Event> schedule, ArrayList<String> friends,
                       ArrayList<String> request, ArrayList<Message> message)
    {
        username = name;
        userAge = age;
        userHeight = height;
        userWeight = weight;
        userGender = gender;
        userPassword = password;
        historyList = history;
        scheduleList = schedule;
        friendsList = friends;
        requestList = request;
        messageList = message;
        calculateBMI();
    }
    // Get elements of user profile
    public String getUsername() {return (username);}
    public int getUserAge() {return (userAge);}
    public int getUserHeight() {return (userHeight);}
    public int getUserWeight() {return (userWeight);}
    public boolean getUserGender() {return (userGender);}
    public String getPassword(){return userPassword;}
    public double getUserBMI(){return BMI;}

    public ArrayList<Event> getScheduleEventList()
    {
        ArrayList<Event> resultList = new ArrayList<>();

        if(scheduleList != null)
        {
            for (int i = 0; i < scheduleList.size(); i++)
            {
                resultList.add(i,scheduleList.get(i));
            }
        }
        return resultList;
    }
    public ArrayList<Event> getHistoryEventList()
    {
        ArrayList<Event> resultList = new ArrayList<>();

        if (historyList != null)
        {
            for (int i = 0; i < historyList.size(); i++)
            {
                resultList.add(i,historyList.get(i));
            }
            return resultList;
        }
        return resultList;
    }
    public ArrayList<String> getFriendsList()
    {
        ArrayList<String> resultList = new ArrayList<>();

        if(friendsList != null)
        {
            for (int i = 0; i < friendsList.size(); i++)
            {
                resultList.add(i,friendsList.get(i));
            }
            return resultList;
        }
        return resultList;
    }
    public ArrayList<String> getRequestList()
    {
        ArrayList<String> resultList = new ArrayList<>();

        if(requestList != null)
        {
            for (int i = 0; i < requestList.size(); i++)
            {
                resultList.add(i,requestList.get(i));
            }
            return resultList;
        }
        return resultList;
    }
    public ArrayList<Message> getMessageList()
    {
        ArrayList<Message> resultList = new ArrayList<>();

        if (messageList != null)
        {
            for (int i = 0; i < messageList.size(); i++)
            {
                resultList.add(i,messageList.get(i));
            }
            return resultList;
        }
        return resultList;
    }

    public String toString()
    {
        return "Name: " + username +"\nAge: " +userAge + "\nHeight: " + userHeight+"\nWeight: "
                + userWeight+"\nGender: " + userGender;
    }

    public void setUserWeight(int weight)
    {
        this.userWeight = weight;
        calculateBMI();
    }

    private void calculateBMI()
    {
        this.BMI = userWeight/sqrt(userHeight/100.0);
    }

    public boolean equals(Object object)
    {
        boolean result;
        UserProfile userprofile;

        result = false;

        if (object instanceof UserProfile)
        {
            userprofile = (UserProfile) object;
            if (userprofile.getUsername().equals(username))
            {
                result = true;
            }
        }
        return result;
    }

    public void replaceSchedule(ArrayList<Event> newSchedule)
    {
        scheduleList = newSchedule;
    }

    public void addHistoryEvent(Date date, String activity, int time)
    {
        if (historyList == null)
        {
            historyList = new ArrayList<>();
        }
        historyList.add(new Event(date, new Activity(time, activity)));
    }

    public void addFriend(String name)
    {
        if (friendsList == null)
        {
            friendsList = new ArrayList<>();
        }
        friendsList.add(name);
    }
    
    public void deleteFriend(String name)
    {
        if (friendsList != null)
        {
            for(int i=0;i<friendsList.size();i++ )
            {
                if (friendsList.get(i).equals(name))
                {
                    friendsList.remove(i);
                }
            }
        }
    }

    public void addRequest(String name)
    {
        if (requestList == null)
        {
            requestList = new ArrayList<>();
        }
        requestList.add(name);
    }

    public void deleteRequest(String name)
    {
        if (requestList != null)
        {
            requestList.remove(name);
        }
    }

    public void addMessage(Message message)
    {
        if (messageList == null)
        {
            messageList = new ArrayList<>();
        }
        messageList.add(message);
    }

    public void deleteMessage(Message message)
    {
        if (messageList != null)
        {
            messageList.remove(message);
        }
    }
}

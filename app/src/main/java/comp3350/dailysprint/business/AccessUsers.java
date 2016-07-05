package comp3350.dailysprint.business;

import java.util.ArrayList;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.objects.Message;
import comp3350.dailysprint.persistence.DataAccess;

public class AccessUsers
{
	private DataAccess dataAccess;
	private ArrayList<UserProfile> users;
	private UserProfile user;
	private int currentUser;

	public AccessUsers()
	{
		dataAccess = Services.getDataAccess(Main.dbName);
		users = new ArrayList<>();
        dataAccess.getUserSequential(users);
		user = null;
		currentUser = 0;
	}

    public String refreshUsers()
    {
        users.clear();
        currentUser = 0;
        return dataAccess.getUserSequential(users);
    }

    public UserProfile getSequential()
    {
        if (users == null)
        {
            users = new ArrayList<>();
            dataAccess.getUserSequential(users);
            currentUser = 0;
        }
        if (currentUser < users.size())
        {
            user = users.get(currentUser);
            currentUser++;
        }
        else
        {
            refreshUsers();
            user = null;
            currentUser = 0;
        }
        return user;
    }

    public void removeByName(ArrayList<UserProfile> userList, String name)
    {
        for(int i=0;i<userList.size();i++)
        {
            if(userList.get(i).getUsername().equals(name))
            {
                userList.remove(i);
            }
        }
    }

    public UserProfile searchName(String name)
    {
        UserProfile curUser;
        UserProfile user = null;
        refreshUsers();
        while((curUser = getSequential()) != null)
        {
            if(curUser.getUsername().equals(name))
            {
                user = curUser;
            }
        }
        refreshUsers();
        return user;
    }

    public ArrayList<UserProfile> getRequestFriends(UserProfile user)
    {
        ArrayList <String> friendList = user.getFriendsList();
        ArrayList <String> requestList = user.getRequestList();
        ArrayList<UserProfile> userList = new ArrayList<>();
        for(int i=0;i<users.size();i++)
        {
            userList.add(users.get(i));
        }
        removeByName(userList,user.getUsername());
        for(int i=0;i<friendList.size();i++)
        {
            removeByName(userList,friendList.get(i));
        }
        for(int i=0;i<requestList.size();i++)
        {
            removeByName(userList,requestList.get(i));
        }
        return userList;
    }


    public ArrayList<UserProfile> getUsers()
    {
        return users;
    }

	public String insertUser(UserProfile currentUser)
	{
		String check = dataAccess.insertUser(currentUser);
        refreshUsers();
        return check;
	}

	public String updateUser(UserProfile currentUser)
	{
        String check = dataAccess.updateUser(currentUser);
        refreshUsers();
        return check;
	}

	public String deleteUser(UserProfile user)
	{
        String check;
        String username = user.getUsername();
        UserProfile currUser;

        for(int i=0;i<users.size();i++)
        {
            currUser = users.get(i);
            ArrayList<String> friends = currUser.getFriendsList();
            ArrayList<String> requests = currUser.getRequestList();
            ArrayList<Message> messages = currUser.getMessageList();
            friends.remove(username);
            requests.remove(username);

            for(int k=0;k<messages.size();k++)
            {
                String from = messages.get(k).getFrom();
                if(from.equals(username))
                {
                    messages.remove(k);
                    k--;
                }
            }
            updateUser(currUser);
        }

        check = dataAccess.deleteUser(user);
        refreshUsers();
        return check;
	}

    public String send(Message message)
    {
        String result = null;
        UserProfile user = searchName(message.getTo());
        if(user != null) {
            user.addMessage(message);
            updateUser(user);
        }
        else
        {
            result = "UDE";
        }
        return result;
    }

    public String sendToFriends(UserProfile user, String message)
    {
        String result = null;
        String username = user.getUsername();
        ArrayList<String> friends = user.getFriendsList();
        for(int i=0;i<friends.size();i++)
        {
            String friendname = friends.get(i);
            Message send = new Message(friendname, username, "message", message);
            if(send(send) != null)
            {
                result = "UDE";
            }
        }
        return result;
    }

}

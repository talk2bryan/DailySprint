package comp3350.dailysprint.acceptance;

import java.util.ArrayList;
import java.util.List;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.persistence.DataAccess;

public class DataAccessStub implements DataAccess
{
	private String dbName;
	private String dbType = "stub";

	private ArrayList<UserProfile> users;
	private ArrayList<ActivityInformation> activities;

	public DataAccessStub(String dbName)
	{
		this.dbName = dbName;
	}
	public DataAccessStub()
	{
		this(Main.dbName);
	}

	public void open(String dbName)
	{
		UserProfile user;

		users = new ArrayList<>();
		user = new UserProfile("Bryan", "password", 21, 140, 170, false);
		users.add(user);
		user = new UserProfile("Jason", "password",21, 176, 180, false);
		users.add(user);
		user = new UserProfile("Marc", "password", 22, 140, 175, false);
		users.add(user);
		user = new UserProfile("Reagan", "password", 20, 130, 170, true);
		users.add(user);


        activities = new ArrayList<>();

        activities.add(new ActivityInformation("running, 4 mph (13 min/mile)",6.0));
        activities.add(new ActivityInformation("running, 5.2 mph (11.5 min/mile)",9.0));
        activities.add(new ActivityInformation("running, 6 mph (10 min/mile)",9.8));
        activities.add(new ActivityInformation("running, 6.7 mph (9 min/mile)",10.5));
        activities.add(new ActivityInformation("running, 7 mph (8.5 min/mile)",11.0));
        activities.add(new ActivityInformation("running, 7.5 mph (8 min/mile)",11.5));
        activities.add(new ActivityInformation("running, 8 mph (7.5 min/mile)",11.8));
        activities.add(new ActivityInformation("running, 8.6 mph (7 min/mile)",12.3));


        activities.add(new ActivityInformation("walking, 2.5 mph, level, firm surface",3.0));
        activities.add(new ActivityInformation("walking, 2.5 mph, downhill",3.3));
        activities.add(new ActivityInformation("walking, 2.8 to 3.2 mph, level, moderate pace, firm surface",3.5));
        activities.add(new ActivityInformation("walking, 3.5 mph, level, brisk, firm surface, walking for exercise",4.3));
        activities.add(new ActivityInformation("walking, 2.9 to 3.5 mph, uphill, 1 to 5% grade",5.3));
        activities.add(new ActivityInformation("walking, 2.9 to 3.5 mph, uphill, 6% to 15% grade",8.0));
        activities.add(new ActivityInformation("walking, 4.0 mph, level, firm surface, very brisk pace",5.0));


        activities.add(new ActivityInformation("jogging, general",7.0));
        activities.add(new ActivityInformation("jogging, in place",8.0));
        activities.add(new ActivityInformation("jogging, on a mini-tramp",4.5));

		System.out.println("Opened " +dbType +" database " +dbName);
	}

	public void close()
	{
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	public String getUserSequential(List<UserProfile> userResult) {
        userResult.addAll(users);
		return null;
	}


	public String insertUser(UserProfile currentUser)
	{
		users.add(currentUser);
		return null;
	}

	public String updateUser(UserProfile currentUser) {
		for(int i = 0; i<users.size();i++)
		{
			if(users.get(i).getUsername().equals(currentUser.getUsername()))
			{
				users.set(i, currentUser);
			}
		}
		return null;
	}

	public UserProfile getCurrentUser(UserProfile currentUser) {
		UserProfile user = null;
		for(int i = 0; i<users.size();i++)
		{
			if(users.get(i).getUsername().equals(currentUser.getUsername()))
			{
				user = users.get(i);
			}
		}
		return user;
	}


	public String deleteUser(UserProfile currentUser) {
		int index;
		
		index = users.indexOf(currentUser);
		if (index >= 0)
		{
			users.remove(index);
		}
		return null;
	}

    public String getUserName(UserProfile currentUser) {
        int index;
        String result = null;

        index = users.indexOf(currentUser);
        if (index >= 0)
        {
            result = users.get(index).getUsername();
        }
        return result;
    }

    public String updateUserWeight(UserProfile currentUser, int weight) {
        int index;
        index = users.indexOf(currentUser);
        if(index >= 0) {
            users.get(index).setUserWeight(weight);
        }
        return null;
    }

	public String getActivitySequential(ArrayList<ActivityInformation> activitiesResult) {
		activitiesResult.addAll(activities);
		return null;
	}

    public String insertActivity(ActivityInformation curActivity) {
        activities.add(curActivity);
        return null;
    }

    public String updateActivity(ActivityInformation curActivity) {
        for(int i = 0; i<activities.size();i++) {
            if(activities.get(i).getActivity().equals(curActivity.getActivity())) {
                activities.set(i, curActivity);
            }
        }
        return null;
    }

    public String deleteActivity(ActivityInformation curActivity) {
        int index;

        index = activities.indexOf(curActivity);
        if (index >= 0) {
            activities.remove(index);
        }
        return null;
    }

    public double getMET(ActivityInformation curActivity) {
        int index;
        double result = -1;

        index = activities.indexOf(curActivity);
        if (index >= 0) {
            result = activities.get(index).getMET();
        }
        return result;
    }

    public ArrayList<UserProfile> getProfileList() {
        return users;
    }


}
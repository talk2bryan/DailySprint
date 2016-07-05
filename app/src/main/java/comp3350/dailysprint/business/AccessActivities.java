package comp3350.dailysprint.business;

import java.util.ArrayList;

import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.application.Services;
import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.persistence.DataAccess;

public class AccessActivities {

    private DataAccess dataAccess = Services.getDataAccess(Main.dbName);
    private ArrayList<ActivityInformation> activities;
    private ActivityInformation activityInfo;
    private int currentActivity;

    public AccessActivities(){
        activities = new ArrayList<>();
        dataAccess.getActivitySequential(activities);
        activityInfo = null;
        currentActivity = 0;
    }
    public String refreshActivities(){
        activities.clear();
        currentActivity = 0;
        return dataAccess.getActivitySequential(activities);
    }

    public ActivityInformation getSequential() {
        if (activities == null) {
            activities = new ArrayList<>();
            dataAccess.getActivitySequential(activities);
            currentActivity = 0;
        }
        if (currentActivity < activities.size())
        {
            activityInfo = activities.get(currentActivity);
            currentActivity++;
        }
        else
        {
            refreshActivities();
            activityInfo = null;
        }
        return activityInfo;
    }

    public double getMET(String activity){
        ActivityInformation newActivity = searchActivity(activity);
        return newActivity.getMET();
    }

    public ActivityInformation searchActivity(String name){
        ActivityInformation curActivity;
        ActivityInformation activity = null;
        refreshActivities();
        while((curActivity = getSequential()) != null)
        {
            if(curActivity.getActivity().equals(name))
            {
                activity = curActivity;
            }
        }
        refreshActivities();
        return activity;
    }

    public ArrayList<String> activitiesToString()
    {
        ArrayList<String> strings = new ArrayList<>();
        ActivityInformation curActivity;
        refreshActivities();
        while((curActivity = getSequential()) != null)
        {
            strings.add(curActivity.getActivity());
            currentActivity++;
        }
        refreshActivities();
        return strings;
    }
}

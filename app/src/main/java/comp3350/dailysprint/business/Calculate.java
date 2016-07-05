package comp3350.dailysprint.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.dailysprint.objects.Activity;
import comp3350.dailysprint.objects.Event;

public class Calculate
{

	public static int calorieLoss(double MET, int weight, int time)
	{
			double calories = MET * (double)weight * (double)time / (60.0);
			return (int) calories;
	}

	public static int random(int max, int min)
	{
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}

	public static ArrayList<Event> createSchedule(String activityName, int days,
												  int avgTime, int bestTime, int goalTime)
	{
		ArrayList<Event> eventList = new ArrayList<>();

		int timeRange = bestTime - avgTime;
		double timeIncrement = (double)(goalTime - bestTime)/(double)days;
		double dailyTime = avgTime;

		Date newDate = new Date();
		Activity newActivity;

		for(int i = 0; i < days-1; i++)
		{
			newActivity = new Activity((int) dailyTime, activityName);
			eventList.add(new Event(newDate, newActivity));
			Calendar c = Calendar.getInstance();
			c.setTime(newDate);
			c.add(Calendar.DATE, 1);
			newDate = c.getTime();
			dailyTime = avgTime + timeIncrement*(i+1) + (double)random(timeRange,0);
		}
		newActivity = new Activity(goalTime, activityName);
		eventList.add(new Event(newDate, newActivity));

		return eventList;
	}
}

package comp3350.dailysprint.objects;

public class Activity {

    private int time;
    private String activity;

    public Activity(int time, String activity) {
        this.time = time;
        this.activity = activity;
    }

    public String toString() {

        return "Time (min): " + time + "\nActivity: " + activity;
    }

    public boolean equals(Activity anActivity)
    {
        boolean result = false;

        if (this.activity == null && anActivity.getActivity() == null)
        {
            if (this.time == anActivity.getTime())
                result = true;
        }
        else if (this.activity != null && anActivity.getActivity() != null)
        {
            result = ( (this.time == anActivity.getTime()) && this.activity.equals(anActivity.getActivity()) );
        }
        return result;
    }

    public String getActivity() {
        return activity;
    }
    public int getTime() {return time;}
}

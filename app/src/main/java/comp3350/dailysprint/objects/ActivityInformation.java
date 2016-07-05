package comp3350.dailysprint.objects;

public class ActivityInformation {
    private double MET;
    private String activity;

    public ActivityInformation(String activity, double MET){
        this.MET = MET;
        this.activity = activity;
    }

    public boolean equals(Object object)
    {
        boolean result;
        ActivityInformation activityInformation;
        result = false;

        if (object instanceof ActivityInformation)
        {
            activityInformation = (ActivityInformation) object;
            if (activityInformation.getActivity().equals(activity))
            {
                result = true;
            }
        }
        return result;
    }
    public double getMET() {
        return MET;
    }

    public String getActivity() {
        return activity;
    }
}

package comp3350.dailysprint.objects;


import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class Event
{
    private Date date;
    private SimpleDateFormat sdf;
    private Activity activity;

    public Event(Date date, Activity activity) {
        this.activity = activity;
        sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
    public String dateToString(){return sdf.format(date);}
    public Activity getActivity() {
        return activity;
    }


    public boolean equals(Event event){
        return ( this.date.equals(event.getDate()) && this.activity.equals(event.getActivity()) );
    }

}
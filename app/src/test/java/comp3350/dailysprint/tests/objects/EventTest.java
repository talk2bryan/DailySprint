package comp3350.dailysprint.tests.objects;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import comp3350.dailysprint.objects.Activity;
import comp3350.dailysprint.objects.Event;

public class EventTest extends TestCase{
    public EventTest(String arg0) {super(arg0);}

    public void testEventComparisonCases() {
        Event event;
        Date date;
        Activity activity;


        date = new Date();
        activity = new Activity(30, "running");

        System.out.println("\nStarting testEventComparisonCases");

        event = new Event(date, activity);
        assertNotNull(event);
        assertEquals(date, event.getDate());
        assertEquals(event.getActivity(), activity);

        Event newEvent = new Event(date,activity);
        assertNotNull(newEvent);
        assertTrue(event.equals(newEvent));

        newEvent = new Event(date, new Activity(10, "running"));
        assertNotNull(newEvent);
        assertFalse(event.equals(newEvent));

        newEvent = new Event(date, new Activity(30, "walking"));
        assertNotNull(newEvent);
        assertFalse(event.equals(newEvent));

        assertEquals(newEvent.getDate(),event.getDate());

        System.out.println("Finished testEventComparisonCases");
    }
}

package comp3350.dailysprint.tests.objects;

import junit.framework.TestCase;
import comp3350.dailysprint.objects.ActivityInformation;

public class ActivityInformationTest extends TestCase {

    public ActivityInformationTest(String arg0) {super(arg0);}


    public void testActivityInformationObject(){
        ActivityInformation activityInfo;
        ActivityInformation newActivityInfo;

        System.out.println("\nStarting testActivityInformationObject");

        activityInfo = new ActivityInformation("An activity", 6.0);
        assertNotNull(activityInfo);

        assertEquals("An activity",activityInfo.getActivity());
        assertTrue("An activity".compareTo(activityInfo.getActivity()) == 0);

        assertEquals(6.0,activityInfo.getMET());
        assertFalse("An Activity".compareTo(activityInfo.getActivity()) == 0);
        assertTrue("An Activity".compareTo(activityInfo.getActivity()) < 0);

        newActivityInfo = new ActivityInformation("An activity",6.0);

        Object anObject = new Object();
        anObject = (Object)newActivityInfo;
        assertNotNull(anObject);

        assertTrue(anObject.equals(activityInfo));

        anObject = new Object();
        assertNotNull(anObject);
        assertFalse(anObject.equals(activityInfo));

        newActivityInfo = new ActivityInformation(null,0);
        assertNotNull(newActivityInfo);
        assertEquals(null,newActivityInfo.getActivity());


        System.out.println("Finished testActivityInformationObject");
    }




}

package comp3350.dailysprint.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.dailysprint.business.Calculate;
import comp3350.dailysprint.objects.Event;

public class CalculateTest extends TestCase{

    public CalculateTest(String arg0) {super(arg0);}

    public void testCalculations() {

        double MET = 3.14;
        int weight = 180;
        int time = 50;

        System.out.println("Start of testCalculations");
        int caloriesLoss = Calculate.calorieLoss(MET, weight, time);
        assertEquals((int)(MET * weight * time / 60), caloriesLoss);


        for(int i = 0; i < 1000; i++) {
            int val = Calculate.random(5, 0);
            assertTrue(val <= 5);
            assertTrue(val >= 0);
        }


        ArrayList<Event> eventList = Calculate.createSchedule("running", 7, 40, 45, 50);
        assertNotNull(eventList);

        System.out.println("End of testCalculations");
    }


}

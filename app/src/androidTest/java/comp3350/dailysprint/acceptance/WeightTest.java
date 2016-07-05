package comp3350.dailysprint.acceptance;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import comp3350.dailysprint.presentation.HomeScreen;


public class WeightTest extends ActivityInstrumentationTestCase2<HomeScreen>
{
    private Solo solo;

    public WeightTest()
    {
        super(HomeScreen.class);
    }

    public void setUp() throws Exception
    {
        // Disable this for full acceptance test
        //System.out.println("Injecting stub database.");
        //Services.createDataAccess(new DataAccessStub(Main.dbName));

        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

    public void testEditWeight()
    {
        solo.waitForActivity("HomeScreen");

        Assert.assertTrue(solo.searchText("Bryan"));
        solo.clickOnText("Bryan");
        solo.clickOnButton("Login");
        System.out.println("");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.clickOnButton("Settings");
        solo.assertCurrentActivity("Expected activity UserSettings", "UserSettings");

        //change weight from 170 to 150
        solo.clearEditText(0);
        solo.enterText(0, "150");
        solo.clickOnButton("Update");

        solo.goBack();

        solo.waitForActivity("UserOptions");
        solo.clickOnButton("Settings");
        solo.assertCurrentActivity("Expected activity UserSettings", "UserSettings");


        // clean up
        solo.clearEditText(0);
        solo.enterText(0, "170");
        solo.clickOnButton("Update");


        //try changing weight to invalid input (empty)
        solo.clearEditText(0);
        solo.enterText(0, "");
        solo.clickOnButton("Update");

        solo.goBack();

        solo.waitForActivity("UserOptions");
        solo.clickOnButton("Settings");
        solo.assertCurrentActivity("Expected activity UserSettings", "UserSettings");

        //view if change occurred
        solo.clickOnButton("Update");
    }
}

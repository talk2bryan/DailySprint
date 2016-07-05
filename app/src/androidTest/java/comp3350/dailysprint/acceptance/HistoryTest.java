package comp3350.dailysprint.acceptance;


import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import comp3350.dailysprint.presentation.HomeScreen;

public class HistoryTest extends ActivityInstrumentationTestCase2<HomeScreen>
{
    private Solo solo;

    public HistoryTest()
    {
        super(HomeScreen.class);
    }

    public void setUp() throws Exception
    {
        // Disable this for full acceptance test
        //System.out.println("Injecting stub database.");
        //Services.createDataAccess(new DataAccessStub(Main.dbName));

        solo = new Solo(getInstrumentation(), getActivity());

        //create a user to test Schedule with
        solo.waitForActivity("HomeScreen");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Expected activity CreateUserProfile", "CreateUserProfile");

        solo.scrollListToLine(1,30);
        solo.clickInList(1,0);
        Assert.assertTrue(solo.searchText("50"));
        solo.scrollListToLine(1,12);
        solo.clickInList(1,1);
        Assert.assertTrue(solo.searchText("22"));
        solo.scrollListToLine(2,100);
        solo.clickInList(1,2);
        Assert.assertTrue(solo.searchText("200"));
        solo.clickOnRadioButton(0);
        solo.clickOnButton("Continue");

        solo.assertCurrentActivity("Expected activity CreatePassword", "CreatePassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0, "Zack");
        solo.enterText(1, "password");
        solo.enterText(2, "password");
        solo.clickOnButton("Begin The Journey");

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
    }

    public void testInvalidEntry()
    {
        solo.waitForActivity("HomeScreen");

        Assert.assertTrue(solo.searchText("Zack"));
        solo.clickOnText("Zack");
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("History");

        solo.assertCurrentActivity("Expected activity ViewHistory", "ViewHistory");
        solo.clickOnButton("Log Activity");

        solo.assertCurrentActivity("Expected activity LogHistory", "LogHistory");
        solo.clickInList(1,0);
        solo.enterText(0,"12");
        solo.clickOnButton("Log Activity");

        //verify that it's been logged
        solo.assertCurrentActivity("Expected activity ViewHistory", "ViewHistory");
        Assert.assertTrue(solo.searchText("12"));
    }
    public void tearDown() throws Exception
    {
        //delete Zack
        solo.goBack();

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Settings");

        solo.assertCurrentActivity("Expected activity UserSettings", "UserSettings");
        solo.clickOnButton("Delete Profile");

        solo.finishOpenedActivities();
    }
}

package comp3350.dailysprint.acceptance;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import comp3350.dailysprint.presentation.HomeScreen;
import android.test.ActivityInstrumentationTestCase2;

public class CreateUserProfileTest extends ActivityInstrumentationTestCase2<HomeScreen>
{
    private Solo solo;

    public CreateUserProfileTest()
    {
        super(HomeScreen.class);
    }

    public void setUp() throws Exception
    {
        // Disable this for full acceptance test
        //System.out.println("Injecting stub database.");
        //Services.createDataAccess(new DataAccessStub(Main.dbName));

        solo = new Solo(getInstrumentation(), getActivity());

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
    }

    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

    public void testCreateUser()
    {
        solo.enterText(0, "Zack");
        solo.enterText(1, "password");
        solo.enterText(2, "password");
        solo.clickOnButton("Begin The Journey");

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        Assert.assertTrue(solo.searchText("Zack"));
        solo.clickInList(5);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Settings");

        solo.assertCurrentActivity("Expected activity UserSettings", "UserSettings");
        solo.clickOnButton("Delete Profile");

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        solo.sleep(1000);
        Assert.assertFalse(solo.searchText("Zack",true));
    }


    public void testInvalidUser()
    {

        solo.enterText(0, "Marc");
        solo.enterText(1, "password");
        solo.enterText(2, "password");
        solo.clickOnButton("Begin The Journey");

        solo.assertCurrentActivity("Expected activity CreatePassword", "CreatePassword");
    }

}

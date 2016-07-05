package comp3350.dailysprint.acceptance;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import comp3350.dailysprint.presentation.HomeScreen;
import android.test.ActivityInstrumentationTestCase2;

public class FriendTest extends ActivityInstrumentationTestCase2<HomeScreen>
{
    private Solo solo;

    public FriendTest()
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
        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        Assert.assertTrue(solo.searchText("Marc"));
        solo.clickInList(3);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Friends");

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Request Friend");

        solo.assertCurrentActivity("Expected activity FriendRequest", "FriendRequest");
        Assert.assertTrue(solo.searchText("Bryan"));
        Assert.assertTrue(solo.searchText("Jason"));
        Assert.assertTrue(solo.searchText("Reagan"));
        solo.clickInList(1);
        solo.clickOnButton("Request Friend");
        solo.clickOnActionBarHomeButton();

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        solo.sleep(1000);
        solo.clickInList(1);
        solo.sleep(1000);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Friends");

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Request Friend");

        solo.assertCurrentActivity("Expected activity FriendRequest", "FriendRequest");
        Assert.assertTrue(solo.searchText("Jason"));
        Assert.assertTrue(solo.searchText("Reagan"));
        solo.goBack();

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Messages");

        solo.assertCurrentActivity("Expected activity ViewMessages", "ViewMessages");
        Assert.assertTrue(solo.searchText("Marc"));
        Assert.assertTrue(solo.searchText("Requested to be your friend"));
        solo.clickInList(1,0);

        solo.assertCurrentActivity("Expected activity FriendResponse", "FriendResponse");
    }

    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

    public void testUserFriendsDecline()
    {

        solo.clickOnButton("Decline");

        solo.assertCurrentActivity("Expected activity ViewMessages", "ViewMessages");
        solo.goBack();

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        Assert.assertFalse(solo.searchText("Marc",true));
        solo.clickOnActionBarHomeButton();

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        Assert.assertTrue(solo.searchText("Marc"));
        solo.clickInList(3);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Friends");

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Messages");

        solo.assertCurrentActivity("Expected activity ViewMessages", "ViewMessages");
        Assert.assertTrue(solo.searchText("Bryan"));
        Assert.assertTrue(solo.searchText("Declined Your Friend Request"));
        solo.clickInList(1);
        solo.clickOnButton("Delete Message");
        Assert.assertFalse(solo.searchText("Bryan",true));
        Assert.assertFalse(solo.searchText("Declined Your Friend Request",true));
        solo.goBack();

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Request Friend");

        solo.assertCurrentActivity("Expected activity FriendRequest", "FriendRequest");
        Assert.assertTrue(solo.searchText("Bryan"));
        Assert.assertTrue(solo.searchText("Jason"));
        Assert.assertTrue(solo.searchText("Reagan"));
    }

    public void testUserFriendsAccept()
    {
        solo.clickOnButton("Accept");

        solo.assertCurrentActivity("Expected activity ViewMessages", "ViewMessages");
        solo.goBack();

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        Assert.assertTrue(solo.searchText("Marc"));
        solo.clickOnActionBarHomeButton();

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        Assert.assertTrue(solo.searchText("Marc"));
        solo.clickInList(3);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Friends");

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        Assert.assertTrue(solo.searchText("Bryan"));
        solo.clickOnButton("Messages");

        solo.assertCurrentActivity("Expected activity ViewMessages", "ViewMessages");
        Assert.assertTrue(solo.searchText("Bryan"));
        Assert.assertTrue(solo.searchText("Accepted Your Friend Request"));
        solo.clickInList(1);
        solo.clickOnButton("Delete Message");
        Assert.assertFalse(solo.searchText("Bryan",true));
        Assert.assertFalse(solo.searchText("Declined Your Friend Request",true));
        solo.goBack();

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Request Friend");

        solo.assertCurrentActivity("Expected activity FriendRequest", "FriendRequest");
        solo.sleep(1000);
        Assert.assertFalse(solo.searchText("Bryan", true));
        Assert.assertTrue(solo.searchText("Jason"));
        Assert.assertTrue(solo.searchText("Reagan"));
        solo.goBack();
        solo.sleep(1000);

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickInList(1);
        solo.clickOnButton("Delete Friend");
        solo.sleep(1000);
        Assert.assertFalse(solo.searchText("Bryan",true));
        solo.clickOnActionBarHomeButton();

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        solo.clickInList(1);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Friends");

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        Assert.assertFalse(solo.searchText("Marc", true));
        solo.clickOnButton("Request Friend");

        solo.assertCurrentActivity("Expected activity FriendRequest", "FriendRequest");
        Assert.assertTrue(solo.searchText("Marc"));
        Assert.assertTrue(solo.searchText("Jason"));
        Assert.assertTrue(solo.searchText("Reagan"));
        solo.clickOnActionBarHomeButton();

        solo.assertCurrentActivity("Expected activity HomeScreen", "HomeScreen");
        solo.sleep(1000);
        solo.clickInList(1);
        solo.sleep(1000);
        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Expected activity EnterPassword", "EnterPassword");
        Assert.assertTrue(solo.searchEditText(""));
        solo.enterText(0,"password");
        solo.clickOnButton("Enter");

        solo.assertCurrentActivity("Expected activity UserOptions", "UserOptions");
        solo.clickOnButton("Friends");

        solo.assertCurrentActivity("Expected activity ViewFriends", "ViewFriends");
        solo.clickOnButton("Messages");

        solo.assertCurrentActivity("Expected activity ViewMessages", "ViewMessages");
        solo.clickInList(1);
        solo.clickOnButton("Delete Message");
    }
}

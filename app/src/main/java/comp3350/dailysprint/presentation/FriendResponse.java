package comp3350.dailysprint.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.Message;
import comp3350.dailysprint.objects.UserProfile;

public class FriendResponse extends AppCompatActivity
{
    private AccessUsers accessUsers;
    private String username;
    private String friendname;
    private UserProfile user;
    private UserProfile friend;
    private Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_response);
        setResult(Activity.RESULT_CANCELED, resultIntent);
        accessUsers = new AccessUsers();
        username = getIntent().getExtras().getString("USERPROFILE");
        friendname = getIntent().getExtras().getString("FRIEND");
        user = accessUsers.searchName(username);
        friend = accessUsers.searchName(friendname);
    }

    public void buttonAcceptOnClick(View v)
    {
        //update requester's response list
        user.getRequestList().remove(friendname);
        user.deleteRequest(friendname);
        //update friends list
        user.addFriend(friendname);
        //update requester's response list
        friend.getRequestList().remove(username);
        friend.deleteRequest(username);
        //update friends list
        friend.addFriend(username);

        accessUsers.updateUser(user);
        accessUsers.updateUser(friend);
        //send message
        Message response = new Message(friendname,username
                ,"response","Accepted Your Friend Request");
        accessUsers.send(response);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void buttonDeclineOnClick(View v)
    {
        user.deleteRequest(friendname);
        accessUsers.updateUser(user);
        //update requester's response list
        friend.deleteRequest(username);
        accessUsers.updateUser(friend);
        //send message
        Message response = new Message(friendname,username
                ,"response","Declined Your Friend Request");
        accessUsers.send(response);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}

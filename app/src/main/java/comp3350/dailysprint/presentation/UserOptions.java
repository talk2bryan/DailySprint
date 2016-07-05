package comp3350.dailysprint.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;

public class UserOptions extends AppCompatActivity {

    public final static int REQUEST_CODE_REFRESH = 1;
    private AccessUsers accessUsers;
    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString(getString(R.string.user_profile));
        user = accessUsers.searchName(name);

        TextView text = (TextView)findViewById(R.id.textView2);
        if(user.getScheduleEventList().isEmpty())
        {
            if (text != null) {
                text.setText("Nothing");
            }
        }
        else
        {
            if (text != null)
            {

                text.setText(user.getScheduleEventList().get(0).getActivity().toString());
            }
        }

    }

    public void buttonScheduleOnClick(View v) {
        Intent userIntent = new Intent(this, ViewSchedule.class);
        userIntent.putExtra(getString(R.string.user_profile), user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }

    public void buttonHistoryOnClick(View v) {
        Intent userIntent = new Intent(this, ViewHistory.class);
        userIntent.putExtra(getString(R.string.user_profile), user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }

    public void buttonFriendsOnClick(View v){
        Intent userIntent = new Intent(this, ViewFriends.class);
        userIntent.putExtra(getString(R.string.user_profile), user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }

    public void buttonSettingsOnClick(View v){
        Intent userIntent = new Intent(this, UserSettings.class);
        userIntent.putExtra(getString(R.string.user_profile), user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Used to update list view after create user finishes
        switch(requestCode) {
            case REQUEST_CODE_REFRESH:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                accessUsers = new AccessUsers();
                user = accessUsers.searchName(user.getUsername());
                if (user != null)
                {
                    TextView text = (TextView)findViewById(R.id.textView2);
                    if(user.getScheduleEventList().isEmpty())
                    {
                        if (text != null)
                        {
                            text.setText("Nothing");
                        }
                    }
                    else
                    {
                        if (text != null)
                        {
                            text.setText(user.getScheduleEventList().get(0).getActivity().toString());
                        }
                    }
                }


                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }
}

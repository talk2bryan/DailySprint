package comp3350.dailysprint.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.Event;
import comp3350.dailysprint.objects.UserProfile;

public class ViewSchedule extends AppCompatActivity
{

    public final static int REQUEST_CODE_REFRESH = 1;
    private AccessUsers accessUsers;
    private ArrayList<Event> scheduleList;
    private ArrayAdapter<Event> scheduleArrayAdapter;
    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString("USERPROFILE");
        user = accessUsers.searchName(name);

        scheduleList = user.getScheduleEventList();
        scheduleArrayAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, scheduleList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                Event event = scheduleList.get(position);
                text1.setText(event.dateToString());
                text2.setText(event.getActivity().toString());

                return view;
            }
        };


        final ListView listView = (ListView) findViewById(R.id.listSchedule);
        if (listView != null)
        {
            listView.setAdapter(scheduleArrayAdapter);
        }
    }


    public void buttonGenerateScheduleOnClick(View v)
    {
        Intent userIntent = new Intent(this, GenerateSchedule.class);
        userIntent.putExtra("USERPROFILE", user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Used to update list view after create user finishes
        switch(requestCode)
        {
            case REQUEST_CODE_REFRESH:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                accessUsers.refreshUsers();
                user = accessUsers.searchName(user.getUsername());
                scheduleList = user.getScheduleEventList();
                scheduleArrayAdapter.clear();
                scheduleArrayAdapter.addAll(scheduleList);
                scheduleArrayAdapter.notifyDataSetChanged();
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }
}





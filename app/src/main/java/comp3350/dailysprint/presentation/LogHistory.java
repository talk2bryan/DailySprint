package comp3350.dailysprint.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessActivities;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.objects.Event;

public class LogHistory extends AppCompatActivity {

    private ListView activities;
    private ArrayAdapter<String> activityArrayAdapter;
    private UserProfile user;
    private int activityPosition = -1;
    private ArrayList<String> activityList;
    private Date date;
    private String activity;
    private int time;
    private boolean checkActivity, checkTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_history);

        AccessUsers accessUsers = new AccessUsers();
        AccessActivities accessActivities = new AccessActivities();
        String name = getIntent().getExtras().getString("USERPROFILE");
        user = accessUsers.searchName(name);
        activityList = accessActivities.activitiesToString();

        date = new Date();


        // DataBind ListView with items from ArrayAdapter
        activities = (ListView) findViewById(R.id.activityView);
        activityArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, activityList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);

                text1.setText(activityList.get(position));

                return view;
            }
        };

        activities.setAdapter(activityArrayAdapter);
        activities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == activityPosition) {
                    activities.setItemChecked(position, false);
                    activityPosition = -1;
                    checkActivity = false;
                } else {
                    activities.setItemChecked(position, true);
                    activityPosition = position;
                    activity = activityList.get(position);
                    checkActivity = true;
                }
                activityArrayAdapter.notifyDataSetChanged();
                checkEnable();
            }
        });

        final EditText editTime = (EditText)findViewById(R.id.editTime);
        if (editTime != null) {
            editTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        time = Integer.parseInt(editTime.getText().toString());
                        checkTime = time > 0 && time < 100;
                    } catch(NumberFormatException nfe) {
                        checkTime = false;
                        time = -1;
                    }
                    checkEnable();
                }
            });
        }

    }

    public void checkEnable()
    {
        Button done = (Button) findViewById(R.id.logHistory);
        if(done != null) {
            if (checkActivity && checkTime) {
                done.setEnabled(true);
            } else {
                done.setEnabled(false);
            }
        }
    }

    public void buttonLogActivityOnClick(View v) {
        Event event = new Event(date, new comp3350.dailysprint.objects.Activity(time, activity));
        user.addHistoryEvent(date, activity, time);
        AccessUsers accessUsers = new AccessUsers();
        String result = accessUsers.updateUser(user);
        if (result != null) {
            Messages.warning(this, result);
        }
        accessUsers.sendToFriends(user, event.getActivity().toString());
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}

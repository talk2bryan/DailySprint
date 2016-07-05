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

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessActivities;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;
import comp3350.dailysprint.objects.Event;
import comp3350.dailysprint.business.Calculate;

public class GenerateSchedule extends AppCompatActivity
{
    private AccessUsers accessUsers;
    private ListView activities;
    private UserProfile user;
    private int activityPosition = -1;
    private ArrayList<String> activityList;
    private String activity;
    private int days, avgTime, bestTime, goalTime;
    private boolean checkActivity, checkDays, checkAvgTime, checkBestTime, checkGoalTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_schedule);

        accessUsers = new AccessUsers();
        AccessActivities accessActivities = new AccessActivities();
        String name = getIntent().getExtras().getString("USERPROFILE");
        user = accessUsers.searchName(name);
        activityList = accessActivities.activitiesToString();

        checkEnable();
        // DataBind ListView with items from ArrayAdapter
        activities = (ListView) findViewById(R.id.activityView);
        ArrayAdapter<String> activityArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, activityList) {
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
                checkEnable();
            }
        });

        final EditText editDays = (EditText)findViewById(R.id.editDays);
        if (editDays != null) {
            editDays.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        days = Integer.parseInt(editDays.getText().toString());
                        checkDays = days > 0 && days < 366;
                    } catch(NumberFormatException nfe) {
                        checkDays = false;
                        days = -1;
                    }
                    checkEnable();
                }
            });
        }

        final EditText editAvgTime = (EditText)findViewById(R.id.editAvgTime);
        if (editAvgTime != null) {
            editAvgTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        avgTime = Integer.parseInt(editAvgTime.getText().toString());
                        checkAvgTime = avgTime > 0 && avgTime < 1000;
                    } catch(NumberFormatException nfe) {
                        checkAvgTime = false;
                        avgTime = -1;
                    }
                    checkEnable();
                }
            });
        }

        final EditText editBestTime = (EditText)findViewById(R.id.editBestTime);
        if (editBestTime != null) {
            editBestTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        bestTime = Integer.parseInt(editBestTime.getText().toString());
                        checkBestTime = bestTime > 0 && bestTime < 1000;
                    } catch(NumberFormatException nfe) {
                        checkBestTime = false;
                        bestTime = -1;
                    }
                    checkEnable();
                }
            });
        }

        final EditText editGoalTime = (EditText)findViewById(R.id.editGoalTime);
        if (editGoalTime != null) {
            editGoalTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        goalTime = Integer.parseInt(editGoalTime.getText().toString());
                        checkGoalTime = goalTime > 0 && goalTime < 1000;
                    } catch(NumberFormatException nfe) {
                        checkGoalTime = false;
                        goalTime = -1;
                    }
                    checkEnable();
                }
            });
        }

    }



    public void checkEnable()
    {
        Button done = (Button) findViewById(R.id.buttonGenerateSchedule);
        if(done != null) {
            if (checkActivity && checkDays && checkAvgTime && checkBestTime && checkGoalTime) {
                done.setEnabled(true);
            } else {
                done.setEnabled(false);
            }
        }
    }

    public void buttonGenerateScheduleOnClick(View v)
    {
        ArrayList<Event> schedule = Calculate.createSchedule(activity, days, avgTime, bestTime, goalTime);
        user.replaceSchedule(schedule);
        String result = accessUsers.updateUser(user);
        if (result != null)
        {
            Messages.warning(this, result);
        }
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}


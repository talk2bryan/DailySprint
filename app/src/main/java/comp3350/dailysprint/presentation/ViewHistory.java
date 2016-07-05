package comp3350.dailysprint.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessActivities;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.business.Calculate;
import comp3350.dailysprint.objects.Event;
import comp3350.dailysprint.objects.UserProfile;

public class ViewHistory extends AppCompatActivity {
    public final static int REQUEST_CODE_REFRESH = 1;
    private int selectedSchedulePosition = -1;
    private UserProfile user;
    private AccessUsers accessUsers;
    private ArrayList<Event> historyList;
    private ArrayAdapter<Event> historyArrayAdapter;
    private AccessActivities accessActivities = new AccessActivities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString(getString(R.string.user_profile));
        user = accessUsers.searchName(name);

        historyList = user.getHistoryEventList();
        historyArrayAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, historyList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                Event event = historyList.get(position);
                text1.setText(event.dateToString());
                text2.setText(event.getActivity().toString() + "\nCalories:\t" +
                        Calculate.calorieLoss(accessActivities.getMET(event.getActivity().getActivity())
                                ,user.getUserWeight(),event.getActivity().getTime()));
                return view;
            }
        };
        final ListView listView = (ListView) findViewById(R.id.listHistory);
        if (listView != null) {
            listView.setAdapter(historyArrayAdapter);
        }
        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == selectedSchedulePosition) {
                        listView.setItemChecked(position, false);
                        selectedSchedulePosition = -1;
                    } else {
                        listView.setItemChecked(position, true);
                        selectedSchedulePosition = position;
                    }
                }
            });
        }
    }

    public void buttonLogActivityOnClick(View v) {
        Intent userIntent = new Intent(this, LogHistory.class);
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
                historyList = user.getHistoryEventList();
                historyArrayAdapter.clear();
                historyArrayAdapter.addAll(historyList);
                historyArrayAdapter.notifyDataSetChanged();
                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }
}
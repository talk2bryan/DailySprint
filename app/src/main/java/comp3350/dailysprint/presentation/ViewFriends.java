package comp3350.dailysprint.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.Message;
import comp3350.dailysprint.objects.UserProfile;

public class ViewFriends extends AppCompatActivity {
    public final static int REQUEST_CODE_REFRESH = 1;
    private int selectedFriendPosition = -1;
    String selected = null;
    private UserProfile user;
    private AccessUsers accessUsers;
    private ArrayList<String> friendList;
    private ArrayAdapter<String> friendArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends);

        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString("USERPROFILE");
        user = accessUsers.searchName(name);

        friendList = user.getFriendsList();
        friendArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, friendList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(friendList.get(position));
                return view;
            }
        };
        listView = (ListView) findViewById(R.id.listFriends);
        if (listView != null) {
            listView.setAdapter(friendArrayAdapter);
        }
        Button deleteButton = (Button) findViewById(R.id.buttonDeleteFriend);
        if (deleteButton != null) {
            deleteButton.setEnabled(false);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button deleteButton = (Button) findViewById(R.id.buttonDeleteFriend);
                if (position == selectedFriendPosition) {
                    listView.setItemChecked(position, false);
                    if (deleteButton != null) {
                        deleteButton.setEnabled(false);
                    }
                    selectedFriendPosition = -1;
                } else {
                    listView.setItemChecked(position, true);
                    if (deleteButton != null) {
                        deleteButton.setEnabled(true);
                    }
                    selectedFriendPosition = position;
                    selectFriendAtPosition(position);
                }
            }
        });
    }

    public void selectFriendAtPosition(int position) {
        selected = friendArrayAdapter.getItem(position);
    }

    public void buttonRequestFriendOnClick(View v) {
        Intent userIntent = new Intent(this, FriendRequest.class);
        userIntent.putExtra("USERPROFILE", user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }

    public void buttonMessagesOnClick(View v) {
        Intent userIntent = new Intent(this, ViewMessages.class);
        userIntent.putExtra("USERPROFILE", user.getUsername());
        startActivityForResult(userIntent, REQUEST_CODE_REFRESH);
    }

    public void buttonDeleteFriendOnClick(View v) {
        String friendname = selected;
        listView.setItemChecked(selectedFriendPosition, false);
        selected = null;
        selectedFriendPosition = -1;

        UserProfile friend = accessUsers.searchName(friendname);
        user.deleteFriend(friendname);
        friend.deleteFriend(user.getUsername());
        friendList = user.getFriendsList();
        accessUsers.updateUser(user);
        accessUsers.updateUser(friend);

        Message response = new Message(friendname,user.getUsername()
                ,"removal","Removed From Friends List");
        accessUsers.send(response);

        Button deleteButton = (Button) findViewById(R.id.buttonDeleteFriend);
        if (deleteButton != null) {
            deleteButton.setEnabled(false);
        }
        friendArrayAdapter.clear();
        friendArrayAdapter.addAll(friendList);
        friendArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Used to update list view after create user finishes
        switch(requestCode) {
            case REQUEST_CODE_REFRESH:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                accessUsers.refreshUsers();
                user = accessUsers.searchName(user.getUsername());
                friendList = user.getFriendsList();
                friendArrayAdapter.clear();
                friendArrayAdapter.addAll(friendList);
                friendArrayAdapter.notifyDataSetChanged();
                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }

}
package comp3350.dailysprint.presentation;

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

public class FriendRequest extends AppCompatActivity {

    private AccessUsers accessUsers;
    private ArrayList<UserProfile> userList;
    private ArrayAdapter<UserProfile> userArrayAdapter;
    private UserProfile user;
    private int selectedUserPosition = -1;
    private boolean checkUser = false;
    private UserProfile selected = null;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_friend);
        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString(getString(R.string.user_profile));
        user = accessUsers.searchName(name);

        userList = accessUsers.getRequestFriends(user);

        Button request = (Button) findViewById(R.id.buttonRequestFriend);
        if (request != null) {
            request.setEnabled(false);
        }
        userArrayAdapter = new ArrayAdapter<UserProfile>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, userList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);

                text1.setText(userList.get(position).getUsername());

                return view;
            }
        };


        listView = (ListView) findViewById(R.id.listUsers);
        if (listView != null) {
            listView.setAdapter(userArrayAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedUserPosition) {
                    listView.setItemChecked(position, false);
                    selectedUserPosition = -1;
                    checkUser = false;
                } else {
                    listView.setItemChecked(position, true);
                    selectedUserPosition = position;
                    selectUserAtPosition(position);
                    checkUser = true;
                }
                checkEnable();
            }
        });

    }
    public void selectUserAtPosition(int position) {
        selected = userArrayAdapter.getItem(position);
    }

    public void checkEnable()
    {
        Button request = (Button) findViewById(R.id.buttonRequestFriend);
        if(request != null) {
            if (checkUser) {
                request.setEnabled(true);
            } else {
                request.setEnabled(false);
            }
        }
    }

    public void buttonRequestFriendOnClick(View v){

        listView.setItemChecked(selectedUserPosition, false);
        selectedUserPosition = -1;
        checkUser = false;
        checkEnable();

        user = accessUsers.searchName(user.getUsername());
        UserProfile friend = accessUsers.searchName(selected.getUsername());

        user.addRequest(friend.getUsername());
        friend.addRequest(user.getUsername());
        accessUsers.updateUser(user);
        accessUsers.updateUser(friend);
        Message request = new Message(friend.getUsername(),user.getUsername()
                ,"request","Requested to be your friend");
        accessUsers.send(request);

        userArrayAdapter.clear();
        userList = accessUsers.getRequestFriends(user);
        userArrayAdapter.addAll(userList);
        userArrayAdapter.notifyDataSetChanged();
    }
}



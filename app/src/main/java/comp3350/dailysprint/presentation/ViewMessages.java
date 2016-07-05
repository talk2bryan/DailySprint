package comp3350.dailysprint.presentation;

import android.app.Activity;
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

public class ViewMessages extends AppCompatActivity {
    public final static int FRIEND_RESPONSE = 1;
    private int selectedMessagePosition = -1;
    Message selected = null;
    private UserProfile user;
    private AccessUsers accessUsers;
    private ArrayList<Message> messageList;
    private ArrayAdapter<Message> messageArrayAdapter;
    private ListView listView;
    private Intent userIntent;
    private int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);
        userIntent = new Intent(this, FriendResponse.class);
        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString(getString(R.string.user_profile));
        user = accessUsers.searchName(name);

        messageList = user.getMessageList();
        messageArrayAdapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, messageList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(messageList.get(position).getFrom());
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text2.setText(messageList.get(position).getMessage());
                return view;
            }
        };
        listView = (ListView) findViewById(R.id.listMessages);
        if (listView != null) {
            listView.setAdapter(messageArrayAdapter);
        }
        Button deleteButton = (Button) findViewById(R.id.buttonDeleteMessage);
        if (deleteButton != null) {
            deleteButton.setEnabled(false);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMessageAtPosition(position);
                Button deleteButton = (Button) findViewById(R.id.buttonDeleteMessage);
                pos = position;
                if(selected.getType().equals("request"))
                {
                    listView.setItemChecked(pos, false);
                    if (deleteButton != null) {
                        deleteButton.setEnabled(false);
                    }
                    selectedMessagePosition = -1;
                    userIntent.putExtra(getString(R.string.user_profile), user.getUsername());
                    userIntent.putExtra("FRIEND", selected.getFrom());
                    startActivityForResult(userIntent, FRIEND_RESPONSE);
                }
                else {
                    if (position == selectedMessagePosition)
                    {
                        listView.setItemChecked(position, false);
                        if (deleteButton != null) {
                            deleteButton.setEnabled(false);
                        }
                        selectedMessagePosition = -1;
                    } else {
                        listView.setItemChecked(position, true);
                        if (deleteButton != null) {
                            deleteButton.setEnabled(true);
                        }
                        selectedMessagePosition = position;
                    }
                }
            }
        });
    }

    public void selectMessageAtPosition(int position)
    {
        selected = messageArrayAdapter.getItem(position);
    }

    public void buttonDeleteMessageOnClick(View v)
    {
        Message message = selected;
        messageList.remove(message);
        user.deleteMessage(message);
        listView.setItemChecked(selectedMessagePosition, false);
        selected = null;
        selectedMessagePosition = -1;
        accessUsers.updateUser(user);
        Button deleteButton = (Button) findViewById(R.id.buttonDeleteMessage);
        if (deleteButton != null) {
            deleteButton.setEnabled(false);
        }
        messageArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Used to update list view after create user finishes
        switch(requestCode) {
            case FRIEND_RESPONSE:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data

                accessUsers = new AccessUsers();
                user = accessUsers.searchName(user.getUsername());
                if(resultCode == Activity.RESULT_OK)
                {
                    user.deleteMessage(selected);
                    accessUsers.updateUser(user);
                    messageList = user.getMessageList();
                    messageArrayAdapter.clear();
                    messageArrayAdapter.addAll(messageList);
                    messageArrayAdapter.notifyDataSetChanged();
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }



}
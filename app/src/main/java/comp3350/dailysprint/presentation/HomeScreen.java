package comp3350.dailysprint.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import comp3350.dailysprint.R;
import comp3350.dailysprint.application.Main;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;

public class HomeScreen extends AppCompatActivity {

    private final static int REQUEST_CODE_CREATE = 1;
    private AccessUsers accessUsers;
    private ArrayList<UserProfile> userList;
    private ArrayAdapter<UserProfile> userArrayAdapter;
    private int selectedUserPosition = -1;
    UserProfile selected = null;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDatabaseToDevice();
        Main.startUp();
        setContentView(R.layout.activity_homescreen);

        accessUsers = new AccessUsers();
        userList = accessUsers.getUsers();

        String result = accessUsers.refreshUsers();
        if (result != null) {
            Messages.fatalError(this, result);
        } else {
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
            Button loginButton = (Button) findViewById(R.id.buttonUserLogin);
            if (loginButton != null) {
                loginButton.setEnabled(false);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Button loginButton = (Button) findViewById(R.id.buttonUserLogin);
                    if (position == selectedUserPosition) {
                        listView.setItemChecked(position, false);
                        if (loginButton != null) {
                            loginButton.setEnabled(false);
                        }
                        selectedUserPosition = -1;
                    } else {
                        listView.setItemChecked(position, true);
                        if (loginButton != null) {
                            loginButton.setEnabled(true);
                        }
                        selectedUserPosition = position;
                        selectUserAtPosition(position);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Main.shutDown();
    }

    public void selectUserAtPosition(int position) {
        selected = userArrayAdapter.getItem(position);
    }

    public void buttonUserCreateOnClick(View v) {
        Intent usersIntent = new Intent(HomeScreen.this, CreateUserProfile.class);
        startActivityForResult(usersIntent, REQUEST_CODE_CREATE);
    }

    public void buttonUserLoginOnClick(View v) {
        UserProfile user = selected;
        Intent userIntent = new Intent(this, EnterPassword.class);
        userIntent.putExtra(getString(R.string.user_profile), user.getUsername());
        HomeScreen.this.startActivity(userIntent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Used to update list view after create user finishes
        switch (requestCode) {
            case REQUEST_CODE_CREATE:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                accessUsers.refreshUsers();
                userArrayAdapter.notifyDataSetChanged();
                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }


    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        } catch (IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}

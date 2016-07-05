package comp3350.dailysprint.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;

public class UserSettings extends AppCompatActivity {

    private AccessUsers accessUsers;
    UserProfile user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        accessUsers = new AccessUsers();
        String name = getIntent().getExtras().getString(getString(R.string.user_profile));

        user = accessUsers.searchName(name);

        EditText editWeightValue = (EditText)findViewById(R.id.updateWeightValue);
        if (editWeightValue != null) {
            editWeightValue.setText(""+user.getUserWeight());
        }
    }
    public void buttonDeleteUserOnClick(View v){
        String result;
        result = accessUsers.deleteUser(user);


        if (result == null) {
            Intent userIntent = new Intent(this, HomeScreen.class);
            UserSettings.this.startActivity(userIntent);
        } else {
            Messages.warning(this, result);
        }
    }

    public void buttonUpdateWeightOnClick(View v){

        EditText editWeightValue = (EditText)findViewById(R.id.updateWeightValue);
        if (editWeightValue != null && editWeightValue.getText().toString().length() > 0 && editWeightValue.getText().toString().length() < 4) {
            int weight = Integer.parseInt(editWeightValue.getText().toString());
            user.setUserWeight(weight);
        }
        accessUsers.updateUser(user);
    }
}

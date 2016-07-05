package comp3350.dailysprint.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;

public class EnterPassword extends AppCompatActivity {
    private String password = "";
    private String guess = "";
    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        AccessUsers accessUsers = new AccessUsers();
        String username = getIntent().getExtras().getString(getString(R.string.user_profile));
        user = accessUsers.searchName(username);
        password = user.getPassword();

        final EditText textPassword = (EditText)findViewById(R.id.textPassword);
        if (textPassword != null) {
            textPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    guess = textPassword.getText().toString();
                }
            });
        }

    }

    public void buttonPasswordOnClick(View v) {
        if(password.equals(guess))
        {
            Intent login = new Intent(this, UserOptions.class);
            login.putExtra(getString(R.string.user_profile), user.getUsername());
            EnterPassword.this.startActivity(login);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Used to update list view after create user finishes
        final int REQUEST_CODE_REFRESH = 1;
        switch(requestCode) {
            case REQUEST_CODE_REFRESH:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
            default:
                //you just got back from activity C - deal with resultCode
                finish();
                break;
        }
    }
}

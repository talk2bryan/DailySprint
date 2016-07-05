package comp3350.dailysprint.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comp3350.dailysprint.R;
import comp3350.dailysprint.business.AccessUsers;
import comp3350.dailysprint.objects.UserProfile;

public class CreatePassword extends AppCompatActivity {

    private int weight;
    private int age;
    private int height;
    private boolean gender;
    private String username = "";
    private String password = "";
    private String passwordConfirmation = "";
    private AccessUsers accessUsers;

    private boolean checkUsername, checkExistence, checkPassword = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        Intent intent = getIntent();
        weight = intent.getIntExtra("WEIGHT",0);
        height = intent.getIntExtra("HEIGHT",0);
        age = intent.getIntExtra("AGE",0);
        gender = intent.getBooleanExtra("GENDER",false);
        accessUsers = new AccessUsers();
        checkEnable();
        final EditText textUsername = (EditText)findViewById(R.id.textUsername);
        if (textUsername != null) {
            textUsername.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    username = textUsername.getText().toString();
                    if(checkUsername = (username.length() > 0)) {
                        UserProfile user = accessUsers.searchName(username);
                        checkExistence = user == null;
                    }
                    checkEnable();
                }
            });
        }

        final EditText textPassword = (EditText)findViewById(R.id.textPassword);
        if (textPassword != null) {
            textPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    password = textPassword.getText().toString();
                    if(password.length() >= 6) {
                        checkPassword = password.equals(passwordConfirmation);
                    }
                    checkEnable();
                }
            });
        }

        final EditText textPasswordConfirmation = (EditText)findViewById(R.id.textPasswordConfirmation);
        if (textPasswordConfirmation != null) {
            textPasswordConfirmation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    passwordConfirmation = textPasswordConfirmation.getText().toString();
                    if(passwordConfirmation.length() >= 6) {
                        checkPassword = passwordConfirmation.equals(password);
                    }
                    checkEnable();
                }
            });
        }

    }

    public void checkEnable()
    {
        Button complete = (Button) findViewById(R.id.buttonComplete);
        if(complete != null) {
            if (checkUsername && checkExistence && checkPassword) {
                complete.setEnabled(true);
            } else {
                complete.setEnabled(false);
            }
        }
    }

    public void buttonCompleteOnClick(View v) {
        UserProfile user = new UserProfile(username, password, age, height, weight, gender);
        String result = accessUsers.insertUser(user);
        if (result != null) {
            Messages.warning(this, result);
        }
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }


}

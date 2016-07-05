package comp3350.dailysprint.presentation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.dailysprint.R;


public class CreateUserProfile extends AppCompatActivity {
    public final static int REQUEST_CODE_CREATE = 1;
    private int weightPosition = -1;
    private int agePosition = -1;
    private int heightPosition = -1;

    private int weight = -1;
    private int age = -1;
    private int height = -1;
    private boolean gender = false;

    private boolean checkWeight,checkAge, checkHeight,checkGender = false;

    private String[] weightList = createStringList(81, 20);
    private String[] ageList = createStringList(101, 10);
    private String[] heightList = createStringList(301, 100);

    // Get reference of widgets from XML layout
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;

    private ArrayAdapter<String> weightAdapter;
    private ArrayAdapter<String> ageAdapter;
    private ArrayAdapter<String> heightAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_profile);

        // DataBind ListView with items from ArrayAdapter
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        weightAdapter = createWeight(weightList);
        ageAdapter = createAge(ageList);
        heightAdapter = createHeight(heightList);

        lv1.setAdapter(weightAdapter);
        lv2.setAdapter(ageAdapter);
        lv3.setAdapter(heightAdapter);

        checkEnable();

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == weightPosition) {
                        lv1.setItemChecked(position, false);
                        weightPosition = -1;
                        checkWeight = false;
                        view.setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        lv1.setItemChecked(position, true);
                        weightPosition = position;
                        weight = Integer.parseInt(weightList[position]);
                        view.setBackgroundColor(Color.GREEN);
                        checkWeight = true;
                    }
                checkEnable();
                weightAdapter.notifyDataSetChanged();
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == agePosition) {
                    lv2.setItemChecked(position, false);
                    agePosition = -1;
                    checkAge = false;
                } else {
                    lv2.setItemChecked(position, true);
                    agePosition = position;
                    age = Integer.parseInt(ageList[position]);
                    checkAge = true;
                }
                checkEnable();
                ageAdapter.notifyDataSetChanged();
            }
        });
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == heightPosition) {
                    lv3.setItemChecked(position, false);
                    heightPosition = -1;
                    checkHeight = false;
                } else {
                    lv3.setItemChecked(position, true);
                    heightPosition = position;
                    height = Integer.parseInt(heightList[position]);
                    checkHeight = true;
                }
                checkEnable();
                heightAdapter.notifyDataSetChanged();

            }
        });
    }

    private String[] createStringList(int range, int start)
    {
        String[] list = new String[range];
        for(int i = 0; i < range; i++)
        {
            list[i] = i+start+"";
        }
        return list;
    }

    private ArrayAdapter<String> createWeight(String[] list)
    {
        // Create a List from String Array elements
        List<String> arrayList = new ArrayList<>(Arrays.asList(list));

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arrayList){
            //@Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                // Get the Layout Parameters for ListView Current Item View
                LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 50;
                view.setLayoutParams(params);
                if(weightPosition == position) {
                    view.setBackgroundColor(Color.GREEN);
                }
                else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                return view;
            }
        };
        checkEnable();
        return arrayAdapter;
    }
    private ArrayAdapter<String> createAge(String[] list)
    {
        // Create a List from String Array elements
        List<String> arrayList = new ArrayList<>(Arrays.asList(list));

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                // Get the Layout Parameters for ListView Current Item View
                LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 50;
                view.setLayoutParams(params);
                if(agePosition == position) {
                    view.setBackgroundColor(Color.GREEN);
                }
                else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                return view;
            }
        };
        checkEnable();
        return arrayAdapter;
    }
    private ArrayAdapter<String> createHeight(String[] list)
    {
        // Create a List from String Array elements
        //List<String> arrayList = new ArrayList<>(Arrays.asList(list));

        // Create an ArrayAdapter from List
        return new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(list))){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                // Get the Layout Parameters for ListView Current Item View
                LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 50;
                view.setLayoutParams(params);
                if(heightPosition == position) {
                    view.setBackgroundColor(Color.GREEN);
                }
                else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                checkEnable();
                return view;
            }
        };
    }

    public void onRadioButtonClicked(View view) {
        checkGender = true;
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = false;
                    break;
            case R.id.radio_female:
                if (checked)
                    gender = true;
                    break;
        }
        checkEnable();
    }

    public void checkEnable()
    {
        Button next = (Button) findViewById(R.id.buttonContinue);
        if(next != null) {
            if (checkWeight && checkAge && checkHeight && checkGender) {
                next.setEnabled(true);
            } else {
                next.setEnabled(false);
            }
        }
    }

    public void buttonUserContinueOnClick(View v) {
        Intent intent = new Intent(this, CreatePassword.class);
        intent.putExtra("AGE", age);
        intent.putExtra("HEIGHT", height);
        intent.putExtra("WEIGHT", weight);
        intent.putExtra("GENDER", gender);
        startActivityForResult(intent, REQUEST_CODE_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Used to update list view after create user finishes
        switch(requestCode) {
            case REQUEST_CODE_CREATE:
                //you just got back from activity B - deal with resultCode
                //use data.getExtra(...) to retrieve the returned data
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
            default:
                //you just got back from activity C - deal with resultCode
                break;
        }
    }
}

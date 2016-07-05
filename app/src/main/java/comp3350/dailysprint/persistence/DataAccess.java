package comp3350.dailysprint.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.dailysprint.objects.ActivityInformation;
import comp3350.dailysprint.objects.UserProfile;

public interface DataAccess
{

    void open(String string);

    void close();

    String getUserSequential(List<UserProfile> userResult);

    String getUserName(UserProfile currentUser);

    String insertUser(UserProfile currentUser);

    String updateUser(UserProfile currentUser);

    String deleteUser(UserProfile currentUser);

    String getActivitySequential(ArrayList<ActivityInformation> activitiesResult);

}

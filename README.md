# DailySprint
This started as a group project for a software engineering class. Well, I am branching out. The DailySprint application is developed for athletes who intend to challenge themselves to meet personally specified goals in a range of Physical activities.
It does so by generating a schedule for the user based on the activity they desire and the goal they aspire to meet. By means of a query, users provide information on the physical activity they intend a schedule for, and a timeframe they intend to accomplish this goal (Goal Length), their current average running time per this distance (Average Time), their best time score (Best Time) and lastly, the target running time they intend to meet by the given deadline (Goal Time). With this information, the application generates a schedule that guarantees they accomplish the said goal in that timeframe.

RoadMap:
The homepage contains user profiles to log in to and a create button for a new user. The default password for each user displayed is “password”.
On successful log in, user is presented with their to-do activity of the day (“Nothing” initially until a schedule is generated) and other options to choose from depending on what it is users intend to do.
Schedule displays the user’s activity schedule for a period of time. They can modify this by “Generat(e)”-ing a new schedule based on what their new goal is.
History, contains completed activities. 
Friends - the option of adding and removing friends.
Settings - the user has the option of deleting their profile which logs them out automatically. Or update their weight; the weight displayed on that page is their current weight.

Packages:
Objects package: contains all major objects used by Daily Sprint. They include UserProfile, Event, Activity, ActivityInformation

Persistence Package: includes the DataAccess and the DataAccessObject.

Presentation Package:  Includes all the logic for the GUI’s throughout the project.

Business Package: contains the AccessUsers, AccessActivities and Calculate, which serve as the database communication interface.

Res/Layout: Contains the XML files for the GUI’s. 

Test Suites are available for these packages, as well as an acceptance test suite contained in the androidTest package

Copyright - All materials before this commit all co-owned by the members of my said group (Zhipeng, Reagan, and Marc). From this commit forward, belong to me (Bryan W.)


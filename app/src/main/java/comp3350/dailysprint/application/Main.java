package comp3350.dailysprint.application;

public class Main {
    public static final String dbName = "UP";
    private static String dbPathName = "database/UP";

    public static void main(String[] args)
    {
        startUp();


        shutDown();
        System.out.println("All done");
    }

    public static void startUp()
    {
        System.out.println("start");
        Services.createDataAccess(dbName);
        //Services.createDataAccessObject(dbName);
    }

    public static void shutDown()
    {
        System.out.println("close");
        Services.closeDataAccess();
    }

    public static String getDBPathName() {
        if (dbPathName == null)
            return dbName;
        else
            return dbPathName;
    }

    public static void setDBPathName(String pathName) {
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }
}

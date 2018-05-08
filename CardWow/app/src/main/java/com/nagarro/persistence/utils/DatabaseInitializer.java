package com.nagarro.persistence.utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nagarro.persistence.database.AppDatabase;
import com.nagarro.persistence.entity.User;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    public static double getLongitude(AppDatabase db, User test_user){
        double longitude=1.0;
        longitude=test_user.getLon();
        return longitude;
    }
    public static double getLatitude(AppDatabase db, User test_user){
        double latitude=1.0;
        latitude=test_user.getLat();
        return latitude;
    }
    public static List<User> getUserList(AppDatabase db){
        List<User> userList = db.userDao().getAll();
        return userList;
    }

    public static void updateChange(AppDatabase db, User user){
        db.userDao().update(user);
    }


    private static void populateWithTestData(AppDatabase db) {
        //Create Three new Users here for testing purposes
        User user1 = new User();
        user1.setFirstName("Humberto");
        user1.setLastName("Perez");
        user1.setLat(-24.2048);
        user1.setLon(150);
        user1.setLink("https://www.linkedin.com/in/humberto-perez-5244a750/");
        addUser(db, user1);

        User user2 = new User();
        user2.setFirstName("Dustin");
        user2.setLastName("Groce");
        user2.setLat(51.5074);
        user2.setLon(0.1278);
        user2.setLink("https://www.linkedin.com/in/dustin-groce-04a72769/");
        addUser(db, user2);

        User user3 = new User();
        user3.setFirstName("Gady");
        user3.setLastName("Pitaru");
        user3.setLat(33.8958);
        user3.setLon(-118.2201);
        user3.setLink("https://www.linkedin.com/in/gadypitaru/");
        addUser(db, user3);

        User user4 = new User();
        user4.setFirstName("Kasey");
        user4.setLastName("Jones");
        user4.setLat(45.5231);
        user4.setLon(-122.6765);
        user4.setLink("https://www.linkedin.com/in/abetterjones/");
        addUser(db, user4);

        User user5 = new User();
        user5.setFirstName("Tyler");
        user5.setLastName("Mize");
        user5.setLat(37.7749);
        user5.setLon(-122.4194);
        user5.setLink("https://www.linkedin.com/in/tyler-mize-704974b3/");
        addUser(db, user5);

        User user6 = new User();
        user6.setFirstName("Scott");
        user6.setLastName("Golden");
        user6.setLat(45.5231);
        user6.setLon(-122.6765);
        user6.setLink("https://www.linkedin.com/in/goldenopportunities/");
        addUser(db, user6);

        User user7 = new User();
        user7.setFirstName("Heather");
        user7.setLastName("Harrison");
        user7.setLat(41.8781);
        user7.setLon(87.6298);
        user7.setLink("https://www.linkedin.com/in/harrison-heather-12346799/");
        addUser(db, user7);

        User user8 = new User();
        user8.setFirstName("Dieter");
        user8.setLastName("Zetsche");
        user8.setLat(51.1657);
        user8.setLon(-10.4515);
        user8.setLink("https://www.linkedin.com/in/dieterzetsche/");
        addUser(db, user8);

        User phone_user = new User();
        phone_user.setFirstName("Zachary");
        phone_user.setLastName("Stovall");
        phone_user.setLat(36.9685);
        phone_user.setLon(-86.4808);
        phone_user.setLink("https://www.linkedin.com/in/zachary-stovall-b9a53ba8/");
        addUser(db, phone_user);



        List<User> userList = db.userDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);

            return null;
        }

    }
}

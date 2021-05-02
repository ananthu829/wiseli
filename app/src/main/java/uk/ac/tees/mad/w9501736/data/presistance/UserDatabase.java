package uk.ac.tees.mad.w9501736.data.presistance;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;


public abstract class UserDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "user_db";

    private static UserDatabase instance;

    public static UserDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    UserDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract UserDao getRecipeDao();

}







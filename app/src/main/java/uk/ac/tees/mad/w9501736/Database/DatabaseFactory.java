package uz.nuper.driver.database;

import androidx.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import uz.nuper.driver.R;
import uz.nuper.driver.model.LocationData;

public class DatabaseFactory {
    private static DatabaseFactory object;
    private AppDatabase appDatabase;

    private DatabaseFactory(Context context){
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,context.getString(R.string.app_name))
                .build();
    }
    static public DatabaseFactory setupObject(Context context){
        if(object == null){
            object = new DatabaseFactory(context);
        }
        return object;
    }
    static public DatabaseFactory getInstance(){
        if(object == null){
            throw new IllegalStateException("Object is not setup");
        }
        return object;
    }

    public void insertLocationDataIntoDatabase(LocationData locationData){
        AsyncTask.execute(() -> {
            appDatabase.getLocationDao().insert(locationData);
        });
    }

    public void getLocationDataFromDatabase(DatabaseCallBack<List<LocationData>> listDatabaseCallBack){
        AsyncTask.execute(() -> {
            List<LocationData> result = appDatabase.getLocationDao().getLocationData();
            listDatabaseCallBack.onSuccess(result);
        });
    }

    public void clearRecords(){
        AsyncTask.execute(() -> {
            appDatabase.getLocationDao().delete();
        });
    }

}

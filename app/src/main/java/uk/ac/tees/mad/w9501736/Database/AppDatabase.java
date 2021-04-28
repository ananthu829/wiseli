package uz.nuper.driver.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import uz.nuper.driver.model.LocationData;

@Database(entities = {LocationData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

 public abstract LocationDataDAO getLocationDao();
}

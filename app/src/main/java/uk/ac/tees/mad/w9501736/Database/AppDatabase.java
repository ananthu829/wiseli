package uk.ac.tees.mad.w9501736.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import uk.ac.tees.mad.w9501736.data.presistance.Converters;
import uk.ac.tees.mad.w9501736.models.CircleData;
import uk.ac.tees.mad.w9501736.models.LoginModel;


@Database(entities = {CircleData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

 public abstract LoginDataDAO getLocationDao();
}

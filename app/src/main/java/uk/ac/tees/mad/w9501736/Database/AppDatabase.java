package uk.ac.tees.mad.w9501736.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import uk.ac.tees.mad.w9501736.data.presistance.Converters;
import uk.ac.tees.mad.w9501736.models.CircleData;
import uk.ac.tees.mad.w9501736.models.LoginModel;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;


@Database(entities = {CircleData.class, UserFriendsList.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

 public abstract LoginDataDAO getLocationDao();
 public  abstract FriendsDataDao getFriendsData();
}

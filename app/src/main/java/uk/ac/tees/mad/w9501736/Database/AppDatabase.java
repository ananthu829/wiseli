package uk.ac.tees.mad.w9501736.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import uk.ac.tees.mad.w9501736.data.presistance.Converters;
import uk.ac.tees.mad.w9501736.models.ActiveInActiveBody;
import uk.ac.tees.mad.w9501736.models.CircleData;
import uk.ac.tees.mad.w9501736.models.FriendsList;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;


@Database(entities = {CircleData.class, UserFriendsList.class, ActiveInActiveBody.class, FriendsList.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract LoginDataDAO getLocationDao();

    public abstract FriendsDataDao getFriendsData();

    public abstract CircleFriendListDataDao getCircleFriendData();

    public abstract CircleActiveInactiveListDataDao getCircleActiveInactiveData();
}

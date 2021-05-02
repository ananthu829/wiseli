package uk.ac.tees.mad.w9501736.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;

@Dao
public interface FriendsDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(UserFriendsList data);

    @Query("SELECT * FROM userFriendList")
    public LiveData<List<UserFriendsList>> getFriendsData();
}

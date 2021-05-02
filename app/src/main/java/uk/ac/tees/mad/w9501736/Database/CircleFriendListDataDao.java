package uk.ac.tees.mad.w9501736.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uk.ac.tees.mad.w9501736.models.FriendsList;

@Dao
public interface CircleFriendListDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<FriendsList> data);

    @Query("SELECT * FROM FriendsList WHERE circleID = :circleID")
    LiveData<FriendsList> getCircleDetail(Integer circleID);
}

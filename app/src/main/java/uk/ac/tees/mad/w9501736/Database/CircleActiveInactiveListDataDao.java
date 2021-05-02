package uk.ac.tees.mad.w9501736.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uk.ac.tees.mad.w9501736.models.ActiveInActiveBody;

@Dao
public interface CircleActiveInactiveListDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<ActiveInActiveBody> data);

    @Query("SELECT * FROM ActiveInActiveBody WHERE circleID = :circleID AND isActive=:iActive")
    List<ActiveInActiveBody> getCircleDetail(Integer circleID,Boolean iActive);
}

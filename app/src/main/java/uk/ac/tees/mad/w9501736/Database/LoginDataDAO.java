package uk.ac.tees.mad.w9501736.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uk.ac.tees.mad.w9501736.models.LoginModel;

@Dao
public interface LoginDataDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(LoginModel.LoginModelDB locationData);

//    @Query("SELECT * FROM LoginModel.LoginModelDB")
//    public List<LoginModel.LoginModelDB> getLocationData();
//
//    @Query("DELETE FROM LoginModel.LoginModelDB")
//    public void delete();
}

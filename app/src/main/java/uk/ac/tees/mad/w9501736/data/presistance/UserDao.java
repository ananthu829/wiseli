package uk.ac.tees.mad.w9501736.data.presistance;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface UserDao {

    @Insert(onConflict = IGNORE)
    long[] insertUsers(WiseLiUser... users);

    @Insert(onConflict = REPLACE)
    void insertUser(WiseLiUser users);


    @Query("SELECT * FROM user WHERE userId = :recipe_id")
    LiveData<WiseLiUser> getRecipe(String recipe_id);

}










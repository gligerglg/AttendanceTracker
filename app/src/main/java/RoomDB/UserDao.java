package RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface UserDao {
    @Insert
    void registerNewUser(User user);
    @Update
    void updateCurrentUser(User user);
    @Query("SELECT * FROM user WHERE username=:username LIMIT 1")
    User getCurrentUser(String username);
}

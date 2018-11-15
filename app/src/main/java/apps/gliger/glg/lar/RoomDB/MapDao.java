package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MapDao {
   @Insert
    void addNewDayMap(DayMap dayMap);
   @Update
    void updateDayMap(DayMap dayMap);
   @Delete
    void deleteDayMap(DayMap dayMap);
   @Query("SELECT * FROM DayMap")
    List<DayMap> getAllDayMaps();
   @Query("SELECT * FROM DayMap WHERE day=:day")
    DayMap getTodayDayMap(String day);
    @Query("DELETE FROM DayMap")
    void deleteAllRecords();
}

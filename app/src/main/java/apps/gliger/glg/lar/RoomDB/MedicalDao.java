package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedicalDao {
    @Insert
    void addMedical(Medical medical);
    @Delete
    void deleteMedical(Medical medical);
    @Query("SELECT * FROM Medical")
    List<Medical> getAllMedicals();
    @Update
    void updateMedical(Medical medical);
    @Query("DELETE FROM Medical")
    void deleteAllRecords();
}

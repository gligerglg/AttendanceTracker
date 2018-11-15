package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insertNewSubject(Subject subject);
    @Query("SELECT * FROM Subject")
    List<Subject> getAllSubjects();
    @Update
    void updateSubject(Subject subject);
    @Query("DELETE FROM subject WHERE id=:id")
    void deleteSubject(int id);
    @Query("SELECT * FROM Subject WHERE subjectName=:subjectName")
    Subject getSubject(String subjectName);
    @Query("DELETE FROM Subject")
    void deleteAllRecords();
}

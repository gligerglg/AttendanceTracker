package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {User.class,Subject.class, DayMap.class, Medical.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract UserDao dataRef();
    public abstract SubjectDao subjectRef();
    public abstract MapDao mapDayRef();
    public abstract MedicalDao medicalRef();
}

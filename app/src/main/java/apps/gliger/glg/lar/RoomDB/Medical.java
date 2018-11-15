package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import apps.gliger.glg.lar.TypeConverters.SubjectDayConverter;

@Entity
public class Medical {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
    private int id;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "affedtedSub")
    @TypeConverters(SubjectDayConverter.class)
    private List<String> affectedSubjects;

    public Medical(String date, List<String> affectedSubjects) {
        this.date = date;
        this.affectedSubjects = affectedSubjects;
    }

    @Ignore
    public Medical() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getAffectedSubjects() {
        return affectedSubjects;
    }

    public void setAffectedSubjects(List<String> affectedSubjects) {
        this.affectedSubjects = affectedSubjects;
    }
}

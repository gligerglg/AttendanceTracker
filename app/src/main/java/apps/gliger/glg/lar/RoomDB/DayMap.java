package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import apps.gliger.glg.lar.TypeConverters.DayMapConverter;

@Entity
public class DayMap {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "day")
    private String day;
    @ColumnInfo(name = "subjectList")
    @TypeConverters(DayMapConverter.class)
    private List<String> subjectList;

    public DayMap(String day, List<String> subjectList) {
        this.day = day;
        this.subjectList = subjectList;
    }

    @Ignore
    public DayMap(int id, String day, List<String> subjectList) {
        this.id = id;
        this.day = day;
        this.subjectList = subjectList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<String> subjectList) {
        this.subjectList = subjectList;
    }
}

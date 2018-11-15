package apps.gliger.glg.lar.RoomDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import apps.gliger.glg.lar.TypeConverters.SubjectDayConverter;

@Entity
public class Subject {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "subjectName")
    private String subjectName;
    @ColumnInfo(name = "courceCode")
    private String courceCode;
    @ColumnInfo(name = "mapDayList")
    @TypeConverters(SubjectDayConverter.class)
    private List<String> mapDayList;
    @ColumnInfo(name = "present")
    private int presentDays =  0;
    @ColumnInfo(name = "absent")
    private int absentDays = 0;
    @ColumnInfo(name = "medical")
    private int medical = 0;

    public Subject(String subjectName, String courceCode) {
        this.subjectName = subjectName;
        this.courceCode = courceCode;
    }

    @Ignore
    public Subject() {
    }

    @Ignore
    public Subject(String subjectName, String courceCode, Double latitude, Double longitude) {
        this.subjectName = subjectName;
        this.courceCode = courceCode;
    }

    @Ignore
    public Subject(String subjectName, String courceCode, Double latitude, Double longitude, List<String> mapDayList) {
        this.subjectName = subjectName;
        this.courceCode = courceCode;
        this.mapDayList = mapDayList;
    }

    @Ignore
    public Subject(String subjectName, String courceCode, Double latitude, Double longitude, List<String> mapDayList, int presentDays, int absentDays, int medicals) {
        this.subjectName = subjectName;
        this.courceCode = courceCode;
        this.mapDayList = mapDayList;
        this.presentDays = presentDays;
        this.absentDays = absentDays;
        this.medical = medicals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCourceCode() {
        return courceCode;
    }

    public void setCourceCode(String courceCode) {
        this.courceCode = courceCode;
    }

    public List<String> getMapDayList() {
        return mapDayList;
    }

    public void setMapDayList(List<String> mapDayList) {
        this.mapDayList = mapDayList;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(int absentDays) {
        this.absentDays = absentDays;
    }

    public int getMedical() {
        return medical;
    }

    public void setMedical(int medical) {
        this.medical = medical;
    }
}

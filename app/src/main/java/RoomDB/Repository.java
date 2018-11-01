package RoomDB;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {
    private String db_name = "LARDB";
    private Database database;
    private Context context;

    public Repository(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context,Database.class,db_name).allowMainThreadQueries().build();
        initDayMaps();
    }

    /**User Repository Methods*/
    public void registerNewUser(final User user){
        database.dataRef().registerNewUser(user);
    }

    public User getCurrentUser(String username){
        return database.dataRef().getCurrentUser(username);
    }

    public void updateUser(User user){
        database.dataRef().updateCurrentUser(user);
    }

    public boolean checkCredentials(User user){
        User currentUser = getCurrentUser(user.getUsername());
        if(TextUtils.equals(currentUser.getUsername(),user.getUsername()) && TextUtils.equals(currentUser.getPassword(),user.getPassword()))
            return true;
        else
            return false;
    }

    public boolean isUserExists(String username){
        User user = getCurrentUser(username);
        if(user==null)
            return false;
        else
            return true;
    }


    /**Subject Repository Methods*/
    public void addNewSubject(Subject subject){
        database.subjectRef().insertNewSubject(subject);
    }

    public void deleteSubject(int id){
        database.subjectRef().deleteSubject(id);
    }

    public void updateSubject(Subject subject){
        database.subjectRef().updateSubject(subject);
    }

    public List<Subject> getAllSubject(){
        return database.subjectRef().getAllSubjects();
    }

    public boolean isSubjectExists(Subject subject){
        for(Subject subject_item : getAllSubject()){
            if(TextUtils.equals(subject.getSubjectName(),subject_item.getSubjectName()) || TextUtils.equals(subject.getCourceCode(),subject_item.getCourceCode()))
                return true;
        }

        return false;
    }


    /**DayMap Repository Methods*/
    public List<DayMap> getAllDayMaps(){
        return database.mapDayRef().getAllDayMaps();
    }

    public DayMap getTodayDayMap(String day){
        return database.mapDayRef().getTodayDayMap(day);
    }

    public void addNewDayMap(DayMap dayMap){
        if(!isDayMapExists(dayMap))
            database.mapDayRef().addNewDayMap(dayMap);
    }

    private boolean isDayMapExists(DayMap dayMap){
        for(DayMap day : getAllDayMaps()){
            if(day.getDay().equals(dayMap.getDay()))
                return true;
        }

        return false;
    }

    public void updateDayMap(DayMap dayMap){
        database.mapDayRef().updateDayMap(dayMap);
        Log.i("DayMap","Updated");
    }

    private void initDayMaps(){
        List<String> subjects = new ArrayList<>();
        addNewDayMap(new DayMap("Monday",subjects));
        addNewDayMap(new DayMap("Tuesday",subjects));
        addNewDayMap(new DayMap("Wednesday",subjects));
        addNewDayMap(new DayMap("Thursday",subjects));
        addNewDayMap(new DayMap("Friday",subjects));
        addNewDayMap(new DayMap("Saturday",subjects));
        addNewDayMap(new DayMap("Sunday",subjects));
    }
}

package apps.gliger.glg.lar.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


public class SubjectDayConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToDayList(String dayList){
        if(dayList==null)
            return Collections.emptyList();

        Type listType = new TypeToken<List<String>>() {}.getType();
        return  gson.fromJson(dayList, listType);
    }

    @TypeConverter
    public static String subjectObjectsToString(List<String> days){
        return gson.toJson(days);
    }
}

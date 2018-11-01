package apps.gliger.glg.lar.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DayMapConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToStringList(String subject){
        if(subject==null)
            return Collections.emptyList();

        Type listType = new TypeToken<List<String>>() {}.getType();
        return  gson.fromJson(subject, listType);
    }

    @TypeConverter
    public static String subjectOgjectsToString(List<String> subjects){
        return gson.toJson(subjects);
    }
}

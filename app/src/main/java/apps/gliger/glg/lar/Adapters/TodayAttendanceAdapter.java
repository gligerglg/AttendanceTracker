package apps.gliger.glg.lar.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import apps.gliger.glg.lar.RoomDB.Repository;
import apps.gliger.glg.lar.RoomDB.Subject;
import apps.gliger.glg.lar.R;

public class TodayAttendanceAdapter extends RecyclerView.Adapter<TodayAttendanceAdapter.TodayHolder> {

    List<Subject> subjectList;
    Repository repository;
    HashMap<Subject, Integer> selectedSubjectMap;
    Context context;

    public TodayAttendanceAdapter(List<Subject> subjectList, Repository repository, Context context) {
        this.subjectList = subjectList;
        this.repository = repository;
        this.context =context;
        initMap();
    }

    @NonNull
    @Override
    public TodayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.today_attendance_ui, viewGroup, false);
        TodayAttendanceAdapter.TodayHolder holder = new TodayAttendanceAdapter.TodayHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TodayHolder todayHolder, final int i) {
        todayHolder.txt_subject.setText(subjectList.get(i).getSubjectName());
        todayHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (todayHolder.tap_status){
                    case 0: setMedicalOff(todayHolder,subjectList.get(i));
                            setAttendOn(todayHolder,subjectList.get(i));
                            todayHolder.tap_status = 1;
                            break;
                    case 1: setAttendOff(todayHolder,subjectList.get(i));
                            setMedicalOn(todayHolder,subjectList.get(i));
                            todayHolder.tap_status = 2;
                            break;
                    case 2:
                            setAttendOff(todayHolder,subjectList.get(i));
                            setMedicalOff(todayHolder,subjectList.get(i));
                            todayHolder.tap_status = 0;
                }
            }
        });



    }

    private void setAttendOn(TodayHolder todayHolder, Subject subject){
        todayHolder.isAttend = true;

        todayHolder.img_selected.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_check_circle));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            todayHolder.img_selected.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle));
        }

        selectedSubjectMap.put(subject, 1);
    }

    private void setAttendOff(TodayHolder todayHolder, Subject subject){
        todayHolder.isAttend = false;

        todayHolder.img_selected.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_unmarked));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            todayHolder.img_selected.setImageDrawable(context.getDrawable(R.drawable.ic_unmarked));
        }

        selectedSubjectMap.put(subject, 0);
    }

    private void setMedicalOn(TodayHolder todayHolder, Subject subject){
        todayHolder.isMedical = true;

        todayHolder.img_medical.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_medical_on));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            todayHolder.img_medical.setImageDrawable(context.getDrawable(R.drawable.ic_medical_on));
        }

        selectedSubjectMap.put(subject, 2);
    }

    private void setMedicalOff(TodayHolder todayHolder, Subject subject){
        todayHolder.isMedical = false;

        todayHolder.img_medical.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_medical_off));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            todayHolder.img_medical.setImageDrawable(context.getDrawable(R.drawable.ic_medical_off));
        }

        selectedSubjectMap.put(subject, 0);
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class TodayHolder extends RecyclerView.ViewHolder {

        boolean isAttend = false;
        boolean isMedical = false;
        int tap_status = 0;
        CardView cardView;
        TextView txt_subject;
        ImageView img_selected, img_medical;

        public TodayHolder(@NonNull View itemView) {
            super(itemView);
            txt_subject = itemView.findViewById(R.id.txt_todayatt_title_subject);
            img_selected = itemView.findViewById(R.id.img_todayatt_img_check);
            img_medical = itemView.findViewById(R.id.img_todayatt_img_medi);
            cardView = itemView.findViewById(R.id.crd_mapDates);
        }
    }

    public void initMap() {
        selectedSubjectMap = new HashMap<>();
        for (Subject subject : subjectList)
            selectedSubjectMap.put(subject, 0);
    }

    public HashMap<Subject, Integer> getAttendanceMap() {
        return selectedSubjectMap;
    }
}

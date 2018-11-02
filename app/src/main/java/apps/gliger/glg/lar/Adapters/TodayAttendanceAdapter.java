package apps.gliger.glg.lar.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import RoomDB.Repository;
import RoomDB.Subject;
import apps.gliger.glg.lar.R;

public class TodayAttendanceAdapter extends RecyclerView.Adapter<TodayAttendanceAdapter.TodayHolder> {

    List<Subject> subjectList;
    Repository repository;
    HashMap<Subject, Boolean> selectedSubjectMap;

    public TodayAttendanceAdapter(List<Subject> subjectList, Repository repository) {
        this.subjectList = subjectList;
        this.repository = repository;
        initMap();
    }

    @NonNull
    @Override
    public TodayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_multiselect_view, viewGroup, false);
        TodayAttendanceAdapter.TodayHolder holder = new TodayAttendanceAdapter.TodayHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TodayHolder todayHolder, final int i) {
        todayHolder.txt_subject.setText(subjectList.get(i).getSubjectName());
        todayHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todayHolder.selected) {
                    todayHolder.selected = false;
                    todayHolder.img_selected.setVisibility(View.GONE);
                    selectedSubjectMap.put(subjectList.get(i), false);

                } else {
                    todayHolder.selected = true;
                    todayHolder.img_selected.setVisibility(View.VISIBLE);
                    selectedSubjectMap.put(subjectList.get(i), true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class TodayHolder extends RecyclerView.ViewHolder {

        boolean selected = false;
        CardView cardView;
        TextView txt_subject;
        ImageView img_selected;

        public TodayHolder(@NonNull View itemView) {
            super(itemView);
            txt_subject = itemView.findViewById(R.id.txt_mapView_title_subject);
            img_selected = itemView.findViewById(R.id.img_mapview_img_check);
            cardView = itemView.findViewById(R.id.crd_mapDates);
        }
    }

    public void initMap() {
        selectedSubjectMap = new HashMap<>();
        for (Subject subject : subjectList)
            selectedSubjectMap.put(subject, false);
    }

    public HashMap<Subject, Boolean> getAttendanceMap() {
        return selectedSubjectMap;
    }
}

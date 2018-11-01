package apps.gliger.glg.lar.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import RoomDB.DayMap;
import RoomDB.Repository;
import RoomDB.Subject;
import apps.gliger.glg.lar.R;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    List<Subject> subjectList;
    DayMap dayMap;
    Repository repository;
    List<String> daylist = new ArrayList<>();

    public DayAdapter(List<Subject> subjectList, DayMap dayMap, Repository repository) {
        this.subjectList = new ArrayList<>(subjectList);
        this.dayMap = dayMap;
        this.repository = repository;
    }

    @NonNull
    @Override
    public DayAdapter.DayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_multiselect_view,viewGroup,false);
        DayAdapter.DayHolder holder = new DayAdapter.DayHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DayAdapter.DayHolder dayHolder, final int i) {
        dayHolder.txt_subject.setText(subjectList.get(i).getSubjectName());

        for(String subject : dayMap.getSubjectList()){
            if(subjectList.get(i).getSubjectName().equals(subject)) {
                dayHolder.img_selected.setVisibility(View.VISIBLE);
                dayHolder.selected = true;
            }
        }

        dayHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayHolder.selected){
                    dayHolder.selected = false;
                    dayHolder.img_selected.setVisibility(View.GONE);

                    //Update DB
                    dayMap.getSubjectList().remove(subjectList.get(i).getSubjectName());
                    subjectList.get(i).getMapDayList().remove(dayMap.getDay());

                }else {
                    dayHolder.selected = true;
                    dayHolder.img_selected.setVisibility(View.VISIBLE);

                    //Update DB
                    if(!dayMap.getSubjectList().contains(subjectList.get(i).getSubjectName())){
                        dayMap.getSubjectList().add(subjectList.get(i).getSubjectName());
                    }

                    if(subjectList.get(i).getMapDayList()==null){
                        daylist.clear();
                        daylist.add(dayMap.getDay());
                        subjectList.get(i).setMapDayList(daylist);
                    }else {
                        if(!subjectList.get(i).getMapDayList().contains(dayMap.getDay())) {
                            subjectList.get(i).getMapDayList().add(dayMap.getDay());
                        }
                    }
                }

                repository.updateDayMap(dayMap);
                repository.updateSubject(subjectList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class DayHolder extends RecyclerView.ViewHolder {
        boolean selected = false;
        CardView cardView;
        TextView txt_subject;
        ImageView img_selected;

        public DayHolder(@NonNull View itemView) {
            super(itemView);
            txt_subject = itemView.findViewById(R.id.txt_mapView_title_subject);
            img_selected = itemView.findViewById(R.id.img_mapview_img_check);
            cardView = itemView.findViewById(R.id.crd_mapDates);
        }
    }
}

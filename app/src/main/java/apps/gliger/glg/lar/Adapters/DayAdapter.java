package apps.gliger.glg.lar.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.gliger.glg.lar.Constant.MedicalDiffCallback;
import apps.gliger.glg.lar.RoomDB.DayMap;
import apps.gliger.glg.lar.RoomDB.Medical;
import apps.gliger.glg.lar.RoomDB.Repository;
import apps.gliger.glg.lar.RoomDB.Subject;
import apps.gliger.glg.lar.R;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    List<Subject> subjectList;
    List<String> selectedList;
    DayMap dayMap;
    Repository repository;
    List<String> daylist = new ArrayList<>();
    boolean isMapping = false;

    public DayAdapter(List<Subject> subjectList, DayMap dayMap, Repository repository) {
        this.subjectList = new ArrayList<>(subjectList);
        this.dayMap = dayMap;
        this.repository = repository;
        isMapping = true;
    }

    public DayAdapter(List<String> selectedList, List<Subject> subjectList, Repository repository){
        this.selectedList = selectedList;
        this.subjectList = subjectList;
        this.repository = repository;
        isMapping = false;
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

        if(isMapping){
            for(String subject : dayMap.getSubjectList()){
                if(subjectList.get(i).getSubjectName().equals(subject)) {
                    dayHolder.img_selected.setVisibility(View.VISIBLE);
                    dayHolder.selected = true;
                }
            }
        }else {
            for(String subject : selectedList){
                if(TextUtils.equals(subjectList.get(i).getSubjectName(),subject)){
                    dayHolder.img_selected.setVisibility(View.VISIBLE);
                    dayHolder.selected = true;
                }
            }
        }


        dayHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isMapping)
                   mappingFunctions(dayHolder,i);
               else
                   medicalFunctions(dayHolder,i);
            }
        });
    }

    public List<String> getSelectedSubjectList(){
        return selectedList;
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

    private void mappingFunctions(DayHolder dayHolder, int i){
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

    private void medicalFunctions(DayHolder dayHolder, int i){
        if(dayHolder.selected){
            dayHolder.selected = false;
            dayHolder.img_selected.setVisibility(View.GONE);

            //Update DB
            selectedList.remove(subjectList.get(i).getSubjectName());
            if(subjectList.get(i).getMedical()!=0)
                subjectList.get(i).setMedical(subjectList.get(i).getMedical() - 1);

        }else {
            dayHolder.selected = true;
            dayHolder.img_selected.setVisibility(View.VISIBLE);

            //Update DB
            selectedList.add(subjectList.get(i).getSubjectName());
            subjectList.get(i).setMedical(subjectList.get(i).getMedical() + 1);
        }

        repository.updateSubject(subjectList.get(i));
    }

    public void updateMedicalItems(List<Medical> medicals){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MedicalDiffCallback(medicals,repository.getAllMedicals()));
        /*medicalList.clear();
        medicalList.addAll(medicals);*/
        diffResult.dispatchUpdatesTo(this);
    }
}

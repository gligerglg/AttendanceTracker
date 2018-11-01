package apps.gliger.glg.lar.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import RoomDB.Repository;
import RoomDB.Subject;
import apps.gliger.glg.lar.R;
import apps.gliger.glg.lar.Constant.SubjectDiffCallback;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {

    List<Subject> subjectList;
    String dayString = "";
    Context context;
    Repository repository;

    public SubjectAdapter(List<Subject> subjectList, Context context, Repository repository) {
        this.subjectList = subjectList;
        this.context = context;
        this.repository = repository;
    }

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_subject_view,viewGroup,false);
        SubjectHolder holder = new SubjectHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectHolder subjectHolder, final int i) {
        subjectHolder.txt_title.setText(subjectList.get(i).getSubjectName());

        subjectHolder.txt_subject.setText(subjectList.get(i).getSubjectName());
        subjectHolder.txt_cc.setText(subjectList.get(i).getCourceCode());

        if(subjectList.get(i).getMapDayList()==null || subjectList.get(i).getMapDayList().size()==0) {
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                subjectHolder.txt_mapping.setTextColor(ContextCompat.getColor(context,R.color.colorError));
            else
                subjectHolder.txt_mapping.setTextColor(context.getResources().getColor(R.color.colorError));
            subjectHolder.txt_mapping.setText("Mapping Days List Empty");

        }
        else {
            dayString="";
            for(String day : subjectList.get(i).getMapDayList())
                dayString += day + "\n";
            subjectHolder.txt_mapping.setText(dayString);
        }

        subjectHolder.txt_attendance.setText("Total Attendance : " + subjectList.get(i).getPresentDays() + " Days\n"
        + "Total Absent : " + subjectList.get(i).getAbsentDays() + " Days");

        subjectHolder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectHolder.foldingCell.toggle(false);
            }
        });

        subjectHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    repository.deleteSubject(subjectList.get(i).getId());
                }catch (Exception r){}

                updateSubjectItems(repository.getAllSubject());
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class SubjectHolder extends RecyclerView.ViewHolder {

        TextView txt_title, txt_subject, txt_cc, txt_mapping, txt_attendance, btn_remove;
        ImageView img_location;
        FoldingCell foldingCell;

        public SubjectHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_subview_title_subject);
            txt_subject = itemView.findViewById(R.id.txt_subview_con_subject);
            txt_cc = itemView.findViewById(R.id.txt_subview_con_cc);
            txt_mapping = itemView.findViewById(R.id.txt_subview_con_mapping);
            txt_attendance = itemView.findViewById(R.id.txt_subview_con_attendance);
            img_location = itemView.findViewById(R.id.img_subview_img_loc);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            foldingCell = itemView.findViewById(R.id.folding_cell);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateSubjectItems(List<Subject> subjects){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SubjectDiffCallback(subjectList,subjects));
        subjectList.clear();
        subjectList.addAll(subjects);
        diffResult.dispatchUpdatesTo(this);
    }
}

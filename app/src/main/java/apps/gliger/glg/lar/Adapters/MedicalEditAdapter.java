package apps.gliger.glg.lar.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import apps.gliger.glg.lar.Constant.MedicalDiffCallback;
import apps.gliger.glg.lar.Constant.SubjectDiffCallback;
import apps.gliger.glg.lar.R;
import apps.gliger.glg.lar.RoomDB.Medical;
import apps.gliger.glg.lar.RoomDB.Repository;
import apps.gliger.glg.lar.RoomDB.Subject;

public class MedicalEditAdapter extends RecyclerView.Adapter<MedicalEditAdapter.MEdicalHolder> {

    List<Medical> medicalList;
    Context context;
    Repository repository;

    public MedicalEditAdapter(List<Medical> medicalList, Context context) {
        this.medicalList = medicalList;
        this.context =context;
        repository = new Repository(context);

    }

    @NonNull
    @Override
    public MEdicalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medical_report_edit_card,viewGroup,false);
        MedicalEditAdapter.MEdicalHolder holder = new MedicalEditAdapter.MEdicalHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MEdicalHolder mEdicalHolder, final int i) {
        String subList = "";
        mEdicalHolder.txt_date.setText(medicalList.get(i).getDate());
        for(String subject : medicalList.get(i).getAffectedSubjects())
            subList += subject + "\n";
        mEdicalHolder.txt_subList.setText(subList);

        mEdicalHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.medical_edit_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view);
                builder.setCancelable(false);
                final Dialog dialog = builder.create();
                dialog.show();

                final EditText txt_year = view.findViewById(R.id.txt_medical_edit_year);
                final EditText txt_month = view.findViewById(R.id.txt_medical_edit_month);
                final EditText txt_day = view.findViewById(R.id.txt_medical_edit_day);
                RecyclerView recyclerView = view.findViewById(R.id.recycle_medical_edit);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);
                Button btn_submit = view.findViewById(R.id.btn_medical_submit);

                final DayAdapter adapter = new DayAdapter(medicalList.get(i).getAffectedSubjects(),repository.getAllSubject(),repository);
                recyclerView.setAdapter(adapter);

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValidateSuccessfullDate(txt_year,txt_month,txt_day))
                        {
                            medicalList.get(i).setDate(generateDate(txt_year,txt_month,txt_day));
                            medicalList.get(i).setAffectedSubjects(adapter.getSelectedSubjectList());
                            repository.updateMedical(medicalList.get(i));
                            mEdicalHolder.imgStatus.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_edit_icon));
                            dialog.dismiss();
                        }
                    }
                });

                txt_year.setText(getYear(medicalList.get(i).getDate()));
                txt_month.setText(getMonth(medicalList.get(i).getDate()));
                txt_day.setText(getDay(medicalList.get(i).getDate()));

            }
        });

        mEdicalHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    removeMedicalData(medicalList.get(i));
                }catch (Exception e){}
                updateMedicalItems(medicalList);
            }
        });
    }

    private List<Subject> getSelectedSubjectList(Medical medical){
        List<Subject> subjectList = new ArrayList<>();
        for(String subject : medical.getAffectedSubjects())
            subjectList.add(repository.getSubject(subject));
        return subjectList;
    }

    private void removeMedicalData(Medical medical){
        List<Subject> subjectList = new ArrayList<>(getSelectedSubjectList(medical));
        for(Subject subject : subjectList) {
            subject.setMedical(subject.getMedical() - 1);
            repository.updateSubject(subject);
        }
        repository.removeMedical(medical);
    }

    private String getYear(String date){
        return date.substring(0,4);
    }

    private String getMonth(String date){
        return date.substring(5,7);
    }

    private String getDay(String date){
        return date.substring(8,10);
    }

    @Override
    public int getItemCount() {
        return medicalList.size();
    }

    public class MEdicalHolder extends RecyclerView.ViewHolder {
        TextView txt_date, txt_subList, btn_remove;
        CardView cardView;
        ImageView imgStatus;
        public MEdicalHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_mediEdit_date);
            txt_subList = itemView.findViewById(R.id.txt_mediEdit_sublist);
            cardView = itemView.findViewById(R.id.crd_medical_edit);
            btn_remove = itemView.findViewById(R.id.btn_medical_remove);
            imgStatus = itemView.findViewById(R.id.img_subEditor_edited);
        }
    }

    public void updateMedicalItems(List<Medical> medicals){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MedicalDiffCallback(medicals,repository.getAllMedicals()));
        medicalList.clear();
        medicalList.addAll(repository.getAllMedicals());
        diffResult.dispatchUpdatesTo(this);
    }

    private boolean isValidateSuccessfullDate(EditText year, EditText month, EditText day){
        String year_str = year.getText().toString();
        String month_str = month.getText().toString();
        String day_str = day.getText().toString();

        if(year_str.length()!=4) {
            year.setError("Doesn't Match");
            return false;
        }

        if(month_str.length()!=2){
            month.setError("Doesn't Match");
            return false;
        }

        if(Integer.parseInt(month_str)>12 || Integer.parseInt(month_str)<0)
        {
            month.setError("Invalid Input");
            return false;
        }

        if(day_str.length()!=2){
            day.setError("Doesn't Match");
            return false;
        }

        if(Integer.parseInt(day_str)>31 || Integer.parseInt(day_str)<0)
        {
            day.setError("Invalid Input");
            return false;
        }

        return true;
    }

    private String generateDate(EditText year, EditText month, EditText day){
        String date="";
        date+=year.getText().toString();
        date+="/" + month.getText().toString();
        date+="/" + day.getText().toString();
        return date;
    }

}

package apps.gliger.glg.lar.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import apps.gliger.glg.lar.RoomDB.Repository;
import apps.gliger.glg.lar.RoomDB.Subject;
import apps.gliger.glg.lar.Constant.FunctionSet;
import apps.gliger.glg.lar.Constant.SubjectDiffCallback;
import apps.gliger.glg.lar.R;

public class SubjectEditorAdapter extends RecyclerView.Adapter<SubjectEditorAdapter.EditorHolder> {

    List<Subject> subjectList;
    Context context;
    Repository repository;
    boolean isEdited = false;
    boolean isReport = false;
    View view;

    DonutProgress progress_present, progress_absent, progress_medical;
    TextView txt_present,txt_absent,txt_medical,txt_total,txt_avg;

    public SubjectEditorAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
        repository = new Repository(context);
    }

    public SubjectEditorAdapter(List<Subject> subjectList, View view,Context context) {
        this.subjectList = subjectList;
        this.view = view;
        this.context = context;
        init_components();
        isReport = true;
    }

    @NonNull
    @Override
    public EditorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_editer_single_subject,viewGroup,false);
        EditorHolder holder = new EditorHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EditorHolder editorHolder, final int i) {
        editorHolder.txt_sub_name.setText(subjectList.get(i).getSubjectName());
        editorHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Edit Window
                if(isReport){
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    double present =(double) subjectList.get(i).getPresentDays();
                    double absent = (double) subjectList.get(i).getAbsentDays();
                    double medical =(double) subjectList.get(i).getMedical();
                    double total = present + absent + medical;
                    double avg = (present+medical)*100/total;

                    progress_present.setProgress(Float.parseFloat(decimalFormat.format((present/total)*100)));
                    progress_absent.setProgress(Float.parseFloat(decimalFormat.format((absent/total)*100)));
                    progress_medical.setProgress(Float.parseFloat(decimalFormat.format((medical/total)*100)));

                    txt_present.setText(""+(int)present);
                    txt_absent.setText(""+(int)absent);
                    txt_medical.setText(""+(int)medical);
                    txt_total.setText(""+(int)total);
                    txt_avg.setText(""+(int)avg+"%");

                    if(avg>80)
                        colorReport(R.color.color_enabled);
                    else if(avg>60)
                        colorReport(R.color.textColorYellow);
                    else
                        colorReport(R.color.colorError);
                }
                else {
                    isEdited = false;
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.dialog_subject_edit,null);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setView(view);
                    alertDialog.setCancelable(false);
                    final Dialog dialog = alertDialog.create();
                    dialog.show();

                    final EditText txt_present = view.findViewById(R.id.txt_subDialog_present);
                    final EditText txt_absent = view.findViewById(R.id.txt_subDialog_absent);
                    final EditText txt_medical = view.findViewById(R.id.txt_subDialog_medical);
                    Button btn_submit = view.findViewById(R.id.btn_subDialog_submit);

                    txt_present.setText(""+subjectList.get(i).getPresentDays());
                    txt_absent.setText(""+subjectList.get(i).getAbsentDays());
                    txt_medical.setText(""+subjectList.get(i).getMedical());

                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Subject subject = subjectList.get(i);
                            List<EditText> editTextList = new ArrayList();
                            editTextList.add(txt_absent);
                            editTextList.add(txt_medical);
                            editTextList.add(txt_present);
                            if(FunctionSet.Companion.isFieldsAllFilled(editTextList)){
                                subject.setPresentDays(Integer.parseInt(txt_present.getText().toString()));
                                subject.setAbsentDays(Integer.parseInt(txt_absent.getText().toString()));
                                subject.setMedical(Integer.parseInt(txt_medical.getText().toString()));
                                repository.updateSubject(subject);
                                isEdited = true;
                                dialog.dismiss();
                            }

                        }
                    });

                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            updateSubjectItems(repository.getAllSubject());
                            if(isEdited)
                                editorHolder.imageView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void colorReport(int color){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           txt_avg.setTextColor(context.getColor(color));
           progress_absent.setInnerBackgroundColor(context.getColor(color));
           progress_medical.setInnerBackgroundColor(context.getColor(color));
           progress_present.setInnerBackgroundColor(context.getColor(color));
       }
       else {
           txt_avg.setTextColor(ContextCompat.getColor(context, color));
           progress_absent.setFinishedStrokeColor(ContextCompat.getColor(context, color));
           progress_medical.setFinishedStrokeColor(ContextCompat.getColor(context, color));
           progress_present.setFinishedStrokeColor(ContextCompat.getColor(context, color));
       }
    }


    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class EditorHolder extends RecyclerView.ViewHolder {
        TextView txt_sub_name;
        CardView cardView;
        ImageView imageView;
        public EditorHolder(@NonNull View itemView) {
            super(itemView);
            txt_sub_name = itemView.findViewById(R.id.txt_mediEdit_date);
            cardView = itemView.findViewById(R.id.crd_sub_editor);
            imageView = itemView.findViewById(R.id.img_subEditor_edited);
        }
    }

    public void updateSubjectItems(List<Subject> subjects){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SubjectDiffCallback(subjectList,subjects));
        subjectList.clear();
        subjectList.addAll(subjects);
        diffResult.dispatchUpdatesTo(this);
    }

    private void init_components(){
        progress_present = view.findViewById(R.id.sub_report_progress_present);
        progress_absent = view.findViewById(R.id.sub_report_progress_absent);
        progress_medical = view.findViewById(R.id.sub_report_progress_medical);
        txt_present = view.findViewById(R.id.txt_sub_report_present);
        txt_absent = view.findViewById(R.id.txt_sub_report_absent);
        txt_medical = view.findViewById(R.id.txt_sub_report_medical);
        txt_total = view.findViewById(R.id.txt_sub_report_total);
        txt_avg = view.findViewById(R.id.txt_sub_report_avg);
    }

}

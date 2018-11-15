package apps.gliger.glg.lar.Fragments


import android.app.AlertDialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import apps.gliger.glg.lar.Adapters.DayAdapter
import apps.gliger.glg.lar.Adapters.MedicalEditAdapter
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.R
import apps.gliger.glg.lar.RoomDB.Medical
import apps.gliger.glg.lar.RoomDB.Repository
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MedicalEditorFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var medicalEditAdapter: MedicalEditAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var repository: Repository
    lateinit var btn_add : TextView
    lateinit var layout: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_medical_editor, container, false)
        init(view)
        functions(view)
        return  view
    }

    private fun functions(view: View) {
        btn_add.setOnClickListener {
            if(repository.allSubject.size!=0)
                addNewItem()
            else
                Toast.makeText(context!!,"Please add Subjects to Continue",Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(view: View) {
        repository = Repository(context)
        layout = view.findViewById(R.id.layout_medical_edit)
        btn_add = view.findViewById(R.id.fab_medical_editor)
        recyclerView = view.findViewById(R.id.recycler_medical_editor)
        linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager
        medicalEditAdapter = MedicalEditAdapter(repository.allMedicals,context)
        recyclerView.adapter = medicalEditAdapter
    }

    fun addNewItem(){
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.medical_edit_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        val txt_year = view.findViewById<EditText>(R.id.txt_medical_edit_year)
        val txt_month = view.findViewById<EditText>(R.id.txt_medical_edit_month)
        val txt_day = view.findViewById<EditText>(R.id.txt_medical_edit_day)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycle_medical_edit)
        val llm = LinearLayoutManager(context)
        recyclerView.layoutManager = llm
        val btn_submit = view.findViewById<Button>(R.id.btn_medical_submit)

        var subjectList = arrayListOf<String>()
        val adapter = DayAdapter(subjectList, repository.allSubject, repository)
        recyclerView.adapter = adapter

        btn_submit.setOnClickListener {
            if (isValidateSuccessfullDate(txt_year, txt_month, txt_day)) {
                var medical = Medical()
                medical.date = generateDate()
                medical.affectedSubjects = adapter.selectedSubjectList
                repository.addNewMedical(medical)
                medicalEditAdapter.updateMedicalItems(repository.allMedicals)
                dialog.dismiss()
            }
        }

        txt_year.setText(getYear())
        txt_month.setText(getMonth())
        txt_day.setText(getDay())
    }

    fun getYear(): String {
        var cal = Calendar.getInstance().time
        var dtf = SimpleDateFormat("yyyy")
        return dtf.format(cal)
    }

    fun getMonth(): String {
        var cal = Calendar.getInstance().time
        var dtf = SimpleDateFormat("MM")
        return dtf.format(cal)
    }

    fun getDay(): String {
        var cal = Calendar.getInstance().time
        var dtf = SimpleDateFormat("dd")
        return dtf.format(cal)
    }

    private fun generateDate() : String{
        return getYear() + "/" + getMonth() + "/" + getDay()
    }

    private fun isValidateSuccessfullDate(year : EditText, month : EditText, day : EditText ) : Boolean{
        var year_str = year.text.toString()
        var month_str = month.text.toString()
        var day_str = day.text.toString()

        if(year_str.length !=4) {
            year.error = "Doesn't Match"
            return false
        }

        if(month_str.length!=2){
            month.error = "Doesn't Match"
            return false
        }

        if(Integer.parseInt(month_str)>12 || Integer.parseInt(month_str)<0)
        {
            month.error = "Invalid Input"
            return false
        }

        if(day_str.length!=2){
            day.error = "Doesn't Match"
            return false
        }

        if(Integer.parseInt(day_str)>31 || Integer.parseInt(day_str)<0) {
            day.error = "Invalid Input"
            return false
        }

        return true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser)
            FunctionSet.setEmptyDataString(layout,repository.allMedicals.size)
    }
}

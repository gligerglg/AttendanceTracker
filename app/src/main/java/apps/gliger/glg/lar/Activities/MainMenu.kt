package apps.gliger.glg.lar.Activities

import apps.gliger.glg.lar.RoomDB.Repository
import apps.gliger.glg.lar.RoomDB.Subject
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import apps.gliger.glg.lar.Adapters.TodayAttendanceAdapter
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.Constant.FunctionSet.Companion.setMessage
import apps.gliger.glg.lar.R
import apps.gliger.glg.lar.RoomDB.Medical
import apps.gliger.glg.lar.SettingsActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainMenu : AppCompatActivity() {

    lateinit var txt_year: TextView
    lateinit var txt_month_day: TextView
    lateinit var txt_status : TextView
    lateinit var shared: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var repository: Repository
    lateinit var layout: ConstraintLayout
    lateinit var today: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Transparent Status Bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_main_menu)

        init()
        functions()
    }

    private fun functions() {
        updateUI()
    }

    private fun init() {
        txt_year = findViewById(R.id.txt_mnu_year)
        txt_month_day = findViewById(R.id.txt_mnu_monthday)
        txt_status = findViewById(R.id.txt_main_status)
        shared = getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        editor = shared.edit()
        repository = Repository(applicationContext)
        layout = findViewById(R.id.layout_main_menu)
        today = FunctionSet.convertDayToString(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
    }

    fun setTarget(v: View) {
        when (v.id) {
            R.id.btn_mnu_setup -> startActivity(Intent(applicationContext, SetupData::class.java))
            R.id.btn_mnu_attendance -> markTodayAttendance()
            R.id.btn_mnu_edit -> startActivity(Intent(applicationContext,EditorActivity::class.java))
            R.id.btn_mnu_logout -> logout()
            R.id.btn_mnu_report -> startActivity(Intent(applicationContext,ActivityReport::class.java))
            R.id.btn_mnu_settings -> startActivity(Intent(applicationContext,SettingsActivity::class.java))
        }
    }

    private fun updateUI() {
        updateDateTime()
    }

    private fun updateDateTime() {
        var date: Date = java.util.Calendar.getInstance().time
        var year_format = SimpleDateFormat("yyyy")
        var date_format = SimpleDateFormat("MMM / dd")
        txt_year.text = year_format.format(date)
        txt_month_day.text = date_format.format(date)

        updateStatusUI()
    }

    private fun updateStatusUI() {
        if(isTodayMarkerd())
            setStatusMessage("You have already marked today attendance")
        else if(repository.isSubjectsAvailableForToday(today))
            setStatusMessage("You are able to mark today attendance")
        else
            setStatusMessage("There are no subjects for today attendance")
    }

    fun logout() {
        editor.putBoolean("stay_login", false)
        editor.commit()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    fun markTodayAttendance() {

        if (isTodayMarkerd())
            setMessage("You have already marked Today Attendance", layout)
        else {
            var isSubjectAvailable = repository.isSubjectsAvailableForToday(today)
            if (isSubjectAvailable) {
                var inflater = LayoutInflater.from(applicationContext)
                var view = inflater.inflate(R.layout.mark_attendance_ui, null)
                var alertDialog = AlertDialog.Builder(this@MainMenu)
                alertDialog.setView(view)
                var dialog = alertDialog.create()
                dialog.show()

                var btn_done: Button = view.findViewById(R.id.btn_mark_att)
                var recycler: RecyclerView = view.findViewById(R.id.recycler_mark_attendance)
                var llm = LinearLayoutManager(applicationContext)
                recycler.layoutManager = llm
                var adapter = TodayAttendanceAdapter(getTodaySubjectList(today), repository, applicationContext)
                recycler.adapter = adapter
                btn_done.setOnClickListener {
                    var attendanceMap = adapter.attendanceMap
                    recordAttendance(attendanceMap)
                    editor.putString("today_record", getDateString())
                    editor.commit()
                    dialog.dismiss()
                }
            } else
                setMessage("Subject List is Empty!", layout)
        }

    }

    fun recordAttendance(attendanceMap: HashMap<Subject, Int>) {
        var medicalList = ArrayList<String>()
        for ((subject, value) in attendanceMap) {
            when(value){
                0->subject.absentDays++
                1->subject.presentDays++
                2->{
                    subject.medical++
                    medicalList.add(subject.subjectName)
                }
            }
            repository.updateSubject(subject)
        }

        if(medicalList.size!=0)
            repository.addNewMedical(Medical(getDateString(),medicalList))
    }

    fun getTodaySubjectList(day: String): List<Subject> {
        var daymap = repository.getTodayDayMap(day)
        var subjectList: ArrayList<Subject> = ArrayList()
        for (subject in daymap.subjectList) {
            subjectList.add(repository.getSubject(subject))
        }
        return subjectList
    }

    fun getDateString(): String {
        var cal = Calendar.getInstance().time
        var dtf = SimpleDateFormat("yyyy/MM/dd")
        return dtf.format(cal)
    }

    fun isTodayMarkerd(): Boolean {
        val prev_record = shared.getString("today_record", "")
        return prev_record == getDateString()
    }

    private fun setStatusMessage(message : String){
        txt_status.text = message
    }

    override fun onPause() {
        super.onPause()
        updateStatusUI()
    }


}

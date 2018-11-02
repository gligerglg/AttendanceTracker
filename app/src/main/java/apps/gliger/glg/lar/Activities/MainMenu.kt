package apps.gliger.glg.lar.Activities

import RoomDB.Repository
import RoomDB.Subject
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
import apps.gliger.glg.lar.Adapters.TodayAttendanceAdapter
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.Constant.FunctionSet.Companion.setMessage
import apps.gliger.glg.lar.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainMenu : AppCompatActivity() {

    lateinit var txt_year: TextView
    lateinit var txt_month_day: TextView
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
            R.id.btn_mnu_edit -> FunctionSet.setMessage("Edit", layout)
            R.id.btn_mnu_logout -> logout()
            R.id.btn_mnu_report -> setMessage("Report", layout)
            R.id.btn_mnu_settings -> setMessage("Settings", layout)
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
    }

    private fun logout() {
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
                var adapter = TodayAttendanceAdapter(getTodaySubjectList(today), repository)
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

    fun recordAttendance(attendanceMap: HashMap<Subject, Boolean>) {
        for ((subject, value) in attendanceMap) {
            if (value)
                subject.presentDays++
            else
                subject.absentDays++
            repository.updateSubject(subject)
        }
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
        var dtf = SimpleDateFormat("yyyyMMdd")
        return dtf.format(cal)
    }

    fun isTodayMarkerd(): Boolean {
        val prev_record = shared.getString("today_record", "")
        return prev_record == getDateString()
    }

}

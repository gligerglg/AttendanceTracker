package apps.gliger.glg.lar.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import apps.gliger.glg.lar.R
import java.text.SimpleDateFormat
import java.util.*

class MainMenu : AppCompatActivity() {

    lateinit var txt_year : TextView
    lateinit var txt_month_day : TextView
    lateinit var shared : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

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
    }

    fun setTarget(v:View){
        when(v.id){
            R.id.btn_mnu_setup -> startActivity(Intent(applicationContext, SetupData::class.java))
            R.id.btn_mnu_attendance -> setMessage("Mark Attendance")
            R.id.btn_mnu_edit -> setMessage("Edit")
            R.id.btn_mnu_logout -> logout()
            R.id.btn_mnu_report -> setMessage("Report")
            R.id.btn_mnu_settings -> setMessage("Settings")
        }
    }

    private fun setMessage (message : String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(){
        updateDateTime()
    }

    private fun updateDateTime(){
        var date : Date = java.util.Calendar.getInstance().time
        var year_format = SimpleDateFormat("yyyy")
        var date_format = SimpleDateFormat("MMM / dd")
        txt_year.text = year_format.format(date)
        txt_month_day.text = date_format.format(date)
    }

    private fun logout(){
        editor.putBoolean("stay_login", false)
        editor.commit()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }
}

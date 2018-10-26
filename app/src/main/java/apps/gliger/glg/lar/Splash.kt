package apps.gliger.glg.lar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class Splash : AppCompatActivity() {

    lateinit var shared : SharedPreferences
    var isStayLogged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Transparent Status Bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splash)

        init()
        Handler().postDelayed({
            if(isStayLogged)
                startActivity(Intent(applicationContext,MainMenu::class.java))
            else
                startActivity(Intent(applicationContext,LoginActivity::class.java))
        },2000)
    }

    fun init(){
        shared = getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        isStayLogged = shared.getBoolean("stay_login",false)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}

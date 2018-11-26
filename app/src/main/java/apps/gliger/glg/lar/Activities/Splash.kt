package apps.gliger.glg.lar.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.view.WindowManager
import apps.gliger.glg.lar.R
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {

    lateinit var shared : SharedPreferences
    var isStayLogged = false
    var isFirstTime = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Transparent Status Bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splash)

       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val fade = Fade()
            fade.excludeTarget(android.R.id.statusBarBackground,true)
            fade.excludeTarget(android.R.id.navigationBarBackground,true)
            fade.excludeTarget(img_splash,true)
            window.exitTransition = fade
            window.enterTransition = fade
        }*/

        init()
        Handler().postDelayed({
            if(isFirstTime){
                startActivity(Intent(applicationContext, IntroActivity::class.java).putExtra("first_time",true))
            }
            else{
                if(isStayLogged)
                    startActivity(Intent(applicationContext, MainMenu::class.java))
                else
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        },2000)

    }

    fun init(){
        shared = getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        isStayLogged = shared.getBoolean("stay_login",false)
        isFirstTime = shared.getBoolean("first_time",true)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}

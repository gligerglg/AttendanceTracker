package apps.gliger.glg.lar.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment

import apps.gliger.glg.lar.Fragments.SampleSlide
import apps.gliger.glg.lar.R

class IntroActivity : AppIntro2() {

    private var isFirstTime: Boolean = false
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        isFirstTime = intent.getBooleanExtra("first_time", false)

        addSlide(SampleSlide.newInstance(R.layout.intro_page_1))
        addSlide(SampleSlide.newInstance(R.layout.intro_page_2))
        addSlide(SampleSlide.newInstance(R.layout.intro_page_3))
        addSlide(SampleSlide.newInstance(R.layout.intro_page_4))
        addSlide(SampleSlide.newInstance(R.layout.intro_page_5))
        addSlide(SampleSlide.newInstance(R.layout.intro_page_6))
        addSlide(SampleSlide.newInstance(R.layout.intro_page_7))

        setFadeAnimation()
        backButtonVisibilityWithDone = false
        showSkipButton(false)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        if (isFirstTime) {
            editor!!.putBoolean("first_time", false)
            editor!!.commit()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        } else
            finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }
}

package apps.gliger.glg.lar.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.WindowManager
import android.widget.TextView
import apps.gliger.glg.lar.Adapters.EditorAdapter
import apps.gliger.glg.lar.R

class EditorActivity : AppCompatActivity() {

    lateinit var txt_status : TextView
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/
        setContentView(R.layout.activity_editor)

        init()
        functions()
    }

    fun init(){
        txt_status = findViewById(R.id.txt_editor_status)
        viewPager = findViewById(R.id.viewPager_editor)
        viewPager.adapter = EditorAdapter(supportFragmentManager)
    }

    fun functions(){
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                txt_status.text = resources.getStringArray(R.array.string_status_editor)[position]
            }
        })
    }
}

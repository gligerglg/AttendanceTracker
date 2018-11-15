package apps.gliger.glg.lar.Activities

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import apps.gliger.glg.lar.Adapters.SetupAdapter
import apps.gliger.glg.lar.R


class SetupData : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var txt_status: TextView
    lateinit var btn_done: Button
    lateinit var txt_status_num: TextView
    lateinit var layout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/
        setContentView(R.layout.activity_setup_data)

        init()
        functions()
    }

    private fun functions() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                txt_status.text = resources.getStringArray(R.array.string_status_setup)[position]
                txt_status_num.text = "Step ${position + 1} / 2"
                if (position == 1)
                    btn_done.text = resources.getString(R.string.done_conf)
                else
                    btn_done.text = resources.getString(R.string.next_step)
            }
        })

        btn_done.setOnClickListener {
            if (TextUtils.equals(btn_done.text, resources.getString(R.string.done_conf)))
                finish()
            else
                viewPager.currentItem += 1
        }
    }

    fun init() {
        viewPager = findViewById(R.id.viewPager_Setup)
        viewPager.adapter = SetupAdapter(supportFragmentManager)
        txt_status = findViewById(R.id.txt_setup_status)
        btn_done = findViewById(R.id.btn_setup_done)
        txt_status_num = findViewById(R.id.txt_status_num)
        layout = findViewById(R.id.layout_setup)
    }

}

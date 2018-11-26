package apps.gliger.glg.lar.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import apps.gliger.glg.lar.Adapters.SubjectEditorAdapter
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.R
import apps.gliger.glg.lar.RoomDB.Repository
import com.github.lzyzsd.circleprogress.DonutProgress
import android.app.Activity
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class ActivityReport : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var subjectEditorAdapter: SubjectEditorAdapter
    lateinit var repository: Repository
    lateinit var layout : ConstraintLayout
    lateinit var add : InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        init()
    }

    private fun init() {
        recyclerView = findViewById(R.id.recycle_sub_report)
        layout = findViewById(R.id.layout_subject_report)
        repository = Repository(applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        subjectEditorAdapter = SubjectEditorAdapter(repository.allSubject,window.decorView.rootView,applicationContext)
        recyclerView.adapter = subjectEditorAdapter

        FunctionSet.setEmptyDataString(layout,repository.allSubject.size)

        /**Admob Interstitial Add**/
        MobileAds.initialize(this, resources.getString(R.string.admob_appId))
        add = InterstitialAd(this)
        add.adUnitId = resources.getString(R.string.admob_test_full_screen)
        add.loadAd(AdRequest.Builder().build())


        add.adListener = object: AdListener() {
            override fun onAdLoaded() {
                if(add.isLoaded)
                    add.show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
            }
        }
    }
}


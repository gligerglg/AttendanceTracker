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


class ActivityReport : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var subjectEditorAdapter: SubjectEditorAdapter
    lateinit var repository: Repository
    lateinit var layout : ConstraintLayout


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
        subjectEditorAdapter = SubjectEditorAdapter(repository.allSubject,window.decorView.rootView)
        recyclerView.adapter = subjectEditorAdapter

        FunctionSet.setEmptyDataString(layout,repository.allSubject.size)
    }
}

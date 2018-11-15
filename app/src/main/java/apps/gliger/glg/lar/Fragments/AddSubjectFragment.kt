package apps.gliger.glg.lar.Fragments


import apps.gliger.glg.lar.RoomDB.Repository
import apps.gliger.glg.lar.RoomDB.Subject
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import apps.gliger.glg.lar.Adapters.SubjectAdapter
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.R
import org.w3c.dom.Text


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddSubjectFragment : Fragment() {

    lateinit var txt_subject_name : EditText
    lateinit var txt_subject_code : EditText
    lateinit var  btn_addSubject : TextView
    lateinit var repository: Repository
    lateinit var layout : FrameLayout
    lateinit var recyclerView: RecyclerView
    lateinit var llm : LinearLayoutManager
    var subjectAdapter: SubjectAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_subject, container, false)
        init(view)
        functions()
        return view
    }

    fun init(view: View){
        txt_subject_name = view.findViewById(R.id.txt_addSub_subname)
        txt_subject_code = view.findViewById(R.id.txt_addSub_cc)
        btn_addSubject = view.findViewById(R.id.btn_addSub_add)
        layout = view.findViewById(R.id.layout_add_subject)
        repository = Repository(activity)
        FunctionSet.setEmptyDataString(layout,repository.allSubject.size)


        //Setup Recycler View
        recyclerView = view.findViewById(R.id.recycler_addSubject)
        llm = LinearLayoutManager(context)
        recyclerView.layoutManager = llm
        subjectAdapter = SubjectAdapter(repository.allSubject, context, repository)
        recyclerView.adapter = subjectAdapter
    }

    fun addNewSubject(){
        if(FunctionSet.isFieldsAllFilled(listOf(txt_subject_name, txt_subject_code))){
            //Add New Subject
            var subject = Subject(txt_subject_name.text.toString(),txt_subject_code.text.toString())

            if(!repository.isSubjectExists(subject)) {
                repository.addNewSubject(subject)
                txt_subject_name.setText("")
                txt_subject_code.setText("")
                txt_subject_name.requestFocus()
                subjectAdapter!!.updateSubjectItems(repository.allSubject)
            }
            else
                FunctionSet.setMessage("Subject is already Exists!", layout)
        }
    }

    fun functions(){
        btn_addSubject.setOnClickListener { addNewSubject() }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            if(subjectAdapter!=null) {
                subjectAdapter!!.updateSubjectItems(repository.allSubject)
                subjectAdapter!!.notifyDataSetChanged()
                FunctionSet.setEmptyDataString(layout,repository.allSubject.size)
            }
        }
    }
}

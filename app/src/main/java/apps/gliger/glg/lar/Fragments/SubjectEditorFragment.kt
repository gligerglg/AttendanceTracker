package apps.gliger.glg.lar.Fragments


import apps.gliger.glg.lar.RoomDB.Repository
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import apps.gliger.glg.lar.Adapters.SubjectEditorAdapter
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SubjectEditorFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var llm : LinearLayoutManager
    lateinit var adapter : SubjectEditorAdapter
    lateinit var repository: Repository
    lateinit var layout : FrameLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_subject_editor, container, false)
        init(view)
        return view
    }

    fun init(view: View){
        recyclerView = view.findViewById(R.id.recycler_subject_editor)
        llm = LinearLayoutManager(view.context)
        recyclerView.layoutManager = llm
        repository = Repository(view.context)
        adapter = SubjectEditorAdapter(repository.allSubject,context)
        recyclerView.adapter = adapter
        layout = view.findViewById(R.id.layout_subject_editor)

        FunctionSet.setEmptyDataString(layout,repository.allSubject.size)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        /*if(isVisibleToUser)
            FunctionSet.setEmptyDataString(context!!,repository.allSubject.size)*/
    }
}

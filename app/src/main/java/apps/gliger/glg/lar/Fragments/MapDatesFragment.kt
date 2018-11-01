package apps.gliger.glg.lar.Fragments


import RoomDB.Repository
import RoomDB.Subject
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Spinner
import apps.gliger.glg.lar.Adapters.DayAdapter
import apps.gliger.glg.lar.R
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MapDatesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var llm : LinearLayoutManager
    lateinit var dayAdapter: DayAdapter
    lateinit var repository: Repository
    lateinit var spinner: Spinner
    lateinit var layout : FrameLayout
    var viewQ : View? = null
    var emptyList = ArrayList<Subject>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewQ = inflater.inflate(R.layout.fragment_map_dates, container, false)
        init(viewQ!!)
        functions()
        return viewQ
    }

    fun init(view: View){
        repository = Repository(activity)
        spinner = view.findViewById(R.id.spinner_mapdate)
        layout = view.findViewById(R.id.layout_mapping)
        //Setup Recycler View
        recyclerView = view.findViewById(R.id.recycler_mapDays)
        llm = LinearLayoutManager(context)
        recyclerView.layoutManager = llm
        dayAdapter = DayAdapter(repository.allSubject, repository.getTodayDayMap(spinner.selectedItem.toString()), repository)
        recyclerView.adapter = dayAdapter


    }

    fun functions(){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateRecycle(position)
            }
        }

    }


    fun updateRecycle(position : Int){
        var day = resources.getStringArray(R.array.array_days)[position]
        var mapDay = repository.getTodayDayMap(day)

        dayAdapter = DayAdapter(repository.allSubject, mapDay, repository)
        dayAdapter.notifyDataSetChanged()
        recyclerView.adapter = dayAdapter
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            updateRecycle(0)
            spinner.setSelection(0)
        }
    }
}

package apps.gliger.glg.lar.Constant

import android.support.v7.util.DiffUtil

import apps.gliger.glg.lar.RoomDB.Subject

class SubjectDiffCallback(private val oldSubList: List<Subject>, private val newSubList: List<Subject>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldSubList.size
    }

    override fun getNewListSize(): Int {
        return newSubList.size
    }

    override fun areItemsTheSame(i: Int, i1: Int): Boolean {
        return oldSubList[i].id == newSubList[i1].id
    }

    override fun areContentsTheSame(i: Int, i1: Int): Boolean {
        return oldSubList[i].subjectName == newSubList[i1].subjectName
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}

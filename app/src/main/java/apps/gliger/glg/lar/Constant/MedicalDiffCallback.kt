package apps.gliger.glg.lar.Constant

import android.support.v7.util.DiffUtil

import apps.gliger.glg.lar.RoomDB.Medical

class MedicalDiffCallback(private val oldMedicalList: List<Medical>, private val newMedicalList: List<Medical>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldMedicalList.size
    }

    override fun getNewListSize(): Int {
        return newMedicalList.size
    }

    override fun areItemsTheSame(i: Int, i1: Int): Boolean {
        return oldMedicalList[i].id == newMedicalList[i1].id
    }

    override fun areContentsTheSame(i: Int, i1: Int): Boolean {
        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}

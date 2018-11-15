package apps.gliger.glg.lar.Constant;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import apps.gliger.glg.lar.RoomDB.Medical;

public class MedicalDiffCallback extends DiffUtil.Callback {

    private List<Medical> oldMedicalList;
    private List<Medical> newMedicalList;

    public MedicalDiffCallback(List<Medical> oldMedicalList, List<Medical> newMedicalList) {
        this.oldMedicalList = oldMedicalList;
        this.newMedicalList = newMedicalList;
    }

    @Override
    public int getOldListSize() {
        return oldMedicalList.size();
    }

    @Override
    public int getNewListSize() {
        return newMedicalList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldMedicalList.get(i).getId()==newMedicalList.get(i1).getId();
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

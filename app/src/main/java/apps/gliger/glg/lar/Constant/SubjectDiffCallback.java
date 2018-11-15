package apps.gliger.glg.lar.Constant;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import apps.gliger.glg.lar.RoomDB.Subject;

public class SubjectDiffCallback extends DiffUtil.Callback {

    private final List<Subject> oldSubList;
    private final List<Subject> newSubList;

    public SubjectDiffCallback(List<Subject> oldSubList, List<Subject> newSubList) {
        this.oldSubList = oldSubList;
        this.newSubList = newSubList;
    }

    @Override
    public int getOldListSize() {
        return oldSubList.size();
    }

    @Override
    public int getNewListSize() {
        return newSubList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldSubList.get(i).getId()==newSubList.get(i1).getId();
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldSubList.get(i).getSubjectName().equals(newSubList.get(i1).getSubjectName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

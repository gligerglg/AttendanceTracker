package apps.gliger.glg.lar.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.gliger.glg.lar.Fragments.MedicalEditorFragment;
import apps.gliger.glg.lar.Fragments.SubjectEditorFragment;

public class EditorAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public EditorAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new SubjectEditorFragment(),
                new MedicalEditorFragment()
        };
    }

    @Override
    public Fragment getItem(int i) {
            return childFragments[i];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }
}

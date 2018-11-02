package apps.gliger.glg.lar.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.gliger.glg.lar.Fragments.AddSubjectFragment;
import apps.gliger.glg.lar.Fragments.MapDatesFragment;

public class SetupAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public SetupAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
            new AddSubjectFragment(),
            new MapDatesFragment()
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

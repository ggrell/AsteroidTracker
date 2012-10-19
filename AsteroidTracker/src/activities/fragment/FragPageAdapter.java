package activities.fragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragPageAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    /** Constructor of the class */
    public FragPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    
    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int page) {
        return this.fragments.get(page);
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {        
        return this.fragments.size();
    }
}

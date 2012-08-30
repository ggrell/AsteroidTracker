package activities.fragment;

import java.util.List;

import fragments.ImpactDetailsFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragPageAdapter extends FragmentPagerAdapter{
    
    final int PAGE_COUNT = 5;
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
        switch (page){
            case (0): 
                return new RecentFragment();
            case (1): 
                return new UpcomingFragment();
            case (2): 
                return new ImpactDetailsFragment();
            case (3): 
                return new NewsFragment();
            default : 
                new RecentFragment();
                MyFragment myFragment = new MyFragment();
                Bundle data = new Bundle();
                data.putInt("current_page", page+1);
                myFragment.setArguments(data);
                return myFragment;
        }
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {        
        return this.fragments.size();
    }
}

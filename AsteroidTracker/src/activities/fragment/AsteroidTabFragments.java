package activities.fragment;


import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TabHost.OnTabChangeListener;
import com.viewpagerindicator.TabPageIndicator;

public class AsteroidTabFragments extends FragmentActivity {
    MyGAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    private static final String[] CONTENT = new String[] { "Recent", "Artists", "Albums", "Songs", "Playlists", "Genres" };

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
        
        mAdapter = new MyGAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
        
        
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        
//        /** Getting a reference to the ViewPager defined the layout file */        
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        
//        /** Getting fragment manager */
//        FragmentManager fm = getSupportFragmentManager();
//        
//        /** Instantiating FragmentPagerAdapter */
//        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm);
//        
//        /** Setting the pagerAdapter to the pager object */
//        pager.setAdapter(pagerAdapter);
        
    }

    class MyGAdapter extends FragmentPagerAdapter {
        private int mCount = CONTENT.length;
        
        public MyGAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return TestFragment.newInstance(AsteroidTabFragments.CONTENT[position % AsteroidTabFragments.CONTENT.length]);
        }

        public int getCount() {
            return AsteroidTabFragments.CONTENT.length;
        }

        public void setCount(int count) {
            if (count > 0 && count <= 10) {
                mCount = count;
                notifyDataSetChanged();
            }
        }

        public CharSequence getPageTitle(int position) {
            return AsteroidTabFragments.CONTENT[position % AsteroidTabFragments.CONTENT.length].toUpperCase();
        }
    }
}

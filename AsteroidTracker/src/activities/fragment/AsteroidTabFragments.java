package activities.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import com.viewpagerindicator.TabPageIndicator;
import fragments.ImpactFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;


public class AsteroidTabFragments extends FragmentActivity implements TabHost.OnTabChangeListener{
    ViewPager mPager;
    PageIndicator mIndicator;
    private TabHost mTabHost;
    private HashMap mapTabInfo = new HashMap();
    private TabInfo mLastTab = null;
    private FragPageAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.tabs_layout);
      setContentView(R.layout.fragment_viewpager_layout);
//        initialiseTabHost(savedInstanceState);
        initFragmentAndPading();
        
//        /** Getting a reference to the ViewPager defined the layout file */        
//        ViewPager pager = (ViewPager) findViewById(R.id.pager2);
//        /** Getting fragment manager */
//        FragmentManager fm = getSupportFragmentManager();
//        /** Instantiating FragmentPagerAdapter */
//        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm);
//        /** Setting the pagerAdapter to the pager object */
//        pager.setAdapter(pagerAdapter);
//        
    }

    public void initFragmentAndPading(){
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, RecentFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, UpcomingFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ImpactFragment.class.getName()));
        this.mPagerAdapter  = new FragPageAdapter(super.getSupportFragmentManager(), fragments);
        
        ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

    }

    private class TabInfo {
                 private String tag;
                 private Class classType;
                 private Bundle args;
                 private Fragment fragment;
                 TabInfo(String tag, Class classType, Bundle args) {
                     this.tag = tag;
                     this.classType = classType;
                     this.args = args;
                 }
                 }

    class TabFactory implements TabContentFactory {
                private final Context mContext;

                /**
                 * @param context
                 */
                public TabFactory(Context context) {
                    mContext = context;
                }
                /** (non-Javadoc)
                 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
                 */
                public View createTabContent(String tag) {
                    View v = new View(mContext);
                    v.setMinimumWidth(0);
                    v.setMinimumHeight(0);
                    return v;
                }
    }

    private void initialiseTabHost(Bundle args) {
                mTabHost = (TabHost)findViewById(android.R.id.tabhost);
                mTabHost.setup();
                TabInfo tabInfo = null;
                AsteroidTabFragments.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator("Recent"), ( tabInfo = new TabInfo("Recent", RecentFragment.class, args)));
//                this.mapTabInfo.put(tabInfo.tag, tabInfo);
                AsteroidTabFragments.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator("Upcoming"), ( tabInfo = new TabInfo("Upcoming", UpcomingFragment.class, args)));
//                this.mapTabInfo.put(tabInfo.tag, tabInfo);
                AsteroidTabFragments.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3").setIndicator("Impact Risk"), ( tabInfo = new TabInfo("Impact Risk", ImpactFragment.class, args)));
//                this.mapTabInfo.put(tabInfo.tag, tabInfo);
                // Default to first tab
                this.onTabChanged("Tab1");
                mTabHost.setOnTabChangedListener(this);
            }

    private static void addTab(AsteroidTabFragments activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();

        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }
        tabHost.addTab(tabSpec);
    }


    @Override
    public void onTabChanged(String tabId) {
            TabInfo newTab = (TabInfo) this.mapTabInfo.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
                   if (mLastTab != null) {
                       if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                       }
                   }
                   if (newTab != null) {
                       if (newTab.fragment == null) {
                           newTab.fragment = Fragment.instantiate(this,
                                   newTab.classType.getName(), newTab.args);
                           ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                       } else {
                           ft.attach(newTab.fragment);
                       }
                   }

                   mLastTab = newTab;
                   ft.commit();
                   this.getSupportFragmentManager().executePendingTransactions();
           }
    }


}
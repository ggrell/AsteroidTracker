package activities.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import service.AsteroidTrackerService;
import service.ContentManager;
import utils.LoadingDialogHelper;
import utils.NetworkUtil;

import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import activities.AsteroidTrackerActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;


public class AsteroidTabFragments extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{
    ViewPager mPager;
    PageIndicator mIndicator;
    private TabHost mTabHost;
    private HashMap mapTabInfo = new HashMap();
    private FragPageAdapter mPagerAdapter;
    private ViewPager mViewPager;

    public static TabSpec TabSpec1_Recent;
    public static TabSpec TabSpec2_Upcoming;
    public static TabSpec TabSpec3_Impact;
    public static TabSpec TabSpec4_News;
    public ListView ls1_ListView_Recent;
    public static ListView ls2_ListView_Upcoming;
    public ListView ls3_ListView_Impact;
    public ListView ls4_ListView_News;
    public static ContentManager contentManager = new ContentManager();
    public static AsteroidTrackerService GitService = new AsteroidTrackerService();
    public static boolean UseGitService;
    NetworkUtil nUtil = new NetworkUtil();
    public static Context cText;
    public static Drawable drawable;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        cText = this;
        LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
        boolean networkAvailable = nUtil.IsNetworkAvailable(this);
        if(networkAvailable){
            UseGitService = GitService.isGitServiceAvailable();
        }
        initTabHost(savedInstanceState);
        initFragmentAndPading();
        drawable = getResources().getDrawable(R.drawable.asteroid);
    }

    private void initTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();


        TabSpec1_Recent=mTabHost.newTabSpec("Tab1");
        TabSpec1_Recent.setIndicator("RECENT");
        TabSpec1_Recent.setContent(R.id.tabthis);
        
        TabSpec2_Upcoming=mTabHost.newTabSpec("Tab2");
        TabSpec2_Upcoming.setIndicator("UPCOMING");
        TabSpec2_Upcoming.setContent(R.id.tabthis);

        TabSpec3_Impact=mTabHost.newTabSpec("Tab3");
        TabSpec3_Impact.setIndicator("IMPACT RISK");
        TabSpec3_Impact.setContent(R.id.tabthis);
        
        TabSpec4_News=mTabHost.newTabSpec("Tab4");
        TabSpec4_News.setIndicator("NEWS");
        TabSpec4_News.setContent(R.id.tabthis);
        
        AsteroidTabFragments.AddTab(this, this.mTabHost, this.TabSpec1_Recent);
        AsteroidTabFragments.AddTab(this, this.mTabHost, this.TabSpec2_Upcoming);
        AsteroidTabFragments.AddTab(this, this.mTabHost, this.TabSpec3_Impact);
        AsteroidTabFragments.AddTab(this, this.mTabHost, this.TabSpec4_News);
        mTabHost.setOnTabChangedListener(this);
    }

    public void initFragmentAndPading(){
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, RecentFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, UpcomingFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ImpactFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, NewsFragment.class.getName()));
        this.mPagerAdapter  = new FragPageAdapter(super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
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


    private static void AddTab(AsteroidTabFragments activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String tabId) {
                int pos = this.mTabHost.getCurrentTab();
                Log.i("tabz", "tabz: "+pos);
                this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    public void onPageSelected(int position) {
            this.mTabHost.setCurrentTab(position);
        }

}
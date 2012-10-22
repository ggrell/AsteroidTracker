package activities.fragment;

import java.util.List;
import java.util.Vector;

import service.AsteroidTrackerService;
import service.ContentManager;
import service.DownloadManager;
import service.SharingService;
import utils.LoadingDialogHelper;
import utils.NetworkUtil;
import activities.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.actionbarsherlock.widget.ShareActionProvider.OnShareTargetSelectedListener;
import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;

public class AsteroidTabFragments extends BaseActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener 
{
    ViewPager mPager;
    PageIndicator mIndicator;
    private TabHost mTabHost;
    public FragPageAdapter mPagerAdapter;
    private ViewPager mViewPager;
    public static ContentManager contentManager = new ContentManager();
    public DownloadManager dManager = new DownloadManager(); 
    public static Context cText;
    public static Drawable drawable;
    ActionBar actionBar;
    String[] tabNames = {"RECENT", "UPCOMING", "IMPACT RISK", "NEWS"};
    public static SharingService shareSvc = new SharingService();
    public static ShareActionProvider shareActionProvider;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        actionBar=getSupportActionBar();
        cText = this;

        initTabHost(savedInstanceState);
        initFragmentAndPading();
        
        LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
        drawable = getResources().getDrawable(R.drawable.asteroid);
        dManager.startDownloads();
        dManager.setFragPageAdapter(mPagerAdapter);

    }

    private void initTabHost(Bundle args) {

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        for (String name: tabNames){
            TabSpec TabSpec = setupTabSpec(new TextView(this), name);
            AsteroidTabFragments.AddTab(this, this.mTabHost, TabSpec);
        }
        mTabHost.setOnTabChangedListener(this);
    }

    private TabSpec setupTabSpec(final View view, final String tag) {
            View tabview = createTabView(mTabHost.getContext(), tag);
            TabSpec TabSpec;
            TabSpec=mTabHost.newTabSpec("tag");
            TabSpec.setIndicator(tabview);
            TabSpec.setContent(
                    new TabContentFactory() {public View createTabContent(String tag) {return view;}}
            );
            return TabSpec;
        }

    private static View createTabView(final Context context, final String text) {
            View view = LayoutInflater.from(context).inflate(R.layout.tabs_custom, null);
            TextView tv = (TextView) view.findViewById(R.id.tabsText);
            tv.setText(text);
            return view;
        }

    
    public void initFragmentAndPading()
    {
        List<Fragment> fragments = new Vector<Fragment>();
         fragments.add(Fragment.instantiate(this, RecentFragment.class.getName()));
//        fragments.add(Fragment.instantiate(this, RecentFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, UpcomingFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ImpactFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, NewsFragment.class.getName()));
        
        this.mPagerAdapter = new FragPageAdapter(super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    class TabFactory implements TabContentFactory {
                private final Context mContext;

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

//    @Override
    public void onTabChanged(String tabId) {
                int pos = this.mTabHost.getCurrentTab();
                Log.i("tabz", "tabz: "+pos);
                this.mViewPager.setCurrentItem(pos);
    }

//    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        
    }

//    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    public void onPageSelected(int position) {
            this.mTabHost.setCurrentTab(position);
        }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
        case R.id.quit:
            finish();
            return true;
        case R.id.about:
            openAbout(this);
            return true;
        case R.id.reload:
          LoadingDialogHelper.closeDialog = 0;
//          int tabtoUpdate = this.mTabHost.getCurrentTab();
          LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
          dManager.startDownloads();
//          Log.d("case", "tabtoUpdate"+tabtoUpdate);
//          switch (tabtoUpdate){
//          case 0:
//              //NEO RECENT
//              dManager.processNEOFeedRecent();
//          case 1:
//              //NEO UPCOMING
//              dManager.processNEOFeedUpcoming();
//          case 2:
//              //IMPACT
//              dManager.processImpactFeed();
//          case 3:
//              //NEO News
//              dManager.processAsteroidNewsFeed();
//          }
          return true;
        default:
            return super.onOptionsItemSelected(item);
        }
}
}
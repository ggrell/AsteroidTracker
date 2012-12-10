package activities.fragment;

import service.ContentManager;
import service.DownloadManager;
import service.SharingService;
import activities.BaseActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;

public class AsteroidTabFragments extends BaseActivity // implements ViewPager.OnPageChangeListener 
{
    PageIndicator mIndicator;
    private TabHost mTabHost;
    public static FragPageAdapter mPagerAdapter;
    public static ViewPager mViewPager;
    public static ContentManager contentManager = new ContentManager();
    public DownloadManager dManager = new DownloadManager(); 
    public static Context cText;
    public static Drawable drawable;
    ActionBar actionBar;
    String[] tabNames = {"RECENT", "UPCOMING", "IMPACT RISK", "NEWS"};
    public static SharingService shareSvc = new SharingService();
    static com.actionbarsherlock.view.MenuItem reloadItem;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        actionBar=getSupportActionBar();
        cText = this;
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        initFragmentAndPading();
        drawable = getResources().getDrawable(R.drawable.asteroid);
//      LoadingDialogHelper.dialog = new ProgressDialog(this);
//      LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
//      dManager.setFragPageAdapter(this.mPagerAdapter);
//      dManager.startDownloads();
    }

    public FragPageAdapter getAdap(){
        return this.mPagerAdapter;
    }

    public void initFragmentAndPading()
    {
        mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        mPagerAdapter = new FragPageAdapter(this, mTabHost, mViewPager);
        mPagerAdapter.addTab(mTabHost.newTabSpec("RECENT").setIndicator("Recent"),RecentFragment.class, null);
        mPagerAdapter.addTab(mTabHost.newTabSpec("UPCOMING").setIndicator("Upcoming"),UpcomingFragment.class, null);
        mPagerAdapter.addTab(mTabHost.newTabSpec("IMPACTRISK").setIndicator("Impact Risk"),ImpactFragment.class, null);
        mPagerAdapter.addTab(mTabHost.newTabSpec("News").setIndicator("News"),NewsFragment.class, null);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.about:
              openAbout(this);
              return true;
        default:
        return false;
        }
    }
}
package activities.fragment;

import service.SharingService;
import utils.NetworkUtil;
import activities.BaseActivity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Window;
import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import fragments.BookFragment;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;

public class AsteroidTabFragments extends BaseActivity {
    PageIndicator mIndicator;
    public static FragPageAdapter mPagerAdapter;
    public static ViewPager mViewPager;
    public static Context cText;
    public static Drawable drawable;
    public static SharingService shareSvc = new SharingService();
    static com.actionbarsherlock.view.MenuItem reloadItem;
    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        Configuration conf = getResources().getConfiguration();
        fixActionBar(conf);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        actionBar = getSupportActionBar();
        setSupportProgressBarIndeterminateVisibility(false);
        cText = this;
        initActionBarFragmentsAndPading(actionBar);
        drawable = getResources().getDrawable(R.drawable.asteroid);
    }

    public FragPageAdapter getAdap(){
        return AsteroidTabFragments.mPagerAdapter;
    }

    public void initActionBarFragmentsAndPading(ActionBar actionBar)
    {
        mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        mPagerAdapter = new FragPageAdapter(this, actionBar, mViewPager);
        mPagerAdapter.addTab(actionBar.newTab().setText("Recent") ,RecentFragment.class, null);
        mPagerAdapter.addTab(actionBar.newTab().setText("Upcoming") ,UpcomingFragment.class, null);
        mPagerAdapter.addTab(actionBar.newTab().setText("ImpactRisk") ,ImpactFragment.class, null);
        mPagerAdapter.addTab(actionBar.newTab().setText("News") ,NewsFragment.class, null);
        mPagerAdapter.addTab(actionBar.newTab().setText("Books") ,BookFragment.class, null);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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

    public void fixActionBar(Configuration configuration) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        getSherlock().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fixActionBar(newConfig);
    }

}
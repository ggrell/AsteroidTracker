package activities.fragment;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TabHost.TabContentFactory;

public class FragPageAdapter extends FragmentPagerAdapter 
implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private TabHost mTabHost;
    private final ArrayList mTabs = new ArrayList();
    private Context mContext = null;
    String[] tabNames = {"RECENT", "UPCOMING", "IMPACT RISK", "NEWS"};

    public FragPageAdapter(SherlockFragmentActivity activity, TabHost tabHost, ViewPager pager) {
        super(activity.getSupportFragmentManager());
        mContext = activity;
        mTabHost = tabHost;
        mViewPager = pager;
        mTabHost.setOnTabChangedListener(this);
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;

        public  TabInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = (TabInfo) mTabs.get(position);
        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
    }

    public void addTab(TabHost.TabSpec tabSpec, Class clss, Bundle args) {
        View tabview = createTabView(mTabHost.getContext(), tabSpec.getTag());
        tabSpec.setContent(new TabFactory(mContext));
        tabSpec.setIndicator(tabview);
        TabInfo info = new TabInfo(clss, args);
        mTabs.add(info);
        mTabHost.addTab(tabSpec);
        notifyDataSetChanged();
    }

    class TabFactory implements TabContentFactory {
        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            return v;
        }
}

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return mTabs.size();
    }

    public void onPageScrollStateChanged(int arg0) {
        int pos =  this.mViewPager.getCurrentItem();
        mTabHost.setCurrentTab(pos);
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    public void onPageSelected(int arg0) {}

  public void onTabChanged(String tabId) {
      int pos = mTabHost.getCurrentTab();
      this.mViewPager.setCurrentItem(pos);
  }

}

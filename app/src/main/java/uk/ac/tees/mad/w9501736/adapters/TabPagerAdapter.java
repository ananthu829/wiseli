package uk.ac.tees.mad.w9501736.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import uk.ac.tees.mad.w9501736.ui.fragment.activePage.ActiveFragment;
import uk.ac.tees.mad.w9501736.ui.fragment.inActivePage.InactiveFragment;


public class TabPagerAdapter extends FragmentPagerAdapter {

    int totalTabs;
    private Context myContext;
    private Integer circleId;

    public TabPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ActiveFragment activeFragment = new ActiveFragment(circleId);
                return activeFragment;
            case 1:
                InactiveFragment inactiveFragment = new InactiveFragment(circleId);
                return inactiveFragment;
            default:
                ActiveFragment defaultFragment = new ActiveFragment(circleId);
                return defaultFragment;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

    public Integer getCircleID() {
        return circleId;
    }

    public void setCircleID(Integer circleId) {
        this.circleId = circleId;
    }
}
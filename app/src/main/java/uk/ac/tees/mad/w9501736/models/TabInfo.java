package uk.ac.tees.mad.w9501736.models;

import androidx.fragment.app.Fragment;

public class TabInfo {

    Fragment tabFragment;
    String tabTitle;

    public TabInfo(Fragment tabFragment, String tabTitle) {
        this.tabFragment = tabFragment;
        this.tabTitle = tabTitle;
    }

    public Fragment getTabFragment() {
        return tabFragment;
    }

    public void setTabFragment(Fragment tabFragment) {
        this.tabFragment = tabFragment;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }
}

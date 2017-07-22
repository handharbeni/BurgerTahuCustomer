package illiyin.mhandharbeni.burgertahucustomer.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import illiyin.mhandharbeni.burgertahucustomer.fragment.FragmentHome;
import illiyin.mhandharbeni.burgertahucustomer.fragment.FragmentOrder;
import illiyin.mhandharbeni.burgertahucustomer.fragment.FragmentProfile;

/**
 * Created by root on 20/07/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new FragmentHome();
            case 1:
                // Games fragment activity
                return new FragmentOrder();
            case 2:
                // Movies fragment activity
                return new FragmentProfile();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Home";
        }
        else if (position == 1)
        {
            title = "Order";
        }
        else if (position == 2)
        {
            title = "Profile";
        }
        return title;
    }
}
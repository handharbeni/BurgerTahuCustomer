package illiyin.mhandharbeni.burgertahucustomer.fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.TabsPagerAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.sub.DetailMenu;
import illiyin.mhandharbeni.burgertahucustomer.fragment.sub.activity.Cart;
import illiyin.mhandharbeni.burgertahucustomer.interface_module.ActivityInterface;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;

/**
 * Created by root on 19/07/17.
 */

public class MainFragment  extends FragmentActivity implements
        ActivityInterface{
    private ServiceAdapter serviceAdapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsPagerAdapter mAdapter;
    private TabLayoutHelper mTabLayoutHelper;
    private String[] tabs = { "Home", "Order", "Profile" };
    private ImageView imgBack;
    private ImageView imgCart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceAdapter = new ServiceAdapter(this);
        startService();

        setContentView(R.layout.layout_main);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cart.class);
                startActivity(i);
            }
        });
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setAdapter(mAdapter);
        mTabLayoutHelper = new TabLayoutHelper(tabLayout, viewPager);
        mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        tabLayout.setTabTextColors(getResources().getColor(android.R.color.white), getResources().getColor(R.color.grey));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                viewPager.setAdapter(mAdapter);
//                viewPager.setCurrentItem(position, true);
//                mAdapter.notifyDataSetChanged();
//                viewPager.setAdapter(viewPager.getAdapter());
//                viewPager.setAdapter(mAdapter);
//                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setAdapter(mAdapter);
                viewPager.setCurrentItem(tab.getPosition());

            }
        });
        hideBack();
    }
//    getSupportFragmentManager().popBackStack();
    public void startService(){
        serviceAdapter.startService();
    }

    @Override
    public void showBack() {
        imgBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBack() {
        imgBack.setVisibility(View.GONE);
    }

    @Override
    public void clickBack(Fragment destination) {
        Intent intent = new Intent(this, MainFragment.class);
        startActivity(intent);
        finish();
    }
}

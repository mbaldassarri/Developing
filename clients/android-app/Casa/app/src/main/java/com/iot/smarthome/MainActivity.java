package com.iot.smarthome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.iot.smarthome.fragments.AlarmFragment;
import com.iot.smarthome.fragments.DevicesFragment;
import com.iot.smarthome.fragments.TempFragment;
import com.iot.smarthome.service.iot.HTTPRequests;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, HTTPRequests.OnStatusChanged {



    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    private DevicesFragment devicesFragment;
    private TempFragment tempFragment;
    private AlarmFragment alarmFragment;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        devicesFragment =new DevicesFragment();
        tempFragment = new TempFragment();
        alarmFragment = new AlarmFragment();
        adapter.addFragment(devicesFragment);
        adapter.addFragment(tempFragment);
        adapter.addFragment(alarmFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_devices:
                viewPager.setCurrentItem(0);
                break;
            case R.id.navigation_termo:
                viewPager.setCurrentItem(1);
                break;
            case R.id.navigation_alarm:
                viewPager.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        Log.d("page", "onPageSelected: "+ position);
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void statusChanged(String status) {
        this.devicesFragment.statusChanged(status);
    }
}

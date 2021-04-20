package com.example.sign_in_register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import our fragments to link to bottombar

import com.example.sign_in_register.fragments.signinFragment;
import com.example.sign_in_register.fragments.emergencyFragment;
import com.example.sign_in_register.fragments.personFragment;
import com.example.sign_in_register.fragments.settingFragment;

public class MainPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, View.OnLongClickListener {

    ViewPager viewPager;
    BottomNavigationView bottombar;
    CustomSoundPool custSoundPool;

    //  transfering data between fragments
    private String mTitle;
    public String getmTitle()
    {
        return mTitle;
    }
    public void setmTitle(String title)
    {
        this.mTitle = title;
    }


    int signInSound, emergencySound, profileSound, settingsSound; // Sound identifiers

    // create control object
    signinFragment signIn = new signinFragment();
    emergencyFragment emergency = new emergencyFragment();
    personFragment person = new personFragment();
    settingFragment setting = new settingFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainpage);

        //get our Navigation install
        init();
    }

    // init method goes here to set everything

    private void init() {

        // To get our main view object and set listener
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        bottombar = findViewById(R.id.bottombar);
        bottombar.setOnNavigationItemSelectedListener(this);
        bottombar.setItemIconTintList(null);

        // Set up SoundPool and load sounds
        setUpSoundPool();

        // Set up views to long click and play descriptions
        View signInV = findViewById(R.id.bottombar_timtable);
        View emergencyV = findViewById(R.id.bottombar_emergency);
        View profileV = findViewById(R.id.bottombar_profile);
        View settingsV = findViewById(R.id.bottombar_setting);
        signInV.setOnLongClickListener(this);
        emergencyV.setOnLongClickListener(this);
        profileV.setOnLongClickListener(this);
        settingsV.setOnLongClickListener(this);

        //page changes, the reason no braek in each case is the app should not shut down after change to other pages
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return signIn;
                    case 1:
                        return emergency;
                    case 2:
                        return person;
                    case 3:
                        return setting;
                }

                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }

    // we have no plan to achieve this so leave it blank
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        bottombar.getMenu().getItem(position).setChecked(true);
    }

    // we have no plan to achieve this so leave it blank
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        viewPager.setCurrentItem(item.getOrder());
        return true;
    }

    // Sets up the SoundPool and loads sounds
    public void setUpSoundPool() {
        custSoundPool = new CustomSoundPool();
        custSoundPool.setCon(this);
        custSoundPool.initialise();

        signInSound = custSoundPool.load(R.raw.sign_in);
        emergencySound = custSoundPool.load(R.raw.emergency);
        profileSound = custSoundPool.load(R.raw.profile);
        settingsSound = custSoundPool.load(R.raw.settings);
    }

    @Override
    public boolean onLongClick(View view) {
        switch(view.getId()) {
            case(R.id.bottombar_timtable): {
                custSoundPool.play(signInSound);
                break;
            }
            case(R.id.bottombar_emergency): {
                custSoundPool.play(emergencySound);
                break;
            }
            case(R.id.bottombar_profile): {
                custSoundPool.play(profileSound);
                break;
            }
            case(R.id.bottombar_setting): {
                custSoundPool.play(settingsSound);
                break;
            }
        }
        return true;
    }
}

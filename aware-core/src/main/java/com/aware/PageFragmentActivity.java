package com.aware;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.aware.PageFragment;
import com.aware.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by researcher on 19/10/15.
 */
public class PageFragmentActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 4;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private Context context;

    private JSONArray configs_study;

    private JSONArray plugins;

    private JSONArray sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aware_page_view);
        context = this;
        try {
            configs_study = new JSONArray(getIntent().getStringExtra("configs_study"));
        } catch (Exception e) {
            Toast.makeText(context, "Error getting the study", Toast.LENGTH_SHORT).show();
            configs_study = new JSONArray();
//            finish();
        }

        parse(configs_study);



        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        findViewById(R.id.background_clr).setBackgroundColor(getResources().getColor(R.color.green, getTheme()));
                        ((ImageView) findViewById(R.id.dot_1)).setImageDrawable(getResources().getDrawable(R.drawable.white_dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_2)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_3)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_4)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));

                        break;
                    case 1:
                        findViewById(R.id.background_clr).setBackgroundColor(getResources().getColor(R.color.blue,getTheme()));
                        ((ImageView) findViewById(R.id.dot_1)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_2)).setImageDrawable(getResources().getDrawable(R.drawable.white_dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_3)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_4)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        break;
                    case 2:
                        findViewById(R.id.background_clr).setBackgroundColor(getResources().getColor(R.color.orange,getTheme()));
                        ((ImageView) findViewById(R.id.dot_1)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_2)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_3)).setImageDrawable(getResources().getDrawable(R.drawable.white_dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_4)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        break;
                    case 3:
                        findViewById(R.id.background_clr).setBackgroundColor(getResources().getColor(R.color.coral,getTheme()));
                        ((ImageView) findViewById(R.id.dot_1)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_2)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_3)).setImageDrawable(getResources().getDrawable(R.drawable.dot, getTheme()));
                        ((ImageView) findViewById(R.id.dot_4)).setImageDrawable(getResources().getDrawable(R.drawable.white_dot, getTheme()));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mPagerAdapter);


    }

    @Override
    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private Context context;
        public ScreenSlidePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new PageFragment(R.layout.aware_study_setup, position, sensors);
                case 1:
                    return new PageFragment(R.layout.aware_study_setup, position, sensors);
                case 2:
                    return new PageFragment(R.layout.aware_study_setup, position, sensors);
                case 3:
                    return new PageFragment(R.layout.aware_study_setup, position, sensors);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private void parse( JSONArray configs) {

//        boolean is_developer = Aware.getSetting(context, Aware_Preferences.DEBUG_FLAG).equals("true");
//
//
//        if( is_developer ) Aware.setSetting(context, Aware_Preferences.DEBUG_FLAG, true);

        //Now apply the new settings
        plugins = new JSONArray();
        sensors = new JSONArray();

        for( int i = 0; i<configs.length(); i++ ) {
            try {
                JSONObject element = configs.getJSONObject(i);
                if( element.has("plugins") ) {
                    plugins = element.getJSONArray("plugins");
                }
                if( element.has("sensors")) {
                    sensors = element.getJSONArray("sensors");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}

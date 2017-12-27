package net.jemand771.fgpmms_client_android;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import net.jemand771.fgpmms_client_android.fragments.FirstFragment;
import net.jemand771.fgpmms_client_android.fragments.LoginFragment;
import net.jemand771.fgpmms_client_android.fragments.SecondFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnHeadlineSelectedListener {



    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        /*
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FirstFragment newf = new FirstFragment();
        ft.replace(R.id.container, newf);
        ft.commit();
  */
    }

    @Override
    public void cawoosh() {
        mSectionsPagerAdapter.setBam(true);
        mSectionsPagerAdapter.notifyDataSetChanged();
        Log.d("schnitzel", "this should reload (but it doesn't (HELL YEAH IT DOES))");
    }

    // Adapter Thingy to return Fragments
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        boolean bam = false;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setBam(boolean bam){
            this.bam = bam;
        }

        @Override
        public Fragment getItem(int position) {

            Log.d("schnitzel", "requesting fragment " + position);
            // gimmida right Fragment
            if(!bam) return new LoginFragment();
            if(position == 0){
                return new FirstFragment();
            }
            else{
                return new SecondFragment();
            }
        }

        @Override
        public int getCount() {
            // determines "end of swipes"
            return 2;
        }

        @Override
        public int getItemPosition(Object object) {
            // Causes adapter to reload all Fragments when
            // notifyDataSetChanged is called
            return POSITION_NONE;
        }
    }
}

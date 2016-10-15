package com.MET.salman.MoeslemExpenseTracker;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

public class NewTransaction extends AppCompatActivity {
    int x;
    DatabaseHandler db;

    String code;
    String date;
    int value;
    String Category;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");

        TextView textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setTypeface(tf);

        db = new DatabaseHandler(this);

        Bundle getPosition = getIntent().getExtras();
        x = getPosition.getInt("position");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        viewPager.setCurrentItem(x);
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "Expense", "Income"};

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }



        @Override
        public Fragment getItem(int position) {

            if(position == 0) {
                return NewTransactionExpenseFragment.newInstance(null,"E");
            }
            else if(position == 1)
            {
                return NewTransactionIncomeFragment.newInstance(null,"I");
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }


}

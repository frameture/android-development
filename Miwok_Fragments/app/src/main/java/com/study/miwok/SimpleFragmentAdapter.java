package com.study.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lenovo on 11-09-2016.
 */
public class SimpleFragmentAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = new String[]{ "Numbers", "Colors",
                                       "Family", "Phrases"};

    public SimpleFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new NumbersFragment();
            case 1:
                return new ColorsFragment();
            case 2:
                return new FamilyFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return new NumbersFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 4;
    }
}

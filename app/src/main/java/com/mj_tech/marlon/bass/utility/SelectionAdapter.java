package com.mj_tech.marlon.bass.utility;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Marlon on 6/9/2015.
 */
public class SelectionAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments;
    public SelectionAdapter(FragmentManager manager,List<Fragment> fragments){
        super(manager);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int index) {
        return fragments.get(index);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+"";
    }
}

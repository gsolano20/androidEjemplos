package com.uigitdev.materialtabswithmenu.tabs.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> tabFragments;
    private ArrayList<String> tabNames;

    public void addFragment(Fragment fragment, String name){
        this.tabFragments.add(fragment);
        this.tabNames.add(name);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        tabFragments = new ArrayList<>();
        tabNames = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}

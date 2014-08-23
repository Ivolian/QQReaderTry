package com.example.QQReader.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.QQReader.Fragment.Bookshelf.ChildFragment1;
import com.example.QQReader.Fragment.Bookshelf.ChildFragment2;


public class BookshelfFragmentPagerAdapter extends FragmentPagerAdapter {

    public BookshelfFragmentPagerAdapter(FragmentManager fragmentManager) {

        super(fragmentManager);
    }

    private final String[] TITLES = {"书架", "书讯"};

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {

        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ChildFragment1();
            case 1:
                return new ChildFragment2();
            default:
                throw new RuntimeException("position should be 0 or 1");
        }
    }

}
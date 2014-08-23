package com.example.QQReader.Fragment.Bookshelf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.QQReader.Activity.MainActivity;
import com.example.QQReader.Adapter.BookshelfFragmentPagerAdapter;
import com.example.QQReader.OpenSourceWidget.astuetz.PagerSlidingTabStrip;
import com.example.QQReader.R;
import com.slidingmenu.lib.SlidingMenu;


public class BookshelfFragment extends Fragment implements ViewPager.OnPageChangeListener,
        View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bookshelf_fragment, container, false);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new BookshelfFragmentPagerAdapter(getFragmentManager()));
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(this);

        rootView.findViewById(R.id.llMenu).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {


        ((MainActivity) getActivity()).getSlidingMenu().toggle();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position == 1) {
            ((MainActivity) getActivity()).changeSlidingMenuTouchMode(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            ((MainActivity) getActivity()).changeSlidingMenuTouchMode(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

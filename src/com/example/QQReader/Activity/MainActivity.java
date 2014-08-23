package com.example.QQReader.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.QQReader.Fragment.BookCityFragment;
import com.example.QQReader.Fragment.Bookshelf.BookshelfFragment;
import com.example.QQReader.Fragment.CategoryFragment;
import com.example.QQReader.Fragment.MeFragment;
import com.example.QQReader.R;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    // ============= colors =============

    int tabSelectedColor;

    int tabUnselectedColor;

    // ============= TabList & 当前选中项 =============

    List<LinearLayout> tabList;

    int positionSelected;

    // ============= Fragment TAGS =============

    final String TAG1 = "TAG1";

    final String TAG2 = "TAG2";

    final String TAG3 = "TAG3";

    final String TAG4 = "TAG4";

    // ============= SlidingMenu =============

    private SlidingMenu slidingMenu;

    private int TOUCH_MODE;
    // ============= Lifecycle =============

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initColor();
        initTabList();

        if (savedInstanceState != null) {
            positionSelected = savedInstanceState.getInt("positionSelected");
        } else {
            // 默认选中第一项
            positionSelected = 0;
        }
        switchTab(positionSelected);
        switchFragment(positionSelected);

        initSlidingMenu();

        FragmentManager.enableDebugLogging(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putInt("positionSelected", positionSelected);
    }

    // ============= onClick =============

    @Override
    public void onClick(View v) {

        for (int i = 0; i != 4; i++) {
            LinearLayout tab = tabList.get(i);
            if (tab == v) {
                // 第i项被选中，而不为当前选中项
                if (i != positionSelected) {
                    switchTab(i);
                    switchFragment(i);
                    positionSelected = i;
                    if (i != 0) {
                        disableSlidingMenu();
                    } else {
                        enableSlidingMenu();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
            return;
        }

        super.onBackPressed();
    }

    private void switchTab(int position) {

        // 去除当前的选中状态
        LinearLayout tabSelected = tabList.get(positionSelected);
        ((ImageView) tabSelected.getChildAt(0)).setImageResource(getTabImageResource(positionSelected, false));
        ((TextView) tabSelected.getChildAt(1)).setTextColor(tabUnselectedColor);

        // 选中新的Tab项
        LinearLayout tab = tabList.get(position);
        ((ImageView) tab.getChildAt(0)).setImageResource(getTabImageResource(position, true));
        ((TextView) tab.getChildAt(1)).setTextColor(tabSelectedColor);
    }

    private void switchFragment(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 隐藏当前显示的Fragment
        String tagOfFragmentSelected = getTagByPosition(positionSelected);
        Fragment fragmentSelected = fragmentManager.findFragmentByTag(tagOfFragmentSelected);
        if (fragmentSelected != null) {
            fragmentTransaction.hide(fragmentSelected);
        }

        // 显示或添加新的Fragment
        String tag = getTagByPosition(position);
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragmentTransaction.add(R.id.llFragmentContainer, getNewFragmentByTag(tag), tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();
    }

    private void initTabList() {

        tabList = new ArrayList<>();
        tabList.add((LinearLayout) findViewById(R.id.llBookshelf));
        tabList.add((LinearLayout) findViewById(R.id.llBookCity));
        tabList.add((LinearLayout) findViewById(R.id.llCategory));
        tabList.add((LinearLayout) findViewById(R.id.llMe));

        for (LinearLayout tab : tabList) {
            tab.setOnClickListener(this);
        }
    }

    private Fragment getNewFragmentByTag(String tag) {

        switch (tag) {
            case TAG1:
                return new BookshelfFragment();
            case TAG2:
                return new BookCityFragment();
            case TAG3:
                return new CategoryFragment();
            case TAG4:
                return new MeFragment();
            default:
                throw new RuntimeException("position should be in 0~3");
        }
    }

    private String getTagByPosition(int position) {

        switch (position) {
            case 0:
                return TAG1;
            case 1:
                return TAG2;
            case 2:
                return TAG3;
            case 3:
                return TAG4;
            default:
                throw new RuntimeException("position should be in 0~3");
        }
    }

    // 根据位置和是否选中，返回Tab的背景。
    private int getTabImageResource(final int position, final boolean selected) {

        switch (position) {
            case 0:
                return selected ? R.drawable.maintab_bookstand_icon_hover : R.drawable.maintab_bookstand_icon;
            case 1:
                return selected ? R.drawable.maintab_city_icon_hover : R.drawable.maintab_city_icon;
            case 2:
                return selected ? R.drawable.maintab_category_icon_hover : R.drawable.maintab_category_icon;
            case 3:
                return selected ? R.drawable.maintab_profile_icon_hover : R.drawable.maintab_profile_icon;
            default:
                throw new RuntimeException("position must be in 0~3");
        }
    }

    // 无关紧要的方法，getResources 好像需要在 onCreate 之后才能调用。
    private void initColor() {

        Resources resources = getResources();
        tabSelectedColor = resources.getColor(R.color.tab_selected);
        tabUnselectedColor = resources.getColor(R.color.tab_unselected);
    }

    private void initSlidingMenu() {

        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        TOUCH_MODE = SlidingMenu.TOUCHMODE_NONE;

        slidingMenu.setShadowDrawable(R.drawable.sidebar_shadow);
        slidingMenu.setBehindOffset(200);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        slidingMenu.setMenu(R.layout.my_sliding_menu);

        slidingMenu.findViewById(R.id.llGroupSearch).setOnClickListener(new View.OnClickListener() {

                                                                            @Override
                                                                            public void onClick(View v) {

                                                                                Intent intent = new Intent(MainActivity.this, GroupSearchActivity.class);
                                                                                startActivity(intent);
                                                                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                                            }
                                                                        }
        );
    }

    public void changeSlidingMenuTouchMode(int touchMode) {

        slidingMenu.setTouchModeAbove(touchMode);
        TOUCH_MODE = touchMode;
    }

    // 离开书架时禁用 SlidingMenu
    private void disableSlidingMenu() {

        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    }

    // 回到书架时恢复原有 TouchMode
    private void enableSlidingMenu() {

        slidingMenu.setTouchModeAbove(TOUCH_MODE);
    }

    public SlidingMenu getSlidingMenu() {

        return slidingMenu;
    }

}

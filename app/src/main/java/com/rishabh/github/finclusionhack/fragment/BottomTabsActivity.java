package com.rishabh.github.finclusionhack.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ncapdevi.fragnav.FragNavController;
import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

public class BottomTabsActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation {
    private BottomBar mBottomBar;
    private FragNavController mNavController;

    //Better convention to properly name the indices what they are in your app
    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_PROFILE = FragNavController.TAB2;
    private final int INDEX_CHAT = FragNavController.TAB3;
    private final int INDEX_NEARBY = FragNavController.TAB4;
    private final int INDEX_FAVORITES = FragNavController.TAB5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tabs);

        List<Fragment> fragments = new ArrayList<>(5);

        fragments.add(HomeFragment.newInstance(0));
        fragments.add(ProfileFragment.newInstance(0));
        fragments.add(ChatFragment.newInstance(0));
        fragments.add(NearbyFragment.newInstance(0));
        fragments.add(FavoriteFragment.newInstance(0));

        mNavController =
                new FragNavController( getSupportFragmentManager(), R.id.container, fragments);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_bottombar, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bb_menu_home:
                        mNavController.switchTab(INDEX_HOME);
                        break;
                    case R.id.bb_menu_profile:
                        mNavController.switchTab(INDEX_PROFILE);
                        break;
                    case R.id.bb_menu_chat:
                        mNavController.switchTab(INDEX_CHAT);
                        break;
                    case R.id.bb_menu_nearby:
                        mNavController.switchTab(INDEX_NEARBY);
                        break;
                    case R.id.bb_menu_favorites:
                        mNavController.switchTab(INDEX_FAVORITES);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                mNavController.clearStack();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mNavController.getCurrentStack().size() > 1) {
            mNavController.pop();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void pushFragment(Fragment fragment) {
        mNavController.push(fragment);
    }
}




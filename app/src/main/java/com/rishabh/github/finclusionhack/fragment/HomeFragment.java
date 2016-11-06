package com.rishabh.github.finclusionhack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rishabh.github.finclusionhack.BaseFragment;

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mFragmentNavigation != null) {
//                    mFragmentNavigation.pushFragment(HomeFragment.newInstance(mInt+1));
//                }
//            }
//        });
//        mButton.setText(getClass().getSimpleName() + " " + mInt);
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }



}

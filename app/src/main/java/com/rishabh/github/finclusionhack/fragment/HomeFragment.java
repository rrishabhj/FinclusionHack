package com.rishabh.github.finclusionhack.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_main,container,false);
        return view;
    }


}

package com.rishabh.github.finclusionhack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.activities.ATMsInfoActivity;
import com.rishabh.github.finclusionhack.activities.BranchesInfoActivity;

public class NearbyFragment extends BaseFragment {

    public static NearbyFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        NearbyFragment fragment = new NearbyFragment();

        fragment.setArguments(args);
        return fragment;
    }


//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mFragmentNavigation != null) {
//                    mFragmentNavigation.pushFragment(NearbyFragment.newInstance(mInt+1));
//                }
//            }
//        });
//        mButton.setText(getClass().getSimpleName() + " " + mInt);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_nearby,container,false);

        view.findViewById(R.id.get_branches_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(getContext(), BranchesInfoActivity.class);
                        getContext().startActivity(intentToLaunch);
                    }
                });

        //get atms button
        view.findViewById(R.id.get_atms_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(getContext(), ATMsInfoActivity.class);
                        getContext().startActivity(intentToLaunch);
                    }
                });

        return view;
    }

}

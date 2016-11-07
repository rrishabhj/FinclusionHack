package com.rishabh.github.finclusionhack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.activities.ATMsInfoActivity;
import com.rishabh.github.finclusionhack.activities.BranchesInfoActivity;

public class NearbyFragment extends BaseFragment {

    CardView cv_branch,cv_atm ;

    public static NearbyFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        NearbyFragment fragment = new NearbyFragment();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onStart() {
        super.onStart();

        cv_branch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(getContext(), BranchesInfoActivity.class);
                        getContext().startActivity(intentToLaunch);
                    }
                });

        //get atms button
        cv_atm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToLaunch = new Intent(getContext(), ATMsInfoActivity.class);
                        getContext().startActivity(intentToLaunch);
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_nearby,container,false);
            cv_branch= (CardView) view.findViewById(R.id.cv_branches);
            cv_atm = (CardView) view.findViewById(R.id.cv_atm);
        return view;
    }
}

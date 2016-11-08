package com.rishabh.github.finclusionhack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rishabh.github.finclusionhack.Apy;
import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.Pmjdy;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.home.FundTransferActivity;
import com.rishabh.github.finclusionhack.home.ImtActivity;

public class HomeFragment extends BaseFragment {


    Button fundTransfer,imt;
    CardView apy,pmjdy;
    public static HomeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        fundTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intenttoLaunch=new Intent(getActivity(), FundTransferActivity.class);
                getContext().startActivity(intenttoLaunch);
            }
        });

        imt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenttoLaunch=new Intent(getActivity(), ImtActivity.class);
                getContext().startActivity(intenttoLaunch);
            }
        });

        apy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentToLaunch=new Intent(getContext(), Apy.class);
                getContext().startActivity(intentToLaunch);
            }
        });

        pmjdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentToLaunch=new Intent(getContext(), Pmjdy.class);
                getContext().startActivity(intentToLaunch);

            }
        });

//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mFragmentNavigation != null) {
//                    mFragmentNavigation.pushFragment(HomeFragment.newInstance(mInt+1));
//                }
//            }
//        });
//        mButton.setText(getClass().getSimpleName() + " " + mInt);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_main,container,false);
        fundTransfer= (Button) view.findViewById(R.id.btn_fund_transfer);
        imt= (Button) view.findViewById(R.id.btn_imt);
        apy= (CardView) view.findViewById(R.id.cv_apy);
        pmjdy= (CardView) view.findViewById(R.id.cv_pmjdy);

        return view;
    }


}

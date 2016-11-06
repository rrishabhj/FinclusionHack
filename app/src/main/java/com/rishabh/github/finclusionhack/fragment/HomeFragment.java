package com.rishabh.github.finclusionhack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.home.FundTransferActivity;
import com.rishabh.github.finclusionhack.home.ImtActivity;

public class HomeFragment extends BaseFragment {


    Button fundTransfer,imt;
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
        return view;
    }


}

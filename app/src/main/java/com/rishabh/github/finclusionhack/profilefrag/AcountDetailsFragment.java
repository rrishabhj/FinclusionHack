package com.rishabh.github.finclusionhack.profilefrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;

/**
 * Created by rishabh on 4/11/16.
 */

public class AcountDetailsFragment extends BaseFragment {

    public static AcountDetailsFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AcountDetailsFragment  fragment = new AcountDetailsFragment ();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (accDetail != null) {
//            accDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mFragmentNavigation != null) {
//                        mFragmentNavigation.pushFragment(AcountDetailsFragment.newInstance(0));
//                    }
//                }
//            });
//            // test.setText(getClass().getSimpleName() + " " + mInt);
//        }else if (transactionDetail!=null){
//            transactionDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mFragmentNavigation!=null){
//                        // mFragmentNavigation.pushFragment(awfaf);
//                    }
//                }
//            });
//        }
//
//    }
//
//    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_acc_detail,container,false);
        return view;
    }
}

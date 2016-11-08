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
import com.rishabh.github.finclusionhack.profile.AcountDetailsFragment;
import com.rishabh.github.finclusionhack.profile.TransactionHistoryActivity;

public class ProfileFragment extends BaseFragment {

    CardView accDetail,transactionDetail;

    public static ProfileFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (accDetail != null) {
//            accDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mFragmentNavigation != null) {
//                        mFragmentNavigation.pushFragment(AcountDetailsFragment.newInstance(0));
//                    }
//                }
//            });
//           // test.setText(getClass().getSimpleName() + " " + mInt);
//        }else if (transactionDetail!=null){
//            transactionDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mFragmentNavigation!=null){
//                       // mFragmentNavigation.pushFragment(awfaf);
//                        Intent intent=new Intent(getActivity(), TransactionHistoryActivity.class);
//                        startActivity(intent);
//                    }
//
//                }
//            });
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_main,container,false);
        //test= (Button) view.findViewById(R.id.btn_profile_main);
        accDetail= (CardView) view.findViewById(R.id.cv_acc_detail);
        transactionDetail= (CardView) view.findViewById(R.id.cv_transaction_detail);

        accDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(AcountDetailsFragment.newInstance(0));
                }
            }
        });

        transactionDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragmentNavigation!=null){
                    // mFragmentNavigation.pushFragment(awfaf);
                    Intent intent=new Intent(getActivity(), TransactionHistoryActivity.class);
                    startActivity(intent);
                }

            }
        });
        return view;
    }
}

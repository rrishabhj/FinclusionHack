package com.rishabh.github.finclusionhack.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;

/**
 * Created by rishabh on 4/11/16.
 */

public class AcountDetailsFragment extends BaseFragment {

    ListView mListView;
    CardView mCardView;

    public static AcountDetailsFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AcountDetailsFragment  fragment = new AcountDetailsFragment ();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        Intent accInfo=new Intent(getContext(),AccountInfoActivity.class);
                        startActivity(accInfo);
                        break;

                    case 1:

                        break;
                    case 2:break;
                    default: break;
                }
            }


        });

        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToLaunch=new Intent(getActivity(),SignOut.class);
                startActivity(intentToLaunch);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_acc_detail,container,false);
        mListView= (ListView) view.findViewById(R.id.lv_acc_detail);
        mCardView= (CardView) view.findViewById(R.id.cv_signout);
        return view;
    }
}

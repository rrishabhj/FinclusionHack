package com.rishabh.github.finclusionhack;

import android.os.Bundle;
import android.view.View;

public class ProfileFragment extends BaseFragment {

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
        if (mButton != null) {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(ProfileFragment.newInstance(mInt+1));
                    }
                }
            });
            mButton.setText(getClass().getSimpleName() + " " + mInt);
        }

    }
}

package com.rishabh.github.finclusionhack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TestFragment extends Fragment {


    private static final String ARGS_TEST_FRAGMENT = "com.github.rishabh.TestFragment";

    public static TestFragment newInstance(int instance){

        Bundle info=new Bundle();
        info.putInt(ARGS_TEST_FRAGMENT,instance);
        TestFragment fragment=new TestFragment();
        fragment.setArguments(info);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.test_fragment,container,false);
        return view;
    }
}

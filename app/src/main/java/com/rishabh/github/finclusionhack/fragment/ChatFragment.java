package com.rishabh.github.finclusionhack.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rishabh.github.finclusionhack.BaseFragment;
import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.apiai.AIDialogSampleActivity;
import com.rishabh.github.finclusionhack.apiai.RecyclerVIewAdaptor;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

import static com.rishabh.github.finclusionhack.apiai.Config.ACCESS_TOKEN;

public class ChatFragment extends BaseFragment {

    private static final String TAG = AIDialogSampleActivity.class.getName();

    RecyclerView messages;
    private RecyclerVIewAdaptor adapter;
    List<String> data;
    Button sendbtn;
    private EditText et_textRequest;

    public static ChatFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        ChatFragment fragment = new ChatFragment();
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
//                    mFragmentNavigation.pushFragment(ChatFragment.newInstance(mInt+1));
//                }
//            }
//        });
//        mButton.setText(getClass().getSimpleName() + " " + mInt);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
//        Intent intentToLaunch=new Intent(getContext(), AIDialogSampleActivity.class);
//        getContext().startActivity(intentToLaunch);

        sendbtn = (Button) view.findViewById(R.id.btn_send);

        et_textRequest = (EditText) view.findViewById(R.id.messageEditText);

        messages = (RecyclerView) view.findViewById(R.id.messageRV);
        data = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        messages.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerVIewAdaptor(data);
        messages.setAdapter(adapter);

        final AIConfiguration config = new AIConfiguration(ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(getContext(), config);

        final AIRequest aiRequest = new AIRequest();

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageRequest = et_textRequest.getText().toString();
                data.add("Me: " + messageRequest);
                adapter.notifyDataSetChanged();

                aiRequest.setQuery(messageRequest);


                new AsyncTask<AIRequest, Void, AIResponse>() {
                    @Override
                    protected AIResponse doInBackground(AIRequest... requests) {
                        final AIRequest request = requests[0];
                        try {
                            final AIResponse response = aiDataService.request(aiRequest);
                            return response;
                        } catch (AIServiceException e) {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(AIResponse aiResponse) {
                        if (aiResponse != null) {
                            String str = aiResponse.getResult().getFulfillment().getSpeech();

                            data.add("Yoda: " + str);
                            adapter.notifyDataSetChanged();
                            Log.i("tag", str);
                            // process aiResponse here
                        }
                    }
                }.execute(aiRequest);

            }
        });

        return view;
    }
}

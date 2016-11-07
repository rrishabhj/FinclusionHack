package com.rishabh.github.finclusionhack.apiai;

/***********************************************************************************************************************
 *
 * API.AI Android SDK -  API.AI libraries usage example
 * =================================================
 *
 * Copyright (C) 2015 by Speaktoit, Inc. (https://www.speaktoit.com)
 * https://www.api.ai
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

import static com.rishabh.github.finclusionhack.apiai.Config.ACCESS_TOKEN;


public class AIDialogSampleActivity extends BaseActivity {

    private static final String TAG = AIDialogSampleActivity.class.getName();



    RecyclerView messages;
    private RecyclerVIewAdaptor adapter;
    List<String> data;
    Button sendbtn;
    private EditText et_textRequest;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        sendbtn = (Button) findViewById(R.id.btn_send);

        et_textRequest = (EditText) findViewById(R.id.messageEditText);

        messages = (RecyclerView) findViewById(R.id.messageRV);
        data = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        messages.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerVIewAdaptor(data);
        messages.setAdapter(adapter);

        final AIConfiguration config = new AIConfiguration(ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(getApplicationContext(), config);

        final AIRequest aiRequest = new AIRequest();


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageRequest = et_textRequest.getText().toString();
                data.add("Me:" + messageRequest);
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

                            data.add("Yoda:" + str);
                            adapter.notifyDataSetChanged();
                            Log.i("tag", str);
                            // process aiResponse here
                        }
                    }
                }.execute(aiRequest);

            }
        });
    }
}

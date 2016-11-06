package com.rishabh.github.finclusionhack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rishabh.github.finclusionhack.fragment.BottomTabsActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnBottomTabs = (Button) findViewById(R.id.btnBottomTabs);
        if (btnBottomTabs != null) {
            btnBottomTabs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, BottomTabsActivity.class));
                }
            });
        }

    }
}
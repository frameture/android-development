package com.study.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Miwok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int[] viewIds = new int[]{R.id.colors, R.id.family, R.id.numbers,
                R.id.phrases};
        for(int i : viewIds){
            initializeViews(i);
        }
    }


    private void initializeViews(final int id){
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch(id) {
                    case R.id.colors:
                        intent = new Intent(getApplicationContext(), ColorsActivity.class);
                        break;
                    case R.id.family:
                        intent = new Intent(getApplicationContext(), FamilyActivity.class);
                        break;
                    case R.id.numbers:
                        intent = new Intent(getApplicationContext(), NumbersActivity.class);
                        break;
                    case R.id.phrases:
                        intent = new Intent(getApplicationContext(), PhrasesActivity.class);
                        break;
                    default:
                        Log.e(TAG, "Default case occured!");
                }
                if(intent != null && intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
            }
        });

    }
}

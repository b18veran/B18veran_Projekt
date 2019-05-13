package com.example.b18veran_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GymnastDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gymnast);
        Intent intent = getIntent();
        String Mountain = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView namn = findViewById(R.id.textView);
        namn.setText(Mountain);
    }
}
package com.example.b18veran_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GymnastDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Övningar");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gymnast);
        Intent intent = getIntent();
        String Gymnaster = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String Gymnaster2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        TextView namn = findViewById(R.id.textView);
        TextView namn2 = findViewById(R.id.textView2);
        namn.setText(Gymnaster);
        namn2.setText(Gymnaster2);
    }
}
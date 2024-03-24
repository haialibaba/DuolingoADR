package com.example.duolingoapp.taikhoan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.duolingoapp.MainActivity;
import com.example.duolingoapp.R;

public class RankingActivity extends AppCompatActivity {

    private TextView backRanking;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        backRanking = findViewById(R.id.txtBack_Ranking);

        backRanking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
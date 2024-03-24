package com.example.duolingoapp.tudien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.duolingoapp.MainActivity;
import com.example.duolingoapp.R;
import com.example.duolingoapp.bocauhoi.QuestionListActivity;

public class DictionaryActivity extends AppCompatActivity {

    private Button home, screenCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);

        home = findViewById(R.id.btnMain);
        screenCurrent = findViewById(R.id.btnListCourse_Subject);

        // Nhận extra từ Intent
        String nameScreen = getIntent().getStringExtra("screenCurrent");
        screenCurrent.setText(nameScreen);

        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DictionaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        screenCurrent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DictionaryActivity.this, QuestionListActivity.class);
                intent.putExtra("screenCurrent", nameScreen);
                startActivity(intent);
            }
        });
    }
}
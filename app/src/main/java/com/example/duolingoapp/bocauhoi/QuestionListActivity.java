package com.example.duolingoapp.bocauhoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.duolingoapp.MainActivity;
import com.example.duolingoapp.R;
import com.example.duolingoapp.dienkhuyet.FillBlanksActivity;
import com.example.duolingoapp.hoctuvung.VocabularyActivity;
import com.example.duolingoapp.tudien.DictionaryActivity;
import com.example.duolingoapp.ui.home.Database;
import com.example.duolingoapp.luyennghe.ListeningActivity;
import com.example.duolingoapp.sapxepcau.ArrangeSentencesActivity;
import com.example.duolingoapp.tracnghiem.QuizActivity;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ArrayList<QuestionList> boCauHois;
    QuestionListAdapter adapter;
    RecyclerView bocauhois;

    int idbo;
    private Button home, screenCurrent, searchDictionary;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_subject);

        home = findViewById(R.id.btnMain);
        screenCurrent = findViewById(R.id.btnListCourse_Subject);
        searchDictionary = findViewById(R.id.btnSearchDictionary);
        bocauhois = findViewById(R.id.recyclerViewListSubject);

        boCauHois = new ArrayList<>();
        AddArrayQuestion();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bocauhois.setLayoutManager(layoutManager);
        adapter = new QuestionListAdapter(this, R.layout.list_item_subject, boCauHois);
        bocauhois.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        // Nhận extra từ Intent
        String nameScreen = getIntent().getStringExtra("screenCurrent");
        screenCurrent.setText(nameScreen);

        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        searchDictionary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, DictionaryActivity.class);
                intent.putExtra("screenCurrent", nameScreen);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new QuestionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý sự kiện click ở đây
                idbo = boCauHois.get(position).getIdBo();
                Intent intent = null;
                switch (nameScreen){
                    case "Học từ vựng":
                        intent = new Intent(QuestionListActivity.this, VocabularyActivity.class);
                        break;
                    case "Điền khuyết":
                        intent = new Intent(QuestionListActivity.this, FillBlanksActivity.class);
                        break;
                    case "Trắc nghiệm":
                        intent = new Intent(QuestionListActivity.this, QuizActivity.class);
                        break;
                    case "Luyện nghe":
                        intent = new Intent(QuestionListActivity.this, ListeningActivity.class);
                        break;
                    case "Sắp xếp câu":
                        intent = new Intent(QuestionListActivity.this, ArrangeSentencesActivity.class);
                        break;
                }
                intent.putExtra("idbo", idbo);
                startActivity(intent);
            }
        });

    }

    private void AddArrayQuestion(){
        database = Database.initDatabase(QuestionListActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi",null);
        boCauHois.clear();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo_ENG = cursor.getString(2);
            String tenbo_VIE = cursor.getString(3);
            byte[] img = cursor.getBlob(4);

            boCauHois.add(new QuestionList(idbo,stt,tenbo_ENG, tenbo_VIE, img));
        }

    }
}
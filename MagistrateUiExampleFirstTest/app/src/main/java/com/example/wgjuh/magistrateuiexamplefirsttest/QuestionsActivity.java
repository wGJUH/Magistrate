package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class QuestionsActivity extends AppCompatActivity {
    ListView questionsList;
    QuestionAdapter questionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questionsList = (ListView)findViewById(R.id.list_questions);
    }
}

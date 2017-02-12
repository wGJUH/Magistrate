package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 11.02.2017.
 */
public class QuestionAdapter extends ArrayAdapter {
    ArrayList<String> questions;
    public QuestionAdapter(Context context, ArrayList<String> questions) {
        super(context, R.layout.single_question_item);
        this.questions = questions;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.single_question_item, null);
        }
        ((TextView)convertView.findViewById(R.id.single_question)).setText("book number: "+ questions.get(position));
        return convertView;
    }
}

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

public class ArticlesAdapter extends ArrayAdapter<String> {
    ArrayList<String> articles;
    public ArticlesAdapter(Context context, ArrayList<String> articles) {
        super(context, R.layout.single_question_item);
        this.articles = articles;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.single_question_item, null);
        }
        //((ImageView)convertView.findViewById(R.id.library_single_image)).setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.help));
        ((TextView)convertView.findViewById(R.id.single_question)).setText("book number: "+ articles.get(position));
        return convertView;
    }
}

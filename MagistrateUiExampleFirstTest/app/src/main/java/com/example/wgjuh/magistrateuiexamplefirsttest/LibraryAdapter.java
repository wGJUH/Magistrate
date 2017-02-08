package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 08.02.2017.
 */

public class LibraryAdapter extends ArrayAdapter  {
    ArrayList<Integer>ids = new ArrayList<>();
    public LibraryAdapter(Context context, ArrayList<Integer> ids) {
        super(context, R.layout.library_single_element, ids);
        this.ids = ids;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.library_single_element, null);
        }
        //((ImageView)convertView.findViewById(R.id.library_single_image)).setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.help));
        ((TextView)convertView.findViewById(R.id.library_single_article)).setText("book number: "+ ids.get(position));
        return convertView;
    }

}

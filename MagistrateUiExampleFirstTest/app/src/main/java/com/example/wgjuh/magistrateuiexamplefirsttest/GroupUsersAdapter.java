package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wGJUH on 21.01.2017.
 */

public class GroupUsersAdapter extends ArrayAdapter {

    Context context;
    ArrayList<String> names;
    public GroupUsersAdapter(Context context, ArrayList<String> names) {
        super(context, R.layout.users_list_item);
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.users_list_item, null);
        } else {
            view =  convertView;
        }
        ((TextView)view.findViewById(R.id.name_field_text)).setText(names.get(position));
        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return names.get(position);
    }
}

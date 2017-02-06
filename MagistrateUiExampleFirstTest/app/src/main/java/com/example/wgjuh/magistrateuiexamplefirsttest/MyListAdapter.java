package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by wGJUH on 20.01.2017.
 */

public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Group> groups;
    public MyListAdapter(Context context, ArrayList<Group> groups){
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.single_group_list_item, null);
        } else {
            view =  convertView;
        }

        TextView text1 = (TextView)view.findViewById(R.id.mtext1);
        TextView text2 = (TextView)view.findViewById(R.id.mtext2);
        Log.d(SqlWorker.LOG_TAG,"REsource 1 : " + groups.get(position).getGroupId());
        text1.setText(groups.get(position).getGroupName());
        text2.setText(groups.get(position).getAdministrator());
        return view;
    }
}

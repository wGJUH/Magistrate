package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wGJUH on 21.01.2017.
 */

public class MyUsersAdapter extends ArrayAdapter {
    Context context;
    ArrayList<User> users;

    /*@NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<User>users = new ArrayList<>();
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }*/

    ArrayList<Integer> checked = new ArrayList<>();
    public MyUsersAdapter(Context context, ArrayList<User> users){
        super(context,R.layout.single_users_list_item);
        this.context = context;
        this.users = users;
    }

    public ArrayList<Integer> getChecked() {
        return checked;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.single_users_list_item, null);
        } else {
            view =  convertView;
        }
        TextView text1 = (TextView)view.findViewById(R.id.user_name);
        TextView text2 = (TextView)view.findViewById(R.id.user_email);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.user_check_box);
        Log.d(SqlWorker.LOG_TAG,"REsource 1 : " + users.get(position).getId());
        text1.setText(users.get(position).getName());
        text2.setText(users.get(position).getEmail());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked.add(position);

            }
        });
        return view;
    }
}

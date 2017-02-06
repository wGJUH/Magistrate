package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SingleGroupActivity extends AppCompatActivity {
    SqlWorker sqlWorker;
    int groupId;
    ListView listView;
    ArrayList<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);
        sqlWorker = SqlWorker.getInstance(this);
        groupId = getIntent().getIntExtra("GROUP_ID",-7733);
        names = getNames();
        listView = (ListView)findViewById(R.id.group_users);
        ArrayAdapter<String> stringArrayAdapter = new GroupUsersAdapter(this,names);
        listView.setAdapter(stringArrayAdapter);
    }
    private ArrayList<String> getNames(){
        return sqlWorker.getUsersFromGroup(groupId);
    }
}

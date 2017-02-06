package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class GroupActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listView;
    SqlWorker sqlWorker;
    int adminId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        sqlWorker = SqlWorker.getInstance(this);
        if(getIntent() != null)
            adminId = getIntent().getIntExtra("ACCOUNT_ID",-7733);
        listView = (ListView)findViewById(R.id.list_groups);
        BaseAdapter mapArrayAdapter = new MyListAdapter(this,getGroups());
        listView.setAdapter(mapArrayAdapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_group) {
            Intent intent = new Intent(this,GroupCreatActivity.class);
            intent.putExtra("ACCOUNT_ID",adminId);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private ArrayList<Group> getGroups(){
        return sqlWorker.getGroupsForAdminId(adminId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view.findViewById(R.id.mtext1);
        Intent intent = new Intent(this,SingleGroupActivity.class);
        intent.putExtra("ACCOUNT_ID",adminId);
        intent.putExtra("GROUP_ID",sqlWorker.getGroupId(textView.getText().toString(),adminId));
        startActivity(intent);
        Log.d(SqlWorker.LOG_TAG,"Pressed on :" + sqlWorker.getGroupId(textView.getText().toString(),adminId));
    }
}

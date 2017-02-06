package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupCreatActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    ArrayList<User> users = new ArrayList<>();
    EditText searchField;
    EditText newGroupName;
    Button button_accept;
    mTextWathcer mTextWathcer;
    SqlWorker sqlWorker;
    ArrayAdapter<User> userArrayAdapter;
    int adminId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creat);
        sqlWorker = SqlWorker.getInstance(this);
        newGroupName = (EditText)findViewById(R.id.group_name_field);
        searchField = (EditText)findViewById(R.id.search_list);
        button_accept = (Button)findViewById(R.id.button_accept_group_creation);
        button_accept.setOnClickListener(this);
        updateUsersArray();
        userArrayAdapter = new MyUsersAdapter(this, users);
        listView = (ListView)findViewById(R.id.users_list);
        listView.setAdapter(userArrayAdapter);
        mTextWathcer = new mTextWathcer(this,userArrayAdapter);
        if(getIntent() != null)
            adminId = getIntent().getIntExtra("ACCOUNT_ID",-7733);
    }
    private void  updateUsersArray(){
        users = sqlWorker.getAllUsers();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_accept_group_creation:
                sqlWorker.addNewGroup(newGroupName.getText().toString(),((MyUsersAdapter)userArrayAdapter).getChecked().toArray(),adminId);
                finish();
                break;
            default:
                break;
        }
    }
}
class mTextWathcer implements TextWatcher{

    ArrayAdapter<User> userArrayAdapter;
    Context context;
    public mTextWathcer(Context context, ArrayAdapter<User> userArrayAdapter){
        this.userArrayAdapter = userArrayAdapter;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        userArrayAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
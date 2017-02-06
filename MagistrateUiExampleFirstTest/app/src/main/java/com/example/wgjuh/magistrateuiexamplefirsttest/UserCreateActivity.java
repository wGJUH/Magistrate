package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserCreateActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editText;
    Button button;
    SqlWorker sqlWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);
        editText = (EditText)findViewById(R.id.new_user_edit_text);
        button = (Button)findViewById(R.id.button_accept_user_creation);
        button.setOnClickListener(this);
        sqlWorker = SqlWorker.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_accept_user_creation:
                Log.d(SqlWorker.LOG_TAG,""+sqlWorker.addNewUser(getEmail(),getPassword(),isAdmin(),getUserName()));
                setResult(1);
                Intent intent = new Intent();
                intent.putExtra("EMAIL", getEmail());
                intent.putExtra("PASSWORD", getPassword());
                setResult(1,intent);
                finish();
                break;
            default:
                break;
        }
    }
    private String getEmail(){
        return getIntent().getStringExtra("EMAIL");
    }
    private String getPassword(){
        return  getIntent().getStringExtra("PASSWORD");
    }
    private int  isAdmin(){
        return  getIntent().getIntExtra("IS_ADMIN",-7733);
    }
    private String getUserName(){
        return  editText.getText().toString();

    }
}

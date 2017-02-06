package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    EditText password;
    Button acceptButton;
    SqlWorker sqlWorker;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        password = (EditText)findViewById(R.id.settings_password_edit);
        acceptButton = (Button)findViewById(R.id.settings_button_password_update);
        sqlWorker = SqlWorker.getInstance(this);
        if (getIntent() != null) {
         user_id = getIntent().getIntExtra("ACCOUNT_ID",-7733);
        }
        setOnclickListner();
    }
    private void setOnclickListner(){
        password.setOnClickListener(this);
        acceptButton.setOnClickListener(this);
    }

    private int getUserId(){
        return user_id;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_button_password_update:
                Log.d(SqlWorker.LOG_TAG, ""+sqlWorker.updateUserPassword(password.getText().toString(),""+user_id));
                finish();
                break;
            default:
                break;
        }
    }
}

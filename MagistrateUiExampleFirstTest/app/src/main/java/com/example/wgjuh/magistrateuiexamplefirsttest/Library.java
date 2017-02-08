package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08.02.2017.
 */

public class Library extends Activity{
    private ListView booksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        ArrayList<Integer> ids  = new ArrayList<>();
        booksList = (ListView)findViewById(R.id.library_list);
        for(int i = 0; i < 100; i++)
            ids.add(i);
        ArrayAdapter<String> stringArrayAdapter = new LibraryAdapter(this,ids);
        booksList.setAdapter(stringArrayAdapter);
        // TODO: 08.02.2017 доделать открывание pdf Файлов в окне чтения 
        booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(UserActivity.TAG,"clicked on: " + position);
                
            }
        });
    }
    private void openPdf(String filename){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ filename);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}

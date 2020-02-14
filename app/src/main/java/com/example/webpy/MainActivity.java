package com.example.webpy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    ListView listView;
    ArrayList<String> link = new ArrayList<>();;
    ArrayAdapter<String> adapter;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edittext);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listview);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        link = databaseHelper.getAlltext();
        adapter= new ArrayAdapter<String>(MainActivity.this,
                R.layout.sample_arraylist_layout, R.id.sampleEditText, link);
        listView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String links = editText.getText().toString();
                if(!links.isEmpty()){
                    if (databaseHelper.addText(links)){
                        editText.setText("");
                        link.clear();
                        link.addAll(databaseHelper.getAlltext());
                        adapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        listView.refreshDrawableState();
                    }
                }


            }
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String showlink = link.get(position);
                Toast.makeText(getApplicationContext(), showlink, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("link", showlink);
                startActivity(intent);
            }
        });

    }


}

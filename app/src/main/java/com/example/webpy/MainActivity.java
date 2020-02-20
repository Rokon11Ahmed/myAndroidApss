package com.example.webpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
    ArrayList<String> link = new ArrayList<>();
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
        try {
            link = databaseHelper.getAlltext();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter= new ArrayAdapter<>(MainActivity.this,
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
                Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
                String showlink = link.get(position);


                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("link", showlink);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.FeedBack:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                String[] emailID = {"rokon11ahmed@gmail.com"};
                String emailSubject = "Feedback From Webpy User";

                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, emailID);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                startActivity(Intent.createChooser(emailIntent,"choose an email client"));

                break;
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String subject ="Web to App converter";
                String body = "You can easily convert any website into app";

                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent,"Share with"));


                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}

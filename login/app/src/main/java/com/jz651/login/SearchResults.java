package com.jz651.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 24565 on 2018/3/25.
 */

public class SearchResults extends AppCompatActivity implements OnItemClickListener {

    private ListView list_results;
    private Bundle receive;
    private List<ArrayList> datalist;
    //Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        list_results = (ListView) findViewById(R.id.search_results);
        receive = getIntent().getExtras();
        Set<String> keySet = receive.keySet();       //get all keys
        for(String key : keySet){
            ArrayList data = receive.getStringArrayList("key");
            datalist.add(data);
        }
        ListAdapter adapter = new ArrayAdapter<ArrayList>(SearchResults.this,android.R.layout.simple_list_item_1,datalist);
        list_results.setAdapter(adapter);
        list_results.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(SearchResults.this,ResultDetail.class);
        SearchResults.this.startActivity(intent);
    }
}

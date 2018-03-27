package com.jz651.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultDetail extends AppCompatActivity {
    private ArrayList<String> list;
    private TextView subject;
    private TextView time;
    private TextView location;
    private TextView contact;
    private TextView user;

    private String a;
    private String b;
    private String c;
    private String d;
    private String e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        subject = (TextView) findViewById(R.id.subject);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        contact = (TextView) findViewById(R.id.contact);
        user = (TextView) findViewById(R.id.user);

        Intent i = getIntent();
        list = i.getStringArrayListExtra("list");
        a = "Subject : ";
        a += list.get(0).toString();
        b = "Time : ";
        b += list.get(1).toString();
        c = "Location : ";
        c += list.get(2).toString();
        d = "Contact Information : ";
        d += list.get(3).toString();
        e = "Name : ";
        e += list.get(4).toString();

        subject.setText(a);
        time.setText(b);
        location.setText(c);
        contact.setText(d);
        user.setText(e);
    }

    /**
     * Created by 24565 on 2018/3/24.
     */

    public static class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
        private String[] mStrs = {"ece590", "System Programming", "Server", "ece551"};
        private SearchView search_view;
        private ListView mListView;
        private ListView list_results;
        //private TextView txResult;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            search_view = (SearchView) findViewById(R.id.search_view);
            mListView = (ListView) findViewById(R.id.list_view);
            list_results = (ListView) findViewById(R.id.list_results);
            list_results.setOnItemClickListener(this);
            search_view.setSubmitButtonEnabled(true);
            mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
            mListView.setTextFilterEnabled(true);
            search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!TextUtils.isEmpty(newText)) {
                        mListView.setFilterText(newText);
                    } else {
                        mListView.clearTextFilter();
                    }
                    return false;
                }
            });
        }

        private void Search(String subject) {
            //Log.d("Test",query);
            Response.Listener<String> responselistener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("error");
                        List<ArrayList> datalist = new ArrayList<>();
                        if(!success){
                            JSONArray jsonArray=jsonResponse.getJSONArray("result");
                            ArrayList data = new ArrayList(5);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject finalObject=jsonArray.getJSONObject(i);
                                String sub=finalObject.getString("subject");
                                String time=finalObject.getString("time");
                                String location=finalObject.getString("location");
                                String contact=finalObject.getString("contact_info");
                                String user=finalObject.getString("UserName");

                                data.add(sub);
                                data.add(time);
                                data.add(location);
                                data.add(contact);
                                data.add(user);
                                datalist.add(data);
                            }
                            ListAdapter adapter = new ArrayAdapter<ArrayList>(SearchActivity.this,android.R.layout.simple_list_item_1,datalist);
                            list_results.setAdapter(adapter);

                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                            builder.setMessage("No such subject exists");
                            builder.setNegativeButton("Retry",null);
                            builder.create();
                            builder.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            SearchRequest searchRequest = new SearchRequest(subject, responselistener);
            RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
            queue.add(searchRequest);
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(SearchActivity.this,ResultDetail.class);
            ArrayList<String> list = ( ArrayList<String>)(list_results).getItemAtPosition(i);
            intent.putStringArrayListExtra("list",list);
            SearchActivity.this.startActivity(intent);
        }

    }
}

package com.jz651.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinyuzhou on 3/24/18.
 */

public class TutorSearch extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String[] mStrs = {"ece590", "System Programming", "Server", "ece551"};
    private SearchView search_view;
    private ListView mListView;
    private ListView list_results;
    private Integer U_id;
    private String UserName;
    private Button btnPre;
    private String duration;
    private String is_voluntary;
    //private TextView txResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_view = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        list_results = (ListView) findViewById(R.id.list_results);
        btnPre = (Button) findViewById(R.id.btnPre);
        list_results.setOnItemClickListener(this);
        search_view.setSubmitButtonEnabled(true);
        Intent intent = getIntent();
        U_id = intent.getIntExtra("user_id",0);
        UserName = intent.getStringExtra("UserName");
        System.out.println(U_id+" -------:-------- "+UserName);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
        mListView.setTextFilterEnabled(true);

        btnPre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent(TutorSearch.this,UserSpace.class);
                BackIntent.putExtra("user_id",U_id);
                BackIntent.putExtra("UserName",UserName);
                TutorSearch.this.startActivity(BackIntent);
            }
        });


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
                        //ArrayList data = new ArrayList(6);
                        for(int i=0;i<jsonArray.length();i++){
                            ArrayList data = new ArrayList(6);
                            JSONObject finalObject=jsonArray.getJSONObject(i);
                            String  session_id = finalObject.getString("session_id");
                            String sub=finalObject.getString("subject");
                            String time=finalObject.getString("time");
                            String location=finalObject.getString("location");
                            String contact=finalObject.getString("contact_info");
                            String user=finalObject.getString("UserName");
                            Double temp_duration = finalObject.getDouble("duration");
                            Integer temp_is_volutary = finalObject.getInt("IS_VOLUNTARY");
                            duration = temp_duration.toString();
                            is_voluntary = temp_is_volutary.toString();
                            data.add(session_id);
                            data.add(sub);
                            data.add(time);
                            data.add(location);
                            data.add(contact);
                            data.add(user);
                            data.add(duration);
                            data.add(is_voluntary);
                            datalist.add(data);
                        }
                        ListAdapter adapter = new ArrayAdapter<ArrayList>(TutorSearch.this,android.R.layout.simple_list_item_1,datalist);
                        list_results.setAdapter(adapter);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(TutorSearch.this);
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
        RequestQueue queue = Volley.newRequestQueue(TutorSearch.this);
        queue.add(searchRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(TutorSearch.this,ResultDetail.class);
        ArrayList<String> list = ( ArrayList<String>)(list_results).getItemAtPosition(i);
        intent.putExtra("U_id",U_id);
        intent.putExtra("UserName",UserName);
        intent.putStringArrayListExtra("list",list);
        TutorSearch.this.startActivity(intent);
    }

}

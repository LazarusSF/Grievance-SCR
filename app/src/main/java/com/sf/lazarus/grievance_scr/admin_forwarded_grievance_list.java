package com.sf.lazarus.grievance_scr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class admin_forwarded_grievance_list extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private AfglAdapter mAfglAdapter;
    private ArrayList<Afgldata> mAfglList;
    private RequestQueue mRequestQueue;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forwarded_grievance_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.afgl_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAfglList = new ArrayList<>();
        mTextView= (TextView) findViewById(R.id.afgl_nodata);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        JsonArrayRequest datareq = new JsonArrayRequest(getString(R.string.adminforwardedgrievancelist), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        if(mRecyclerView.getVisibility()==View.GONE||mRecyclerView.getVisibility()==View.INVISIBLE){
                            mTextView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        JSONObject obj = response.getJSONObject(i);

                        String mstatus = obj.getString("status");
                        String mgid = obj.getString("gid");
                        String mdata = obj.getString("data");
                        String mdate = obj.getString("datetime");
                        String mpfno= obj.getString("pfno");
                        mAfglList.add(new Afgldata(mstatus, mgid, mdata, mdate,mpfno));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAfglAdapter = new AfglAdapter(admin_forwarded_grievance_list.this, mAfglList);
                mRecyclerView.setAdapter(mAfglAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(datareq);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,admin_home.class));
    }
}

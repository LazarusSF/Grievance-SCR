package com.sf.lazarus.grievance_scr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class admin_new_grievance_list extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AnglAdapter mAnglAdapter;
    private ArrayList<Angldata> mAnglList;
    private RequestQueue mRequestQueue;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_grievance_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.angl_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnglList = new ArrayList<>();
        mTextView= (TextView) findViewById(R.id.angl_nodata);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
   }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,admin_home.class));
    }

    private void parseJSON() {

        JsonArrayRequest datareq = new JsonArrayRequest(getString(R.string.adminnewgrievancelist), new Response.Listener<JSONArray>() {
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
                        String mpfno=obj.getString("pfno");
                        mAnglList.add(new Angldata(mstatus, mgid, mdata, mdate, mpfno));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAnglAdapter = new AnglAdapter(admin_new_grievance_list.this, mAnglList);
                mRecyclerView.setAdapter(mAnglAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(datareq);
    }
}
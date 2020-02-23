package com.sf.lazarus.grievance_scr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.sf.lazarus.grievance_scr.R.layout.activity_new_grievance;

public class new_grievance extends AppCompatActivity {

    private EditText data_ng;
    AlertDialog progressDialog;
    private int ppono;

    private static final String TAG = "NewGrievanceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_new_grievance);

        Bundle getpfno=getIntent().getExtras();
        ppono=getpfno.getInt("pfno_4use");

        final String pfno=String.valueOf(ppono);
        FancyButton submit_ng = (FancyButton) findViewById(R.id.new_grievance_submit_button);
        data_ng = (EditText) findViewById(R.id.ng_description);
        progressDialog = new SpotsDialog(this, R.style.newpwd_custom);

        submit_ng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase(pfno,data_ng.getText().toString());
            }
        });
    }

    private void updateDatabase(final String pfppono,final String data) {

        String cancel_req_tag = "new data";
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.newgrievance), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (error) {
                        String errorMsg = jObj.getString("error_msg");
                        Vibrator vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(300);

                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }
                    else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Updating Error: " + error.getMessage());
                Vibrator vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(300);
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pfno", pfppono);
                params.put("data", data);
                params.put("status", "pending");
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }



    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder reset_pwd=new AlertDialog.Builder(new_grievance.this);
        reset_pwd.setIcon(R.drawable.ic_error_outline_black_24dp)
                .setCancelable(false)
                .setMessage("Do you want to exit ?")
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(new_grievance.this, user_home.class);
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj= reset_pwd.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();
    }

}

/*
        new_grievance_submit_button_obj.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Alert show = Alerter.create(new_grievance.this)
        .setIcon(R.drawable.ic_action_name)
        .setTitle("Railway Grievance")
        .setText("Thank You for submitting...\n come back to check status\n click here to go back")
        .setTextTypeface(Typeface.MONOSPACE)
        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
        .enableInfiniteDuration(true)
        .enableProgress(true)
        .disableOutsideTouch()
        .enableVibration(true)
        .setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        finish();
        }
        })
        .show();

        }
        });
*/

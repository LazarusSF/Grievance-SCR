package com.sf.lazarus.grievance_scr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class admin_changepassword extends AppCompatActivity {

    private EditText rp_newpwd, rp_new_repwd;
    private FancyButton submit;
    AlertDialog progressDialog;
    private String mobile;

    private static final String TAG = "ChangeAdminPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_changepassword);
        Bundle extras=getIntent().getExtras();
        mobile= extras.getString("mobile");

        rp_newpwd = (EditText) findViewById(R.id.change_pwd_enter_pwd);
        rp_new_repwd = (EditText) findViewById(R.id.change_pwd_re_enter_pwd);


        submit = (FancyButton) findViewById(R.id.change_newpwd_submit_button);
        progressDialog = new SpotsDialog(this, R.style.newpwd_custom);
        progressDialog.setCancelable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(mobile,
                        rp_newpwd.getText().toString(),
                        rp_new_repwd.getText().toString());

            }
        });
    }
    private void changePassword(final String mobile, final String pwd, final String re_pwd) {

        String cancel_req_tag = "AdminReset";
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.adminchangepassword), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                admin_changepassword.this,
                                admin_home.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(300);

                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
                params.put("mobile", mobile);
                params.put("newpassword", pwd);
                params.put("renewpassword", re_pwd);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
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

        AlertDialog.Builder reset_pwd = new AlertDialog.Builder(admin_changepassword.this);
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
                        Intent myIntent = new Intent(getApplicationContext(), user_login.class);
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj = reset_pwd.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();
    }


}

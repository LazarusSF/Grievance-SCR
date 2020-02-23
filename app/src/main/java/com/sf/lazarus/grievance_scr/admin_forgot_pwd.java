package com.sf.lazarus.grievance_scr;

import android.app.AlertDialog;
import android.content.Context;
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


public class admin_forgot_pwd extends AppCompatActivity {

    private EditText rp_username,rp_mobile;
    private FancyButton submit;
    AlertDialog progressDialog;

    private static final String TAG = "ForgotNewPasswordActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forgot_pwd);


        rp_username=(EditText) findViewById(R.id.username_adminforgotpassword);
        rp_mobile=(EditText) findViewById(R.id.mobile_adminforgotpassword);

        submit=(FancyButton) findViewById(R.id.submit_adminforgotpassword);
        progressDialog = new SpotsDialog(this,R.style.newpwd_custom);
        progressDialog.setCancelable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(rp_username.getText().toString(),rp_mobile.getText().toString());

            }
        });
    }

    private void changePassword(final String username, final String mobile) {


        String cancel_req_tag = "forgot";
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.adminforgotpwd), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                admin_forgot_pwd.this,
                                admin_enter_otp.class);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        String errorMsg = jObj.getString("error_msg");
                        Vibrator vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
                params.put("uname", username);
                params.put("mobile", mobile);
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
        startActivity(new Intent(admin_forgot_pwd.this,admin_login.class));
    }

}

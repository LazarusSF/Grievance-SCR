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

public class user_login extends AppCompatActivity {

        private static final String TAG = "LoginActivity";
        AlertDialog progressDialog;
        private EditText loginInputPfno, loginInputPassword;
        private FancyButton btnlogin;
        private FancyButton btnLinkSignup;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_login);
            loginInputPfno = (EditText) findViewById(R.id.pfno_check);
            loginInputPassword = (EditText) findViewById(R.id.pwd_check);
            btnlogin = (FancyButton) findViewById(R.id.login_userlogin);
            btnLinkSignup = (FancyButton) findViewById(R.id.register_userlogin);
            FancyButton btnfrgtpwd= (FancyButton) findViewById(R.id.userlogin_forgot_button);
            // Progress dialog
            progressDialog = new SpotsDialog(this,R.style.userlogin_custom);
            progressDialog.setCancelable(false);

            btnfrgtpwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(user_login.this,forgot_pwd.class));
                }
            });

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    loginUser(loginInputPfno.getText().toString(),
                            loginInputPassword.getText().toString());
                }
            });

            btnLinkSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), new_user.class);
                    startActivity(i);

                }
            });
        }

    private void loginUser( final String pfno, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.userlogin), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        // Launch User activity
                        Toast.makeText(getApplicationContext(),"Welcome "+user, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                user_login.this,
                                user_home.class);
                        int pf_xtra=jObj.getJSONObject("user").getInt("pfno");
                        Bundle bundle=new Bundle();
                        bundle.putInt("pfno_4use",pf_xtra);
                        intent.putExtras(bundle);
                        /*
                        intent.putExtra("username", user);*/
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
                    catch (Exception e){
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
                params.put("pfno", pfno);
                params.put("password", password);
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
        startActivity(new Intent(this,home.class));
    }
}


/*
package com.sf.lazarus.grievance_scr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class admin_reset_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reset_password);
    }
    public void click_submit_admin_reset_pwd(View view){

        AlertDialog.Builder admin_reset_pwd=new AlertDialog.Builder(admin_reset_password.this);
        admin_reset_pwd.setIcon(R.drawable.ic_error_outline_black_24dp)
                .setCancelable(false)
                .setMessage("Do you want to submit ?")
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent_submit_reset_pwd_obj=new Intent(admin_reset_password.this,admin_login.class);
                        startActivity(intent_submit_reset_pwd_obj);
                    }
                });
        AlertDialog home_alert_obj= admin_reset_pwd.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();

    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder admin_reset_pwd=new AlertDialog.Builder(admin_reset_password.this);
        admin_reset_pwd.setIcon(R.drawable.ic_error_outline_black_24dp)
                .setCancelable(false)
                .setMessage("Do you want to submit ?")
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent_submit_admin_reset_pwd_obj=new Intent(admin_reset_password.this,admin_login.class);
                        startActivity(intent_submit_admin_reset_pwd_obj);
                    }
                });
        AlertDialog home_alert_obj= admin_reset_pwd.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();

    }

}
*/
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

public class admin_reset_password extends AppCompatActivity {

    private EditText rp_newpwd,rp_new_repwd,rp_mobile;
    private FancyButton submit;
    AlertDialog progressDialog;

    private static final String TAG = "AdminResetPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reset_password);


        rp_newpwd=(EditText) findViewById(R.id.admin_reset_pwd_enter_pwd);
        rp_new_repwd=(EditText) findViewById(R.id.admin_reset_pwd_re_enter_pwd);
        rp_mobile=(EditText) findViewById(R.id.admin_reset_pwd_enter_mobile);

        submit=(FancyButton) findViewById(R.id.admin_reset_password_submit_button);
        progressDialog = new SpotsDialog(this,R.style.newpwd_custom);
        progressDialog.setCancelable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(rp_newpwd.getText().toString(),rp_new_repwd.getText().toString(),
                rp_mobile.getText().toString());

            }
        });
    }

    private void changePassword(final String pwd, final String newpwd, final String mobile) {

        String cancel_req_tag = "change";
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.adminresetpassword), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error)
                    {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                admin_reset_password.this,
                                admin_home.class);
                        startActivity(intent);
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
                params.put("mobile",mobile);
                params.put("newpassword", pwd);
                params.put("renewpassword", newpwd);
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

        AlertDialog.Builder reset_pwd=new AlertDialog.Builder(admin_reset_password.this);
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
                        Intent myIntent = new Intent(admin_reset_password.this, admin_home.class);
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj= reset_pwd.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();
    }
 /*   public void click_submit_reset_pwd(View view){
        AlertDialog.Builder reset_pwd=new AlertDialog.Builder(reset_password.this);
        reset_pwd.setIcon(R.drawable.ic_error_outline_black_24dp)
                .setCancelable(false)
                .setMessage("Do you want to submit ?")
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent_submit_reset_pwd_obj=new Intent(reset_password.this,user_login.class);
                        startActivity(intent_submit_reset_pwd_obj);
                    }
                });
        AlertDialog home_alert_obj= reset_pwd.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();

    }
*/
}

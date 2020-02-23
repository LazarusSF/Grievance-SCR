package com.sf.lazarus.grievance_scr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


public class new_user extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    AlertDialog progressDialog;

    private EditText signupInputName, signupInputPfno , signupInputDesignation, signupInputMobile, signupInputPassword, signupInputReEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//this
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//this
        setContentView(R.layout.activity_new_user);//this
  //      setContentView(R.layout.activity_new_user);

        // Progress dialog
        progressDialog = new SpotsDialog(this,R.style.newuser_custom);
        progressDialog.setCancelable(false);

        signupInputName = (EditText) findViewById(R.id.newuser_name_editview);
        signupInputPfno = (EditText) findViewById(R.id.newuser_pfno_editview);
        signupInputDesignation = (EditText) findViewById(R.id.newuser_designation_editview);
        signupInputMobile = (EditText) findViewById(R.id.newuser_mobile_editview);
        signupInputPassword = (EditText) findViewById(R.id.newuser_pwd_editview);
        signupInputReEnterPassword = (EditText) findViewById(R.id.newuser_repwd_editview);

        FancyButton btnSignUp = (FancyButton) findViewById(R.id.new_user_register_button);
        FancyButton btnLinkLogin = (FancyButton) findViewById(R.id.old_user_login_button);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    registerUser(signupInputName.getText().toString(),
                            signupInputPfno.getText().toString(),
                            signupInputDesignation.getText().toString(),
                            signupInputMobile.getText().toString(),
                            signupInputPassword.getText().toString(),
                            signupInputReEnterPassword.getText().toString());
            }
        });
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),user_login.class);
                startActivity(i);
            }
        });
    }


    private void registerUser(final String name,  final String pfno, final String desig,
                              final String mobile, final String pass, final String repas) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.newuser), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(new_user.this, "Hi " + user +", You are successfully Added!",
                                Toast.LENGTH_SHORT).show();
finish();
                        // Launch login activity
               //         Intent intent = new Intent(
                 //               new_user.this,
                   //             user_home.class);
                     //   startActivity(intent);
                    } else {

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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Vibrator vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(300);
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("pfno", pfno);
                params.put("designation", desig);
                params.put("mobile", mobile);
                params.put("password", pass);
                params.put("repwd",repas);
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
        startActivity(new Intent(this,home.class));
    }
}
//            Alerter showAlert = Alerter.create(new_user.this)
//                    .setIcon(R.drawable.ic_action_name)
//                    .setTitle("Railway Grievance")
//                    .setText("PASSWORD NOT MATCHED")
//                    .setTextTypeface(Typeface.MONOSPACE)
//                    .setBackgroundColorRes(R.color.colorAccent)
//                    .setDuration(2000)
//                    .enableProgress(true)
//                    .disableOutsideTouch()
//                    .enableVibration(true)
//                    .enableSwipeToDismiss();
//                showAlert.show();

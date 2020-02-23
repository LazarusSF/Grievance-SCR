package com.sf.lazarus.grievance_scr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

//:::




public class Close_Grievance extends AppCompatActivity implements View.OnClickListener {

    private FancyButton uploadButton, btnselectpic;

    private ImageView imageview;
    private ProgressDialog dialog = null;
    private JSONObject jsonObject;
    private String FNAME,pf,gd,cmnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close__grievance);

        pf=getIntent().getStringExtra("PEpfno");
        gd=getIntent().getStringExtra("PEgid");
        cmnt=getIntent().getStringExtra("PEcmnt");

        FNAME=pf+gd;

        uploadButton = (FancyButton)findViewById(R.id.uploadButton);
        btnselectpic = (FancyButton)findViewById(R.id.button_selectpic);
        imageview = (ImageView)findViewById(R.id.imageView_pic);


        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        jsonObject = new JSONObject();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_selectpic:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 592);
                break;
            case R.id.uploadButton:
                Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                dialog.show();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                try {
                    jsonObject.put("name", FNAME);
                    jsonObject.put("image", encodedImage);
                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.utils_closeGrievance), jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.e("Message from server", jsonObject.toString());
                                imageview.setVisibility(View.GONE);
                                dialog.dismiss();
                                Toast.makeText(Close_Grievance.this, "Image Uploaded Successfully \nand Commented", Toast.LENGTH_SHORT).show();
                                commentMethod();
                                closeMethod();
                                finish();
                                startActivity(new Intent(Close_Grievance.this,admin_home.class));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Message from server", volleyError.toString());
                        dialog.dismiss();
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(this).add(jsonObjectRequest);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Submit by attaching an Image",Toast.LENGTH_SHORT).show();
    }

    private void closeMethod() {

        String cancel_req_tag = "close_angl "+pf+" "+gd;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.closegrievance_toClose), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (error) {
                        String errorMsg = jObj.getString("error_msg");

                        Toast.makeText(Close_Grievance.this,
                                errorMsg, Toast.LENGTH_SHORT).show();

                    }
                    else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(Close_Grievance.this,errorMsg, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Close_Grievance.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pfno", pf);
                params.put("gid", gd);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(Close_Grievance.this).addToRequestQueue(strReq,cancel_req_tag);

    }


    private void commentMethod() {

        String cancel_req_tag = "close_angl "+pf+" "+gd;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                getString(R.string.closgrievance_toComment), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (error) {
                        String errorMsg = jObj.getString("error_msg");

                        Toast.makeText(Close_Grievance.this,
                                errorMsg, Toast.LENGTH_SHORT).show();

                    }
                    else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(Close_Grievance.this,errorMsg, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Close_Grievance.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pf", pf);
                params.put("gd", gd);
                params.put("cmnt",cmnt);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(Close_Grievance.this).addToRequestQueue(strReq,cancel_req_tag);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 592 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageview.setVisibility(View.VISIBLE);
            imageview.setImageURI(selectedImageUri);
        }
    }
}



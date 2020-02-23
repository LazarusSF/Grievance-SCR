package com.sf.lazarus.grievance_scr;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;



/**
 * Created by LAZARUS on 25-01-2018.
 */
//change this and close grievance immediately and delete this line

public class AnglAdapter extends RecyclerView.Adapter<AnglAdapter.AnglViewHolder>{
    private Context mcontext;
    private ArrayList<Angldata> mAngldatas;
    public String status,gid,date,data,pfno;

    public AnglAdapter(Context context, ArrayList<Angldata> AngldataArrayList){
        mcontext=context;
        mAngldatas=AngldataArrayList;
    }

    @Override
    public AnglViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.angl_item,parent, false);
        return new AnglViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnglViewHolder holder, int position) {
        Angldata angldata=mAngldatas.get(position);
        status=angldata.getstatus();
        gid=angldata.getgid();
        date=angldata.getdate();
        data=angldata.getdata();
        pfno=angldata.getpfno();

        holder.dstatus.setText(status);
        holder.dgid.setText(gid);
        holder.ddate.setText(date);
        holder.ddata.setText(data);
        holder.dpfno.setText(pfno);

    }

    @Override
    public int getItemCount() {
        return mAngldatas.size();
    }

    public class AnglViewHolder extends RecyclerView.ViewHolder{

        public TextView dstatus, dgid, ddata, ddate, dpfno;
        public Button dforward, dclose;
        private String pf, gd;
        AlertDialog progressDialog = new SpotsDialog(mcontext, R.style.newpwd_custom);

        public AnglViewHolder(View itemView) {
            super(itemView);
            dstatus = (TextView) itemView.findViewById(R.id.angl_status2show);
            dgid = (TextView) itemView.findViewById(R.id.angl_gid2show);
            ddata = (TextView) itemView.findViewById(R.id.angl_data2show);
            ddate = (TextView) itemView.findViewById(R.id.angl_date2show);
            dpfno = (TextView) itemView.findViewById(R.id.angl_pfno2show);
            dforward = (Button) itemView.findViewById(R.id.forward_angl);
            dclose = (Button) itemView.findViewById(R.id.close_angl);
            dforward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String datasms= (String) ddata.getText();
                    String datesms= (String) ddate.getText();
                    try {
                        AnglViewHolder.this.sendsms(pfno,gid, datasms, datesms);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //    Toast.makeText(mcontext,"Selected Department",Toast.LENGTH_SHORT).show();
                  //  forwardmethod(pf, gd);
                  //  Toast.makeText(mcontext,"Done forwarded",Toast.LENGTH_SHORT).show();

                }
            });
            dclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pf = (String) dpfno.getText();
                    gd = (String) dgid.getText();
                    View mView = LayoutInflater.from(mcontext).inflate(R.layout.commentitem, null);
                    final EditText editText = (EditText) mView.findViewById(R.id.commentHere);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mcontext);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.setTitle("Comment To Close");
                    dialog.setCancelable(false);

                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Submit", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            closemethod(pf, gd);
                            Toast.makeText(mcontext, "Closed Grievance Without any Attachment", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }

                    });
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }

                    });
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Submit with\nAttachment", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String commentdata = editText.getText().toString();

                            Intent intent = new Intent(mcontext, Close_Grievance.class);
                            intent.putExtra("PEpfno", pf);
                            intent.putExtra("PEgid", gd);
                            intent.putExtra("PEcmnt", commentdata);
                            mcontext.startActivity(intent);
                            dialog.dismiss();
                        }

                    });

                    dialog.show();


                }

            });
        }

        private void sendsms(final String pf,final String gid, final String data, final String date) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(mcontext);
            View mView = LayoutInflater.from(mcontext).inflate(R.layout.dialog_numbers,null);
            final RadioButton rb1 = (RadioButton) mView.findViewById(R.id.radioButton1);
            final RadioButton rb2 = (RadioButton) mView.findViewById(R.id.radioButton2);
            final RadioButton rb3 = (RadioButton) mView.findViewById(R.id.radioButton3);
            final RadioButton rb4 = (RadioButton) mView.findViewById(R.id.radioButton4);
            final RadioButton rb5 = (RadioButton) mView.findViewById(R.id.radioButton5);
            final RadioButton rb6 = (RadioButton) mView.findViewById(R.id.radioButton6);
            final RadioButton rb7 = (RadioButton) mView.findViewById(R.id.radioButton7);
            final RadioButton rb8 = (RadioButton) mView.findViewById(R.id.radioButton8);
            final RadioButton rb9 = (RadioButton) mView.findViewById(R.id.radioButton9);
            final RadioButton rb10 = (RadioButton) mView.findViewById(R.id.radioButton10);
            final RadioButton rb11 = (RadioButton) mView.findViewById(R.id.radioButton11);
            final RadioButton rb12 = (RadioButton) mView.findViewById(R.id.radioButton12);
            final RadioButton rb13 = (RadioButton) mView.findViewById(R.id.radioButton13);
            final RadioButton rb14 = (RadioButton) mView.findViewById(R.id.radioButton14);
            final RadioButton rb15 = (RadioButton) mView.findViewById(R.id.radioButton15);
            final RadioButton rb16 = (RadioButton) mView.findViewById(R.id.radioButton16);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.setTitle("Select Department");
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Submit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String dept1, dept2, dept3, dept4, dept5, dept6, dept7, dept8, dept9, dept10, dept11;
                    String dept12, dept13, dept14, dept15, dept16;
                    dept1 = "";
                    dept2 = "";
                    dept3 = "";
                    dept4 = "";
                    dept5 = "";
                    dept6 = "";
                    dept7 = "";
                    dept8 = "";
                    dept9 = "";
                    dept10 = "";
                    dept11 = "";
                    dept12 = "";
                    dept13 = "";
                    dept14 = "";
                    dept15 = "";
                    dept16 = "";

                    if (rb1.isChecked()) {
                        Toast.makeText(mcontext, "Department 1", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept1);
                    } else if (rb2.isChecked()) {
                        Toast.makeText(mcontext, "Department 2", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept2);
                    } else if (rb3.isChecked()) {
                        Toast.makeText(mcontext, "Department 3", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept3);
                    } else if (rb4.isChecked()) {
                        Toast.makeText(mcontext, "Department 4", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept4);

                    } else if (rb5.isChecked()) {
                        Toast.makeText(mcontext, "Department 5", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept5);

                    } else if (rb6.isChecked()) {
                        Toast.makeText(mcontext, "Department 6", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept6);

                    } else if (rb7.isChecked()) {
                        Toast.makeText(mcontext, "Department 7", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept7);

                    } else if (rb8.isChecked()) {
                        Toast.makeText(mcontext, "Department 8", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept8);

                    } else if (rb9.isChecked()) {
                        Toast.makeText(mcontext, "Department 9", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept9);

                    } else if (rb10.isChecked()) {
                        Toast.makeText(mcontext, "Department 10", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept10);

                    } else if (rb11.isChecked()) {
                        Toast.makeText(mcontext, "Department 11", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept11);

                    } else if (rb12.isChecked()) {
                        Toast.makeText(mcontext, "Department 12", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept12);

                    } else if (rb13.isChecked()) {
                        Toast.makeText(mcontext, "Department 13", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept13);
                    } else if (rb14.isChecked()) {
                        Toast.makeText(mcontext, "Department 14", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept14);

                    } else if (rb15.isChecked()) {
                        Toast.makeText(mcontext, "Department 15", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept15);

                    } else if (rb16.isChecked()) {
                        Toast.makeText(mcontext, "Department 16", Toast.LENGTH_SHORT).show();
                        oksend(pf, gid, data, date, dept16);

                    } else {
                        Toast.makeText(mcontext, "No Department Selected", Toast.LENGTH_LONG).show();
                    }
                }
            });

            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }

            });


            dialog.show();
        }
        private void oksend(String pf, String gd, String data,String date,String dept){
            //code to send msg goes here
            Toast.makeText(mcontext,"Selected Department",Toast.LENGTH_SHORT).show();
             forwardmethod(pf, gd);

        }

        private void closemethod(final String pf, final String gd) {

            String cancel_req_tag = "close_angl "+pf+" "+gd;
            showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    mcontext.getString(R.string.angladapter_toClose), new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    hideDialog();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        if (error) {
                            String errorMsg = jObj.getString("error_msg");

                            Toast.makeText(mcontext,
                                    errorMsg, Toast.LENGTH_LONG).show();

                        }
                        else {
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(mcontext,errorMsg, Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(mcontext,admin_new_grievance_list.class);
                            mcontext.startActivity(intent);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mcontext,
                            error.getMessage(), Toast.LENGTH_LONG).show();

                    hideDialog();
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
            AppSingleton.getInstance(mcontext).addToRequestQueue(strReq,cancel_req_tag);
        }



        private void showDialog() {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
        private void hideDialog() {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }


        private void forwardmethod(final String pf, final String gd) {

            String cancel_req_tag = "forward_method "+pf+" "+gd;
            showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    mcontext.getString(R.string.angladapter_toForward), new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    hideDialog();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        if (error) {
                            String errorMsg = jObj.getString("error_msg");

                            Toast.makeText(mcontext,
                                    errorMsg, Toast.LENGTH_LONG).show();

                        }
                        else {
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(mcontext,errorMsg, Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(mcontext,admin_new_grievance_list.class);
                            mcontext.startActivity(intent);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mcontext,
                            error.getMessage(), Toast.LENGTH_LONG).show();

                    hideDialog();
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
            AppSingleton.getInstance(mcontext).addToRequestQueue(strReq,cancel_req_tag);

        }
    }

}
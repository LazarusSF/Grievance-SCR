package com.sf.lazarus.grievance_scr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class AfglAdapter  extends RecyclerView.Adapter<AfglAdapter.AfglViewHolder> {

    private Context mcontext;
    private ArrayList<Afgldata> mAfgldatas;
    public String status,gid,date,data,pfno;

    public AfglAdapter(Context context, ArrayList<Afgldata> AfgldataArrayList){
        mcontext=context;
        mAfgldatas=AfgldataArrayList;
    }

    @Override
    public AfglViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.afgl_item,parent, false);
        return new AfglAdapter.AfglViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AfglViewHolder holder, int position) {
        Afgldata afgldata=mAfgldatas.get(position);
        status=afgldata.getstatus();
        gid=afgldata.getgid();
        date=afgldata.getdate();
        data=afgldata.getdata();
        pfno=afgldata.getpfno();

        holder.dstatus.setText(status);
        holder.dgid.setText(gid);
        holder.ddate.setText(date);
        holder.ddata.setText(data);
        holder.dpfno.setText(pfno);

    }

    @Override
    public int getItemCount() {
        return mAfgldatas.size();
    }


    public class AfglViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView dstatus,dgid,ddata,ddate,dpfno;
        public Button dclose;
        AlertDialog progressDialog = new SpotsDialog(mcontext, R.style.newpwd_custom);
        private String pf,gd;


        public AfglViewHolder(View itemView) {
            super(itemView);
            dstatus= (TextView) itemView.findViewById(R.id.afgl_status2show);
            dgid= (TextView) itemView.findViewById(R.id.afgl_gid2show);
            ddata= (TextView) itemView.findViewById(R.id.afgl_data2show);
            ddate= (TextView) itemView.findViewById(R.id.afgl_date2show);
            dpfno= (TextView) itemView.findViewById(R.id.afgl_pfno2show);
            dclose= (Button) itemView.findViewById(R.id.close_afgl);
            dclose.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(v.getId()==dclose.getId()){
                pf= (String) dpfno.getText();
                gd= (String) dgid.getText();


                View mView= LayoutInflater.from(mcontext).inflate(R.layout.commentitem,null);
                final EditText editText= (EditText) mView.findViewById(R.id.commentHere);
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(mcontext);
                mBuilder.setView(mView);
                final AlertDialog dialog=mBuilder.create();
                dialog.setTitle("Comment To Close");
                dialog.setCancelable(false);

                dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Submit",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        closemethod(pf,gd);
                        Toast.makeText(mcontext,"Closed Grievance Without any Attachment",Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }

                });
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"Cancel",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }

                });
                dialog.setButton(DialogInterface.BUTTON_POSITIVE,"Submit with\nAttachment",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String commentdata= editText.getText().toString();

                        Intent intent = new Intent(mcontext, Close_Grievance.class);
                        intent.putExtra("PEpfno",pf);
                        intent.putExtra("PEgid",gd);
                        intent.putExtra("PEcmnt",commentdata);
                        mcontext.startActivity(intent);
                        dialog.dismiss();
                    }

                });

                dialog.show();



            }
        }
        private void closemethod(final String pf, final String gd) {

            String cancel_req_tag = "close_angl "+pf+" "+gd;
            showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    mcontext.getString(R.string.afgladapter), new Response.Listener<String>() {

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
                            Intent intent=new Intent(mcontext,admin_forwarded_grievance_list.class);
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


    }

}

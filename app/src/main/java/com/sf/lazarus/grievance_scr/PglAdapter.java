package com.sf.lazarus.grievance_scr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LAZARUS on 22-01-2018.
 */

public class PglAdapter extends RecyclerView.Adapter<PglAdapter.PglViewHolder> {

    private Context mcontext;
    private ArrayList<Pgldata> mPgldata;
    private String PFNO;

    public PglAdapter(Context context,ArrayList<Pgldata> pgldataArrayList,String pfno){
        mcontext=context;
        mPgldata=pgldataArrayList;
        PFNO=pfno;
    }

    @Override
    public PglViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mcontext).inflate(R.layout.pgl_item, parent, false);
        return new PglViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PglViewHolder holder, int position) {
        Pgldata pgldata=mPgldata.get(position);
        String status=pgldata.getstatus();
        String gid=pgldata.getgid();
        String date=pgldata.getdate();
        String data=pgldata.getdata();
        String cmnts=pgldata.getcomments();
        String URL=mcontext.getString(R.string.pgladapter);
        String fname=".JPG";
        String CURL=URL+PFNO+gid+fname;

        holder.dstatus.setText(status);
        holder.dgid.setText(gid);
        holder.ddate.setText(date);
        holder.ddata.setText(data);
        if(cmnts!="") {
            holder.dcmnts.setText(cmnts);
            holder.dcmnts.setVisibility(View.VISIBLE);
        }
         if(holder.dstatus.getText().toString().equals("closed")) {
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(mcontext).load(CURL).into(holder.imageView);
        }
        else{
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mPgldata.size();
    }


    public class PglViewHolder extends RecyclerView.ViewHolder {

        public TextView dstatus,dgid,ddata,ddate,dcmnts;
        public ImageView imageView;

        public PglViewHolder(View itemView) {
            super(itemView);
            dstatus= (TextView) itemView.findViewById(R.id.status2show);
            dgid= (TextView) itemView.findViewById(R.id.gid2show);
            ddata= (TextView) itemView.findViewById(R.id.data2show);
            ddate= (TextView) itemView.findViewById(R.id.date2show);
            dcmnts=(TextView) itemView.findViewById(R.id.cmnts2show);
            imageView= (ImageView) itemView.findViewById(R.id.img2show);
        }
    }
}

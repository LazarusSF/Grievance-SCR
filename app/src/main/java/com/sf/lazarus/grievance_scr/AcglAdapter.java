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
 * Created by LAZARUS on 26-01-2018.
 */

public class AcglAdapter extends RecyclerView.Adapter<AcglAdapter.AcglViewHolder> {

    private Context mcontext;
    private ArrayList<Acgldata> mAcgldatas;
    public AcglAdapter(Context context, ArrayList<Acgldata> AcgldataArrayList){
        mcontext=context;
        mAcgldatas=AcgldataArrayList;
    }

    @Override
    public AcglViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.acgl_item,parent, false);
        return new AcglAdapter.AcglViewHolder(view);

    }
    @Override
    public void onBindViewHolder(AcglViewHolder holder, int position) {

        Acgldata angldata=mAcgldatas.get(position);
        String status=angldata.getstatus();
        String gid=angldata.getgid();
        String date=angldata.getdate();
        String data=angldata.getdata();
        String pfno=angldata.getpfno();
        String cmnts=angldata.getcomments();

        holder.dstatus.setText(status);
        holder.dgid.setText(gid);
        holder.ddate.setText(date);
        holder.ddata.setText(data);
        holder.dpfno.setText(pfno);

        String URL=mcontext.getString(R.string.pgladapter);
        String fname=".JPG";
        String CURL=URL+pfno+gid+fname;

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
        return mAcgldatas.size();
    }


    public class AcglViewHolder extends RecyclerView.ViewHolder{
        public TextView dstatus,dgid,ddata,ddate,dpfno,dcmnts;
        public ImageView imageView;

        public AcglViewHolder(View itemView) {
            super(itemView);
            dstatus= (TextView) itemView.findViewById(R.id.acgl_status2show);
            dgid= (TextView) itemView.findViewById(R.id.acgl_gid2show);
            ddata= (TextView) itemView.findViewById(R.id.acgl_data2show);
            ddate= (TextView) itemView.findViewById(R.id.acgl_date2show);
            dpfno= (TextView) itemView.findViewById(R.id.acgl_pfno2show);
            dcmnts= (TextView) itemView.findViewById(R.id.acgl_cmnts2show);
            imageView= (ImageView) itemView.findViewById(R.id.acgl_img2show);

        }

    }
}

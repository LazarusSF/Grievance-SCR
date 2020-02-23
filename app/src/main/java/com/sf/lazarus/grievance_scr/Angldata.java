package com.sf.lazarus.grievance_scr;

/**
 * Created by LAZARUS on 25-01-2018.
 */

public class Angldata {

    private String dstatus,dgid,ddata,ddate,dpfno;

    public Angldata(String status,String gid,String data,String date,String pfno){
        dstatus=status;
        dgid=gid;
        ddata=data;
        ddate=date;
        dpfno=pfno;
    }

    public String getstatus() {
        return dstatus;
    }

    public String getgid() {
        return dgid;
    }

    public String getdata() {
        return ddata;
    }

    public String getdate() { return ddate; }

    public String getpfno() { return dpfno; }

}

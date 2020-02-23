package com.sf.lazarus.grievance_scr;

/**
 * Created by LAZARUS on 26-01-2018.
 */

public class Acgldata {

    private String dstatus,dgid,ddata,ddate,dpfno,dcomments;

    public Acgldata(String status,String gid,String data,String date, String pfno,String dcmnts){
        dstatus=status;
        dgid=gid;
        ddata=data;
        ddate=date;
        dpfno=pfno;
        dcomments=dcmnts;
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

    public String getpfno() {  return dpfno; }

    public String getcomments() {  return dcomments; }

}

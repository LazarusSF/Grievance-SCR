package com.sf.lazarus.grievance_scr;

/**
 * Created by LAZARUS on 22-01-2018.
 */

public class Pgldata {
    private String dstatus,dgid,ddata,ddate,dcmnts;

    public Pgldata(String status,String gid,String data,String date, String dcomments){
        dstatus=status;
        dgid=gid;
        ddata=data;
        ddate=date;
        dcmnts=dcomments;
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

    public String getcomments() { return dcmnts; }
}

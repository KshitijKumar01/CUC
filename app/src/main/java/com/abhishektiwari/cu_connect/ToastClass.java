package com.abhishektiwari.cu_connect;

import android.content.Context;

import com.rejowan.cutetoast.CuteToast;

public class ToastClass {
    Context context;


    public   ToastClass(Context context)
    {
        this.context=context;
    }
    public void errortoast( String error)
    {
        CuteToast.ct(context, error, CuteToast.LENGTH_SHORT, CuteToast.ERROR, true).show();

    }
    public void successtoaast(String success)
    {
        CuteToast.ct(context, success, CuteToast.LENGTH_SHORT, CuteToast.SUCCESS, true).show();

    }
    public void warntoast(String warning)
    {
        CuteToast.ct(context, warning, CuteToast.LENGTH_SHORT, CuteToast.WARN, true).show();

    }

}

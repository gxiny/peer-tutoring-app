package com.jz651.Peer_Tutoring;

import android.app.Application;

/**
 * Created by 24565 on 2018/4/13.
 */


public class GlobalVariable  extends Application{
    private static String Email;

    @Override
    public void onCreate() {
        super.onCreate();
        setEmail(Email);
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

}

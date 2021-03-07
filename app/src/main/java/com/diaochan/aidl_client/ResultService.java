package com.diaochan.aidl_client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.diaochan.aidl.ILoginInterface;


public class ResultService extends Service {

    private static final String TAG = "diaochan >>>";
    
    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginInterface.Stub() {
            @Override
            public void login() throws RemoteException {
                
            }

            @Override
            public void loginCallback(boolean loginStatus, String loginUser) throws RemoteException {
                //不用等到挂起 让Server端拉活自己
                Log.e(TAG,"loginStatus = " + loginStatus + "/ loginUser = " + loginUser);
            }
        };
    }
}

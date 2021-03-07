package com.diaochan.aidl_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diaochan.aidl.ILoginInterface;

public class MainActivity extends AppCompatActivity {

    //是否开启跨进程通信
    private boolean isStartRemote = false;

    private ILoginInterface iLogin;//AIDL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        initBindService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务
        if (isStartRemote) {
            unbindService(conn);
            isStartRemote = false;
        }
    }


    // 绑定服务
    private void initBindService() {
        Intent intent = new Intent();

        //设置Server应用Action（服务的唯一标示）
        intent.setAction("BinderB_Action");

        //设置Server应用包名
        intent.setPackage("com.diaochan.aidl_service");

        //开启绑定服务
        bindService(intent, conn, BIND_AUTO_CREATE);

        //标示跨进程绑定
        isStartRemote = true;
    }

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //使用Server端的方法
            iLogin = ILoginInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    // 点击事件
    public void startQQLoginAction(View view) {
        if (iLogin != null) {
            try {
                iLogin.login();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "请先安装QQ", Toast.LENGTH_SHORT).show();
        }
    }
}
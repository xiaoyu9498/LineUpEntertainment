package com.tensun.lineupentertainment;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tensun.lineupentertainment.socket.IClient;
import com.tensun.lineupentertainment.socket.SocketClient;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class App extends Application implements IClient {

    private static App instance;
    private SocketClient mClient;
    private long time=6*60*1000;//排队等待时间

    @Override
    public void onCreate() {    	     
        super.onCreate();
        String processName = getProcessName(this, android.os.Process.myPid());
        if (!TextUtils.isEmpty(processName) && processName.equals(this.getPackageName())) {
            instance = this;

        }

    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();

    }
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public void tcpConnect(String ip,int port){
        mClient = new SocketClient(ip, port,this);
    }

    public void sendTcp(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mClient!=null)
                    mClient.sendMessage((message+"\n").getBytes());
            }
        }).start();
    }

    public void run10min(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendTcp("start");

                    for (int i = 0; i <= 100; i++) {
                        EventBus.getDefault().post(i);
                        Thread.sleep(time / 100);
                        if (i == 20) {
                            sendTcp("disappear1");
                        } else if (i == 40) {
                            sendTcp("disappear2");
                        } else if (i == 60) {
                            sendTcp("disappear3");
                        }else if (i == 80) {
                            sendTcp("disappear4");
                        }else if (i == 100) {
                            sendTcp("disappear5");
                        }
                    }
                    EventBus.getDefault().post("alarm");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void rxData(String var1) {

    }

    @Override
    public void onConnected() {
        Log.e("!!!", "已连接");
        EventBus.getDefault().post("Connected");
    }

    @Override
    public void onClosed() {
        Log.e("!!!", "断开连接");
        EventBus.getDefault().post("Not connect");
    }
}

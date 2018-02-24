package com.cn.my_bmob;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

/**
 * Created by anqilin on 18/1/13.
 */

public class MyPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String msg=intent.getStringExtra("msg");
            String message="";
            Log.d("bmob", "客户端收到推送内容："+msg);
            JSONTokener jsonTokener=new JSONTokener(msg);
            try {
                JSONObject object=(JSONObject) jsonTokener.nextValue();
                message=object.getString("alert");
            }catch (JSONException e){
                e.printStackTrace();
            }
            NotificationManager manager=
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(context)
                      .setContentTitle("mybmob")
                      .setContentText(message)
                      .setSmallIcon(R.mipmap.ic_launcher)
                      .setWhen(System.currentTimeMillis())
                      .build();
            manager.notify(1, notification);
        }
    }
}

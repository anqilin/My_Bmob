package com.cn.my_bmob;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.Feedback;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button query;
    Button sendmessage;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(MainActivity.this,"d31b82c81e87534b6ad4e2097d57ba4e");

        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {

              @Override

              public void done(BmobInstallation bmobInstallation, BmobException e) {

                  if (e == null) {
                      Logger log = Logger.getLogger("success");
                      log.info(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());

                  } else {
                      Logger log = Logger.getLogger("fail");
                      log.setLevel(Level.WARNING);
                      log.info(e.getMessage());

                  }

              }

          });
         BmobPush.startWork(this);

        button=(Button)this.findViewById(R.id.button);

        query=(Button)this.findViewById(R.id.button2) ;
        sendmessage=(Button)this.findViewById(R.id.button3);
        message=(EditText)this.findViewById(R.id.edit);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        query.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
        sendmessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().trim()!=null&&message.getText().toString().trim()!="") {
                    BmobPushManager pushManager = new BmobPushManager();
                    pushManager.pushMessageAll(message.getText().toString().trim(), new PushListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


    }
    private void query(){
        BmobQuery<Feedback> query=new BmobQuery<Feedback>();
        query.findObjects(new FindListener<Feedback>() {
            @Override
            public void done(List<Feedback> list, BmobException e) {
                if(e==null){
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("query");
                    String str="";
                    for(Feedback feedback:list){
                        str +=feedback.getName()+":"+feedback.getId()+"\n";
                    }
                    builder.setMessage(str);
                    builder.create().show();
                }

            }
        });
    }

    private void save(){
        Feedback feedback=new Feedback();
        feedback.setName("anqilin");
        feedback.setId("123");
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}

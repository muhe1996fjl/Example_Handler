package com.example.example_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar)findViewById(R.id.pb);
        tv = (TextView)findViewById(R.id.tv);

        Thread thread = new Thread(handlerrun);
        thread.start();
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            pb.setProgress(message.arg1);
            tv.setText(message.arg1 + "%");
            pb.setSecondaryProgress(message.arg1 + 10);
            return false;
        }
    });

    Runnable handlerrun = new  Runnable(){
        int progressValues = 0;

        @Override
        public void run() {
           while(true){
               Message msg = handler.obtainMessage();

               msg.arg1 = progressValues++;

               handler.sendMessage(msg);

               if(progressValues > 100){
                   progressValues = 0;
               }

               try{
                   Thread.sleep(300);
               }
               catch(InterruptedException e){
                   e.printStackTrace();
               }
           }
        };
    };

}

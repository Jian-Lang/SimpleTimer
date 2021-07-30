package com.example.newtimer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.newtimer.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    LinearLayout linearLayout;
    TextView textView;
    Button button;
    int time = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0){
                textView.setText(timeRefreshing(time));
                time++;
            }
            else if(msg.what == 1){
                time = 0;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        textView = findViewById(R.id.text_timer);
        button = findViewById(R.id.btn_timer_start);
        linearLayout = findViewById(R.id.linear_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.style_bluesky) {
            textView.setTextColor(Color.rgb(255,255,255));
            Toast.makeText(this,"湛蓝天空主题已切换",Toast.LENGTH_SHORT).show();
            linearLayout.setBackgroundResource(R.drawable.pic_bluesky);
            return true;
        }
        if (id == R.id.style_night) {
            textView.setTextColor(Color.rgb(236,255,64));
            Toast.makeText(this,"静谧夜晚主题已切换",Toast.LENGTH_SHORT).show();
            linearLayout.setBackgroundResource(R.drawable.night);
            return true;
        }
        if (id == R.id.style_traffic) {
            textView.setTextColor(Color.rgb(0,0,0));
            Toast.makeText(this,"道路交通主题已切换",Toast.LENGTH_SHORT).show();
            linearLayout.setBackgroundResource(R.drawable.pic_traffic);
            return true;
        }
        if (id == R.id.style_yun) {
            textView.setTextColor(Color.rgb(229,57,219));
            Toast.makeText(this,"奇幻云彩主题已切换",Toast.LENGTH_SHORT).show();
            linearLayout.setBackgroundResource(R.drawable.pic_yun);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void TimeRefreshed(View view){
        boolean flag = true;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        switch (view.getId()){
            case R.id.btn_timer_start:
                if(button.getText().equals("运行中")){
                    Toast.makeText(MainActivity.this,"正在运行中，请勿重复点击",Toast.LENGTH_SHORT).show();
                }
                if(button.getText().equals("启动")){
                    button.setText("运行中");
                    timer.schedule(timerTask,0,1000);
                }
                break;
            case R.id.btn_timer_end:
                String res = "已停止，本次计时时长：" + timeRefreshing(time);
                Toast.makeText(this,res,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
        }
    }
    public String timeRefreshing(int time){
        int second = time%60;
        int minute = time/60%60;
        int hour = time/60/60;
        String second_s = "";
        String minute_s = "";
        String hour_s = "";
        if(second < 10){
            second_s = "0" + second;
        }
        else{
            second_s = second + "";
        }
        if(minute < 10){
            minute_s = "0" + minute;
        }
        else{
            minute_s = minute + "";
        }
        if(hour < 10){
            hour_s = "0" + hour;
        }
        else{
            hour_s = hour + "";
        }
        return hour_s+":"+minute_s+":"+second_s;
    }
}
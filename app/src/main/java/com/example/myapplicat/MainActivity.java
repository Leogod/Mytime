package com.example.myapplicat;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplicat.data.Edititem;
import com.example.myapplicat.data.ItimeRecord;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    private  ArrayList<ItimeRecord> itimeRecords=new ArrayList<ItimeRecord>();
    private  ItimeRecordArrayAdapter itimeAdaper;
    private ListView listViewRecords;
    private AppBarConfiguration mAppBarConfiguration;
    private Handler mHandler;
    private TextView textViewBottom,textViewMiddle,textViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new OnFabClickListener()); //给fab加号设置响应函数

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        listViewRecords=this.findViewById(R.id.list_view_itime_record);
        textViewBottom=this.findViewById(R.id.text_view_time_bottom);
        textViewMiddle=this.findViewById(R.id.text_view_record_middle);
        textViewTop=this.findViewById(R.id.text_view_record_top);

        //itimeRecords.add(new ItimeRecord(R.drawable.marker,"没有标题","你好明天",2019,12,20,17,52));
        itimeAdaper=new ItimeRecordArrayAdapter(this,R.layout.list_item_record,itimeRecords);
        listViewRecords.setAdapter(itimeAdaper);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1: {
                        SimpleDateFormat dateFormatterChina = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        // TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取时区 这句加上，很关键。
                        //dateFormatterChina.setTimeZone(timeZoneChina);//设置系统时区
                        long sysTime = System.currentTimeMillis();//获取系统时间

                       // CharSequence sysTimeStr = dateFormatterChina.format(sysTime);//时间显示格式



                        ItimeRecord recordFirst = itimeRecords.get(0);
                        //textViewTop.setText(recordFirst.getTitle());
                        //textViewMiddle.setText(recordFirst.getYear()+"年"+recordFirst.getMonth()+"月"+recordFirst.getDay()+"日");



                        Calendar cal = Calendar.getInstance();
                        cal.set(recordFirst.getYear(), recordFirst.getMonth(), recordFirst.getDay(), recordFirst.getHour(), recordFirst.getMinute(), 0);//第二个参数月份是实际值减1
                        Date date = cal.getTime();
                        long timeStamp = date.getTime() - 8 * 60 * 60 * 1000;//获取时间戳
                       // CharSequence timeStampStr = dateFormatterChina.format(timeStamp);




                        if (sysTime < timeStamp) {
                            long dateMinus = timeStamp - sysTime;
                            long totalSeconds = dateMinus / 1000;

                            //求出现在的秒
                            long currentSecond = totalSeconds % 60;

                            //求出现在的分
                            long totalMinutes = totalSeconds / 60;
                            long currentMinute = totalMinutes % 60;

                            //求出现在的小时
                            long totalHour = totalMinutes / 60;
                            long currentHour = totalHour % 24;

                            //求出现在的天数
                            long totalDay = totalHour / 24;


                            textViewBottom.setText(totalDay + "天" + currentMinute + "分" + currentSecond  + "秒");
                        } else {
                            long dateMinus =sysTime-timeStamp ;
                            long totalSeconds = dateMinus / 1000;

                            //求出现在的秒
                            long currentSecond = totalSeconds % 60;

                            //求出现在的分
                            long totalMinutes = totalSeconds / 60;
                            long currentMinute = totalMinutes % 60;

                            //求出现在的小时
                            long totalHour = totalMinutes / 60;
                            long currentHour = totalHour % 24;

                            //求出现在的天数
                            long totalDay = totalHour / 24;
                            textViewBottom.setText(totalDay + "天" + currentMinute + "分" + currentSecond + "秒");
                        }

                    }


                       // CharSequence sysTimeStr = dateFormatterChina.format( sysTime);//时间显示格式
                        //textView.setText(sysTimeStr); //更新时间
                        break;
                    default:
                        break;

                }
            }
        };




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {

                    Bundle bundle =  data.getExtras();
                    ItimeRecord record= (ItimeRecord) bundle.getSerializable("record");
                    itimeRecords.add(record);
                    itimeAdaper.notifyDataSetChanged();

                    if(itimeRecords.size()!=0){
                        ItimeRecord recordFirst=itimeRecords.get(0);
                        //textViewTop.setText(recordFirst.getTitle());
                        //textViewMiddle.setText(recordFirst.getYear()+"年"+recordFirst.getMonth()+"月"+recordFirst.getDay()+"日");
                        new TimeThread().start();
                    }



                    //Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private class OnFabClickListener implements View.OnClickListener {//响应加号

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,TimeRecordEditActivity.class);
            ItimeRecord itimeRecord=new ItimeRecord();
            itimeRecord.setMotto("aaaa");
            Bundle bundle=new Bundle();
            bundle.putSerializable("record",itimeRecord);
            intent.putExtras(bundle);

            startActivityForResult(intent, 1);

        }
    }


    private class ItimeRecordArrayAdapter extends ArrayAdapter<ItimeRecord>{
        int resourceId;
        public ItimeRecordArrayAdapter(@NonNull Context context, int resource, @NonNull List<ItimeRecord> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //super.getView(position, convertView, parent);
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_record_left);
            TextView top = (TextView)item.findViewById(R.id.text_view_record_top);
            TextView middle=item.findViewById(R.id.text_view_record_middle);
            TextView bottom = (TextView)item.findViewById(R.id.text_view_record_bottom);

            ItimeRecord itimeRecord_item= this.getItem(position);
            img.setImageResource(R.drawable.pic1);
            top.setText(itimeRecord_item.getTitle());
            middle.setText(itimeRecord_item.getYear()+"年"+itimeRecord_item.getMonth()+"月"+itimeRecord_item.getDay()+"日");
            bottom.setText(itimeRecord_item.getMotto());

            return item;
        }

    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}



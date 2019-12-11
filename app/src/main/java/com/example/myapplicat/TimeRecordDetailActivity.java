package com.example.myapplicat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplicat.data.ItimeRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeRecordDetailActivity extends AppCompatActivity {
    //MainActivity.ArrayList<ItimeRecord> itimeRecords;
    private ItimeRecord record;
    private ImageView imageViewScan,imageViewDelete,imageViewShare,imageViewPen,imageViewJiantou;
    private Handler mHandler;
    private TextView textView;
    private  int position;

    private Intent rIntent;
    private Bundle rBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record_detail);

        imageViewScan=this.findViewById(R.id.image_view_scan);
        imageViewDelete=this.findViewById(R.id.image_view_delete);
        imageViewShare=this.findViewById(R.id.image_view_share);
        imageViewPen=this.findViewById(R.id.image_view_pen);
        imageViewJiantou=this.findViewById(R.id.image_view_jiantou);
        textView=this.findViewById(R.id.textView2);

        record = (ItimeRecord) getIntent().getSerializableExtra("record");
        position=getIntent().getIntExtra("position",-1);

        rIntent=new Intent();
        rBundle=new Bundle();
        rIntent.putExtra("position",position);


        new TimeThread().start();
        mHandler = new Handler(){  //实现主页面倒计时
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1: {
                        SimpleDateFormat dateFormatterChina = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        long sysTime = System.currentTimeMillis();//获取系统时间


                        Calendar cal = Calendar.getInstance();
                        cal.set(record.getYear(), record.getMonth(), record.getDay(), record.getHour(), record.getMinute(), 0);//第二个参数月份是实际值减1
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


                            textView.setText(totalDay + "天" + currentMinute + "分" + currentSecond  + "秒");
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
                            textView.setText(totalDay + "天" + currentMinute + "分" + currentSecond + "秒");
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




        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_FIRST_USER, rIntent);
                TimeRecordDetailActivity.this.finish();
            }
        });

        imageViewPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TimeRecordDetailActivity.this,TimeRecordEditActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("recordDToE",record);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);

            }
        });

        imageViewJiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent rIntent=new Intent();
                //Bundle bundle=new Bundle();
                rBundle.putSerializable("record",record);
                rIntent.putExtras(rBundle);


                setResult(RESULT_OK, rIntent);
                TimeRecordDetailActivity.this.finish();


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                     record = (ItimeRecord) bundle.getSerializable("record");


                    //Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
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

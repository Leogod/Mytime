package com.example.myapplicat;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplicat.data.Edititem;
import com.example.myapplicat.data.ItimeRecord;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private  ArrayList<ItimeRecord> itimeRecords=new ArrayList<ItimeRecord>();
    private  ItimeRecordArrayAdapter itimeAdaper;
    private ListView listViewRecords;
    private AppBarConfiguration mAppBarConfiguration;

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
        itimeRecords.add(new ItimeRecord(R.drawable.marker,"没有标题","你好明天",2019,12,20,17,52));
        itimeAdaper=new ItimeRecordArrayAdapter(this,R.layout.list_item_record,itimeRecords);
        listViewRecords.setAdapter(itimeAdaper);




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



                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
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
}

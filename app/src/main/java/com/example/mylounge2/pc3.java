package com.example.mylounge2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class pc3 extends AppCompatActivity {
    private TextView tv_pcID;
    private TextView tv_pcName;
    private Button btn_reserve;
    private ListView listView;
    private list_listAdapter adapter;
    private List<list> userList;
    Date mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc3);
        tv_pcName = findViewById(R.id.tv_pcName);
        btn_reserve = findViewById(R.id.btn_reserve);
        Intent intent = getIntent();
        String list_startHour;
        String list_startMin;
        String list_endHour;
        String list_endMin;
        final String userID = intent.getStringExtra("userID");
        final String pcID = intent.getStringExtra("pcID");
        String pcName =  intent.getStringExtra("pcName");
        final String userName = intent.getStringExtra("userName");
        final String startTime = intent.getStringExtra("startTime");
        final String endTime = intent.getStringExtra("endTime");
        final String userPhNum = intent.getStringExtra("userPhNum");
        tv_pcName.setText(pcName);

        listView = findViewById(R.id.listView);
        userList = new ArrayList<list>();
        adapter = new list_listAdapter(getApplicationContext(), userList);
        listView.setAdapter(adapter);
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));

            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userDate, userStartTime, userEndTime;

            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userDate = object.getString("date");
                userStartTime = object.getString("startTime");
                userEndTime = object.getString("endTime");
                int userStartHour =  Integer.parseInt(userStartTime) / 100;
                int userStartMin =  Integer.parseInt(userStartTime) % 100;
                int userEndHour=  Integer.parseInt(userEndTime) / 100;;
                int userEndMin=  Integer.parseInt(userEndTime) % 100;;
                if(userStartHour < 10){
                    list_startHour = "0" + Integer.toString(userStartHour);
                }
                else{
                    list_startHour = Integer.toString(userStartHour);
                }
                if(userStartMin < 10){
                    list_startMin = "0" + Integer.toString(userStartMin);
                }
                else{
                    list_startMin = Integer.toString(userStartMin);
                }
                if(userEndHour < 10){
                    list_endHour = "0" + Integer.toString(userEndHour);
                }
                else{
                    list_endHour = Integer.toString(userEndHour);
                }
                if(userEndMin < 10){
                    list_endMin = "0" + Integer.toString(userEndMin);
                }
                else{
                    list_endMin = Integer.toString(userEndMin);
                }

                list list1 = new list(userDate, "시작 " + list_startHour + "시 " + list_startMin +"분", "종료 " + list_endHour + "시 " + list_endMin + "분");
                userList.add(list1);
                count++;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                mDate = new Date();
                String current = dateFormat.format(mDate);
                Date today = dateFormat.parse(current); //현재 날짜
                Date end = dateFormat.parse(userDate);  //예약한 날짜
                int compare = today.compareTo(end);     //날짜 비교
                if (compare > 0) {                      //현재 날짜 > 예약한 날짜
                    userList.clear();                   //초기화
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pc3.this, reserve.class);
                intent.putExtra("userID",userID);
                intent.putExtra("pcID",pcID);
                intent.putExtra("userName",userName);
                intent.putExtra("userPhNum",userPhNum);
                startActivity(intent);
            }
        });
    }
}
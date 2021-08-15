package com.example.mylounge2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylounge2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class look extends AppCompatActivity {
    private TextView tv_pcID;
    private TextView tv_pcName;
    private Button btn_reserve;
    private ListView listView;
    private list_listAdapter adapter;
    private List<list> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        tv_pcName = findViewById(R.id.tv_pcName);
        btn_reserve = findViewById(R.id.btn_reserve);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String pcID = intent.getStringExtra("pcID");
        String pcName = intent.getStringExtra("pcName");
        final String userName = intent.getStringExtra("userName");
        final String startTime = intent.getStringExtra("startTime");
        final String endTime = intent.getStringExtra("endTime");
        tv_pcName.setText(pcName);

        listView = findViewById(R.id.listView);
        userList = new ArrayList<list>();
        adapter = new list_listAdapter(getApplicationContext(), userList);
        listView.setAdapter(adapter);
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));

            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userDate, userStartTime, userEndTime;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userDate = object.getString("date");
                userStartTime = object.getString("startTime");
                userEndTime = object.getString("endTime");

                list list1 = new list(userDate,userStartTime, userEndTime);
                userList.add(list1);
                count++;

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
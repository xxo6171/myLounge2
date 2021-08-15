package com.example.mylounge2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylounge2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class look_reserve extends AppCompatActivity {
    private TextView tv_pcName;
    private Button btn_manage;
    private Button btn_pc1;
    private Button btn_pc2;
    private Button btn_pc3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_reserve);
        tv_pcName = (TextView)findViewById(R.id.tv_pcName);
        btn_pc1 = findViewById(R.id.btn_pc1);
        btn_pc2 = findViewById(R.id.btn_pc2);
        btn_pc3 = findViewById(R.id.btn_pc3);

        btn_pc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask1().execute();
            }
        });
        btn_pc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask2().execute();
            }
        });
        btn_pc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask3().execute();
            }
        });


    }
    //각각의 PC 조회 창으로 이동
    class BackgroundTask1 extends AsyncTask<Void, Void, String>{
        String target1;
        Intent intent = getIntent();
        final String userID1 = intent.getStringExtra("userID");
        final String userName1 = intent.getStringExtra("userName");
        final String startTime1 = intent.getStringExtra("startTime");
        final String endTime1 = intent.getStringExtra("endTime");
        final String userPhNum = intent.getStringExtra("userPhNum");
        @Override
        protected void onPreExecute(){
            target1 = "https://infognu.ansan.ac.kr/~lounge/List1.php";
        }
        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target1);  //URL 객체 생성

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소느는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder 클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄 씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n"); //stringBuilder에 넣어줌
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result){
            Intent intent = new Intent(look_reserve.this, pc1.class);
            intent.putExtra("userList", result);
            intent.putExtra("pcName", "PC 1");
            intent.putExtra("pcID","1");
            intent.putExtra("userID",userID1);
            intent.putExtra("userName",userName1);
            intent.putExtra("startTime",startTime1);
            intent.putExtra("endTime",endTime1);
            intent.putExtra("userPhNum",userPhNum);
            startActivity(intent);
        }
    }
    class BackgroundTask2 extends AsyncTask<Void, Void, String>{
        String target2;
        Intent intent = getIntent();
        final String userID2 = intent.getStringExtra("userID");
        final String userName2 = intent.getStringExtra("userName");
        final String startTime2 = intent.getStringExtra("startTime");
        final String endTime2 = intent.getStringExtra("endTime");
        final String userPhNum = intent.getStringExtra("userPhNum");
        @Override
        protected void onPreExecute(){
            target2 = "https://infognu.ansan.ac.kr/~lounge/List2.php";
        }
        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target2);  //URL 객체 생성

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소느는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder 클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄 씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n"); //stringBuilder에 넣어줌
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result){
            Intent intent = new Intent(look_reserve.this, pc2.class);
            intent.putExtra("userList", result);
            intent.putExtra("pcName", "PC 2");
            intent.putExtra("pcID","2");
            intent.putExtra("userID",userID2);
            intent.putExtra("userName",userName2);
            intent.putExtra("startTime",startTime2);
            intent.putExtra("endTime",endTime2);
            intent.putExtra("userPhNum",userPhNum);
            startActivity(intent);
        }
    }
    class BackgroundTask3 extends AsyncTask<Void , Void, String>{
        String target3;
        Intent intent = getIntent();
        final String userID3 = intent.getStringExtra("userID");
        final String userName3 = intent.getStringExtra("userName");
        final String startTime3 = intent.getStringExtra("startTime");
        final String endTime3 = intent.getStringExtra("endTime");
        final String userPhNum = intent.getStringExtra("userPhNum");
        @Override
        protected void onPreExecute(){
            target3 = "https://infognu.ansan.ac.kr/~lounge/List3.php";
        }
        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target3);  //URL 객체 생성

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소느는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder 클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄 씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n"); //stringBuilder에 넣어줌
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result){
            Intent intent = new Intent(look_reserve.this, pc3.class);
            intent.putExtra("userList", result);
            intent.putExtra("pcName", "PC 3");
            intent.putExtra("pcID","3");
            intent.putExtra("userID",userID3);
            intent.putExtra("userName",userName3);
            intent.putExtra("startTime",startTime3);
            intent.putExtra("endTime",endTime3);
            intent.putExtra("userPhNum",userPhNum);
            startActivity(intent);
        }
    }
}
















package com.example.mylounge2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mylounge2.R;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {
    private EditText et_id, et_name;
    private Button btn_login;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_id = findViewById(R.id.et_id);           //학번
        et_name = findViewById(R.id.et_name);       //이름
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {        //클릭 시 회원가입 화면으로 이동
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                    //로그인 버튼 클릭시
                //EditText에 현재 입력한 값을 get으로 가져온다.
                Intent intent = getIntent();
                final String startTime = intent.getStringExtra("startTime");
                final String endTime = intent.getStringExtra("endTime");
                String userID = et_id.getText().toString();
                String userName = et_name.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //데이터 운반체계 제이슨 사용
                        //예외처리
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //실패 성공 알려줌
                            boolean success = jsonObject.getBoolean("success");
                            if(success){ //로그인에 성공한 경우
                                String userID = jsonObject.getString("userID");
                                String userName = jsonObject.getString("userName");
                                String userPhNum = jsonObject.getString("userPhNum");
                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userName", userName);
                                intent.putExtra("userPhNum", userPhNum);
                                intent.putExtra("startTime", startTime);
                                intent.putExtra("endTime", endTime);
                                startActivity(intent);          //성공 후 메인화면으로 이동

                            }else{ //로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login.this);
                queue.add(loginRequest);

            }
        });

    }
    //뒤로가기 두 번 누르면 종료
    @Override
    public void onBackPressed(){
        ActivityCompat.finishAffinity(this);
    }
}
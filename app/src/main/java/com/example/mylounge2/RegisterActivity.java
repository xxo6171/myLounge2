package com.example.mylounge2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id, et_name, et_phNum;
    private Button btn_register, btn_recheck;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {    //액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_phNum = findViewById(R.id.et_phNum);
        btn_recheck = findViewById(R.id.btn_recheck);
        btn_register = findViewById(R.id.btn_register);

        btn_recheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //데이터 운반체계 제이슨 사용
                        //예외처리
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //실패 성공 알려줌
                            boolean success = jsonObject.getBoolean("success");
                            if(success){ //중복이 없는 경우
                                AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
                                ad.setTitle("중복 확인");
                                ad.setMessage("사용 가능합니다.");
                                ad.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                ad.show();
                                count = 1;
                            }else { //중복이 있는경우
                                AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
                                ad.setTitle("중복 확인");
                                ad.setMessage("이미 가입된 학번입니다.");
                                ad.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                ad.show();
                                et_id.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //서버로 Volley를 이용해서 요청을 함
                userVaildateRequest userVaildateRequest = new userVaildateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(userVaildateRequest);

            }
        });

        //회원가입 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText에 현재 입력한 값을 get으로 가져온다.
                if (count == 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
                    ad.setTitle("확인");
                    ad.setMessage("중복 체크를 해주세요");
                    ad.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    ad.show();
                } else {
                    String userID = et_id.getText().toString();
                    String userName = et_name.getText().toString();
                    final String userPhNum = et_phNum.getText().toString();
                    if ((et_id.getText().toString().equals("") || et_id.getText().toString() == null) ||
                            (et_name.getText().toString().equals("") || et_name.getText().toString() == null) ||
                            (et_phNum.getText().toString().equals("") || et_phNum.getText().toString() == null)) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
                        ad.setTitle("다시 입력");
                        ad.setMessage("입력하지 않은 부분이 있습니다.\n다시 입력 하세요");
                        ad.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        ad.show();
                    } else {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //데이터 운반체계 제이슨 사용
                                //예외처리
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //실패 성공 알려줌
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) { //회원가입에 성공한 경우
                                        Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, login.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        //서버로 Volley를 이용해서 요청을 함
                        RegisterRequest registerRequest = new RegisterRequest(userID, userName, userPhNum, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                    }
                }
            }
        });


    }


}
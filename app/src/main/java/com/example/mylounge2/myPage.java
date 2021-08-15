package com.example.mylounge2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class myPage extends AppCompatActivity {
  TextView studentID, studentName, studentPhNum;
  Button btn_nameChange, btn_phChange, btn_userCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        String userPhNum = intent.getStringExtra("userPhNum");

        studentID = findViewById(R.id.studentID);
        studentName=findViewById(R.id.studentName);
        studentPhNum=findViewById(R.id.studentPhNum);
        btn_nameChange =findViewById(R.id.btn_nameChange);
        btn_phChange = findViewById(R.id.btn_phChange);
        btn_userCancel = findViewById(R.id.btn_userCancel);
        studentID.setText(userID);
        studentName.setText(userName);
        studentPhNum.setText(userPhNum);

        btn_nameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(myPage.this);
                ad.setTitle("이름 변경");
                ad.setMessage("변경할 이름을 입력해주세요");
                final EditText nameEd = new EditText(myPage.this);
                ad.setView(nameEd);

                ad.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //실패 성공 알려줌
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){ //취소 성공한 경우
                                        Toast.makeText(getApplicationContext(),"변경이 완료되었습니다.\n로그인 화면으로 넘어갑니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(myPage.this, login.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        nameChangeRequest nameChangeRequest  = new nameChangeRequest(nameEd.getText().toString(), userID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(myPage.this);
                        queue.add(nameChangeRequest);
                    }
                });
                ad.show();
            }
        });

        btn_phChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(myPage.this);
                ad.setTitle("전화번호 변경");
                ad.setMessage("변경할 번호을 입력해주세요");
                final EditText phEd = new EditText(myPage.this);
                phEd.setInputType(InputType.TYPE_CLASS_PHONE);
                phEd.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(11)
                });
                ad.setView(phEd);
                ad.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //실패 성공 알려줌
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){ //취소 성공한 경우
                                        Toast.makeText(getApplicationContext(),"변경이 완료되었습니다.\n로그인 화면으로 넘어갑니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(myPage.this, login.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        phNumChangeRequest phNumChangeRequest  = new phNumChangeRequest(phEd.getText().toString(), userID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(myPage.this);
                        queue.add(phNumChangeRequest);
                    }
                });
                ad.show();

            }
        });

        btn_userCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(myPage.this);
                ad.setTitle("회원 탈퇴");
                ad.setMessage("회원을 탈퇴하시겠습니까?\n맞으면 '회원탈퇴'를 입력해주세요");
                final EditText cancelEd = new EditText(myPage.this);

                ad.setView(cancelEd);

                ad.setPositiveButton("완료", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(cancelEd.getText().toString().equals("회원탈퇴")){
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        //실패 성공 알려줌
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { //취소 성공한 경우
                                            Toast.makeText(getApplicationContext(), "탈퇴가 완료되었습니다.\n로그인 화면으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(myPage.this, login.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            userCancelRequest userCancelRequest = new userCancelRequest(userID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(myPage.this);
                            queue.add(userCancelRequest);
                            cancelAlarm();
                            cancelAlarm2();
                        }
                        else{
                                dialogInterface.dismiss();
                                Toast.makeText(getApplicationContext(), "잘못 입력하였거나 빈 값입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ad.show();

            }
        });




    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
    private void cancelAlarm2() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
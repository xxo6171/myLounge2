package com.example.mylounge2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mylounge2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class reserve extends AppCompatActivity{
    private TextView tv_pcName;
    TextView tv_startTime, tv_endTime;
    TextView time_cal;
    TextView tv_date;
    EditText et_date;
    DatePickerDialog.OnDateSetListener setListener;
    Button btn_cal;
    int stHour, stMin;   //시작 시간 분
    int enHour, enMin;   //종료 시간 분
    //조건에 따른 시간 총 데이터 값
    String stHour_d;
    String stMin_d;
    String enHour_d;
    String enMin_d;
    String list_startTime;
    String list_endTime;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        tv_startTime = findViewById(R.id.tv_startTime);
        tv_endTime = findViewById(R.id.tv_endTime);

        btn_cal = findViewById(R.id.btn_cal);
        tv_pcName = findViewById(R.id.tv_pcName);
        et_date = findViewById(R.id.et_date);
        et_date.setShowSoftInputOnFocus(false);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        //달력
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        reserve.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = year + "-" + month + "-" + day;
                        et_date.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //시작시간
        tv_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        reserve.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {


                                stHour = hourOfDay;
                                stMin = minute;
                                //시간 분 저장
                                String time = stHour + ":" + stMin;

                                //24시간 설정
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    //12 시간 설정
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    //텍스트보기에서 선택한 시간 설정
                                    tv_startTime.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                //투명 배경 설정
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //이전에 선택한 시간 표시

                timePickerDialog.updateTime(stHour, stMin);
                //Show dialog
                timePickerDialog.show();

            }
        });
        //종료 시간
        tv_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        reserve.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                enHour = hourOfDay;
                                enMin = minute;
                                //시간 분 저장
                                String time = enHour + ":" + enMin;
                                //24시간 설정
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    //12 시간 설정
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    //텍스트보기에서 선택한 시간 설정
                                    tv_endTime.setText(f12Hours.format(date));
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                //투명 배경 설정
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //이전에 선택한 시간 표시
                timePickerDialog.updateTime(enHour, enMin);
                //Show dialog
                timePickerDialog.show();

            }
        });

        //예약 버튼 클릭시 메인화면으로 넘어감
        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                final String userID5 = intent.getStringExtra("userID");
                final String pcID5 = intent.getStringExtra("pcID");
                final String userName5 = intent.getStringExtra("userName");
                final String userPhNum = intent.getStringExtra("userPhNum");
                final String startTime5;
                final String endTime5;
                final String date2 = et_date.getText().toString();


                if (stHour < 10) {
                    stHour_d = "0" + Integer.toString(stHour);
                } else {
                    stHour_d = Integer.toString(stHour);
                }
                if (stMin < 10) {
                    stMin_d = "0" + Integer.toString(stMin);
                } else {
                    stMin_d = Integer.toString(stMin);
                }
                if (enHour < 10) {
                    enHour_d = "0" + Integer.toString(enHour);
                } else {
                    enHour_d = Integer.toString(enHour);
                }
                if (enMin < 10) {
                    enMin_d = "0" + Integer.toString(enMin);
                } else {
                    enMin_d = Integer.toString(enMin);
                }
                list_startTime = stHour_d + " : " + stMin_d;
                list_endTime = enHour_d + " : " + enMin_d;
                startTime5 = stHour_d + stMin_d;
                endTime5 = enHour_d + enMin_d;
                if ((et_date.getText().toString().equals("") || et_date.getText().toString() == null) ||
                        (tv_startTime.getText().toString().equals("") || tv_startTime.getText().toString() == null) ||
                        (tv_endTime.getText().toString().equals("") || tv_endTime.getText().toString() == null)) {
                    androidx.appcompat.app.AlertDialog.Builder ad = new androidx.appcompat.app.AlertDialog.Builder(reserve.this);
                    ad.setTitle("다시 입력");
                    ad.setMessage("선택하지 않은 시간이 있습니다.\n다시 입력 하세요");
                    ad.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    ad.show();
                } else {
                    AlertDialog.Builder ad = new AlertDialog.Builder(reserve.this);
                    ad.setTitle("예약");
                    ad.setMessage("예약하시겠습니까?");
                    ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(String response) {
                                    //데이터 운반체계 제이슨 사용
                                    //예외처리
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        //실패 성공 알려줌
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { //예약에 성공한 경우
                                            Calendar c = Calendar.getInstance();    //시작 날짜
                                            Calendar d = Calendar.getInstance();    //종료 날짜
                                            c.set(Calendar.YEAR, year);
                                            c.set(Calendar.MONTH, month);
                                            c.set(Calendar.DAY_OF_MONTH, day);
                                            c.set(Calendar.HOUR_OF_DAY, stHour);
                                            c.set(Calendar.MINUTE, stMin);
                                            c.set(Calendar.SECOND, 0);
                                            d.set(Calendar.YEAR, year);
                                            d.set(Calendar.MONTH, month);
                                            d.set(Calendar.DAY_OF_MONTH, day);
                                            d.set(Calendar.HOUR_OF_DAY, enHour);
                                            d.set(Calendar.MINUTE, enMin);
                                            d.set(Calendar.SECOND, 0);
                                            startAlarm(c);  //시작 시간
                                            endAlarm(d); //종료시간
                                            Toast.makeText(getApplicationContext(), "예약에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(reserve.this, MainActivity.class);
                                            intent.putExtra("userID", userID5);
                                            intent.putExtra("pcID", pcID5);
                                            intent.putExtra("startTime", startTime5);
                                            intent.putExtra("endTime", endTime5);
                                            intent.putExtra("userName", userName5);
                                            intent.putExtra("userPhNum",userPhNum);
                                            startActivity(intent);
                                        } else { //예약에 실패한 경우
                                            Toast.makeText(getApplicationContext(), "이미 예약중인 시간입니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            Reserve_Request reserve_Request = new Reserve_Request(userID5, pcID5, date2, startTime5, endTime5, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(reserve.this);
                            queue.add(reserve_Request);
                        }
                    });
                    ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    ad.show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void endAlarm(Calendar d){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, d.getTimeInMillis(), pendingIntent);
    }

}
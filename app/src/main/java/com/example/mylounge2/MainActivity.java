package com.example.mylounge2;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mylounge2.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button look_reserve;    //조회 예약 버튼
    private Button cancel;          //예약 취소 버튼
    private ImageView btn_navi;     //네비게이션 메뉴
    private NavigationView naviview;
    private ViewFlipper viewFlipper;
    private TextView tv_total;
    private Button btn_notice, btn_eclass, btn_intranet, btn_library;
    private long backBtnTime= 0;            //뒤로가기 두번

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        final String userPhNum = intent.getStringExtra("userPhNum");
        final String startTime = intent.getStringExtra("startTime");
        final String endTime = intent.getStringExtra("endTime");
        look_reserve = findViewById(R.id.look_reserve);      //특정 아이디 가져오기
        cancel = findViewById(R.id.cancel);
        btn_navi = findViewById(R.id.btn_navi);
        naviview = findViewById(R.id.naviView);
        btn_notice = findViewById(R.id.btn_notice);
        btn_eclass = findViewById(R.id.btn_eclass);
        btn_intranet = findViewById(R.id.btn_intranet);
        btn_library = findViewById(R.id.btn_library);
        //예약 조회 버튼
        look_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, look_reserve.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);
                intent.putExtra("userPhNum",userPhNum);
                startActivity(intent);      //액티비티 이동
            }
        });
        //예약 취소
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                final String userID = intent.getStringExtra("userID");
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("예약");
                ad.setMessage("예약 취소하시겠습니까?");
                ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //데이터 운반체계 제이슨 사용
                                //예외처리
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //실패 성공 알려줌
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){ //취소 성공한 경우
                                        Toast.makeText(getApplicationContext(),"취소가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                                    }else{ //취소 실패한 경우
                                        Toast.makeText(getApplicationContext(),"취소에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        CancelRequest cancelRequest  = new CancelRequest(userID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(cancelRequest);
                        cancelAlarm();
                        cancelAlarm2();
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
        });
        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //안산대학교 공지사항 웹사이트로 이동
                Intent notice = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ansan.ac.kr/universityLife/noticeList.do"));
                startActivity(notice);
            }
        });
        btn_eclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //이클래스 로그인 웹사이트로 이동
                Intent eclass = new Intent(Intent.ACTION_VIEW, Uri.parse("https://eclass.ansan.ac.kr/ilos/m/main/login_form.acl"));
                startActivity(eclass);
            }
        });
        btn_intranet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {      //인트라넷 웹사이트로 이동
                Intent intranet = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ea.ansan.ac.kr"));
                startActivity(intranet);
            }
        });
        btn_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {       //학술정보원 사이트로 이동
                Intent library = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.ansan.ac.kr/"));
                startActivity(library);
            }
        });

        //네비 버튼 클릭시 오른쪽에서 왼쪽으로 나옴
        final DrawerLayout layout_drawer = findViewById(R.id.layout_drawer);
        btn_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_drawer.openDrawer(GravityCompat.END);
            }

        });
        naviview.setNavigationItemSelectedListener(this);

        //프로필에서 로그인한 이름 값 받아오기
       View header = naviview.getHeaderView(0);
        TextView state = (TextView)header.findViewById(R.id.state);
        String pro_userName = intent.getStringExtra("userName");
        state.setText(pro_userName + "님 반갑습니다");
        //

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        final String userPhNum = intent.getStringExtra("userPhNum");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        if(menuItem.getItemId() == R.id.myPage){
            Intent myPageIntent = new Intent(MainActivity.this, myPage.class);
            myPageIntent.putExtra("userID",userID);
            myPageIntent.putExtra("userName",userName);
            myPageIntent.putExtra("userPhNum",userPhNum);
            startActivity(myPageIntent);
        }
        else if(menuItem.getItemId() == R.id.loadmap){  //오시는길
            Intent loadmap_intent = new Intent(MainActivity.this, loadmap.class);
            startActivity(loadmap_intent);
        }
        else if(menuItem.getItemId() == R.id.bus){      //스쿨버스 시간표
            Intent bus_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ansan.ac.kr/universityLife/schoolBus.do"));
            startActivity(bus_intent);
        }
        else if(menuItem.getItemId() == R.id.homepage){ //안산대학교 홈페이지
            Intent homepage_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ansan.ac.kr"));
            //url 연결
            startActivity(homepage_intent);
        }
        else if(menuItem.getItemId() == R.id.logout){ //로그아웃
            Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
            Intent logout_intent = new Intent(MainActivity.this, login.class);
            logout_intent.putExtra("startTime", startTime);
            logout_intent.putExtra("endTime", endTime);
            startActivity(logout_intent);
        }
        final DrawerLayout layout_drawer = findViewById(R.id.layout_drawer);
        layout_drawer.closeDrawers();

        return false;
    }
    //뒤로가기 버튼 클릭 시
    @Override
    public void onBackPressed(){
        final DrawerLayout layout_drawer = findViewById(R.id.layout_drawer);
        if(layout_drawer.isDrawerOpen(GravityCompat.END)){
            layout_drawer.closeDrawers();
        }
        else{
            long curTime = System.currentTimeMillis();
            long gapTime = curTime - backBtnTime;
            if(0 <= gapTime && 2000 >= gapTime){
                ActivityCompat.finishAffinity(this);
            }
            else{
                backBtnTime = curTime;
                Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();

            }
        }
    }
    //예약이 취소될 때
    //시작 알람 취소
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
    //종료 알람 취소
    private void cancelAlarm2() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
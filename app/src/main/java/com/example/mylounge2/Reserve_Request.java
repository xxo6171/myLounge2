package com.example.mylounge2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Reserve_Request extends StringRequest {
    // 서버 URL 설정 (php 파일 연동)
    final static private String URL = "https://infognu.ansan.ac.kr/~lounge/Reserve.php";
    private Map<String, String> map;

    public Reserve_Request(String userID, String pcID, String date, String startTime, String endTime,
                           Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("userID", userID);
        map.put("pcID", pcID);
        map.put("date",date);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

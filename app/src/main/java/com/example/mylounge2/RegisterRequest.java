package com.example.mylounge2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
   // 서버 URL 설정 (php 파일 연동)
    final static private String URL = "https://infognu.ansan.ac.kr/~lounge/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userName, String userPhNum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userName", userName);
        map.put("userPhNum", userPhNum);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

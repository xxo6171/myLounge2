package com.example.mylounge2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class nameChangeRequest extends StringRequest {

    final static private String URL = "https://infognu.ansan.ac.kr/~lounge/nameChange.php";
    private Map<String, String> map;

    public nameChangeRequest(String userName, String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userName",userName);
        map.put("userID", userID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

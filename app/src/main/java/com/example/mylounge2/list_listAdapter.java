package com.example.mylounge2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mylounge2.R;

import java.util.List;

public class list_listAdapter extends BaseAdapter {

    private Context context;
    private List<list> userList;

    public list_listAdapter(Context context, List<list> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list, null);
        TextView date = v.findViewById(R.id.startDate);
        TextView startTime = v.findViewById(R.id.startTime);
        TextView endTime = v.findViewById(R.id.endTime);
        date.setText(userList.get(i).getDate());
        startTime.setText(userList.get(i).getStartTime());
        endTime.setText(userList.get(i).getEndTime());

        return v;
    }
}

package com.example.bt_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class NameAdapter extends BaseAdapter {
    private Context mContext;
    private Random mRandom = new Random();
    private ArrayList<String> mNames = new ArrayList<>();
    public NameAdapter(Context context){
        mContext =context;
        for (int i=0; i<5; i++){
            mNames.add(getRandomName());
        }

    }

    @Override
    public int getCount() {
        return mNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View ConvertView, ViewGroup parent) {
        View view = null;
        if (ConvertView==null){
           view= LayoutInflater.from(mContext).inflate(R.layout.name_view,parent,false);

        } else {
            view = ConvertView;
        }
        String name = mNames.get(position);
        TextView nameTextView =(TextView)view.findViewById(R.id.name_view);
        nameTextView.setText(name);
        TextView positionTextView = (TextView)view.findViewById(R.id.position_view);
        positionTextView.setText(String.format("I'm #%d",(position+1)));
        return view;
    }
    private String getRandomName(){
        String[] names = new String[]{
                "Hannah", "Emily", "Sarah", "Madison", "Brianna",
                "Kaylee", "Kaitlyn", "Hailey", "Alexis", "Elizabeth",
                "Michael", "Jacob", "Matthew", "Nicholas", "Christopher",
                "Josept", "Zachary", "Joshua", "Andrew", "Willam"
        };
        return names[mRandom.nextInt(names.length)];
    }
}

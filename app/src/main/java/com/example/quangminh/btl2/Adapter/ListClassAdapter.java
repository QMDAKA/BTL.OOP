package com.example.quangminh.btl2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quangminh.btl2.Entity.Classroom;
import com.example.quangminh.btl2.R;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/18/2016.
 */
public class ListClassAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Classroom> list_classroom;
    private LayoutInflater mInflater;

    public ListClassAdapter(Context context,
                            ArrayList<Classroom> classrooms) {
        super();
        this.context = context;
        this.list_classroom = classrooms;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list_classroom.size();
    }

    @Override
    public Object getItem(int position) {
        return list_classroom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        convertView = mInflater.inflate(R.layout.classroomitem, parent, false);
        holder = new ViewHolder();
        holder.maLh = (TextView) convertView.findViewById(R.id.maLH);
        holder.maMh = (TextView) convertView.findViewById(R.id.maMH);
        holder.tenMh = (TextView) convertView.findViewById(R.id.tenMH);
        holder.thoigian = (TextView) convertView.findViewById(R.id.time);
        holder.diadiem = (TextView) convertView.findViewById(R.id.address);
        Classroom item = list_classroom.get(position);
        holder.tenMh.setText(item.getSubject().getName());
        holder.maMh.setText(item.getSubject().getMhp());
        holder.maLh.setText(item.getMslh());
        holder.diadiem.setText(item.getAddress());
        holder.thoigian.setText(item.getTimeBegin() + "-" + item.getTimeEnd() + "-" + item.getDay());

        return convertView;

    }

    static class ViewHolder {
        TextView maMh;
        TextView tenMh;
        TextView maLh;
        TextView thoigian;
        TextView diadiem;


    }
}



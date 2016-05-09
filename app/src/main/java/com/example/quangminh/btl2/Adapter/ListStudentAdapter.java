package com.example.quangminh.btl2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quangminh.btl2.Entity.Classroom;
import com.example.quangminh.btl2.Entity.Student;
import com.example.quangminh.btl2.R;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/19/2016.
 */
public class ListStudentAdapter  extends BaseAdapter{
    private Context context;
    private ArrayList<Student> list_student;
    private LayoutInflater mInflater;



    public ListStudentAdapter(Context context, ArrayList<Student> list_student) {
        this.context = context;
        this.list_student = list_student;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list_student.size();
    }

    @Override
    public Object getItem(int position) {
        return list_student.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        convertView = mInflater.inflate(R.layout.list_student_item, parent, false);
        holder = new ViewHolder();
        holder.name=(TextView)convertView.findViewById(R.id.nameStudent);
        holder.grade=(TextView)convertView.findViewById(R.id.gradeStudent);
        holder.sign = (TextView)convertView.findViewById(R.id.signStudent);
        Student item=list_student.get(position);
        holder.name.setText(item.getName());
        holder.sign.setText(item.getName().toUpperCase().charAt(0)+"");
        holder.grade.setText( item.getGrade());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView grade;
        TextView sign;

    }
}

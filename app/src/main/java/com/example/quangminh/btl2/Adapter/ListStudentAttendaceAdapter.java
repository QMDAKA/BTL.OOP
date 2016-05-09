package com.example.quangminh.btl2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quangminh.btl2.Entity.Student;
import com.example.quangminh.btl2.R;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/26/2016.
 */
public class ListStudentAttendaceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Student> list_student;
    private ArrayList<Long> listID;
    private LayoutInflater mInflater;
    public static ArrayList<Student> listSVnghihoc;

    public static ArrayList<Student> getListSVnghihoc() {
        return listSVnghihoc;
    }

    public static void setListSVnghihoc(ArrayList<Student> listSVnghihoc) {
        ListStudentAttendaceAdapter.listSVnghihoc = listSVnghihoc;
    }

    public ListStudentAttendaceAdapter(Context context, ArrayList<Student> list_student,ArrayList<Long> listID) {
        this.context = context;
        this.list_student = list_student;
        this.listID=listID;
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
        convertView = mInflater.inflate(R.layout.studentattendance_item, parent, false);
        holder = new ViewHolder();
        holder.name=(TextView)convertView.findViewById(R.id.nameStudent2);
        holder.grade=(TextView)convertView.findViewById(R.id.gradeStudent2);
        holder.radioGroup=(RadioGroup)convertView.findViewById(R.id.rg);
        holder.radioButton=(RadioButton)convertView.findViewById(R.id.kodihoc);
        holder.sign = (TextView)convertView.findViewById(R.id.signStudent);
        final Student item=list_student.get(position);
        holder.name.setText(item.getName());
        holder.sign.setText(item.getName().toUpperCase().charAt(0)+"");
        holder.grade.setText(item.getGrade());
        if(checkIdOfArrayListStudent(item.getId())){
            holder.radioButton.setChecked(true);
            holder.radioGroup.setEnabled(false);
        }
        else {
            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.kodihoc) {
                        listSVnghihoc.add(item);
                    } else {
                        listSVnghihoc.remove(item);
                    }
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView grade;
        RadioGroup radioGroup;
        RadioButton radioButton;
        TextView sign;
    }
    // add comment
    boolean checkIdOfArrayListStudent(long Id){
        for(int i=0;i<listID.size();i++){
            if(Id==listID.get(i)){
                return true;
            }
        }
        return false;
    }
}

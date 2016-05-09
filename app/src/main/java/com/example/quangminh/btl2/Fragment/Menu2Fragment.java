package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quangminh.btl2.Entity.Classroom;
import com.example.quangminh.btl2.Entity.Mark;
import com.example.quangminh.btl2.Entity.Student;
import com.example.quangminh.btl2.Entity.StudentAttendance;
import com.example.quangminh.btl2.Entity.Subject;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Quang Minh on 4/19/2016.
 */
public class Menu2Fragment extends Fragment {
    private Button mark;
    private Button studentattendance;
    ArrayList<Student> studentArrayList=new ArrayList<>();
    public String url_get_class_by_id= MainActivity.url+"classrooms/";
    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    private Classroom classroom;
    public static Menu2Fragment newInstance() {
        Menu2Fragment myFragment = new Menu2Fragment();
        return myFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu2,null);
        mark=(Button)view.findViewById(R.id.mark);
        studentattendance=(Button)view.findViewById(R.id.studentattendance);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setCornerRadius(10);
        gdDefault.setColor(Color.parseColor("#fbafba"));
        mark.setBackgroundDrawable(gdDefault);
        studentattendance.setBackgroundDrawable(gdDefault);
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                ListStudentMarkFragment listStudentMarkFragment = new ListStudentMarkFragment();
                listStudentMarkFragment.setStudentArrayList(studentArrayList);
                fragmentTransaction.replace(R.id.container, listStudentMarkFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        studentattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                ListLessonFragment li = new ListLessonFragment();
                li.setClassroom(getClassroom());
                fragmentTransaction.replace(R.id.container, li);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        if(studentArrayList.size()==0) {
            new getStudentInClass().execute();
        }
        return view;
    }
    class getStudentInClass extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpRequest request = HttpRequest.get(url_get_class_by_id+getClassroom().getId());
            String response=request.body();
            try {
                JSONObject json  = new JSONObject(response);
                int success = json.getInt("success");
                if(success==1) {
                    JSONObject c = json.getJSONObject("response");
                    JSONArray students=c.getJSONArray("students");
                    JSONArray studentattendances=c.getJSONArray("studentAttendances");
                    for(int i=0;i<students.length();i++){
                        Student s=new Student();
                        JSONObject student=students.getJSONObject(i);
                        s.setId(student.getLong("id"));
                        JSONArray marks=student.getJSONArray("marks");
                        for(int j=0;j<marks.length();j++){
                            JSONObject mark=marks.getJSONObject(j);
                            Mark m=new Mark();
                            m.setId(mark.getLong("id"));
                            m.setDiemGiuaKy(mark.getDouble("diemGiuaKy"));
                            m.setDiemCuoiKy(mark.getDouble("diemCuoiKy"));
                            m.setTongDiem(m.getDiemCuoiKy()*0.7+m.getDiemGiuaKy()*0.3);
                            s.getMarks().add(m);
                        }
                        s.setName(student.getString("name"));
                        s.setGrade(student.getString("grade"));
                        studentArrayList.add(s);
                    }
                    for(int i=0;i<studentattendances.length();i++){
                        StudentAttendance s=new StudentAttendance();
                        JSONObject studentattendance=studentattendances.getJSONObject(i);
                        s.setId(studentattendance.getLong("id"));
                        Date d=new Date(studentattendance.getInt("date"));
                        s.setDate(d);
                        classroom.getStudentAttendances().add(s);
                    }
                    Log.e("student list",studentArrayList.size()+"");
                    classroom.setStudents(studentArrayList);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
}

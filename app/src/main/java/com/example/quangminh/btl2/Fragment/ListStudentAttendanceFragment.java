package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.quangminh.btl2.Adapter.ListStudentAttendaceAdapter;
import com.example.quangminh.btl2.Entity.Student;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quang Minh on 4/26/2016.
 */
public class ListStudentAttendanceFragment extends Fragment {
    public String url_post_attendance= MainActivity.url+"attendances/";
    public String url_load_attendance=MainActivity.url+"attendances/";
    public static ListStudentAttendanceFragment newInstance() {
        ListStudentAttendanceFragment myFragment = new ListStudentAttendanceFragment();
        return myFragment;
    }
    Button button;
    ListView lv;
    ArrayList<Student> students;
    ArrayList<Long> studentattendances;
    ListStudentAttendaceAdapter adapter;
    private long idStudentAttendance;

    public long getIdStudentAttendance() {
        return idStudentAttendance;
    }

    public void setIdStudentAttendance(long idStudentAttendance) {
        this.idStudentAttendance = idStudentAttendance;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_studentattendance,null);
        lv=(ListView) view.findViewById(R.id.listView3);
        button=(Button)view.findViewById(R.id.save_list_student);
        ListStudentAttendaceAdapter.listSVnghihoc=new ArrayList<>();
        studentattendances=new ArrayList<>();
        new loadListStudentAttendance().execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postAttendances().execute();
            }
        });
        return view;
    }
    class postAttendances extends AsyncTask<String,String,String>{
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
            url_post_attendance+=idStudentAttendance+"/addListStudent";
            List<Long> listID=new ArrayList<>();
            ArrayList<Student> list=ListStudentAttendaceAdapter.getListSVnghihoc();
            for(int i=0;i<list.size();i++){
                listID.add(list.get(i).getId());
            }
            ObjectMapper objectMapper=new ObjectMapper();
            try {
                String jsonInString=objectMapper.writeValueAsString(listID);
                HttpRequest request;
                request = HttpRequest.post(url_post_attendance)
                        .accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .acceptEncoding("gzip,deflate")
                        .contentType("application/json");
                int response = request.send(jsonInString).code();
                Log.e("response", response+ "");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    class loadListStudentAttendance extends AsyncTask<String,String,String>{
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
            adapter=new ListStudentAttendaceAdapter(getActivity(),getStudents(),studentattendances);
            if(studentattendances.size()>0){
                button.setVisibility(View.GONE);
            }
            lv.setAdapter(adapter);

        }

        @Override
        protected String doInBackground(String... params) {
            url_load_attendance+=idStudentAttendance+"/students";
            HttpRequest request = HttpRequest.get(url_load_attendance);
            String response=request.body();
            try {
                JSONObject json  = new JSONObject(response);
                int success = json.getInt("success");
                if(success==1){
                    JSONArray c = json.getJSONArray("response");
                    for(int i=0;i<c.length();i++){
                        JSONObject s=c.getJSONObject(i);
                        studentattendances.add(s.getLong("id"));

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}

package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quangminh.btl2.Adapter.ListClassAdapter;
import com.example.quangminh.btl2.Entity.Classroom;
import com.example.quangminh.btl2.Entity.Subject;
import com.example.quangminh.btl2.Entity.Teacher;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/18/2016.
 */
public class ListClassroomFragment extends Fragment {
    private int type;
    private Teacher teacher;
    public String url_get_list_class= MainActivity.url+"classrooms";
    ListClassAdapter adapter;
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ListView lv;
    ArrayList<Classroom> classrooms=new ArrayList<>();
    public static ListClassroomFragment newInstance() {
        ListClassroomFragment myFragment = new ListClassroomFragment();
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.listclass,null);
        lv=(ListView)view.findViewById(R.id.listView);
        classrooms=teacher.getClassrooms();
        new listClassAsyncTask().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Classroom c=classrooms.get(position);
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                Menu2Fragment menu2Fragment = new Menu2Fragment();
                menu2Fragment.setClassroom(c);
                fragmentTransaction.replace(R.id.container, menu2Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    class listClassAsyncTask extends AsyncTask<String,String,String>{
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
        protected String doInBackground(String... params) {

            for(int i=0;i<classrooms.size();i++){
                String url=url_get_list_class+"/"+classrooms.get(i).getId()+"/"+"subject";
                HttpRequest request = HttpRequest.get(url);
                String response=request.body();
                try {
                    JSONObject json  = new JSONObject(response);
                    int success = json.getInt("success");
                    if(success==1) {
                        JSONObject c = json.getJSONObject("response");
                        Subject subject=new Subject();
                        subject.setId(c.getLong("id"));
                        subject.setMhp(c.getString("mhp"));
                        subject.setName(c.getString("name"));
                        classrooms.get(i).setSubject(subject);

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }



            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            adapter=new ListClassAdapter(getActivity(),classrooms);
            lv.setAdapter(adapter);
        }
    }

}

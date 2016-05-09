package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/29/2016.
 */
public class ListRegisterClassroomFragment extends Fragment {
    private Long teacherId;
    public String url_get_list_register_class= MainActivity.url+"classrooms";
    ListClassAdapter adapter;
    public static int confirm=0;
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    ListView lv;
    BroadcastReceiver receiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String strAction = intent.getAction();
            if (strAction.equalsIgnoreCase(MainActivity.action_remove)) {
                Bundle bundle = intent.getBundleExtra("position");
                int a = bundle.getInt("positionItem");
                Classroom classroom=classrooms.get(a);
                MenuFragment.getTeacher().getClassrooms().add(classroom);
                classrooms.remove(a);
                adapter.notifyDataSetChanged();
                Intent brIntent = new Intent();
                brIntent.setAction(MainActivity.action_add);


            }
        }
    };


    ArrayList<Classroom> classrooms=new ArrayList<>();
    public static ListRegisterClassroomFragment newInstance() {
        ListRegisterClassroomFragment myFragment = new ListRegisterClassroomFragment();

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.listclass,null);
        lv=(ListView)view.findViewById(R.id.listView);
        new listRegisterClassroom().execute();
        new loadSubjectRegisterClassrooms().execute();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(MainActivity.action_remove);
        getActivity().registerReceiver(receiver, mFilter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertFragment alertFragment=new AlertFragment();
                alertFragment.setTeacherId(teacherId);
                alertFragment.setClassroomId(classrooms.get(position).getId());
                alertFragment.setPosition(position);
                alertFragment.show(getFragmentManager(), "confirm");


            }
        });
        return view;
    }

    class listRegisterClassroom extends AsyncTask<String,String,String>{
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
            url_get_list_register_class += "/registerClassroom/" + teacherId;
            HttpRequest request = HttpRequest.get(url_get_list_register_class);
            String response = request.body();
            try {
                JSONObject json  = new JSONObject(response);
                int success = json.getInt("success");
                if(success==1) {
                    JSONArray c = json.getJSONArray("response");
                    for(int i=0;i<c.length();i++){
                        Classroom classroom = new Classroom();
                        JSONObject b = c.getJSONObject(i);
                        classroom.setId(b.getLong("id"));
                        classroom.setMslh(b.getString("mslh"));
                        classroom.setTimeBegin(b.getString("timeBegin"));
                        classroom.setTimeEnd(b.getString("timeEnd"));
                        classroom.setAddress(b.getString("address"));
                        classroom.setDay(b.getString("day"));
                        classrooms.add(classroom);
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();

        }
    }
    class loadSubjectRegisterClassrooms extends AsyncTask<String,String,String>{
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
                String url=MainActivity.url+"classrooms/"+classrooms.get(i).getId()+"/"+"subject";
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

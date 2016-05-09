package com.example.quangminh.btl2.Fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quangminh.btl2.Adapter.ListClassAdapter;
import com.example.quangminh.btl2.Adapter.ListStudentAdapter;
import com.example.quangminh.btl2.Entity.Student;
import com.example.quangminh.btl2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quang Minh on 4/19/2016.
 */
public class ListStudentMarkFragment extends Fragment {
    private ArrayList<Student> studentArrayList;
    ListView lv;
    ListStudentAdapter adapter;
    public ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    public void setStudentArrayList(ArrayList<Student> studentArrayList) {
        this.studentArrayList = studentArrayList;
    }

    public static ListStudentMarkFragment newInstance() {
        ListStudentMarkFragment myFragment = new ListStudentMarkFragment();
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.liststudent,null);
        lv=(ListView)view.findViewById(R.id.listView2);
        adapter=new ListStudentAdapter(getActivity(),getStudentArrayList());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.support.v4.app.DialogFragment dialogFragment=new SetMarkDialogFragment();
                SetMarkDialogFragment df=new SetMarkDialogFragment();
                df.setStudent(studentArrayList.get(position));
                df.show(getFragmentManager(),"Set Mark");
               // dialogFragment.show(getFragmentManager(),"Set Mark");

            }
        });
        return view;
    }
}

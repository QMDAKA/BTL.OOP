package com.example.quangminh.btl2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.quangminh.btl2.Entity.Classroom;
import com.example.quangminh.btl2.R;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/22/2016.
 */
public class ListLessonFragment extends Fragment {
    public static ListLessonFragment newInstance() {
        ListLessonFragment myFragment = new ListLessonFragment();
        return myFragment;
    }
    Classroom classroom;

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_lesson,null);
        ArrayList<String > arrayList=new ArrayList<>();
        arrayList.add("Day 1");
        arrayList.add("Day 2");
        arrayList.add("Day 3");
        arrayList.add("Day 4");
        arrayList.add("Day 5");
        arrayList.add("Day 6");
        arrayList.add("Day 7");

        ListView lv=(ListView)view.findViewById(R.id.listLesson);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                ListStudentAttendanceFragment li=new ListStudentAttendanceFragment();
                li.setStudents(classroom.getStudents());
                li.setIdStudentAttendance(classroom.getStudentAttendances().get(position).getId());
                fragmentTransaction.replace(R.id.container, li);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}

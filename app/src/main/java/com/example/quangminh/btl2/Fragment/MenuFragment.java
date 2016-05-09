package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quangminh.btl2.Entity.Teacher;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Quang Minh on 4/16/2016.
 */
public class MenuFragment extends Fragment {
    private Button classWasRegister;
    private Button registerClass;
    private Button infor;
    private static Teacher teacher;

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher teacher) {
        MenuFragment.teacher = teacher;
    }

    public static MenuFragment newInstance() {
        MenuFragment myFragment = new MenuFragment();
        return myFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        MainActivity.fab.show();
        View view=inflater.inflate(R.layout.menu,null);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setCornerRadius(10);
        gdDefault.setColor(Color.parseColor("#fbafba"));
        init(view);
        infor.setBackgroundDrawable(gdDefault);
        classWasRegister.setBackgroundDrawable(gdDefault);
        registerClass.setBackgroundDrawable(gdDefault);
        registerClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToListClassRoomRegisterFragment();
            }
        });
        classWasRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToListClassRoomFragment(1);
            }
        });

        return view;
    }
    void init(View view){
        classWasRegister=(Button)view.findViewById(R.id.classwasregister);
        registerClass=(Button)view.findViewById(R.id.RegisterClass);
        infor=(Button)view.findViewById(R.id.Infor);
    }
    void jumpToListClassRoomFragment(int type) {
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();
        ListClassroomFragment listClassroomFragment = new ListClassroomFragment();
        listClassroomFragment.setType(type);
        listClassroomFragment.setTeacher(getTeacher());
        fragmentTransaction.replace(R.id.container, listClassroomFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
    void jumpToListClassRoomRegisterFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();
        ListRegisterClassroomFragment listClassroomFragment = new ListRegisterClassroomFragment();
        listClassroomFragment.setTeacherId(getTeacher().getId());
        fragmentTransaction.replace(R.id.container, listClassroomFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
    

}

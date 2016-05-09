package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quangminh.btl2.Entity.Classroom;
import com.example.quangminh.btl2.Entity.Teacher;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.github.kevinsawicki.http.HttpRequest;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quang Minh on 4/15/2016.
 */
public class LoginFragment extends Fragment {
    private EditText email;
    private EditText pass;
    private Button button;
    private Button register;
    private TextView text;
    Teacher teacher=new Teacher();

    public static String url_check_pass= MainActivity.url+"teachers/check";

    public static LoginFragment newInstance() {
        LoginFragment myFragment = new LoginFragment();
        return myFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        teacher.setId(new Long(0));
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        MainActivity.fab.hide();
        View view = inflater.inflate(R.layout.login,null);
        SpannableString content = new SpannableString("Forgot your password ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        text = (TextView) view.findViewById(R.id.textView2);
        text.setText(content);
        email = (EditText) view.findViewById(R.id.email);
        pass = (EditText) view.findViewById(R.id.pass);
        button = (Button) view.findViewById(R.id.button1);
        register=(Button) view.findViewById(R.id.register);
        button.setEnabled(false);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.GRAY);
        gdDefault.setCornerRadius(10);
        button.setBackgroundDrawable(gdDefault);
        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                boolean required = true;
                Validation.isEmailAddress(email, required);
                gdDefault.setColor(Color.RED);
                button.setEnabled(true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().compareTo("") != 0
                        && pass.getText().toString().compareTo("") != 0) {
                    gdDefault.setColor(Color.parseColor("#fbafba"));
                    button.setEnabled(true);
                } else {
                    gdDefault.setColor(Color.parseColor("#666666"));
                    button.setEnabled(false);
                }
            }
        });
        pass.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().compareTo("") != 0
                        && pass.getText().toString().compareTo("") != 0) {
                    gdDefault.setColor(Color.parseColor("#fbafba"));
                    button.setEnabled(true);
                } else {
                    gdDefault.setColor(Color.parseColor("#666666"));
                    button.setEnabled(false);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new checkUserTeacher().execute();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.container, registerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return view;

    }
    class checkUserTeacher extends AsyncTask<String ,String ,String>{
        ProgressDialog pDialog;
        String e=email.getText().toString();
        String p=pass.getText().toString();

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
        protected String doInBackground(String... args) {
            HttpRequest request = HttpRequest.get(url_check_pass, true, "email", e, "pass", p);
            String response=request.body();
            try {
                JSONObject json  = new JSONObject(response);
                int success = json.getInt("success");
                if(success==1) {
                    JSONObject c = json.getJSONObject("response");
                    teacher.setId(c.getLong("id"));
                    teacher.setName(c.getString("name"));
                    teacher.setPassword(c.getString("pass"));
                    teacher.setEmail(c.getString("email"));
                    JSONArray array = c.getJSONArray("classrooms");
                    for (int i = 0; i < array.length(); i++) {
                        Classroom classroom = new Classroom();
                        JSONObject b = array.getJSONObject(i);
                        classroom.setId(b.getLong("id"));
                        classroom.setMslh(b.getString("mslh"));
                        classroom.setTimeBegin(b.getString("timeBegin"));
                        classroom.setTimeEnd(b.getString("timeEnd"));
                        classroom.setAddress(b.getString("address"));
                        classroom.setDay(b.getString("day"));
                        teacher.getClassrooms().add(classroom);
                    }

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            if(teacher.getId()!=0){
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                MenuFragment menuFragment = new MenuFragment();
                menuFragment.setTeacher(teacher);
                fragmentTransaction.replace(R.id.container, menuFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else {
                Toast.makeText(getContext(),"Invalid email or password",Toast.LENGTH_LONG).show();


            }
        }
    }
}

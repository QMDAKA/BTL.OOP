package com.example.quangminh.btl2.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Quang Minh on 4/18/2016.
 */

public class RegisterFragment extends Fragment {
    private EditText name;
    private EditText email;
    private EditText pass;
    private EditText passConfirm;
    private Button button;
    public String url_register= MainActivity.url+"teachers";

    public static RegisterFragment newInstance() {
        RegisterFragment myFragment = new RegisterFragment();
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register,null);
        init(view);
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
                if(email.getText().toString().compareTo("") != 0
                        && pass.getText().toString().compareTo("") != 0&&name.getText().toString().compareTo("")!=0){
                   if( pass.getText().toString().compareTo(passConfirm.getText().toString())==0){
                        new register().execute();
                   }
                   else{
                       Toast.makeText(getContext(),"pass and pass confirm not correct",Toast.LENGTH_LONG).show();
                   }
                }
            }
        });
        return view;
    }

    void init(View view){
        name =(EditText) view.findViewById(R.id.nameteacher);
        email=(EditText) view.findViewById(R.id.emailteacher);
        pass=(EditText) view.findViewById(R.id.passwordteacher);
        passConfirm=(EditText)view.findViewById(R.id.confirmpassword);
        button=(Button)view.findViewById(R.id.create);

    }
    class register extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        String e=email.getText().toString();
        String p=pass.getText().toString();
        String n=name.getText().toString();
        int response;
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
            String uri=url_register+"?name="+n+"&email="+e+"&pass="+p;
            response=HttpRequest.post(uri).code();
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            if(response>=200&&response<=300){
                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.container, loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else{
                Toast.makeText(getContext(),"failed",Toast.LENGTH_LONG).show();


            }
        }
    }
}

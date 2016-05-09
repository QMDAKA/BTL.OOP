package com.example.quangminh.btl2.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.quangminh.btl2.Entity.Mark;
import com.example.quangminh.btl2.Entity.Student;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
/*import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Quang Minh on 4/19/2016.
 */
public class SetMarkDialogFragment extends DialogFragment {
    Student student;
    EditText dgk,dck;
    public String url_put_mark= MainActivity.url+"marks/";
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.mark_dialog_fragment, null))
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        new putMark().execute();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SetMarkDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    @Override
    public void onStart()
    {
        super.onStart();
        dgk=(EditText)this.getDialog().findViewById(R.id.diemgiuaky);
        dgk.setText(student.getMarks().get(0).getDiemGiuaKy() + "");
        dck=(EditText)this.getDialog().findViewById(R.id.diemcuoiky);
        dck.setText(student.getMarks().get(0).getDiemCuoiKy()+"");

    }

    class putMark extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        String diemck=dck.getText().toString();
        String diemgk=dgk.getText().toString();

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
            pDialog.cancel();
            super.onPostExecute(s);

        }

        @Override
        protected String doInBackground(String... params) {
            url_put_mark=url_put_mark+student.getMarks().get(0).getId();
            Mark mark=new Mark();
            mark=student.getMarks().get(0);
            mark.setDiemCuoiKy( Double.parseDouble(diemck));
            mark.setDiemGiuaKy(Double.parseDouble(diemgk));
            mark.setTongDiem(mark.getDiemCuoiKy()*0.7+mark.getDiemGiuaKy()*0.3);

            ObjectMapper objectMapper=new ObjectMapper();
            try {
                String jsonInString=objectMapper.writeValueAsString(mark);
                HttpRequest request;
                request = HttpRequest.put(url_put_mark)
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
}

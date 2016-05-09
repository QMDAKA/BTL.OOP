package com.example.quangminh.btl2.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.quangminh.btl2.Adapter.ListClassAdapter;
import com.example.quangminh.btl2.MainActivity;
import com.example.quangminh.btl2.R;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Quang Minh on 4/29/2016.
 */
public class AlertFragment extends DialogFragment {
    Long classroomId;
    Long teacherId;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                        // Set Dialog Title
                .setTitle("Confirm")
                        // Set Dialog Message
                .setMessage("Are you sure")

                        // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new addRelationClassTeacher().execute();
                        Intent brIntent = new Intent();
                        brIntent.setAction(MainActivity.action_remove);
                        Bundle bundle = new Bundle();
                        bundle.putInt("positionItem", getPosition());
                        brIntent.putExtra("position", bundle);
                        getActivity().sendBroadcast(brIntent);

                    }
                })

                        // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }
    class addRelationClassTeacher extends AsyncTask<String,String,String>{
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
            String url_post= MainActivity.url+"classrooms/"+classroomId+"/teachers/"+teacherId;
            HttpRequest request;
            request = HttpRequest.post(url_post)
                    .accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .acceptEncoding("gzip,deflate")
                    .contentType("application/json");
            int response = request.send("").code();
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
        }
    }
}

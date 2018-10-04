package com.gitjaipur.univ;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class feedback extends Fragment {

    private DatabaseReference mdatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View myView =  inflater.inflate(R.layout.feedback,container,false);
        final EditText feedback = myView.findViewById(R.id.feedback_input);
        final EditText name = myView.findViewById(R.id.name_input);

        Button submit = myView.findViewById(R.id.submit);
        mdatabase= FirebaseDatabase.getInstance().getReference().child("user");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String feed = feedback.getText().toString();
                String nam = name.getText().toString();

                if(!TextUtils.isEmpty(feed)&&!TextUtils.isEmpty(nam))
                {
//create a map
                    HashMap< String,String> hm =
                            new HashMap< String,String>();
                    hm.put("name", nam);
                    hm.put("feedback", feed);

                    mdatabase.setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                feedback.setText("");
                                name.setText("");
//do whatever you want, your details have been uploaded to your database- mdatabase
                                AlertDialog dialog = new AlertDialog.Builder(feedback.getContext())
                                        .setTitle("submitted")
                                        .setMessage("your feedback is submitted ! Thank you .")
                                        .setPositiveButton("ok",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        feedback.setText("");
                                                        name.setText("");
                                                    }
                                                }).create();
                                dialog.show();
                            }
                        }
                    });
                }
                else {
                    AlertDialog dialog = new AlertDialog.Builder(feedback.getContext())
                            .setTitle(" Error ")
                            .setMessage("some error occur . Please try again !")
                            .setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).create();
                    dialog.show();
                }
            }
        });

        return myView;
    }
}

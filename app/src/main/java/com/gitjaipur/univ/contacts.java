package com.gitjaipur.univ;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class contacts extends Fragment {



    String[] names = {"Prof. N.P. Kaushik","Prof. Rajeev Gupta","Dr. Abha Jain","Sh. Rameshwar Lal Parsoya","Dr. A.K. Dwivedi",
                "Prof. H.D. Charan","Prof. S.C jain","Dr. Deepak Bhatia","Dr. Neeraj Jain","Dr. Sangeeta kaushal","S P Yadav"};
    String[] phones = {"Vice-Chancellor","Pro Vice-Chancellor","Registrar","Finance Comptroller","Controller of Examination"
                ,"Dean Academics", "Dean Faculty Affair","Nodal Office","Dy. Registrar ","Asstt. Registrar ","Asstt. Registrar "};
    String[] email = {"vcofficertu@yahoo.co.in","","registrar_rtu@yahoo.co.in","financeofficerrtu@gmail.com","coe@rtu.ac.in"
                ,"rtu.dir.acad@gmail.com","dean.facaffairs@rtu.ac.in","nodalit.rtu@gmail.com","njaindr@rtu.ac.in","",""};
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View myView =  inflater.inflate(R.layout.contacts,container,false);



            ListView lv = myView.findViewById(R.id.listview);

            lv.setAdapter(new customadapter(getActivity(),R.layout.custom_row,names,phones,email));





            return myView;

        }
      public class customadapter extends ArrayAdapter{

          public customadapter(Context context, int resource, String[] names, String[] phones, String[] objects) {
              super(context, resource, objects);
          }

          @Override
          public View getView(int position, View convertView, ViewGroup parent) {
              View v=((Activity)getContext()).getLayoutInflater().inflate(R.layout.custom_row,null);
              TextView tv_name = (TextView) v.findViewById(R.id.name);
              tv_name.setText(names[position]);
              TextView tv_phone = (TextView) v.findViewById(R.id.phone);
              tv_phone.setText(phones[position]);
              TextView tv_email = (TextView) v.findViewById(R.id.email);
              tv_email.setText(email[position]);

              return v;
          }
      }
    }


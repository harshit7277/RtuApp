package com.gitjaipur.univ;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class notify extends AppCompatActivity {

    private String TAG = notify.class.getSimpleName();
    private ListView lv;
    public int counter;
    ArrayList<HashMap<String, String>> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        notificationList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetNotification().execute();
    }

    private class GetNotification extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(notify.this,"","please wait",false);

            }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://rtuapk.co.nf/rtu.json";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    counter  = contacts.length();
                    // looping through All Contacts

                    for (int i = 0; i < counter; i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        int id = c.getInt("id");
                        String id1 = Integer.toString( id );
                        String title = c.getString("title");
                        String Date = c.getString("Date");
                        String Time = c.getString("Time");



                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id1);
                        contact.put("title", title);
                        contact.put("Date", Date );
                        contact.put("Time", Time);

                        // adding contact to contact list
                        notificationList.add(contact);

                        }
                } catch (final JSONException e) {
                    Log.e(TAG, "server error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }




            } else {
                Log.e(TAG, " server error.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(notify.this, notificationList,
                    R.layout.row_item, new String[]{ "title","Date","Time"},
                    new int[]{R.id.title,R.id.Date,R.id.Time} );
            lv.setAdapter(adapter);


            pd.dismiss();

        }
    }
}
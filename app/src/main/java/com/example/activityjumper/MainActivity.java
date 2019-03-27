package com.example.activityjumper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    private VolleyRequestQueue reqQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        reqQueue = new VolleyRequestQueue(getBaseContext());

        fetchActivityData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reqQueue.stop();
        reqQueue = null;
    }

    private void fetchActivityData()
    {
        final Spinner spinner = findViewById(R.id.spinner);
        final ArrayAdapter<String>  spinnerArrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        StringRequest strReq = new StringRequest(Request.Method.GET, "https://www.danielwinckler.se/android2/dummyoutput.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jArray = jsonObject.getJSONArray("travelSpeeds");
                            for (int i = 0; i < jArray.length(); i++)
                            {
                                JSONObject jo = jArray.getJSONObject(i);
                                spinnerArrayAdapter.add(jo.getString("Way"));
                            }
                        }
                        catch (JSONException je) {
                            spinnerArrayAdapter.add("JSONException: " + je.getMessage());
                        }
                        spinnerArrayAdapter.notifyDataSetChanged();
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinnerArrayAdapter.add("That didn't work!");
                        spinnerArrayAdapter.notifyDataSetChanged();
                    }
                });
        StringRequest strRequest = reqQueue.addStrRequest(strReq);
    }

    public void buttonGotoImgLoadOnClick(View view)
    {
        Intent intent = new Intent(this, CatalystImageActivity.class);
        startActivity(intent);
    }
}

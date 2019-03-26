package com.example.activityjumper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.HttpURLConnection;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

class VolleyRequestQueue {
    RequestQueue requestQueue;
    Cache cache;
    Network network;

    public VolleyRequestQueue(Context appContext)
    {
        cache = new DiskBasedCache(appContext.getCacheDir(), 1024 * 1024); // 1MB cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    public Request addRequest(Request request)
    {
        return requestQueue.add(request);
    }

    public void stop()
    {
        requestQueue.stop();
    }
}

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
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String>  spinnerArrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item);
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
        Request request = reqQueue.addRequest(strReq);
    }

    private JSONObject getJsonData(String jsonStr)
    {
        return null;
    }

    private String getString(String webAddr)
    {
        String s = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(webAddr);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            /*
            InputStreamReader isw = new InputStreamReader(in);


            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                //System.out.print(current);
            }
            */
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                // Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
            }

            s = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return s;
    }

    private void fillWayOfTravelSpinner(JSONObject jsonData) throws JSONException {
        JSONArray ways = jsonData.getJSONArray("travelSpeeds");
        if (ways != null)
        {
            for (int i = 0; i < ways.length(); i++)
            {
                JSONObject jo =  ways.getJSONObject(i);
                System.out.println(jo);
            }
        }
        else
        {
            System.out.println("fillWayOfTravelSpinner: ways == null");
        }
    }
}

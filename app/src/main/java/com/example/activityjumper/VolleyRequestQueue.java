package com.example.activityjumper;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageRequest;

public class VolleyRequestQueue {
    private RequestQueue requestQueue;
    private Cache cache;
    private Network network;

    VolleyRequestQueue(Context appContext)
    {
        cache = new DiskBasedCache(appContext.getCacheDir(), 1024 * 1024); // 1MB cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    StringRequest addStrRequest(StringRequest strRequest)
    {
        return (StringRequest)requestQueue.add(strRequest);
    }

    ImageRequest addImageRequest(ImageRequest imgRequest)
    {
        return (ImageRequest)requestQueue.add(imgRequest);
    }

    void stop()
    {
        requestQueue.stop();
    }
}

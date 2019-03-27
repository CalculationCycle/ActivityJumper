package com.example.activityjumper;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.VolleyError;

public class CatalystImageActivity extends AppCompatActivity {

    private VolleyRequestQueue reqQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalyst_image);
    }

    @Override
    protected void onResume(){
        super.onResume();
        reqQueue = new VolleyRequestQueue(getBaseContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        reqQueue.stop();
        reqQueue = null;
    }

    public void buttonLoadCatalystImgOnClick(View view)
    {
        String imgUrl = "https://www.danielwinckler.se/android2/catalyst.jpg";
        ImageRequest imageRequest = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView catImgView = findViewById(R.id.catalystImageView);
                        catImgView.setImageBitmap(response);
                    }
                },
                355, 192, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //listener.onData(null, "获取验证码失败 :( 请重试几次");
                        System.out.println("VolleyError: " + error.getMessage());
                    }
                }
                );
        reqQueue.addImageRequest(imageRequest);
    }
}

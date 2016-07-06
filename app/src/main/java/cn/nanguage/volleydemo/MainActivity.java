package cn.nanguage.volleydemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import cn.nanguage.volleydemo.MyApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    MyApplication helper = MyApplication.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        //final ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
        //1、创建请求队列
        /*RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        *//**2、实例化StringRequest请求
         *  第一个参数选择Request.Method.GET即get请求方式
         *  第二个参数的url地址根据文档所给
         *  第三个参数Response.Listener 请求成功的回调
         *  第四个参数Response.ErrorListener 请求失败的回调
         *//*
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://marsweather.ingenology.com/v1/latest/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //String s即为服务器返回的数据
                        Log.d("TAG", s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(),volleyError);
            }
        });
        //3、将请求添加进请求队列
        requestQueue.add(stringRequest);*/

        /*RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        ImageRequest imageRequest = new ImageRequest(
                "http://ww1.sinaimg.cn/large/610dc034jw1f5hpzuy3r7j20np0zkgpd.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.defaultimg);
            }
        });

        mQueue.add(imageRequest);
    }*/

        //RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("TAG", response.toString());
                        try {
                            JSONArray images = response.getJSONArray("results");
                            int index = new Random().nextInt(images.length());

                            JSONObject imageItem = images.getJSONObject(index);

                            String imageUrl = imageItem.getString("url");
                            loadImg(imageUrl);
                            //Log.d("TAG", imageUrl.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);

            }
        });
        helper.add(jsonObjectRequest);
    }
    /**
     * Downloads and displays the picture using Volley.
     * @param imageUrl the URL of the picture.
     */
    private void loadImg(String imageUrl) {
        // Retrieves an image specified by the URL, and displays it in the UI
        ImageRequest request = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageError(error);
                    }
                });

        // we don't need to set the priority here;
        // ImageRequest already comes in with
        // priority set to LOW, that is exactly what we need.
        helper.add(request);
    }
    private void imageError(Exception e) {
        e.printStackTrace();
    }
}


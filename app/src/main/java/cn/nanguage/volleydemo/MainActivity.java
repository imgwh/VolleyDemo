package cn.nanguage.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1、创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        /**2、实例化StringRequest请求
         *  第一个参数选择Request.Method.GET即get请求方式
         *  第二个参数的url地址根据文档所给
         *  第三个参数Response.Listener 请求成功的回调
         *  第四个参数Response.ErrorListener 请求失败的回调
         */
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
        requestQueue.add(stringRequest);
    }
}
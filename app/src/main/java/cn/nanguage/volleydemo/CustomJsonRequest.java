package cn.nanguage.volleydemo;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;

import org.json.JSONObject;
/**
 * Created by imgwh on 2016/7/6.
 */
public class CustomJsonRequest extends JsonObjectRequest{

    public CustomJsonRequest(int method, String url, JSONObject jsonRequest,
                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }


}

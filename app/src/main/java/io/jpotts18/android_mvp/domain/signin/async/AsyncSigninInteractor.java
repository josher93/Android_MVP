package io.jpotts18.android_mvp.domain.signin.async;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.jpotts18.android_mvp.domain.customs.VolleySingleton;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public class AsyncSigninInteractor implements IAsyncSigninInteractor
{
    @Override
    public void validateCredentialsAsync(final OnSigninFinishedListener listener, Context context, String username, String password, String info, String externlIp, String deviceId)
    {
        JSONObject jObject = new JSONObject();
        try
        {
            jObject.put("email", username);
            jObject.put("password", password);
            jObject.put("deviceInfo", info);
            jObject.put("deviceIP", externlIp);
            jObject.put("deviceID", deviceId);
            System.out.println(jObject);
        } catch (JSONException e1)
        {
            e1.printStackTrace();
        }

        VolleySingleton.getInstance(context).addToRequestQueue(new JsonObjectRequest(
            Request.Method.POST,
            "http://csncusgats.cloudapp.net:8074/v1/signin/",
            jObject,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    listener.onSuccess();
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    listener.onError();
                }
            }), 0);


    }
}

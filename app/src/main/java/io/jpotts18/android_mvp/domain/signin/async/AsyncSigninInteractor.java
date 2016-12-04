package io.jpotts18.android_mvp.domain.signin.async;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.jpotts18.android_mvp.domain.customs.VolleySingleton;
import io.jpotts18.android_mvp.domain.models.ErrorResponseViewModel;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public class AsyncSigninInteractor implements IAsyncSigninInteractor
{
    String Token;
    String Balance;
    String CountryID;
    String iso3Code;
    String PhoneCode;
    int SessionID;
    boolean VendorM;

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
        }
        catch (JSONException e1)
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
                        processResponse(response);
                        listener.onSuccess();
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                listener.onError(processVolleyError(error));
            }
        }), 0);

    }


    public void processResponse(JSONObject SigninResponseObject)
    {
        try
        {
            Token = SigninResponseObject.has("token") ? SigninResponseObject.getString("token") : "";
            Balance = SigninResponseObject.has("AvailableAmount") ? SigninResponseObject.getString("AvailableAmount") : "";
            SessionID = SigninResponseObject.has("SesionID") ? SigninResponseObject.getInt("SesionID") : 0;
            VendorM = SigninResponseObject.has("VendorM") ? SigninResponseObject.getBoolean("VendorM") : false;

            CountryID = SigninResponseObject.has("CountryID") ? SigninResponseObject.getString("CountryID") : "";
            iso3Code = SigninResponseObject.has("ISO3Code") ? SigninResponseObject.getString("ISO3Code") : "";
            PhoneCode = SigninResponseObject.has("PhoneCode") ? SigninResponseObject.getString("PhoneCode") : "";

            /*sessionManager = new SessionManager(Login.this);
            sessionManager.CreateLoginSession(UserEmail, Token, Balance, EncrptdPwd, SessionID, VendorM, CountryID, iso3Code, PhoneCode);*/


            //Hace el request para traer el perfil del usuario
            //RequestProfile();

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public ErrorResponseViewModel processVolleyError(VolleyError pError)
    {
        ErrorResponseViewModel errorResponse = new ErrorResponseViewModel();
        int statusCode = 0;
        NetworkResponse networkResponse = pError.networkResponse;

        if (networkResponse != null)
        {
            statusCode = networkResponse.statusCode;
        }

        if (pError instanceof TimeoutError || pError instanceof NoConnectionError)
        {
            errorResponse.setTitle("LO SENTIMOS");
            errorResponse.setLine1("Algo ha salido mal, revisa tu conexión a internet.");
            errorResponse.setAcceptButton("Aceptar");
        }
        else if (pError instanceof ServerError)
        {
            errorResponse.setTitle("LO SENTIMOS");
            errorResponse.setLine1("Algo ha salido mal, intentalo nuevamente.");
            errorResponse.setAcceptButton("Aceptar");
        }
        else if (pError instanceof NetworkError)
        {
            errorResponse.setTitle("LO SENTIMOS");
            errorResponse.setLine1("Algo ha salido mal, revisa tu conexión a internet.");
            errorResponse.setAcceptButton("Aceptar");
        }
        else if (pError instanceof AuthFailureError)
        {
            errorResponse.setTitle("CREDENCIALES INCORRECTAS");
            errorResponse.setLine1("Las credenciales son incorrectas, intenta de nuevo.");
            errorResponse.setAcceptButton("Aceptar");
        }

        return errorResponse;
    }


}

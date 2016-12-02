package io.jpotts18.android_mvp.domain.customs;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public final class VolleySingleton
{
    private ImageLoader imageLoader;
    private static VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context)
    {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
        //Esta parte de Imagen Loader es para la optimizacion de la imagen
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache()
        {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized VolleySingleton getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new VolleySingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public  void addToRequestQueue(Request req, int pMaxRetries)
    {
        int socketTimeout = 8000;//8 segundos

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, pMaxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        int retryIntents = policy.getCurrentRetryCount();
        Log.i("CurrentRetryCount", String.valueOf(retryIntents));

        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }
}

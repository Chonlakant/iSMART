package com.mncomunity1;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import com.mncomunity1.activity.LoginActivity;
import com.mncomunity1.event.ApiBus;
import com.mncomunity1.helper.MyPreferenceManager;
import com.mncomunity1.service.ApiHandler;
import com.mncomunity1.service.ApiService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


/**
 * Created by madhur on 3/1/15.
 */
public class IsmartApp extends Application implements Application.ActivityLifecycleCallbacks {
    private static PrefManager prefManager;
    public static final String TAG = IsmartApp.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    public static final String API_ENDPOINT = "http://mn-community.com";

    public static Activity currentActivity;
    private MyPreferenceManager pref;

    private static IsmartApp Instance;
    public static volatile Handler applicationHandler = null;
    private ApiHandler someApiHandler;

    private static OkHttpClient sHttpClient;
    private static Context sContext = null;


    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        Instance = this;
        applicationHandler = new Handler(getInstance().getMainLooper());
        prefManager = new PrefManager(getSharedPreferences("App", MODE_PRIVATE));
        saveInstallation(0);


        someApiHandler = new ApiHandler(this, buildApi(), ApiBus.getInstance());
        someApiHandler.registerForEvents();
    }


    public static Context getAppContext() {
        return sContext;
    }


    public static OkHttpClient getHttpClient() {
        if (sHttpClient == null) {
            sHttpClient = new OkHttpClient();
            int cacheSize = 10 * 1024 * 1024;
            File cacheLocation = new File(StorageUtils.getIdealCacheDirectory(IsmartApp.getAppContext()).toString());
            cacheLocation.mkdirs();
            com.squareup.okhttp.Cache cache = new com.squareup.okhttp.Cache(cacheLocation, cacheSize);
            sHttpClient.setCache(cache);
        }
        return sHttpClient;
    }


    ApiService buildApi() {

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_ENDPOINT)

                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //request.addQueryParam("p1", "var1");
                        //request.addQueryParam("p2", "");
                    }
                })

                .build()
                .create(ApiService.class);
    }

    public static void saveInstallation(int userId) {
    }
    public static void removeInstallation(int userId) {

    }

    public static IsmartApp get(Context context) {
        return (IsmartApp) context.getApplicationContext();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void logout(Context context) {
        pref.clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public static IsmartApp getInstance() {
        return Instance;
    }

    public static boolean applicationOnPause = false;
    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {
        currentActivity = arg0;
        Log.e("VMVMVM", "onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("VMVMVM", "onActivityDestroyed ");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        applicationOnPause = false;
        Log.e("VMVMVM", "onActivityResumed " + activity.getClass());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        applicationOnPause = true;
        Log.e("VMVMVM", "onActivityPaused " + activity.getClass());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("VMVMVM", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("VMVMVM", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("VMVMVM", "onActivityDestroyed ");
    }
    public static PrefManager getPrefManagerPaty() {
        return prefManager;
    }
}

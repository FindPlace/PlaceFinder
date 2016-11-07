package com.example.anupam.places.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by anupam on 06-11-2016.
 */
public class ConnectionDetector {
    static Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

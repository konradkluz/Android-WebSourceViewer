package com.konradkluz.websourceviewer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import javax.inject.Inject;

/**
 * Created by konradkluz on 18/10/2017.
 */

public class Utils {

    @Inject
    public Utils() {
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isUrlValid(String url) {
        return !(!Patterns.WEB_URL.matcher(url).matches() ||
                (!url.startsWith("http") && !url.startsWith("https")));
    }
}

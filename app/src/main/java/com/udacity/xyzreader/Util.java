package com.udacity.xyzreader;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String formatDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {
            Date dateFormatted = simpleDateFormat.parse(date);
            simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            return simpleDateFormat.format(dateFormatted);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}

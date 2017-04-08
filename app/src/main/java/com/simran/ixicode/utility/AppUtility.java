package com.simran.ixicode.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class AppUtility {

    private static String SP_PREFS_NAME = "thoughtbuzz";
    public static String SP_USERNAME = "username";
    public static String SP_PASSWORD = "password";

    public static Point getDeviceDimension(Context context) {
        if (context == null) {
            return new Point();
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        return size;
    }

    public boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) || ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING)))
            return true;
        else
            return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) || ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING));
    }

    public static String getUserToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_PREFS_NAME, 0);
        String username = sharedPreferences.getString(SP_USERNAME, "");
        String password = sharedPreferences.getString(SP_PASSWORD, "");
        return (username + ":" + password);
    }

    public static void printMessage(String msg) {
        if (AppConstant.DEBUG && msg != null && !msg.equalsIgnoreCase("")) {
            System.out.println(msg);
        }
    }

    public static void setBackGroundImage(String imageUrl, ImageView imageView, int placeHolder, Context context) {
        if (!TextUtils.isEmpty(imageUrl)) {
            if (context instanceof Activity) {
                Glide.with((Activity) context).load(imageUrl).placeholder(placeHolder).error(placeHolder).into(imageView);
            } else {
                Glide.with((Activity) context).load(imageUrl).placeholder(placeHolder).error(placeHolder).into(imageView);
            }
        } else {
            Log.e("Error", "Image URL cant be empty");
        }
    }

}

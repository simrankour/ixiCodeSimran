package com.simran.ixicode;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import NetworkingLib.loader.LoaderHandler;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class MyApplication extends Application {

    public static Typeface myTypeface;

    @Override
    public void onCreate() {
        super.onCreate();
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/CalibreRegular.ttf");

        LoaderHandler.setContext(this);
        LoaderHandler.setLoggingEnabled(true);
    }

    public static Toast getCustomToast(Context context, String str) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.toasttext);
        textView.setText(str);

        Toast toast = new Toast(context);
        toast.setView(view);
        return toast;

    }

}

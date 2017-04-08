package NetworkingLib;

import android.util.Log;

import NetworkingLib.loader.LoaderHandler;

/**
 * Created by Simranjit Kour on 8/4/17.
 */

public class Logger {

    /*
     Print error log in console
     */
    public static void e(String tag, String msg) {
        if (LoaderHandler.isLoggingEnabled()) {
            if (msg != null)
                Log.e(tag, msg);
        }
    }

    /*
    Print Debug log in console
     */
    public static void d(String tag, String msg) {
        if (LoaderHandler.isLoggingEnabled()) {
            if (msg != null)
                Log.d(tag, msg);
        }
    }

    /*
Print Information log in console
 */
    public static void i(String tag, String msg) {
        if (LoaderHandler.isLoggingEnabled()) {
            if (msg != null)
                Log.i(tag, msg);
        }
    }
}

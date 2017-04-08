package NetworkingLib.listeners;

import android.content.Loader;

import NetworkingLib.models.HTTPResponse;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public interface OnLoaderFinishedListener {

    void onLoaderFinished(Loader loader, HTTPResponse HTTPResponse);

}

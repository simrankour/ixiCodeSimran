package NetworkingLib.loader;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import NetworkingLib.listeners.OnLoaderFinishedListener;
import NetworkingLib.models.HTTPRequest;
import NetworkingLib.models.HTTPResponse;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class HttpLoaderCallbacks<M> implements LoaderManager.LoaderCallbacks<HTTPResponse> {

    private static final String TAG = "HttpLoaderCallbacks";
    private final HTTPRequest mHttpRequest;
    private final Context context;
    private OnLoaderFinishedListener mOnLoaderFinishedListener;


    public HttpLoaderCallbacks(Context context, OnLoaderFinishedListener mOnLoaderFinishedListener, HTTPRequest mHttpRequest) {
        this.mHttpRequest = mHttpRequest;
        this.context = context;
        this.mOnLoaderFinishedListener = mOnLoaderFinishedListener;
    }

    @Override
    public Loader<HTTPResponse> onCreateLoader(int id, Bundle bundle) {
        HttpAsyncTaskLoader httpAsyncTaskLoader = new HttpAsyncTaskLoader(context, mHttpRequest);
        return httpAsyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<HTTPResponse> loader, HTTPResponse data) {
        mOnLoaderFinishedListener.onLoaderFinished(loader, data);

    }

    @Override
    public void onLoaderReset(Loader<HTTPResponse> loader) {
    }

}

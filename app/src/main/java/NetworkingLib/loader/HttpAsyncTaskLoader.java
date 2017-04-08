package NetworkingLib.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import NetworkingLib.HttpUtility;
import NetworkingLib.Logger;
import NetworkingLib.models.HTTPRequest;
import NetworkingLib.models.HTTPResponse;


/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class HttpAsyncTaskLoader extends AsyncTaskLoader<HTTPResponse> {
    private static final String TAG = "HttpAsyncTaskLoader";
    private final Context context;
    private HTTPRequest mHTTPRequest;
    private HTTPResponse mHTTPResponse;

    public HttpAsyncTaskLoader(Context context, HTTPRequest httpRequest) {
        //Pass the reference of Context object in super constructor here.
        super(LoaderHandler.getContext());
        this.context = context;
        mHTTPRequest = httpRequest;
    }


    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        Logger.i(TAG, "*****onStartLoading() LoaderId:" + getId());
        super.onStartLoading();
        if (mHTTPResponse != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mHTTPResponse);
        } else if (mHTTPResponse == null) { //todo: changes
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }

    }

    @Override
    public HTTPResponse loadInBackground() {
        Logger.i(TAG, "*****loadInBackground() LoaderId:" + getId());
        HTTPResponse HTTPResponse = null;
        if (mHTTPRequest.getRequestType() == HTTPRequest.RequestType.POST) {
            HTTPResponse = HttpUtility.doPost(context, mHTTPRequest);
        } else if (mHTTPRequest.getRequestType() == HTTPRequest.RequestType.GET) {
            HTTPResponse = HttpUtility.doGet(context, mHTTPRequest);
        }

        return HTTPResponse;
    }

    /**
     * Subclasses must implement this to take care of stopping their loader,
     * as per {@link #stopLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #stopLoading()}.
     * This will always be called from the process's main thread.
     */
    @Override
    protected void onStopLoading() {
        Logger.i(TAG, "*****onStopLoading() LoaderId:" + getId());
//        super.onStopLoading();
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Subclasses must implement this to take care of resetting their loader,
     * as per {@link #reset()}.  This is not called by clients directly,
     * but as a result of a call to {@link #reset()}.
     * This will always be called from the process's main thread.
     */
    @Override
    protected void onReset() {
        Logger.i(TAG, "*****onReset() LoaderId:" + getId());
        super.onReset();
        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mHTTPResponse != null) {
            releaseResources(mHTTPResponse);
        }

    }

    /**
     * Called if the task was canceled before it was completed.  Gives the class a chance
     * to properly dispose of the result.
     *
     * @param data
     */
    @Override
    public void onCanceled(HTTPResponse data) {
        Logger.i(TAG, "*****onCanceled() LoaderId:" + getId());
        super.onCanceled(data);
        releaseResources(data);
    }

    /**
     * Sends the result of the load to the registered listener. Should only be called by subclasses.
     * <p/>
     * Must be called from the process's main thread.
     *
     * @param data the result of the load
     */
    @Override
    public void deliverResult(HTTPResponse data) {
        Logger.i(TAG, "*****deliverResult() LoaderId:" + getId());
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        HTTPResponse oldResponse = mHTTPResponse;
        mHTTPResponse = data;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
        }


        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.

        if (oldResponse != null && oldResponse != data) {
            releaseResources(oldResponse);
        }
    }

    private void releaseResources(HTTPResponse data) {
        // For a simple, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
        data = null;
    }

}

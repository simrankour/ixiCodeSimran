package NetworkingLib.loader;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;

import NetworkingLib.HttpConstants;
import NetworkingLib.dialogs.DialogHandler;
import NetworkingLib.listeners.OnLoadCompleteListener;
import NetworkingLib.listeners.OnLoaderFinishedListener;
import NetworkingLib.models.HTTPRequest;
import NetworkingLib.models.HTTPResponse;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class LoaderHandler {

    private static final String TAG = "LoaderHandler";
    // We will use this as loader id. Increase it by 1 every time you are going to create a new loader.
    private static int loaderId = -1;
    private static Context sContext;
    private static boolean loggingEnabled = false;
    private Activity mFragmentActivity;
    private HTTPRequest mHttpRequest;
    private OnLoadCompleteListener mOnLoadCompleteListener = null;
    private LoaderManager mLoaderManager;
    private DialogHandler mDialogHandler;

    public LoaderHandler(Activity fragmentActivity, HTTPRequest httpRequest, LoaderManager loaderManager) {
        this.mFragmentActivity = fragmentActivity;
        this.mHttpRequest = httpRequest;
        this.mLoaderManager = loaderManager;

        LoaderManager.enableDebugLogging(loggingEnabled);
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new NullPointerException(HttpConstants.APPLICATION_CONTEXT_NOT_SET_MESSAGE);
        }
        return sContext;
    }

    public static void setContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException(HttpConstants.ILLEGAL_ARGUMENT_EXCEPTION_CONTEXT_NULL);
        }
        LoaderHandler.sContext = context.getApplicationContext();
    }

    public static boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    /**
     * Enable and disable the log information of the LoaderHandler.
     *
     * @param loggingEnabled If true, the logger will print the log info in logcat.
     */
    public static void setLoggingEnabled(boolean loggingEnabled) {
        LoaderHandler.loggingEnabled = loggingEnabled;
    }

    /**
     * Will be called using activity reference
     *
     * @param activity
     * @param httpRequest
     * @return
     */
    public static LoaderHandler newInstance(Activity activity, HTTPRequest httpRequest) {
        return new LoaderHandler(activity, httpRequest, activity.getLoaderManager());
    }

    /**
     * Will be called using Fragment reference
     *
     * @param fragment
     * @param httpRequest
     * @return
     */
    public static LoaderHandler newInstance(Fragment fragment, HTTPRequest httpRequest) {
        return new LoaderHandler(fragment.getActivity(), httpRequest, fragment.getLoaderManager());
    }

    public void setOnLoadCompleteListener(OnLoadCompleteListener onLoadCompleteListener) {
        this.mOnLoadCompleteListener = onLoadCompleteListener;
    }

    /**
     * Start loading data from server. Data will be received in registered {@link OnLoadCompleteListener} listener.
     */
    public void loadData() {
        if (mHttpRequest.isShowProgressDialog()) {
            if (mDialogHandler == null) {
                mDialogHandler = new DialogHandler(mFragmentActivity);
            }
            mDialogHandler.showDefaultProgressDialog();
        }
        LoaderManager.LoaderCallbacks<HTTPResponse> myLoaderCallbacks = new HttpLoaderCallbacks<HTTPResponse>(getContext(), new OnLoaderFinishedListener() {

            @Override
            public void onLoaderFinished(Loader loader, HTTPResponse data) {
                if (mOnLoadCompleteListener != null) {
                    data.setResponseID(loader.getId());
                    mOnLoadCompleteListener.onLoadComplete(data);
                }
//                loader.reset();
                mLoaderManager.destroyLoader(loader.getId());
                if (mDialogHandler != null) {
                    mDialogHandler.dismissProgressDialog();
                }
            }
        }, mHttpRequest);
        mLoaderManager.initLoader(++loaderId, null, myLoaderCallbacks);//todo check .forceLoad();
    }
}

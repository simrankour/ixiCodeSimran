package NetworkingLib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.simran.ixicode.MyApplication;
import com.simran.ixicode.R;
import com.simran.ixicode.utility.AppConstant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import NetworkingLib.models.BaseModel;
import NetworkingLib.models.HTTPRequest;
import NetworkingLib.models.HTTPResponse;

/**
 * Created by Simranjit Kour on 8/4/17.
 */

public class HttpUtility {
    private static final String TAG = "HttpUtility";

    private static final int CONNECTION_TIME_OUT = 10 * 1000; //in Milliseconds
    private static final int SOCKET_TIME_OUT = 15 * 1000;

    /**
     * Creates and returns the {@link DefaultHttpClient}. We are setting Connection Timeout and Socket Time out here.
     */
    public static DefaultHttpClient getDefaultHttpClient() {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIME_OUT);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

        return httpClient;
    }

    public static void setCommonHeaders(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader(HttpConstants.HTTP_HEADER_KEY_CONTENT_TYPE, HttpConstants.HTTP_HEADER_VALUE_APPLICATION_JSON);
        httpRequestBase.setHeader(HttpConstants.HTTP_HEADER_KEY_USER_AGENT, System.getProperty(HttpConstants.HTTP_HEADER_VALUE_HTTP_AGENT));
    }

    public static void setGlobalHeaders(DefaultHttpClient httpClient) {
        httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
                System.getProperty("http.agent"));
    }

    private static void setErrorMessage(HTTPResponse httpResponse) {
        httpResponse.setError(true);
        httpResponse.setErrorMessage(HttpConstants.HTTP_DEFAULT_ERROR_MESSAGE);
    }

    public static String encodeURL(String url) {
        return url.trim().replaceAll(" ", "%20");
    }

    public static String readStream(InputStream inputStream) throws IOException {
        //InputStream in = new FileInputStream(new File("C:/temp/test.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }

    public static boolean isNetworkAvailable(Context context) {
        //It doesn't throw any exception here. We are just apply the try catch to be a safe side in case of application context.
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING) {
                return true;
            } else
                return connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING;
        } catch (Exception e) {
            return false;
        }
    }

    public static HTTPResponse doGet(Context context, HTTPRequest httpRequest) {
        HTTPResponse mResponse = new HTTPResponse(httpRequest.getRequestID());
        mResponse.setObject(httpRequest.getRequestObject());


        if (!isNetworkAvailable(context)) {
            String error = context.getResources().getString(R.string.message_network_not_available);
            MyApplication.getCustomToast(context, error).show();
            mResponse.setErrorMessage(error);
            return mResponse;
        }

        try {
            String url = encodeURL(httpRequest.getRequestURL());
            HttpGet httpGet = new HttpGet(url);
            if (AppConstant.DEBUG)
                printRequest("get", url, "");


            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIME_OUT);
            HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIME_OUT);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            setGlobalHeaders(httpClient);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();

                if (entity != null) {
                    InputStream in = entity.getContent();
                    String result = readStream(in);
                    Log.i("simran","simran ====="+result);
                    Log.i("simran","hello"+getBaseModel(result, httpRequest));
                    mResponse.setResponseString(result);
                    mResponse.setBaseModel(getBaseModel(result, httpRequest));
                } else {
                    mResponse.setErrorMessage("Error : Entity is null");
                }
            } else {
                mResponse.setErrorMessage("Error : Response status code : " + statusLine.getStatusCode());
            }
            if (AppConstant.DEBUG)
                printResponse("get", url, "" + mResponse.getHttpStatusCode(), mResponse.getResponseString());

        } catch (Exception e) {
            mResponse.setErrorMessage("Error : " + e.toString());
            if (AppConstant.DEBUG)
                printError(e);
        }

        return mResponse;
    }

    public static BaseModel getBaseModel(String resultJson, HTTPRequest httpRequest) {
        BaseModel baseModel = httpRequest.getBaseModel();
        baseModel.fromJSON(resultJson);
        return baseModel;
    }

    public static HTTPResponse doPost(Context context, HTTPRequest httpRequest) {
        HTTPResponse mResponse = new HTTPResponse(httpRequest.getRequestID());
        mResponse.setObject(httpRequest.getRequestObject());

        if (!isNetworkAvailable(context)) {
            String error = context.getResources().getString(R.string.message_network_not_available);
            try {
                MyApplication.getCustomToast(context, error).show();
            } catch (Exception e) {
            }
            mResponse.setErrorMessage(error);
            return mResponse;
        }

        try {
            String url = encodeURL(httpRequest.getRequestURL());
            HttpPost httpPost = new HttpPost(url);

            if (httpRequest.getRequestNameValuePairList() != null) {
                setHttpPostNameValuePairEntity(context, httpRequest, httpPost);
            } else if (httpRequest.getRequestJson() != null) {
                setHttpPostJsonStringEntity(context, httpRequest, httpPost);
            }
            if (AppConstant.DEBUG)
                printRequest("post", url, "" + httpRequest.getRequestNameValuePairList() + "\t" + httpRequest.getRequestJson());

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIME_OUT);
            HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIME_OUT);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            setGlobalHeaders(httpClient);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();

                if (entity != null) {
                    InputStream in = entity.getContent();
                    String result = readStream(in);
                    mResponse.setResponseString(result);
                    mResponse.setBaseModel(getBaseModel(result, httpRequest));
                } else {
                    mResponse.setErrorMessage("Error : Entity is null");
                }
            } else {
                mResponse.setErrorMessage("Error : Response status code : " + statusLine.getStatusCode());
            }

            if (AppConstant.DEBUG)
                printResponse("post", url, "" + mResponse.getHttpStatusCode(), mResponse.getResponseString());

        } catch (Exception e) {
            mResponse.setErrorMessage("Error : " + e.toString());
            if (AppConstant.DEBUG)
                printError(e);
        }

        return mResponse;
    }

    private static void setHttpPostJsonStringEntity(Context context, HTTPRequest httpRequest, HttpPost httpPost) throws UnsupportedEncodingException {
        httpPost.setEntity(new StringEntity(httpRequest.getRequestJson().toString(), HTTP.UTF_8));
    }

    private static void setHttpPostNameValuePairEntity(Context context, HTTPRequest httpRequest, HttpPost httpPost) throws UnsupportedEncodingException {
        httpPost.setEntity(new UrlEncodedFormEntity(httpRequest.getRequestNameValuePairList()));
    }


    public static void printRequest(String type, String url, String request) {
        Log.i(TAG, "\nRequest Api | type : " + type + "\n url : " + url + "\n header : " + "\n request : " + request);
    }

    public static void printResponse(String type, String url, String statusCode, String response) {
        Log.i(TAG, "\nResponse Api | type : " + type + "\n url : " + url + "\n status code : " + statusCode + "\n response : " + response);
    }

    public static void printError(Exception e) {
        e.printStackTrace();
        Log.i(TAG, "Response Error message : " + e.getMessage());
    }
}

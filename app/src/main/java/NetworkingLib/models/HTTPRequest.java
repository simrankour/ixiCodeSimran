package NetworkingLib.models;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class HTTPRequest {

    private String requestURL;
    private RequestType requestType; //Get, Post
    private JSONObject requestJson;
    private ArrayList<NameValuePair> requestNameValuePairList;
    private boolean showProgressDialog = true;
    private Object requestObject;
    private BaseModel baseModel = null;
    private int requestID = -1;


    public HTTPRequest() {
    }

    public HTTPRequest(int requestID) {
        setRequestID(requestID);
    }

    public HTTPRequest(int requestID, JSONObject jsonRequest) {
        setRequestID(requestID);
        setRequestJson(jsonRequest);
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public JSONObject getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(JSONObject requestJson) {
        this.requestJson = requestJson;
    }

    public Object getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
    }

    public boolean isShowProgressDialog() {
        return showProgressDialog;
    }

    public void setShowProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
    }

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public ArrayList<NameValuePair> getRequestNameValuePairList() {
        return requestNameValuePairList;
    }

    public void setRequestNameValuePairList(ArrayList<NameValuePair> requestNameValuePairList) {
        this.requestNameValuePairList = requestNameValuePairList;
    }

    public enum RequestType {GET, POST}
}


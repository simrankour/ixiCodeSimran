package NetworkingLib.models;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class HTTPResponse {
    private boolean isError;
    private String responseString = "";
    private String errorMessage = "";
    private int httpStatusCode;
    private BaseModel baseModel;
    private Object object;
    private int responseID;

    public HTTPResponse() {

    }

    public HTTPResponse(int responseID) {
        setResponseID(responseID);
    }

    public HTTPResponse(int responseID, String response) {
        setResponseID(responseID);
        setResponseString(response);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    /**
     * Returns the id of the loader which downloaded this data.
     *
     * @return
     */
    public int getResponseID() {
        return responseID;
    }

    /**
     * Sets the Id of the loader which downloaded this data. You don't have to set it yourself, the loader which downloaded this data will set it internally
     *
     * @param responseID
     */

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

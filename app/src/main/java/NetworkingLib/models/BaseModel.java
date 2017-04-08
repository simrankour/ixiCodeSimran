package NetworkingLib.models;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Simranjit Kour on 8/4/17.
 */

public interface BaseModel {
    /**
     * This method is useful to request with api.
     * It return the JSONObject which is forward to api for the POST request.
     *
     * @return JSONObject
     */

    JSONObject toJSON();

    /**
     * This method is useful to request with api.
     * It return the ArrayList<NameValuePair> which is forward to api for the POST request.
     *
     * @return ArrayList<NameValuePair>
     */

    ArrayList<NameValuePair> toNameValuePairList();

    /**
     * This method is useful after getting the response from the server.
     * It is working for the GET and POST both.
     *
     * @param json It take the jsonString and assign to each variable of this class.
     */

    void fromJSON(String json);
}

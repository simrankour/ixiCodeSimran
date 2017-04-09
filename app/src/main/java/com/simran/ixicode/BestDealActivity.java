package com.simran.ixicode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.simran.ixicode.models.BestResult;

public class BestDealActivity extends AppCompatActivity {

    private TextView originName;
    private TextView destinationName;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Car Available = "+data.isDirectCar());
                    originName.setText("Origin= "+data.getOriginName());
                    destinationName.setText("Destination= "+data.getDestinationName());
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("Flight Available = "+data.isDirectFlight());
                    originName.setText("Origin= "+data.getOriginName());
                    destinationName.setText("Destination= "+data.getDestinationName());
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("Public Transport Available = "+data.isDirectBus());
                    originName.setText("Origin= "+data.getOriginName());
                    destinationName.setText("Destination= "+data.getDestinationName());
                    return true;
            }
            return false;
        }

    };
    private Gson gson;
    private RequestQueue requestQueue;
    private String originCityId;
    private String destinationCityId;
    private BestResult data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_deal);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
            } else {
                originCityId = extras.getString("STRING_ORIGIN");
                destinationCityId = extras.getString("STRING_DESTINATION");
            }
        } else {
            originCityId = (String) savedInstanceState.getSerializable("STRING_ORIGIN");
            destinationCityId = (String) savedInstanceState.getSerializable("STRING_DESTINATION");
        }

        Log.i("Simran request", "hello");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        fetchBestResult();

        mTextMessage = (TextView) findViewById(R.id.message);
        originName = (TextView) findViewById(R.id.originName);
        destinationName = (TextView) findViewById(R.id.destinationName);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void fetchBestResult() {
        Log.i("Simran request", "hello2");
        String ENDPOINT = "http://build2.ixigo.com/api/v2/a2b/modes?apiKey=ixicode!2$&originCityId=" + originCityId + "&destinationCityId=" + destinationCityId;
        Log.i("Simran request", ENDPOINT);
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoadedBestResult, onPostsErrorBestResult);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoadedBestResult = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JsonElement jelement = new JsonParser().parse(response);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonObject jsonObject = jobject.getAsJsonObject("data");

            Log.i("Simran PostActivity", "" + jsonObject);
            data = gson.fromJson(jsonObject.toString(), BestResult.class);
            Log.i("Simran data", "" + data.toString());
            Log.i("Simran best result", "" + data.getOriginName());

            mTextMessage.setText("Car Available = "+data.isDirectCar());
            originName.setText("Origin= "+data.getOriginName());
            destinationName.setText("Destination= "+data.getDestinationName());
        }
    };

    private final Response.ErrorListener onPostsErrorBestResult = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Simran PostActivity", error.toString());
        }
    };


}

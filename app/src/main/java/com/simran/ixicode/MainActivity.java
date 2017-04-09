package com.simran.ixicode;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.simran.ixicode.adapter.CityAutoCompleteAdapter;
import com.simran.ixicode.customview.DelayAutoCompleteTextView;
import com.simran.ixicode.models.BestResult;
import com.simran.ixicode.models.City;
import com.simran.ixicode.models.CityDetails;
import com.simran.ixicode.models.Place;
import com.simran.ixicode.utility.AppConstant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "PostsActivity";
    private static final int THRESHOLD = 2;
    private List<CityDetails> posts;

    //    private static final String ENDPOINT = "https://kylewbanks.com/rest/posts.json";
    private String ENDPOINT = "http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=indi";
    private RequestQueue requestQueue;
    private Gson gson;

    private DelayAutoCompleteTextView sourceCity;
    private DelayAutoCompleteTextView destCity;
    private String cityId = "503b2a98e4b032e338f14f01";
    private String searchCriteria = "Places To Visit";
    private LinearLayout searchLayout;
    private LinearLayout compareLayout;
    private String originCityId;
    private String destinationCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        compareLayout = (LinearLayout) findViewById(R.id.compareLayout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching your location...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                gson = gsonBuilder.create();

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                fetchCurrentCity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sourceCity = (DelayAutoCompleteTextView) findViewById(R.id.et_city_title);
        sourceCity.setThreshold(THRESHOLD);
        sourceCity.setAdapter(new CityAutoCompleteAdapter(this)); // 'this' is Activity instance
        sourceCity.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        sourceCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                City city = (City) adapterView.getItemAtPosition(position);
                sourceCity.setText(city.getText());
                originCityId = city.getXid();
            }
        });


        destCity = (DelayAutoCompleteTextView) findViewById(R.id.et_city_title1);
        destCity.setThreshold(THRESHOLD);
        destCity.setAdapter(new CityAutoCompleteAdapter(this)); // 'this' is Activity instance
        destCity.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator1));
        destCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                City city = (City) adapterView.getItemAtPosition(position);
                destCity.setText(city.getText());
                cityId = city.getId();
                destinationCityId = city.getXid();
            }
        });
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.check(R.id.radio_placetovisit);

        Button buttonSearch = (Button) findViewById(R.id.btnSearch);

    }

    public void onSearchButtonClick(View view) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        fetchPosts();


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_placetovisit:
                if (checked)
                    searchCriteria = "Places To Visit";
                break;
            case R.id.radio_thingstodo:
                if (checked)
                    searchCriteria = "Things To Do";
                break;
            case R.id.radio_hotel:
                if (checked)
                    searchCriteria = "Hotel";
                break;
        }
    }

    private void fetchPosts() {
        String type = searchCriteria.replaceAll(" ", "+");
        ENDPOINT = "http://build2.ixigo.com/api/v3/namedentities/city/" + cityId + "/categories?apiKey=ixicode!2$&type=" + type + "&limit=5";
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("Simran PostActivity", response);
            JSONObject json = null;
            try {
                json = new JSONObject(response);
                JSONObject json_LL = json.getJSONObject("data");
                String str_value = json_LL.getString(searchCriteria);
                Log.i("Simran", "" + str_value);

                List<Place> posts = Arrays.asList(gson.fromJson(str_value, Place[].class));

                Log.i("Simran PostActivity", posts.size() + " posts loaded.");
                for (Place post : posts) {
                    Log.i("Simran PostActivity", post.getName() + ": " + post.getId());
                }
                Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                intent.putExtra(AppConstant.PLACE_LIST, (Serializable) posts);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Simran PostActivity", error.toString());
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            compareLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_gallery) {
            compareLayout.setVisibility(View.VISIBLE);
            searchLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void handlePostsList(List<CityDetails> posts) {
        this.posts = posts;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (CityDetails post : MainActivity.this.posts) {
                    Toast.makeText(MainActivity.this, post.title, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void failedLoadingPosts() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class PostFetcher extends AsyncTask<Void, Void, String> {
        private static final String TAG = "PostFetcher";
        //        public static final String SERVER_URL = "http://kylewbanks.com/rest/posts.json";
        public static final String SERVER_URL = "http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=de";

        @Override
        protected String doInBackground(Void... params) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(SERVER_URL);

                //Perform the request and check the status code
                HttpResponse response = client.execute(post);
                Log.e("simran", "simran=== " + response.getEntity().getContent());
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        //Read the server response and attempt to parse it as JSON
                        Reader reader = new InputStreamReader(content);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        List<CityDetails> posts = new ArrayList<CityDetails>();
                        posts = Arrays.asList(gson.fromJson(reader, CityDetails[].class));
                        content.close();

                        handlePostsList(posts);
                    } catch (Exception ex) {
                        Log.e(TAG, "Failed to parse JSON due to: " + ex);
                        failedLoadingPosts();
                    }
                } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }


//                HTTPRequest httpRequest = new HTTPRequest();
////                httpRequest.setBaseModel((BaseModel) new CityDetails());
//                httpRequest.setRequestURL("http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=de");
//                HttpUtility.doGet(getApplicationContext(),httpRequest);


            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }
            return null;
        }
    }


    private void fetchCurrentCity() {
        ENDPOINT = "http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=gurgaon";
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoadedCC, onPostsErrorCC);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoadedCC = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("Simran PostActivity", response);
            List<City> posts = Arrays.asList(gson.fromJson(response, City[].class));
            sourceCity.setText(posts.get(0).getText());
            originCityId = posts.get(0).getXid();
        }
    };

    private final Response.ErrorListener onPostsErrorCC = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Simran PostActivity", error.toString());
        }
    };

    public void onbtnBestDealClick(View view) {
//        Log.i("Simran request", "hello");
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//        gson = gsonBuilder.create();
//
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        fetchBestResult();

        Intent intent = new Intent(getApplicationContext(), BestDealActivity.class);
        intent.putExtra("STRING_ORIGIN", originCityId);
        intent.putExtra("STRING_DESTINATION", destinationCityId);
        startActivity(intent);
    }

    private void fetchBestResult() {
        Log.i("Simran request", "hello2");
        ENDPOINT = "http://build2.ixigo.com/api/v2/a2b/modes?apiKey=ixicode!2$&originCityId=" + originCityId + "&destinationCityId=" + destinationCityId;
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
            BestResult data = gson.fromJson(jsonObject.toString(), BestResult.class);
            Log.i("Simran data", "" + data.toString());
            Log.i("Simran best result", "" + data.getOriginName());

            Intent intent = new Intent(getApplicationContext(), BestDealActivity.class);
//            intent.putExtra(AppConstant.BEST_DEAL, data);
            startActivity(intent);

        }
    };

    private final Response.ErrorListener onPostsErrorBestResult = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Simran PostActivity", error.toString());
        }
    };


}

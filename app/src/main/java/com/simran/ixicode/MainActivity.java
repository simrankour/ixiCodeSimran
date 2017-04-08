package com.simran.ixicode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import NetworkingLib.HttpUtility;
import NetworkingLib.models.HTTPRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "PostsActivity";
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PostFetcher fetcher = new PostFetcher();
        fetcher.execute();

//        HTTPRequest httpRequest = new HTTPRequest();
//        httpRequest.setRequestURL("http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=de");
//        HttpUtility.doGet(getApplicationContext(),httpRequest);
    }

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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void handlePostsList(List<Post> posts) {
        this.posts = posts;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Post post : MainActivity.this.posts) {
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
        public static final String SERVER_URL = "http://kylewbanks.com/rest/posts.json";

        @Override
        protected String doInBackground(Void... params) {
            try {
//                //Create an HTTP client
//                HttpClient client = new DefaultHttpClient();
//                HttpPost post = new HttpPost(SERVER_URL);
//
//                //Perform the request and check the status code
//                HttpResponse response = client.execute(post);
//                StatusLine statusLine = response.getStatusLine();
//                if(statusLine.getStatusCode() == 200) {
//                    HttpEntity entity = response.getEntity();
//                    InputStream content = entity.getContent();
//
//                    try {
//                        //Read the server response and attempt to parse it as JSON
//                        Reader reader = new InputStreamReader(content);
//
//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                        Gson gson = gsonBuilder.create();
//                        List<Post> posts = new ArrayList<Post>();
//                        posts = Arrays.asList(gson.fromJson(reader, Post[].class));
//                        content.close();
//
//                        handlePostsList(posts);
//                    } catch (Exception ex) {
//                        Log.e(TAG, "Failed to parse JSON due to: " + ex);
//                        failedLoadingPosts();
//                    }
//                } else {
//                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
//                    failedLoadingPosts();
//                }
                HTTPRequest httpRequest = new HTTPRequest();
                httpRequest.setRequestURL("http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=de");
                HttpUtility.doGet(getApplicationContext(),httpRequest);
            } catch(Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                failedLoadingPosts();
            }
            return null;
        }
    }
}

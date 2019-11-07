package com.example.fetchwithretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements PostAdapter.OnPostListener{


    private Button button;
    private RecyclerView recyclerView;
    private List<Post> postList;
    private PostAdapter adapter;
    private ProgressBar progressBar;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private final String SAMPLE_URL = "http://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        recyclerView = findViewById(R.id.post_recycler);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button_fetch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFetch();
            }
        });

        Gson gson = new GsonBuilder().serializeNulls().create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        prepareData();
//        showData();
    }

    private void clickFetch() {
        getData();
        showData();
        Toast.makeText(MainActivity.this, "Returned " + postList.size() + " Posts", Toast.LENGTH_SHORT).show();
    }

    private boolean isConnected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }

    private void getData(){

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(SAMPLE_URL);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Post> posts = response.body();

                if (posts == null)
                    posts = new ArrayList<>();
                postList = posts;
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareData(){
//        if (postList == null)
            postList = new ArrayList<>();
//        postList.clear();
    }

    private void showData(){
        adapter = new PostAdapter(postList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onPostClick(int position) {
        Toast.makeText(MainActivity.this, postList.get(position).getBody(), Toast.LENGTH_SHORT).show();
    }

    public class Fetch extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            getData();
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            showData();
            Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();

        }
    }


}

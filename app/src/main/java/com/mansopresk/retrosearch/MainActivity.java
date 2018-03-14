package com.mansopresk.retrosearch;

import android.app.Activity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.mansopresk.retrosearch.model.DocumentCategories;
import com.mansopresk.retrosearch.model.JsonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity
{
    public static final String BASE_URL = "https://s3.ap-south-1.amazonaws.com";
    private RecyclerView mRecyclerView;
    private ArrayList<DocumentCategories> mArrayList;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL).build();

            RequestInterface requestInterface = restAdapter.create(RequestInterface.class);

            Call<List<DocumentCategories>> myCall = requestInterface.getJSON();

        myCall.enqueue(new Callback<List<DocumentCategories>>() {
                @Override
                public void success(List<DocumentCategories> documentCategories, Response response)
                {
                    DataAdapter docAdapter = new DataAdapter(documentCategories, MainActivity.class);
                     mRecyclerView.setAdapter(docAdapter);

                    LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                    mRecyclerView.setLayoutManager(llm);
                    mRecyclerView.setHasFixedSize(true);
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error)
                {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }

        });

//        requestInterface .getJSON(new Callback<JsonResponse>()
//        {
//            @Override
//            public void success(JsonResponse api, Response response)
//            {
//                DataAdapter docAdapter = new DataAdapter(api.getDocuments(), MainActivity.this);
//                mRecyclerView.setAdapter(docAdapter);
//
//                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
//                mRecyclerView.setLayoutManager(llm);
//                mRecyclerView.setHasFixedSize(true);
//                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void failure(RetrofitError error)
//            {
//                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
//            }
//        });


//        initViews();
//        loadJSON();
    }

//    private void initViews(){
//        mRecyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
//    }
//    private void loadJSON(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RequestInterface request = retrofit.create(RequestInterface.class);
//        Call<JsonResponse> call = request.getJSON();
//        call.enqueue(new Callback<JsonResponse>() {
//            private Call<JsonResponse> call;
//            private Throwable t;
//
//            @Override
//            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
//
//                JsonResponse jsonResponse = response.body();
//                mArrayList = new ArrayList<DocumentCategories>(Arrays.asList(jsonResponse.getDocuments()));
//                mAdapter = new DataAdapter(mArrayList);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<JsonResponse> call, Throwable t) {
//                this.call = call;
//                this.t = t;
//                Log.d("Error",t.getMessage());
//            }
//        });
  //  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}

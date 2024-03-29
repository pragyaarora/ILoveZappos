package com.example.pragya.ilovezappos;

import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    List<ZapposItem> list;
    ZapposItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        list = new ArrayList<>();
        adapter = new ZapposItemAdapter(list, SearchActivity.this, findViewById(R.id.fab));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpTopx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
        findViewById(R.id.empty_view).setVisibility(View.VISIBLE);

    }

    private int dpTopx(int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        addActionsToSearchMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    private void addActionsToSearchMenuItem(MenuItem item) {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("pragya", "OnQuery TextSubmit : " + query);
                fetchSearchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("pragya", "OnQuery Textchange : " + newText);
                return false;
            }
        });

    }

    private void fetchSearchData(String query) {

        Retrofit client = new Retrofit.Builder().baseUrl("https://api.zappos.com").addConverterFactory(GsonConverterFactory.create()).build();

        ZapposAPIClass apiObject = client.create(ZapposAPIClass.class);

        Call<ZapposItemList> call = apiObject.loadItems(query);

        findViewById(R.id.empty_view).setVisibility(View.GONE);

        findViewById(R.id.progress).setVisibility(View.VISIBLE);

        findViewById(R.id.recycler_view).setVisibility(View.GONE);

        call.enqueue(new Callback<ZapposItemList>() {
            @Override
            public void onResponse(Call<ZapposItemList> call, Response<ZapposItemList> response) {

                ZapposItemList items = response.body();
                // TODO Populate the recyclerview here
                initRecyclerView(items);
            }

            @Override
            public void onFailure(Call<ZapposItemList> call, Throwable t) {

            }
        });


    }

    private void initRecyclerView(final ZapposItemList items) {
        Log.d("pragya", "items : " + items.results.size());
        this.list = items.results;
        adapter.setList(list);
        findViewById(R.id.progress).setVisibility(View.GONE);

        if (list.isEmpty()) {
            findViewById(R.id.recycler_view).setVisibility(View.GONE);
            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_view).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("pragya", "Ondestroy called");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("pragya", "Onstop called");
        super.onStop();
    }

}

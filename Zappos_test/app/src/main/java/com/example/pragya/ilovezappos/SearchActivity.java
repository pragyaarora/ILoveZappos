package com.example.pragya.ilovezappos;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        setContentView(R.layout.activity_search);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        list = new ArrayList<>();
        adapter = new ZapposItemAdapter(list, SearchActivity.this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpTopx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // RecyclerView.LayoutManager manager = new GridLayoutManager(SearchActivity.this, 2);

        recyclerView.setAdapter(adapter);
        //tItemAnimator());
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

    }


}

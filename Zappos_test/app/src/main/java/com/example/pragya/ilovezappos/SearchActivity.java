package com.example.pragya.ilovezappos;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
                Log.d("pragya", items.currentResultCount + "");

            }

            @Override
            public void onFailure(Call<ZapposItemList> call, Throwable t) {

            }
        });


    }
}

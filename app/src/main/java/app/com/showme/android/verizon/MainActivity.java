package app.com.showme.android.verizon;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import app.com.showme.android.verizon.models.Flickr;
import app.com.showme.android.verizon.models.Photo;
import app.com.showme.android.verizon.presenters.RecyclerViewAdapter;
import app.com.showme.android.verizon.presenters.RetrofitFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recyclerview)
    RecyclerView womp;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewAdapter mRecyclerViewAdapter;
    List<Photo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mData = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        womp.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(mData);
        womp.setAdapter(mRecyclerViewAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mData.clear();
                getFlickr(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void getFlickr(String query) {
        RetrofitFactory.getInstance()
                .getFlickr(getString(R.string.api_key),
                        getString(R.string.method),
                        getString(R.string.format), "1", query, "1")
                .enqueue(new Callback<Flickr>() {
                    @Override
                    public void onResponse(Call<Flickr> call, Response<Flickr> response) {
                        mData.addAll(response.body().getPhotos().getPhoto());
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Flickr> call, Throwable t) {
                        Log.d("hey", t + "");
                    }
                });
    }
}



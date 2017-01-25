package app.com.showme.android.verizon;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import app.com.showme.android.verizon.models.photo_search.Flickr;
import app.com.showme.android.verizon.presenters.PaginationAdapter;
import app.com.showme.android.verizon.presenters.PaginationScrollListener;
import app.com.showme.android.verizon.presenters.RetrofitFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // TODO: 1/24/17 clean
    // TODO: 1/24/17 internet check?
    // TODO: 1/24/17 change colors to Verizon red

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_progressbar)
    ProgressBar mProgressBar;
    LinearLayoutManager mLinearLayoutManager;
    PaginationAdapter mPaginationAdapter;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 100;
    private int currentPage = PAGE_START;

    String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mQuery = "";
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPaginationAdapter = new PaginationAdapter();
        mRecyclerView.setAdapter(mPaginationAdapter);
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        mProgressBar.setVisibility(View.GONE);
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        getFlickr(mQuery);
        mPaginationAdapter.removeLoadingFooter();
        isLoading = false;
        if (currentPage != TOTAL_PAGES) mPaginationAdapter.addLoadingFooter();
        else isLastPage = true;
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
                mPaginationAdapter.clear();
                mQuery = query;
                currentPage = 1;
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
                        getString(R.string.format), "1", query, String.valueOf(currentPage))
                .enqueue(new Callback<Flickr>() {
                    @Override
                    public void onResponse(Call<Flickr> call, Response<Flickr> response) {
                        mPaginationAdapter.addAll(response.body().getPhotos().getPhoto());
                    }

                    @Override
                    public void onFailure(Call<Flickr> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
    }
}



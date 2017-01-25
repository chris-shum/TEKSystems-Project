package app.com.showme.android.verizon;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import java.util.List;

import app.com.showme.android.verizon.models.Movie;
import app.com.showme.android.verizon.models.photo_search.Flickr;
import app.com.showme.android.verizon.models.photo_search.Photo;
import app.com.showme.android.verizon.presenters.PaginationAdapter;
import app.com.showme.android.verizon.presenters.PaginationAdapter2;
import app.com.showme.android.verizon.presenters.PaginationScrollListener;
import app.com.showme.android.verizon.presenters.RetrofitFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // TODO: 1/24/17 http://blog.grafixartist.com/android-pagination-tutorial-getting-started-recyclerview/
    // TODO: 1/24/17 clean
    // TODO: 1/24/17 internet check?
    // TODO: 1/24/17 change colors to Verizon red

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_recyclerview)
    RecyclerView rv;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;

    String mQuery;

    PaginationAdapter2 adapter2;
    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mQuery = "";

        progressBar = (ProgressBar) findViewById(R.id.main_progress);

        adapter = new PaginationAdapter();
        adapter2 = new PaginationAdapter2(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
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


        // mocking network delay for API call
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        loadFirstPage();
//            }
//        }, 1000);

    }


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
//        List<Movie> movies = Movie.createMovies(adapter2.getItemCount());
//        List<Photo> movies = Movie.createMovies(adapter2.getItemCount());
        progressBar.setVisibility(View.GONE);
//        adapter.addAll(movies);

        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Toast.makeText(this, "Loading next Page", Toast.LENGTH_SHORT).show();
//        List<Photo> movies = Movie.createMovies(adapter.getItemCount());
        getFlickr("hey");
        adapter.removeLoadingFooter();
        isLoading = false;

//        adapter.addAll(movies);

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
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
                adapter.clear();
                currentPage = 1;
                mQuery = query;
                getFlickr(query);
                return true;
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
                        adapter.addAll(response.body().getPhotos().getPhoto());
                        List<Photo> movies = Movie.createMovies(adapter.getItemCount());
//                        adapter.addAll(movies);
                        int temp = response.body().getPhotos().getPages();
//                        TOTAL_PAGES = temp;
                        Log.d("hey", mQuery);
                        Log.d("hey", "" + currentPage);
                        Log.d("hey", "" + temp);
                        Toast.makeText(MainActivity.this, "yes " + adapter.getItemCount(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Flickr> call, Throwable t) {
                        Log.d("hey", t + "");
                    }
                });
    }

}



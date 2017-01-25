package app.com.showme.android.verizon.presenters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.com.showme.android.verizon.DetailsActivity;
import app.com.showme.android.verizon.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ShowMe on 1/25/17.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<app.com.showme.android.verizon.models.photo_search.Photo> movies;

    private boolean isLoadingAdded = false;

    public PaginationAdapter() {
        movies = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.flickr_card, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final app.com.showme.android.verizon.models.photo_search.Photo movie = movies.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH movieVH = (MovieVH) holder;

                int farm = movie.getFarm();
                String server = movie.getServer();
                String id = movie.getId();
                String secret = movie.getSecret();
                movieVH.wooooop.setText(position+"");
//                movieVH.textView.setText(movie.getTitle());
                Picasso.with(movieVH.textView.getContext()).load("https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg").into(movieVH.textView);
                movieVH.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("wah", (Serializable) movies.get(position));
                        intent.putExtras(bundle);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) view.getContext(), view, "waaaaaah");
                        view.getContext().startActivity(intent, options.toBundle());
                    }
                });

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(app.com.showme.android.verizon.models.photo_search.Photo mc) {
        movies.add(mc);
        notifyItemInserted(movies.size() - 1);
    }

    public void addAll(List<app.com.showme.android.verizon.models.photo_search.Photo> mcList) {
        for (app.com.showme.android.verizon.models.photo_search.Photo mc : mcList) {
            add(mc);
        }
    }

    public void remove(app.com.showme.android.verizon.models.photo_search.Photo city) {
        int position = movies.indexOf(city);
        if (position > -1) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new app.com.showme.android.verizon.models.photo_search.Photo());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movies.size() - 1;
        app.com.showme.android.verizon.models.photo_search.Photo item = getItem(position);

        if (item != null) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public app.com.showme.android.verizon.models.photo_search.Photo getItem(int position) {
        return movies.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        private ImageView textView;
        @BindView(R.id.argggggggh)
        TextView wooooop;

        public MovieVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textView = (ImageView) itemView.findViewById(R.id.uuuuuuugh);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
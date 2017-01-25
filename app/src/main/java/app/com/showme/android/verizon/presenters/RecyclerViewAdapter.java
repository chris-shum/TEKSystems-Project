package app.com.showme.android.verizon.presenters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import app.com.showme.android.verizon.DetailsActivity;
import app.com.showme.android.verizon.R;
import app.com.showme.android.verizon.models.Photo;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ShowMe on 10/28/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Photo> photoList;

    public RecyclerViewAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_card, parent, false);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int farm = photoList.get(position).getFarm();
        String server = photoList.get(position).getServer();
        String id = photoList.get(position).getId();
        String secret = photoList.get(position).getSecret();
        Picasso.with(holder.bwah.getContext()).load("https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg").into(holder.bwah);
        holder.bwah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wah", (Serializable) photoList.get(position));
                intent.putExtras(bundle);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) view.getContext(), view, "waaaaaah");
                view.getContext().startActivity(intent, options.toBundle());
            }
        });
        Log.d("heya", "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg");
    }

    @Override
    public int getItemCount() {
        if (photoList == null) {
            return 0;
        } else {
            return photoList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.uuuuuuugh)
        ImageView bwah;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
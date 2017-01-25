package app.com.showme.android.verizon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.com.showme.android.verizon.models.photo_search.Photo;
import app.com.showme.android.verizon.models.photo_info.FlickrInfo;
import app.com.showme.android.verizon.presenters.RetrofitFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    // TODO: 1/24/17 format details page with textview
    // TODO: 1/24/17 figure out how to make the mini pop up

    @BindView(R.id.waaaahimage)
    ImageView boop;
    @BindView(R.id.hmmmmmmtextview)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Photo photo = (Photo) bundle.getSerializable("wah");
        int farm = photo.getFarm();
        String server = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();
        Picasso.with(this).load("https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg").into(boop);
        getPhotoInfo(photo.getId());
        text.setText(photo.getTitle());
    }

    public void getPhotoInfo(String userID) {
        RetrofitFactory.getInstance()
                .getFlickrInfo(getString(R.string.api_key),
                        getString(R.string.method2),
                        getString(R.string.format), "1", userID)
                .enqueue(new Callback<FlickrInfo>() {
                    @Override
                    public void onResponse(Call<FlickrInfo> call, Response<FlickrInfo> response) {
//                        response.body().getPhoto().getOwner().getUsername();
//                        response.body().getPhoto().getDates().getPosted();
//                        response.body().getPhoto().getDescription();

                    }

                    @Override
                    public void onFailure(Call<FlickrInfo> call, Throwable t) {
                        Log.d("hey", t + "");
                    }
                });
    }
}

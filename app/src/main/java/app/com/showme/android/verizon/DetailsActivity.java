package app.com.showme.android.verizon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import app.com.showme.android.verizon.models.photo_info.FlickrInfo;
import app.com.showme.android.verizon.models.photo_search.Photo;
import app.com.showme.android.verizon.presenters.ImageGetter;
import app.com.showme.android.verizon.presenters.RetrofitFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    @BindView(R.id.details_imageview)
    ImageView mImageView;
    @BindView(R.id.details_author)
    TextView mAuthorTextView;
    @BindView(R.id.details_date)
    TextView mDateTextView;
    @BindView(R.id.details_cardView)
    CardView mDetailsCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Photo photo = (Photo) bundle.getSerializable("Photo");

        getPhotoInfo(photo.getId());

        Picasso.with(this).load(ImageGetter.getImage(photo)).into(mImageView,
                PicassoPalette.with(ImageGetter.getImage(photo), mImageView)
                        .use(PicassoPalette.Profile.MUTED).intoBackground(mDetailsCardView)
                        .use(PicassoPalette.Profile.VIBRANT_LIGHT).intoTextColor(mAuthorTextView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                        .use(PicassoPalette.Profile.VIBRANT_LIGHT).intoTextColor(mDateTextView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
        );
    }

    public void getPhotoInfo(String photoID) {
        RetrofitFactory.getInstance()
                .getFlickrInfo(getString(R.string.api_key),
                        getString(R.string.method2),
                        getString(R.string.format), "1", photoID)
                .enqueue(new Callback<FlickrInfo>() {
                    @Override
                    public void onResponse(Call<FlickrInfo> call, Response<FlickrInfo> response) {
                        mAuthorTextView.setText(response.body().getPhoto().getOwner().getUsername());

                        long unixSeconds = Long.valueOf(response.body().getPhoto().getDates().getPosted());
                        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // give a timezone reference for formating (see comment at the bottom
                        String formattedDate = sdf.format(date);

                        mDateTextView.setText(formattedDate);
                    }

                    @Override
                    public void onFailure(Call<FlickrInfo> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
    }
}

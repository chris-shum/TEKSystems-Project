package app.com.showme.android.verizon.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import app.com.showme.android.verizon.R;
import app.com.showme.android.verizon.models.Flickr;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ShowMe on 10/28/16.
 */

//all methods for calls made to the flask API are made through here
//each method updates the realm database

public class PresentFlickr {

    public static void getFlickr(final Context context, String query) {
        final Resources resources = context.getResources();
        RetrofitFactory.getInstance()
                .getFlickr(resources.getString(R.string.api_key),
                        resources.getString(R.string.method),
                        resources.getString(R.string.format), "1", query, "1")
                .enqueue(new Callback<Flickr>() {
                    @Override
                    public void onResponse(Call<Flickr> call, Response<Flickr> response) {
                    }

                    @Override
                    public void onFailure(Call<Flickr> call, Throwable t) {
                        connectionFailureToast(context);
                        Log.d("hey", t + "");
                    }
                });
    }

    public static void connectionFailureToast(Context context) {
        Toast.makeText(context, "Boop", Toast.LENGTH_SHORT).show();
    }
}


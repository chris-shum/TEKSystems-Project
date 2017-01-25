package app.com.showme.android.verizon.presenters;

import app.com.showme.android.verizon.models.RetrofitCallToFlickr;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.com.showme.android.verizon.models.RetrofitCallToFlickr.BASE_URL;

/**
 * Created by ShowMe on 11/10/16.
 */

public class RetrofitFactory {
        private static RetrofitCallToFlickr service;

        public static RetrofitCallToFlickr getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().
                        addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(RetrofitCallToFlickr.class);
                return service;
            } else {
                return service;
            }
        }
    }


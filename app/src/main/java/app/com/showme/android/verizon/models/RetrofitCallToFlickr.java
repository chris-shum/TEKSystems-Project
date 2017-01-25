package app.com.showme.android.verizon.models;

import app.com.showme.android.verizon.models.photo_info.FlickrInfo;
import app.com.showme.android.verizon.models.photo_search.Flickr;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ShowMe on 10/28/16.
 */

public interface RetrofitCallToFlickr {

    String BASE_URL = "https://api.flickr.com/services/rest/";

    @GET(".")
    Call<Flickr> getFlickr(@Query("api_key") String api_key,
                           @Query("method") String method,
                           @Query("format") String format,
                           @Query("nojsoncallback") String nojsoncallback,
                           @Query("text") String text,
                           @Query("page") String page);

    @GET(".")
    Call<FlickrInfo> getFlickrInfo(@Query("api_key") String api_key,
                                   @Query("method") String method,
                                   @Query("format") String format,
                                   @Query("nojsoncallback") String nojsoncallback,
                                   @Query("photo_id") String photoID);

}

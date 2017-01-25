package app.com.showme.android.verizon.presenters;

import app.com.showme.android.verizon.models.photo_search.Photo;

/**
 * Created by ShowMe on 1/25/17.
 */

public class ImageGetter {
    public static String getImage(Photo photo){
        int farm = photo.getFarm();
        String server = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();
        return "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
    }
}

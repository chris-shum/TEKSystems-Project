package app.com.showme.android.verizon.models;

import java.util.ArrayList;
import java.util.List;

import app.com.showme.android.verizon.models.photo_search.Photo;

/**
 * Created by ShowMe on 1/25/17.
 */

public class Movie { private String title;

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Creating 10 dummy content for list.
     *
     * @param itemCount
     * @return
     */
    public static List<Photo> createMovies(int itemCount) {
        List<Photo> movies = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Photo movie = new Photo();
            movies.add(movie);
        }
        return movies;
    }
}